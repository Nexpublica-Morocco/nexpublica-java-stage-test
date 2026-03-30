# Java internship technical test — session analytics

Take-home exercise for **internship (stage)** hiring. Stack: **Java 17**, **Maven**, **JUnit 5**.

**New here?** Read **[ASSIGNMENT.md](ASSIGNMENT.md)** first (setup, checks, troubleshooting).

## What candidates do

1. Read the specification in `SessionAnalyzer` (class-level Javadoc).
2. Implement `totalSessionSecondsPerUser(List<Event>)` until **`mvn test`** passes.
3. Push to your **GitHub Classroom** repository.

Parsing `events.sample.json` is **optional**; all official tests build `Event` objects in code.

## Run locally

```bash
mvn test
```

From the **repository root** (where `pom.xml` is).

## Use of AI (Copilot, ChatGPT, etc.)

- **Allowed.** We assume candidates will use assistants.
- **Expectation:** you must **explain and justify** your solution in a live interview (trade-offs, edge cases, complexity). Copy-paste without understanding fails the human review.
- Automated tests check **behaviour**, not originality.

## For Nexpublica (GitHub Classroom)

### Correct starter repository

Use **`Nexpublica-Morocco/nexpublica-java-stage-test`** as the assignment starter (mark as **template** on GitHub if required).

Do **not** use auto-generated repos such as `nexpublica-morocco-classroom-ad65c9-java-engineering-test-nexpublica-java-stage-test` (old layout without `pom.xml` / tests).

### Branches

| Branch | Purpose |
|--------|---------|
| **`candidate-starter`** | Default branch for **students** — `SessionAnalyzer` throws `UnsupportedOperationException` until they implement. CI is **red** until tests pass. |
| **`main`** | **Reference solution** for reviewers; CI should stay **green**. |

See **[CLASSROOM.md](CLASSROOM.md)** for maintenance and sync steps.

## Project layout

```
pom.xml
ASSIGNMENT.md          — candidate quick start
src/main/java/com/nexpublica/stage/
  Event.java           — immutable event (do not change contract)
  SessionAnalyzer.java — your work (spec + implementation)
src/test/java/.../SessionAnalyzerTest.java — black-box tests
src/main/resources/events.sample.json — optional JSON exercise
```

## Licence

Internal hiring use — Nexpublica Morocco unless stated otherwise.
