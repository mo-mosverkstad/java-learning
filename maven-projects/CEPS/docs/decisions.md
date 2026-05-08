# Project Naming Proposals

Current name: **CEPS** (Concurrent Event Processing System)

---

## Alternatives

### Descriptive / Technical

| Name | Meaning |
|------|---------|
| **CEPL** | Concurrent Event Processing Lab |
| **EventForge** | Forging events through concurrent pipelines |
| **FlowEngine** | Emphasizes the data flow architecture |

### Metaphorical / Memorable

| Name | Meaning |
|------|---------|
| **Torrent** | Parallel streams of events flowing through workers |
| **Anvil** | Where raw events get hammered into results (implies heavy processing) |
| **Loom** | Threads weaving events into results (plays on "thread" double meaning) |

### Academic / Course-aligned

| Name | Meaning |
|------|---------|
| **ConcurrencyLab** | Straightforward, says what it is |
| **ParaFlow** | Parallel + flow |
| **SyncForge** | Synchronization + forging results |

---

## Top Picks

| Name | Why | Caveat |
|------|-----|--------|
| **Loom** | "Thread" pun, memorable, short | Conflicts with Java's Project Loom |
| **Torrent** | Evokes parallel streams, language-neutral, strong imagery | — |
| **EventForge** | Clear purpose, sounds like a real system name | — |
| **CEPL** | Keeps the CEPS spirit but adds "Lab" to signal it's for learning | — |

---

## Decision

**CEPS** (Concurrent Event Processing System) — kept as-is. Clear, descriptive, professional.

---

# Language Choice Decision: C++ (not pure C)

**Chosen:** C++17 with C-style concurrency discipline

## Course Requirement

From ID1217 official syllabus:
> Students should implement concurrently executing and distributed programmes in C, Java or other programming languages by means of pthreads, OpenMP, MPI and Java threads and monitors.

- ✅ C is allowed
- ✅ C++ is allowed (treated as "C + libraries")
- ❌ No requirement to use pure C
- The course evaluates **concepts and correctness**, not language purity

## Why C++ over pure C

| Reason | Explanation |
|--------|-------------|
| Focus on concurrency, not memory leaks | RAII eliminates orthogonal bugs (double frees, leaks during shutdown) |
| Concurrency errors become more visible | Lock guards, destructors, and types expose logic errors clearly |
| Better signal-to-noise ratio | Study race conditions and deadlocks, not manual malloc/free |
| Industry relevance | MPI code, pthread-based systems, and embedded work today are often C++ |
| No academic risk | TAs routinely accept C++ + pthreads/MPI |

## Self-Imposed Discipline (Important)

### ✅ Allowed (and encouraged)
- `pthread_*` (primary concurrency API)
- `std::thread` only *after* pthread phases (for comparison)
- `std::mutex` only when explicitly comparing with pthread_mutex
- RAII (`std::lock_guard`)
- `std::atomic` in memory-model phases
- `std::vector`, `std::queue` (non-thread-safe!)

### 🚫 Avoid early on
- `std::async`
- High-level concurrent containers
- Lock-free libraries
- Heavy template abstractions

## Summary

> **Pick C++. Write it like a disciplined systems programmer, not like a framework author.**

---

# Folder Structure Decision

**Chosen:** Option B — Shared spec + isolated projects

```
CEPS/
├── docs/
│   ├── ideas.md              # course analysis & learning proposals
│   ├── readiness-review.md  # implementation guidance & readiness checklist
│   └── decisions.md         # this file (project-level decisions)
├── spec/
│   └── invariants.md        # shared correctness rules for both implementations
├── ceps-cpp/
│   ├── docs/
│   │   └── study.md         # C++ implementation proposal & design
│   ├── CMakeLists.txt
│   └── phase1/ ... phase9/
└── ceps-java/
    ├── docs/
    │   └── study.md         # Java implementation proposal & design
    ├── pom.xml
    └── src/
```

**Rationale:**
- `docs/` — all documentation in one place
- `spec/` — shared invariants that both implementations must satisfy
- `ceps-cpp/` and `ceps-java/` — fully independent build systems, no cross-dependencies

---

# Implementation Docs Organization

**Chosen:** Option A — Docs per implementation (docs live next to the code they describe)

```
CEPS/
├── docs/                        # project-level shared docs
│   ├── decisions.md
│   ├── ideas.md
│   └── readiness-review.md
├── spec/
│   └── invariants.md
├── ceps-cpp/
│   ├── docs/
│   │   ├── study.md             # C++ implementation proposal & design
│   │   ├── history.md           # development log (phase-by-phase progress)
│   │   ├── build.md             # build instructions & dependencies
│   │   ├── test.md              # test strategy & how to run tests
│   │   ├── demo.md              # demo scenarios & expected output
│   │   └── codebase-analysis.md # architecture notes & code walkthrough
│   ├── CMakeLists.txt
│   └── phase1/ ... phase9/
└── ceps-java/
    ├── docs/
    │   ├── study.md             # Java implementation proposal & design
    │   ├── history.md
    │   ├── build.md
    │   ├── test.md
    │   ├── demo.md
    │   └── codebase-analysis.md
    ├── pom.xml
    └── src/
```

**Rationale:**
- Each implementation is self-contained — all docs (including study plan) live in `docs/`
- `study.md` is the primary design reference, alongside operational docs
- Could extract `ceps-cpp/` or `ceps-java/` into its own repo independently
- `CEPS/docs/` stays clean with only project-level/shared documents
- Matches real-world project conventions
