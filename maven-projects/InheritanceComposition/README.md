# InheritanceComposition

Demonstrates how **inheritance breaks encapsulation** (Chapter 9.3) by adapting `java.io.RandomAccessFile` using both inheritance and composition.

## The Pathology

Both adapters try to track `bytesRead` — the total bytes read from the file:

- **Inheritance adapter** — overrides `read(byte[])` to increment the counter. But `readFully()` does NOT call `read(byte[])` internally; it uses its own read loop. The override is **silently bypassed**, and `bytesRead` stays at zero. The subclass cannot know or control which internal methods the superclass uses.

- **Composition adapter** — wraps `RandomAccessFile` as a private field and counts bytes in its own `readBytes()` method. No reliance on superclass internals, so counting is always correct.

## Project Structure

```
src/main/java/se/inheritancecomposition/
├── Main.java                                 # Entry point, demos both adapters
├── MessageProtocol.java                      # Data class for parsed protocol
├── InheritedProtocolInterpreterAdapter.java  # Inheritance-based adapter (broken counting)
└── CompositeProtocolInterpreterAdapter.java  # Composition-based adapter (correct counting)

src/test/java/se/inheritancecomposition/
├── MainTest.java
├── MessageProtocolTest.java
├── InheritedProtocolInterpreterAdapterTest.java
└── CompositeProtocolInterpreterAdapterTest.java
```

## Build & Run

```bash
mvn compile exec:java
```

## Run Tests

```bash
mvn test
```

## Expected Output

```
--- Inheritance vs Composition Demo ---
(Each adapter overrides/wraps read() to track bytesRead)

[Inheritance adapter (extends RandomAccessFile)]:
  Overrides read(byte[]) to count bytes, but readFully()
  does NOT call read(byte[]) — the override is SILENTLY BYPASSED.

  First read:  MessageProtocol(title = BikeAlert, description = Chain replacement needed, bytesRead = 0)
  Second read: MessageProtocol(title = BatteryAlert, description = Battery replacement needed, bytesRead = 0)

[Composition adapter (wraps RandomAccessFile)]:
  Counts bytes in its own readBytes() method — no reliance
  on superclass internals, so counting is always correct.

  First read:  MessageProtocol(title = BikeAlert, description = Chain replacement needed, bytesRead = 33)
  Second read: MessageProtocol(title = BatteryAlert, description = Battery replacement needed, bytesRead = 71)

Conclusion:
  Inheritance: bytesRead = 0 (WRONG) because the superclass
    bypasses our overridden read(byte[]) — we cannot control
    which internal methods the superclass calls.
  Composition: bytesRead is CORRECT because we explicitly
    control all read calls without depending on internals.
```
