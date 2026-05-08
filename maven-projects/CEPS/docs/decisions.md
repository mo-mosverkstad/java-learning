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
│   ├── study.md             # C++ implementation proposal & design
│   ├── CMakeLists.txt
│   └── phase1/ ... phase9/
└── ceps-java/
    ├── study.md             # Java implementation proposal & design
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
│   ├── study.md                 # C++ implementation proposal & design
│   ├── docs/
│   │   ├── history.md           # development log (phase-by-phase progress)
│   │   ├── build.md             # build instructions & dependencies
│   │   ├── test.md              # test strategy & how to run tests
│   │   ├── demo.md              # demo scenarios & expected output
│   │   └── codebase-analysis.md # architecture notes & code walkthrough
│   ├── CMakeLists.txt
│   └── phase1/ ... phase9/
└── ceps-java/
    ├── study.md                 # Java implementation proposal & design
    ├── docs/
    │   ├── history.md
    │   ├── build.md
    │   ├── test.md
    │   ├── demo.md
    │   └── codebase-analysis.md
    ├── pom.xml
    └── src/
```

**Rationale:**
- Each implementation is self-contained — study plan + code + docs together
- `study.md` lives at the root of each implementation as the primary design reference
- Could extract `ceps-cpp/` or `ceps-java/` into its own repo independently
- `CEPS/docs/` stays clean with only project-level/shared documents
- Matches real-world project conventions
