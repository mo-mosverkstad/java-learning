# CEPS — C++ Implementation Proposal

## Concurrent Event Processing System (C++ / pthreads / MPI)

---

## Project Overview

A multi-phase concurrent event processing engine built with POSIX threads, condition variables, semaphores, and MPI. Each phase adds one concurrency concept while preserving correctness invariants.

---

## Technology Stack

- **Language:** C++17
- **Threading:** pthreads (POSIX Threads)
- **Synchronization:** pthread_mutex, pthread_cond, POSIX semaphores
- **Distributed:** MPI (OpenMPI or MPICH)
- **Build:** CMake
- **Testing:** assertions + stress scripts

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
│  ┌─────────────────────────────────────┐                            │
│  │           EventQueue                │   ← Synchronization       │
│  │  ┌───────────────────────────────┐  │     Layer                  │
│  │  │ Phase 3: mutex               │  │                            │
│  │  │ Phase 4: mutex + cond_var    │  │                            │
│  │  │ Phase 5: bounded + 2 conds   │  │                            │
│  │  │ Phase 6: semaphores only     │  │                            │
│  │  └───────────────────────────────┘  │                            │
│  └──────────────────┬──────────────────┘                            │
│                     ↓                                               │
│  ┌──────────┐   ┌──────────┐   ┌──────────┐                        │
│  │ Worker 1 │   │ Worker 2 │   │ Worker M │   ← Processing        │
│  └────┬─────┘   └────┬─────┘   └────┬─────┘     Layer              │
│       │               │               │                             │
│       └───────────────┼───────────────┘                             │
│                       ↓                                             │
│  ┌─────────────────────────────────────┐                            │
│  │         Aggregator (mutex)          │   ← Result Collection     │
│  └──────────────────┬──────────────────┘     Layer                  │
│                     ↓                                               │
│  ┌─────────────────────────────────────┐                            │
│  │      Statistics / Validator         │   ← Output Layer          │
│  └─────────────────────────────────────┘                            │
│                                                                     │
└─────────────────────────────────────────────────────────────────────┘
```

### Component Responsibilities

| Component | Responsibility | Thread Model |
|-----------|---------------|---------------|
| Producer | Generate events with unique IDs | 1 pthread per producer |
| EventQueue | Buffer + synchronize access | Shared data structure |
| Worker | Dequeue + CPU-bound processing | 1 pthread per worker (or pool) |
| Aggregator | Collect results under lock | Accessed by all workers |
| Validator | Compare output vs ground truth | Single-threaded post-run |

### Phase 9 — MPI Deployment Architecture

```
┌─────────────────────────────────────────────────────────────────────────┐
│                     MPI Cluster (mpirun -np N)                          │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                         │
│  ┌─────────────┐  ┌─────────────┐         MPI_Send(Event)               │
│  │  Rank 0     │  │  Rank 1     │  ─────────────────────┐               │
│  │  Producer 0 │  │  Producer 1 │                       │               │
│  └─────────────┘  └─────────────┘                       ↓               │
│                                            ┌─────────────────────────┐  │
│                                            │       Rank P            │  │
│                                            │     Dispatcher          │  │
│                                            │  (round-robin router)   │  │
│                                            └──────────┬──────────────┘  │
│                                                       │                 │
│                              MPI_Send(Event)          │                 │
│                    ┌──────────────┬───────────────────┘                 │
│                    ↓              ↓              ↓                       │
│           ┌──────────────┐┌──────────────┐┌──────────────┐              │
│           │  Rank P+1    ││  Rank P+2    ││  Rank P+3    │              │
│           │  Worker 0    ││  Worker 1    ││  Worker 2    │              │
│           └──────┬───────┘└──────┬───────┘└──────┬───────┘              │
│                  │               │               │                      │
│                  └───────────────┼───────────────┘                      │
│                                  │  MPI_Send(Result)                    │
│                                  ↓                                      │
│                    ┌─────────────────────────┐                          │
│                    │       Rank P            │                          │
│                    │  Dispatcher/Aggregator  │                          │
│                    │   (collects results)    │                          │
│                    └─────────────────────────┘                          │
│                                                                         │
└─────────────────────────────────────────────────────────────────────────┘

  Communication: MPI_Send / MPI_Recv (blocking)
  Termination:   Poison-pill event (eventId = -1)
  No shared memory between ranks
```

### Thread Interaction Diagram (Phase 4–7, Shared Memory)

```
  Producer Thread          EventQueue              Worker Thread
  ──────────────          ──────────              ─────────────
       │                      │                        │
       │── lock(mutex) ──────→│                        │
       │── push(event) ──────→│                        │
       │── signal(notEmpty) ─→│                        │
       │── unlock(mutex) ────→│                        │
       │                      │←── lock(mutex) ───────│
       │                      │    [if empty: wait()]  │
       │                      │←── pop(event) ────────│
       │                      │←── unlock(mutex) ─────│
       │                      │                        │── process(event)
       │                      │                        │── aggregator.add(result)
       │                      │                        │
```

---

## Data Model

```cpp
struct Event {
    uint64_t eventId;
    uint64_t timestamp;   // milliseconds since epoch
    int type;             // 0=LOW, 1=MEDIUM, 2=HIGH
    int payload;          // integer for deterministic processing
};

struct Result {
    uint64_t eventId;
    int output;           // processed result (e.g., payload * payload + type)
};
```

Processing function (deterministic, CPU-bound):
```cpp
Result process(const Event& e) {
    // Simulate work with a busy loop proportional to type
    int result = e.payload;
    for (int i = 0; i < (e.type + 1) * 1000; i++)
        result = (result * 31 + i) % 1000003;
    return {e.eventId, result};
}
```

---

## Phase Breakdown

### Phase 1 — Sequential Baseline

**Goal:** Establish ground truth output.

**Structure:**
```
cpp/phase1-seq/
├── CMakeLists.txt
├── event.h
├── processor.h
└── main.cpp
```

**Implementation:**
- Single-threaded: generate N events → process sequentially → write results to file
- Output file serves as correctness oracle for all later phases

**Validation:** Deterministic output file (sorted by eventId)

---

### Phase 2 — Threaded Producers (Unsafe)

**Goal:** Demonstrate race conditions.

**Structure:**
```
cpp/phase2-threads/
├── CMakeLists.txt
├── event.h
├── queue.h          // std::queue<Event>, NO protection
├── producer.cpp
└── main.cpp
```

**Implementation:**
- P producer threads push events into a shared `std::queue<Event>`
- 1 worker thread pops and processes
- **No mutex** — intentionally broken

**Expected Observations:**
- Lost events (count mismatch)
- Segfaults or corrupted data
- Non-deterministic output

**Deliverable:** Document observed failures with different thread counts

---

### Phase 3 — Mutex Protection

**Goal:** Correct mutual exclusion.

**Structure:**
```
cpp/phase3-mutex/
├── CMakeLists.txt
├── safe_queue.h     // pthread_mutex_t protecting std::queue
├── producer.cpp
├── worker.cpp
└── main.cpp
```

**Implementation:**
```cpp
class SafeQueue {
    std::queue<Event> q;
    pthread_mutex_t mutex = PTHREAD_MUTEX_INITIALIZER;
public:
    void push(const Event& e);
    bool tryPop(Event& out);  // returns false if empty
};
```

- Workers busy-wait (poll) on tryPop — correct but wasteful
- Verify: output matches Phase 1 ground truth

**Deliverable:** Prove correctness; measure CPU waste from busy-waiting

---

### Phase 4 — Condition Variables (Producer-Consumer)

**Goal:** Eliminate busy-waiting with proper coordination.

**Structure:**
```
cpp/phase4-condvar/
├── CMakeLists.txt
├── blocking_queue.h   // mutex + cond var
├── producer.cpp
├── worker.cpp
└── main.cpp
```

**Implementation:**
```cpp
class BlockingQueue {
    std::queue<Event> q;
    pthread_mutex_t mutex;
    pthread_cond_t notEmpty;
    bool done = false;  // poison flag
public:
    void push(const Event& e);
    bool pop(Event& out);  // blocks until event or done
    void shutdown();
};
```

- Workers call `pop()` which blocks via `pthread_cond_wait` in a while-loop
- Producers call `push()` which signals `notEmpty`
- Graceful shutdown via `done` flag + broadcast

**Key correctness points:**
- Predicate-based wait: `while (q.empty() && !done)`
- Signal after push, broadcast on shutdown
- Use `pthread_cond_broadcast` (not `signal`) when multiple workers share the same condition variable — avoids missed wakeups

---

### Phase 5 — Bounded Queue (Backpressure)

**Goal:** Prevent unbounded memory growth.

**Structure:**
```
cpp/phase5-bounded/
├── CMakeLists.txt
├── bounded_queue.h    // two condition variables
├── producer.cpp
├── worker.cpp
└── main.cpp
```

**Implementation:**
```cpp
class BoundedQueue {
    Event* buffer;       // circular buffer
    int capacity, count, head, tail;
    pthread_mutex_t mutex;
    pthread_cond_t notFull;
    pthread_cond_t notEmpty;
    bool done;
public:
    BoundedQueue(int cap);
    void push(const Event& e);  // blocks if full
    bool pop(Event& out);       // blocks if empty
    void shutdown();
};
```

- Producers block on `notFull` when `count == capacity`
- Workers block on `notEmpty` when `count == 0`
- Circular buffer for cache efficiency

**Experiments:** Vary capacity (1, 10, 100, 1000) and measure throughput

---

### Phase 5b — Memory Visibility & Reordering

**Goal:** Understand why mutex/atomic provides visibility, not just exclusion.

**Structure:**
```
cpp/phase5b-visibility/
├── CMakeLists.txt
├── visibility_bug.cpp    // broken: flag without atomic
├── visibility_fix.cpp    // fixed: std::atomic<bool>
└── main.cpp
```

**Experiments:**
- Shared `bool done = false` without atomic → worker may spin forever (compiler/CPU reorders)
- Fix with `std::atomic<bool>` using acquire/release semantics
- Demonstrate that mutex implicitly provides happens-before (visibility)

**Key concepts:**
- Visibility ≠ atomicity ≠ mutual exclusion
- `std::atomic` with `memory_order_acquire` / `memory_order_release`
- Why `volatile` in C++ does NOT solve concurrency (unlike Java)

---

### Phase 6 — Semaphore-Based Queue

**Goal:** Same bounded queue, different primitive.

**Structure:**
```
cpp/phase6-semaphore/
├── CMakeLists.txt
├── sem_queue.h
├── starvation_demo.cpp   // unfair scheduling demo
└── main.cpp
```

**Implementation:**
```cpp
class SemQueue {
    Event* buffer;
    int capacity, head, tail;
    sem_t empty_slots;  // init = capacity
    sem_t full_slots;   // init = 0
    sem_t mutex;        // init = 1 (binary semaphore)
public:
    void push(const Event& e);
    bool pop(Event& out);
};
```

- No pthread_cond, no pthread_mutex — semaphores only
- Compare correctness and readability with Phase 5

**Starvation & Fairness Exercises:**
- Demonstrate worker starvation under heavy producer load with small queue
- Show that POSIX semaphores have no fairness guarantee
- Measure per-worker event count to detect imbalance

---

### Phase 7 — Thread Pool

**Goal:** Fixed worker pool with task distribution.

**Structure:**
```
cpp/phase7-pool/
├── CMakeLists.txt
├── thread_pool.h
├── aggregator.h
└── main.cpp
```

**Implementation:**
```cpp
class ThreadPool {
    std::vector<pthread_t> threads;
    BoundedQueue& queue;
    Aggregator& aggregator;
    int numWorkers;
public:
    ThreadPool(int n, BoundedQueue& q, Aggregator& agg);
    void start();
    void join();
};

class Aggregator {
    std::vector<Result> results;
    pthread_mutex_t mutex;
public:
    void addResult(const Result& r);
    void report();  // print statistics
};
```

- N worker threads pull from shared queue
- Results aggregated under lock
- Measure throughput vs worker count

---

### Phase 8 — Performance Analysis

**Goal:** Quantify scalability.

**Structure:**
```
cpp/phase8-perf/
├── CMakeLists.txt
├── benchmark.cpp
└── results/
    └── .gitkeep
```

**Metrics to collect:**
- Total events processed per second (throughput)
- Average event latency (enqueue → result)
- Speedup: T(1 worker) / T(N workers)
- Lock contention: time spent waiting on mutex

**Experiments:**
| Producers | Workers | Queue Size | Event Count | Measure        |
|-----------|---------|------------|-------------|----------------|
| 1         | 1       | 100        | 100000      | Baseline       |
| 4         | 1       | 100        | 100000      | Producer load  |
| 1         | 4       | 100        | 100000      | Worker scaling |
| 4         | 4       | 100        | 100000      | Full parallel  |
| 4         | 4       | 10         | 100000      | Backpressure   |
| 4         | 8       | 100        | 100000      | Over-subscribe |

**Output:** CSV + analysis document

---

### Phase 9 — MPI Message Passing (Distributed)

**Goal:** Replace shared memory with explicit messages.

**Structure:**
```
cpp/phase9-mpi/
├── CMakeLists.txt
├── event_serial.h    // serialization helpers
├── producer.cpp      // MPI rank 0..P-1
├── dispatcher.cpp    // MPI rank P (routes events)
├── worker.cpp        // MPI rank P+1..P+W
└── main.cpp
```

**Architecture:**
```
Producers (rank 0..P-1)  →  MPI_Send  →  Dispatcher (rank P)  →  MPI_Send  →  Workers (rank P+1..N)
                                                                      ↓
                                                              MPI_Send results back
```

- No shared memory between processes
- Dispatcher acts as router/load-balancer
- Workers send results back to dispatcher for aggregation
- Graceful termination via poison-pill messages

**Design Note:** The centralized dispatcher is an intentional bottleneck. This design emphasizes correctness and clarity, not scalability. A production system would use decentralized routing.

**Failure Assumptions:**
- No process crashes during execution (fail-stop not handled)
- Messages are delivered reliably (MPI guarantees in-order, no loss for point-to-point)
- Delivery semantics: at-most-once (no retry logic)
- If a worker hangs, the system deadlocks — this is a known limitation, not a bug to fix

**Key learning:** Same correctness invariants, completely different mechanism

---

## Deployment View (Single Machine, Phase 1–8)

```
┌──────────────────────────────────────┐
│         Linux / macOS Host           │
│                                      │
│  ┌────────────────────────────────┐  │
│  │     Single Process (./ceps)    │  │
│  │                                │  │
│  │  P producer threads            │  │
│  │  W worker threads              │  │
│  │  1 main thread (orchestrator)  │  │
│  │                                │  │
│  │  Shared: EventQueue, Aggregator│  │
│  └────────────────────────────────┘  │
│                                      │
│  Output: results.txt, perf.csv       │
└──────────────────────────────────────┘
```

## Deployment View (MPI, Phase 9)

```
┌──────────────────────────────────────────────────────────┐
│              MPI Cluster (or single host)                 │
│                                                          │
│  ┌──────────┐ ┌──────────┐ ┌──────────┐ ┌──────────┐    │
│  │ Process 0│ │ Process 1│ │ Process 2│ │ Process 3│... │
│  │(Producer)│ │(Producer)│ │(Dispatch)│ │ (Worker) │    │
│  └──────────┘ └──────────┘ └──────────┘ └──────────┘    │
│                                                          │
│  Launch: mpirun -np 6 ./ceps_mpi                         │
│  Network: MPI internal (TCP/shared-mem depending on hw)  │
│  No shared address space between processes               │
└──────────────────────────────────────────────────────────┘
```

---

## Correctness Invariants (All Phases)

1. **No event loss:** `count(produced) == count(processed)`
2. **No duplication:** each eventId appears exactly once in results
3. **Determinism:** same input → same result set (order may vary)
4. **Termination:** all threads/processes exit cleanly
5. **No deadlock:** system always makes progress under load

---

## Safety vs Liveness Matrix

| Phase | Safety | Liveness | Progress Risks |
|-------|--------|----------|----------------|
| Phase 2 (unsafe) | ❌ | ❌ | Data corruption, lost events |
| Phase 3 (mutex) | ✅ | ❌ | Busy-wait wastes CPU |
| Phase 4 (condvar) | ✅ | ✅ | Missed signal if broadcast not used |
| Phase 5 (bounded) | ✅ | ✅ | Deadlock if shutdown logic wrong |
| Phase 5b (visibility) | ✅ | ✅ | Infinite spin without atomic |
| Phase 6 (semaphore) | ✅ | ✅ | Starvation (no fairness guarantee) |
| Phase 7 (pool) | ✅ | ✅ | Thread leak if join missed |
| Phase 9 (MPI) | ✅ | ✅ | Blocking deadlock on mismatched send/recv |

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
                                 │ shutdown signal                 │ done
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

## Build & Run

```bash
# All phases use CMake
mkdir build && cd build
cmake .. && make

# Phase 9 (MPI)
mpirun -np 6 ./ceps_mpi   # 2 producers + 1 dispatcher + 3 workers
```

---

## Recommended Study Order

| Week | Phase | Concept Learned |
|------|-------|-----------------|
| 1    | 1     | Sequential correctness, ground truth |
| 2    | 2     | Race conditions are real |
| 2    | 3     | Mutex solves safety |
| 3    | 4     | Condition variables solve coordination |
| 4    | 5     | Bounded buffers, backpressure |
| 4    | 6     | Semaphores as alternative |
| 5    | 7     | Thread pools, scalability |
| 6    | 8     | Performance measurement |
| 7    | 9     | Distributed message passing |
