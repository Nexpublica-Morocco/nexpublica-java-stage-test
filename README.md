# Java internship technical test — session analytics

Professional take-home exercise for **internship (stage)** campaigns. Stack: **Java 17**, **Maven**, **JUnit 5**.

## What candidates do

1. Read the specification in `SessionAnalyzer` (class-level Javadoc).
2. Implement `totalSessionSecondsPerUser(List<Event>)` so that **`mvn test` passes**.
3. Push to their **GitHub Classroom** repository (or the branch you specify).

Parsing `events.sample.json` is **optional**; all official tests build `Event` objects in code.

## Run locally

```bash
mvn test
```

## Use of AI (Copilot, ChatGPT, etc.)

- **Allowed.** We assume candidates will use assistants.
- **Expectation:** they must **explain and justify** their solution in a live interview (trade-offs, edge cases, complexity). Blind copy-paste without understanding fails the human review.
- Automated tests are there to check **behaviour**, not originality.

## For maintainers (Nexpublica)

- **`main` branch** contains a **reference implementation** so CI stays green and reviewers have a baseline.
- For a **candidate-facing starter without solution**:
  1. Replace the body of `SessionAnalyzer.totalSessionSecondsPerUser` with `throw new UnsupportedOperationException("TODO");` (keep the specification Javadoc).
  2. Use that commit as a **template** or **orphan branch** for GitHub Classroom, **or** maintain a long-lived branch `candidate-starter` (recommended).
- See `CLASSROOM.md` for Classroom wiring.

## Project layout

```
src/main/java/com/nexpublica/stage/
  Event.java           — immutable event (do not change contract)
  SessionAnalyzer.java — candidate work (spec + implementation)
src/test/java/.../SessionAnalyzerTest.java — black-box tests
src/main/resources/events.sample.json — optional JSON exercise
```

## Licence

Internal hiring use — Nexpublica Morocco unless stated otherwise.
