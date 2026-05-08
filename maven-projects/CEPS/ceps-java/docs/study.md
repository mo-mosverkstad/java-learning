# CEPS — Java Implementation Proposal

## Concurrent Event Processing System (Java / Threads / Monitors / Executors / RMI)

---

## Project Overview

A multi-phase concurrent event processing engine built with Java concurrency primitives, progressing from raw threads to java.util.concurrent to RMI. Each phase adds one concurrency concept while preserving correctness invariants.

---

## Technology Stack

- **Language:** Java 17
- **Build:** Maven
- **Testing:** JUnit 5 + stress test harness
- **Distributed:** Java RMI (Phase 9)

---

## Software Architecture

### Logical Architecture (All Phases)

```
┌─────────────────────────────────────────────────────────────────────┐
│                        CEPS Application                             │
├─────────────────────────────────────────────────────────────────────┤
│                                                                     │
│  ┌──────────┐   ┌──────────┐   ┌──────────┐                        │
│  │Producer 1│   │Producer 2│   │Producer N│   ← Event Generation   │
│  └────┬─────┘   └────┬─────┘   └────┬─────┘     Layer              │
│       │               │               │                             │
│       └───────────────┼───────────────┘                             │
│                       ↓                                             │
│  ┌─────────────────────────────────────────┐                        │
│  │           EventQueue                    │   ← Synchronization   │
│  │  ┌───────────────────────────────────┐  │     Layer              │
│  │  │ Phase 3: synchronized             │  │                        │
│  │  │ Phase 4: wait/notify              │  │                        │
│  │  │ Phase 5: bounded + notifyAll      │  │                        │
│  │  │ Phase 6: Semaphore only           │  │                        │
│  │  │ Phase 7: ArrayBlockingQueue       │  │                        │
│  │  └───────────────────────────────────┘  │                        │
│  └──────────────────┬──────────────────────┘                        │
│                     ↓                                               │
│  ┌──────────┐   ┌──────────┐   ┌──────────┐                        │
│  │ Worker 1 │   │ Worker 2 │   │ Worker M │   ← Processing        │
│  └────┬─────┘   └────┬─────┘   └────┬─────┘     Layer              │
│       │               │               │                             │
│       └───────────────┼───────────────┘                             │
│                       ↓                                             │
│  ┌─────────────────────────────────────────┐                        │
│  │    Aggregator (synchronizedList)        │   ← Result Collection │
│  └──────────────────┬──────────────────────┘     Layer              │
│                     ↓                                               │
│  ┌─────────────────────────────────────────┐                        │
│  │      Statistics / Validator             │   ← Output Layer      │
│  └─────────────────────────────────────────┘                        │
│                                                                     │
└─────────────────────────────────────────────────────────────────────┘
```

### Component Responsibilities

| Component | Responsibility | Thread Model |
|-----------|---------------|---------------|
| Producer | Generate events with unique IDs | 1 Thread per producer |
| EventQueue | Buffer + synchronize access | Shared monitor object |
| Worker | Dequeue + CPU-bound processing | 1 Thread per worker (or ExecutorService) |
| Aggregator | Collect results thread-safely | synchronizedList or ConcurrentLinkedQueue |
| Validator | Compare output vs ground truth | Single-threaded post-run |

### Thread Interaction Diagram (Phase 4–7, Shared Memory)

```
  Producer Thread          MonitorQueue            Worker Thread
  ──────────────          ────────────            ─────────────
       │                      │                        │
       │── synchronized ─────→│                        │
       │── q.add(event) ─────→│                        │
       │── notify() ─────────→│                        │
       │── exit monitor ─────→│                        │
       │                      │←── synchronized ──────│
       │                      │    [while empty: wait()]
       │                      │←── q.poll() ──────────│
       │                      │←── exit monitor ──────│
       │                      │                        │── Processor.process(event)
       │                      │                        │── aggregator.addResult(result)
       │                      │                        │
```

### Phase 9 — RMI Deployment Architecture

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                         JVM Network (RMI)                                    │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│  ┌─────────────────┐         RMI call: submit(Event)                        │
│  │   JVM 1         │  ─────────────────────────────────┐                    │
│  │  ProducerClient │                                   │                    │
│  └─────────────────┘                                   ↓                    │
│                                          ┌──────────────────────────┐       │
│  ┌─────────────────┐                     │        JVM 2             │       │
│  │   JVM 1b        │  ──────────────────→│   DispatcherServer       │       │
│  │  ProducerClient │                     │   implements EventReceiver│      │
│  └─────────────────┘                     └────────────┬─────────────┘       │
│                                                       │                     │
│                                    RMI call: submit(Event)                  │
│                          ┌────────────────┼────────────────┐                │
│                          ↓                ↓                ↓                │
│               ┌──────────────┐ ┌──────────────┐ ┌──────────────┐           │
│               │    JVM 3     │ │    JVM 4     │ │    JVM 5     │           │
│               │ WorkerServer │ │ WorkerServer │ │ WorkerServer │           │
│               └──────┬───────┘ └──────┬───────┘ └──────┬───────┘           │
│                      │                │                │                    │
│                      └────────────────┼────────────────┘                    │
│                                       │  RMI call: collect(Result)          │
│                                       ↓                                     │
│                          ┌──────────────────────────┐                       │
│                          │        JVM 6             │                       │
│                          │   AggregatorServer       │                       │
│                          │ implements ResultCollector│                       │
│                          └──────────────────────────┘                       │
│                                                                             │
│  Registry: rmiregistry (port 1099)                                          │
│  Lookup:   Dispatcher, Workers, Aggregator registered by name               │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

## Data Model

```java
public record Event(long eventId, long timestamp, int type, int payload) {}

public record Result(long eventId, int output) {}
```

Processing function (deterministic, CPU-bound):
```java
public class Processor {
    public static Result process(Event e) {
        int result = e.payload();
        for (int i = 0; i < (e.type() + 1) * 1000; i++)
            result = (result * 31 + i) % 1000003;
        return new Result(e.eventId(), result);
    }
}
```

---

## Maven Project Structure

```
java/
├── pom.xml
└── src/
    ├── main/java/ceps/
    │   ├── model/
    │   │   ├── Event.java
    │   │   ├── Result.java
    │   │   └── Processor.java
    │   ├── phase1/  ... phase9/
    │   └── util/
    │       └── Validator.java
    └── test/java/ceps/
        └── phase1/ ... phase9/
```

---

## Phase Breakdown

### Phase 1 — Sequential Baseline

**Goal:** Establish ground truth output.

**Implementation:**
```java
public class SequentialCeps {
    public static void main(String[] args) {
        List<Event> events = EventGenerator.generate(100000);
        List<Result> results = events.stream()
            .map(Processor::process)
            .sorted(Comparator.comparingLong(Result::eventId))
            .toList();
        Validator.writeGroundTruth(results, "ground-truth.txt");
    }
}
```

**Deliverable:** Ground truth file for correctness validation in all later phases.

---

### Phase 2 — Threaded Producers (Unsafe)

**Goal:** Demonstrate race conditions in Java.

**Implementation:**
```java
public class UnsafeCeps {
    static Queue<Event> queue = new LinkedList<>(); // NOT thread-safe

    public static void main(String[] args) throws Exception {
        Thread[] producers = new Thread[4];
        for (int i = 0; i < 4; i++) {
            int id = i;
            producers[i] = new Thread(() -> {
                for (int j = 0; j < 25000; j++)
                    queue.add(EventGenerator.single(id * 25000 + j));
            });
        }
        for (Thread t : producers) t.start();
        for (Thread t : producers) t.join();
        System.out.println("Expected: 100000, Got: " + queue.size());
    }
}
```

**Expected Observations:**
- `queue.size()` < 100000 (lost events)
- Possible `NullPointerException` or `ConcurrentModificationException`
- Non-deterministic behavior across runs

---

### Phase 3 — Synchronized (Mutual Exclusion)

**Goal:** Correct access with intrinsic locks.

**Implementation:**
```java
public class SyncQueue<T> {
    private final Queue<T> q = new LinkedList<>();

    public synchronized void push(T item) { q.add(item); }
    public synchronized T tryPop() { return q.poll(); }
    public synchronized int size() { return q.size(); }
}
```

- Workers poll in a loop (busy-wait) — correct but wasteful
- Verify output matches ground truth

---

### Phase 4 — Wait/Notify (Producer-Consumer)

**Goal:** Eliminate busy-waiting with monitor coordination.

**Implementation:**
```java
public class MonitorQueue<T> {
    private final Queue<T> q = new LinkedList<>();
    private boolean done = false;

    public synchronized void push(T item) {
        q.add(item);
        notifyAll();  // use notifyAll when multiple workers share this monitor
    }

    public synchronized T pop() throws InterruptedException {
        while (q.isEmpty() && !done) wait();
        return q.poll(); // null means shutdown
    }

    public synchronized void shutdown() {
        done = true;
        notifyAll();
    }
}
```

**Key correctness points:**
- `while` loop (not `if`) guards against spurious wakeups
- `notifyAll()` on shutdown to wake all blocked workers
- Workers exit when `pop()` returns null
- **notify() vs notifyAll():** Use `notify()` only when you can prove a single waiter exists. With multiple workers, `notify()` may wake only one worker while others remain blocked indefinitely. Default to `notifyAll()` unless performance profiling justifies `notify()`.

---

### Phase 5 — Bounded Queue (Backpressure)

**Goal:** Prevent unbounded memory growth.

**Implementation:**
```java
public class BoundedQueue<T> {
    private final Queue<T> q = new LinkedList<>();
    private final int capacity;
    private boolean done = false;

    public BoundedQueue(int capacity) { this.capacity = capacity; }

    public synchronized void push(T item) throws InterruptedException {
        while (q.size() >= capacity) wait();
        q.add(item);
        notifyAll();
    }

    public synchronized T pop() throws InterruptedException {
        while (q.isEmpty() && !done) wait();
        T item = q.poll();
        notifyAll();
        return item;
    }

    public synchronized void shutdown() {
        done = true;
        notifyAll();
    }
}
```

- Producers block when queue is full
- Workers block when queue is empty
- `notifyAll()` used because both producers and workers wait on same monitor

**Experiment:** Compare with `java.util.concurrent.ArrayBlockingQueue` (drop-in replacement)

---

### Phase 5b — Memory Visibility & Volatile

**Goal:** Understand Java Memory Model visibility guarantees.

**Experiments:**

1. **Broken double-checked locking** (without `volatile`):
```java
public class BrokenSingleton {
    private static BrokenSingleton instance; // BUG: no volatile
    public static BrokenSingleton get() {
        if (instance == null) {
            synchronized (BrokenSingleton.class) {
                if (instance == null)
                    instance = new BrokenSingleton(); // may be seen partially constructed
            }
        }
        return instance;
    }
}
```

2. **Visibility bug** — flag without volatile:
```java
static boolean done = false; // BUG: worker may never see update
// Fix: static volatile boolean done = false;
```

**Key concepts:**
- `synchronized` provides happens-before (visibility + atomicity)
- `volatile` provides visibility only (no atomicity for compound operations)
- Without either, JIT/CPU may cache or reorder reads
- Java `volatile` ≠ C++ `volatile` (Java's is a concurrency primitive)

---

### Phase 6 — Semaphore-Based Queue

**Goal:** Same bounded queue using only Semaphores.

**Implementation:**
```java
public class SemQueue<T> {
    private final Object[] buffer;
    private int head = 0, tail = 0;
    private final Semaphore emptySlots;
    private final Semaphore fullSlots;
    private final Semaphore mutex = new Semaphore(1);

    public SemQueue(int capacity) {
        buffer = new Object[capacity];
        emptySlots = new Semaphore(capacity);
        fullSlots = new Semaphore(0);
    }

    public void push(T item) throws InterruptedException {
        emptySlots.acquire();
        mutex.acquire();
        buffer[tail] = item;
        tail = (tail + 1) % buffer.length;
        mutex.release();
        fullSlots.release();
    }

    @SuppressWarnings("unchecked")
    public T pop() throws InterruptedException {
        fullSlots.acquire();
        mutex.acquire();
        T item = (T) buffer[head];
        head = (head + 1) % buffer.length;
        mutex.release();
        emptySlots.release();
        return item;
    }
}
```

- No `synchronized` keyword used
- Compare readability and correctness reasoning with Phase 5

**Starvation & Fairness Exercises:**
- Compare `new Semaphore(n, false)` (unfair) vs `new Semaphore(n, true)` (fair)
- Measure per-worker event count to detect imbalance
- Demonstrate worker starvation under heavy producer load with unfair semaphore
- Show throughput tradeoff: fair semaphores are slower but prevent starvation

---

### Phase 7 — ExecutorService Worker Pool

**Goal:** Task-based concurrency instead of manual thread management.

**Implementation:**
```java
public class PoolCeps {
    private static final Event POISON = new Event(-1, 0, 0, 0);

    public static void main(String[] args) throws Exception {
        BlockingQueue<Event> queue = new ArrayBlockingQueue<>(100);
        Aggregator aggregator = new Aggregator();
        int numWorkers = 4;
        ExecutorService producers = Executors.newFixedThreadPool(4);
        ExecutorService workers = Executors.newFixedThreadPool(numWorkers);

        // Submit producer tasks
        for (int i = 0; i < 4; i++) {
            int id = i;
            producers.submit(() -> {
                for (int j = 0; j < 25000; j++)
                    queue.put(EventGenerator.single(id * 25000 + j));
            });
        }

        // Wait for producers, then send poison pills
        producers.shutdown();
        producers.awaitTermination(60, TimeUnit.SECONDS);
        for (int i = 0; i < numWorkers; i++)
            queue.put(POISON);

        // Submit worker tasks
        for (int i = 0; i < numWorkers; i++) {
            workers.submit(() -> {
                while (true) {
                    Event e = queue.take();
                    if (e.eventId() == -1) break;  // poison pill
                    aggregator.addResult(Processor.process(e));
                }
            });
        }

        workers.shutdown();
        workers.awaitTermination(60, TimeUnit.SECONDS);
        aggregator.report();
    }
}

public class Aggregator {
    private final List<Result> results = Collections.synchronizedList(new ArrayList<>());

    public void addResult(Result r) { results.add(r); }

    public void report() {
        System.out.printf("Processed: %d events%n", results.size());
    }
}
```

**Design Note:** Uses poison-pill events (eventId = -1) for clean shutdown instead of timeout-based polling. This clearly separates "queue empty" from "system done" and maintains consistency with the C++ and MPI phases.

**Contrast with C++:** No manual pthread lifecycle — framework handles thread reuse and shutdown.

---

### Phase 8 — Performance Analysis

**Goal:** Quantify scalability.

**Implementation:**
```java
public class Benchmark {
    public static void main(String[] args) throws Exception {
        int[] producerCounts = {1, 4};
        int[] workerCounts = {1, 2, 4, 8};
        int[] queueSizes = {10, 100, 1000};

        for (int p : producerCounts)
            for (int w : workerCounts)
                for (int qs : queueSizes)
                    runExperiment(p, w, qs, 100000);
    }

    static void runExperiment(int producers, int workers, int queueSize, int events) {
        long start = System.nanoTime();
        // ... run CEPS with given parameters ...
        long elapsed = System.nanoTime() - start;
        System.out.printf("P=%d W=%d Q=%d -> %d ms, throughput=%.0f events/s%n",
            producers, workers, queueSize, elapsed / 1_000_000,
            events * 1e9 / elapsed);
    }
}
```

**Metrics:**
| Metric | How |
|--------|-----|
| Throughput | events / total_time |
| Latency | timestamp at result - timestamp at creation |
| Speedup | T(1 worker) / T(N workers) |
| Contention | compare synchronized vs Semaphore vs BlockingQueue |

---

### Phase 9 — RMI Distributed Variant

**Goal:** Replace shared memory with remote method invocation.

**Architecture:**
```
Producer (client) → RMI → Dispatcher (server) → RMI → Worker (server) → RMI → Aggregator (server)
```

**Interfaces:**
```java
public interface EventReceiver extends Remote {
    void submit(Event event) throws RemoteException;
    void shutdown() throws RemoteException;
}

public interface ResultCollector extends Remote {
    void collect(Result result) throws RemoteException;
    List<Result> getResults() throws RemoteException;
}
```

**Components:**
- `DispatcherServer` — implements `EventReceiver`, distributes events round-robin to workers
- `WorkerServer` — receives events, processes, sends results to `ResultCollector`
- `AggregatorServer` — implements `ResultCollector`, gathers all results
- `ProducerClient` — generates events, calls `dispatcher.submit(event)`

**Design Note:** The centralized dispatcher is an intentional bottleneck. This design emphasizes correctness and clarity, not scalability. A production system would use decentralized routing or consistent hashing.

**Failure Assumptions:**
- No JVM crashes during execution (fail-stop not handled)
- RMI calls may throw `RemoteException` — treated as fatal (no retry)
- Delivery semantics: at-most-once (RMI default)
- If a worker JVM hangs, dispatcher blocks on that RMI call — known limitation
- Serialization assumes compatible class versions across JVMs

**Key learning:** Same invariants, no shared memory, explicit failure handling via `RemoteException`

---

## Deployment View (Single Machine, Phase 1–8)

```
┌──────────────────────────────────────────┐
│         Host Machine (any OS)            │
│                                          │
│  ┌────────────────────────────────────┐  │
│  │         JVM (java -jar ceps.jar)   │  │
│  │                                    │  │
│  │  P producer threads                │  │
│  │  W worker threads (or pool)        │  │
│  │  1 main thread (orchestrator)      │  │
│  │                                    │  │
│  │  Heap: EventQueue, Aggregator      │  │
│  │  GC: handles memory automatically  │  │
│  └────────────────────────────────────┘  │
│                                          │
│  Output: ground-truth.txt, perf.csv      │
└──────────────────────────────────────────┘
```

## Deployment View (RMI, Phase 9)

```
┌──────────────────────────────────────────────────────────────────┐
│              Network (localhost or LAN)                           │
│                                                                  │
│  ┌────────────┐ ┌────────────┐ ┌────────────┐ ┌────────────┐    │
│  │   JVM 1    │ │   JVM 2    │ │   JVM 3    │ │   JVM 4    │    │
│  │ Producer   │ │ Dispatcher │ │  Worker(s) │ │ Aggregator │    │
│  └────────────┘ └────────────┘ └────────────┘ └────────────┘    │
│                                                                  │
│  ┌────────────────────────────────────────────────────────────┐  │
│  │  rmiregistry (port 1099)                                   │  │
│  │  Bindings: "Dispatcher", "Worker0".."WorkerN", "Aggregator"│  │
│  └────────────────────────────────────────────────────────────┘  │
│                                                                  │
│  Start order:                                                    │
│    1. rmiregistry                                                │
│    2. AggregatorServer (registers ResultCollector)               │
│    3. WorkerServer ×N  (registers, looks up Aggregator)          │
│    4. DispatcherServer (registers, looks up Workers)             │
│    5. ProducerClient   (looks up Dispatcher, sends events)       │
└──────────────────────────────────────────────────────────────────┘
```

---

## Correctness Invariants (All Phases)

1. **No event loss:** `count(produced) == count(processed)`
2. **No duplication:** each eventId appears exactly once in results
3. **Determinism:** same input → same result set (order may vary)
4. **Termination:** all threads exit cleanly, no hanging
5. **No deadlock:** system always makes progress

---

## Safety vs Liveness Matrix

| Phase | Safety | Liveness | Progress Risks |
|-------|--------|----------|----------------|
| Phase 2 (unsafe) | ❌ | ❌ | Data corruption, lost events |
| Phase 3 (synchronized) | ✅ | ❌ | Busy-wait wastes CPU |
| Phase 4 (wait/notify) | ✅ | ✅ | Missed notify if not using notifyAll |
| Phase 5 (bounded) | ✅ | ✅ | Deadlock if shutdown logic wrong |
| Phase 5b (volatile) | ✅ | ✅ | Infinite loop without volatile |
| Phase 6 (Semaphore) | ✅ | ✅ | Starvation with unfair semaphore |
| Phase 7 (Executor) | ✅ | ✅ | Hung workers if poison pill lost |
| Phase 9 (RMI) | ✅ | ✅ | Blocking on failed remote call |

---

## Component State Diagrams

### Producer States
```
  ┌───────┐    generate     ┌──────────┐    queue full    ┌─────────┐
  │ START │───────────────→│ PRODUCING │───────────────→│ BLOCKED │
  └───────┘                 └─────┬────┘                  └────┬────┘
                                  │ all events sent             │ space available
                                  ↓                             ↓
                            ┌──────────┐                  ┌──────────┐
                            │   DONE   │                  │ PRODUCING│
                            └──────────┘                  └──────────┘
```

### Worker States
```
  ┌───────┐                 ┌─────────┐    event available  ┌────────────┐
  │ START │───────────────→│ WAITING │────────────────────→│ PROCESSING │
  └───────┘                 └────┬────┘                     └─────┬──────┘
                                 │ poison pill                    │ done
                                 ↓                                 ↓
                           ┌──────────┐                      ┌─────────┐
                           │   DONE   │                      │ WAITING │
                           └──────────┘                      └─────────┘
```

### EventQueue States
```
  ┌───────┐    push     ┌─────────────┐    push (at cap)   ┌──────┐
  │ EMPTY │────────────→│ HAS_EVENTS  │───────────────────→│ FULL │
  └───┬───┘             └──────┬──────┘                    └──┬───┘
      │                        │ pop (last event)              │ pop
      │                        ↓                               ↓
      │                  ┌───────┐                       ┌─────────────┐
      │                  │ EMPTY │                       │ HAS_EVENTS  │
      │                  └───────┘                       └─────────────┘
      │ shutdown
      ↓
  ┌──────────┐
  │ SHUTDOWN │
  └──────────┘
```

---

## Validation Utility

```java
public class Validator {
    public static boolean validate(List<Result> results, String groundTruthPath) {
        Map<Long, Integer> truth = loadGroundTruth(groundTruthPath);
        if (results.size() != truth.size()) return false;
        for (Result r : results)
            if (!truth.getOrDefault(r.eventId(), -1).equals(r.output()))
                return false;
        return true;
    }
}
```

Used in every phase to confirm correctness against Phase 1 output.

---

## Build & Run

```bash
mvn compile

# Run specific phase
mvn exec:java -Dexec.mainClass="ceps.phase1.SequentialCeps"
mvn exec:java -Dexec.mainClass="ceps.phase8.Benchmark"

# Phase 9 (RMI) — start registry + servers + client
rmiregistry &
mvn exec:java -Dexec.mainClass="ceps.phase9.AggregatorServer"
mvn exec:java -Dexec.mainClass="ceps.phase9.WorkerServer"
mvn exec:java -Dexec.mainClass="ceps.phase9.DispatcherServer"
mvn exec:java -Dexec.mainClass="ceps.phase9.ProducerClient"

# Tests
mvn test
```

---

## Recommended Study Order

| Week | Phase | Concept Learned |
|------|-------|-----------------|
| 1    | 1     | Sequential correctness, ground truth |
| 2    | 2     | Race conditions in Java |
| 2    | 3     | synchronized solves safety |
| 3    | 4     | wait/notify solves coordination |
| 4    | 5     | Bounded buffers, backpressure |
| 4    | 6     | Semaphores as alternative |
| 5    | 7     | ExecutorService, task-based model |
| 6    | 8     | Performance measurement |
| 7    | 9     | RMI distributed variant |
