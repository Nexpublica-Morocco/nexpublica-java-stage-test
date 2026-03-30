# Java internship technical test — session analytics

**If you are a candidate:** open **[ASSIGNMENT.md](ASSIGNMENT.md)** first, then **[CANDIDATE_GUIDE.md](CANDIDATE_GUIDE.md)** if you need FAQ and IDE tips.

Stack: **Java 17**, **Maven**, **JUnit 5**.

## Quick commands

From the repository root (next to `pom.xml`):

```bash
mvn test
```

**At the start:** tests fail with `TODO` / `UnsupportedOperationException` — expected.  
**When finished:** `BUILD SUCCESS` and **13 tests passed**.

## AI tools

Allowed (Copilot, ChatGPT, etc.). You must be ready to **explain** your solution in an interview.

## Repository layout

| Path | Role |
|------|------|
| [ASSIGNMENT.md](ASSIGNMENT.md) | **Start here** — step-by-step |
| [CANDIDATE_GUIDE.md](CANDIDATE_GUIDE.md) | FAQ, IDE, common errors |
| `src/main/java/.../SessionAnalyzer.java` | **Your implementation** (spec in Javadoc) |
| `src/main/java/.../Event.java` | Given — do not change |
| `src/test/java/.../SessionAnalyzerTest.java` | Tests — do not change |
| `src/main/resources/events.sample.json` | Optional practice only |

## Wrong repository?

You need **`pom.xml`** at the root. If you only see `events.json` and a `java/` folder without Maven, contact Nexpublica — the Classroom assignment must use starter **`nexpublica-java-stage-test`**.

---

## For Nexpublica (maintainers)

See **[CLASSROOM.md](CLASSROOM.md)**. Student-facing default branch: **`candidate-starter`**. Reference solution: **`main`**.

## Licence

Internal hiring use — Nexpublica Morocco unless stated otherwise.
