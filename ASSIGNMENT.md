# Technical test — start here (candidates)

You received this repository via **GitHub Classroom**. Follow the steps below in order.

## Step 0 — Check that you have the right project (30 seconds)

At the **root** of your repo you **must** see:

| Required | What it means |
|----------|----------------|
| `pom.xml` | Maven project |
| `src/main/java/...` | Your code |
| `src/test/java/...` | Automated tests |

**Wrong project?** If you only see `events.json`, a loose `java/` folder, and **no `pom.xml`**, the assignment is misconfigured. **Stop** and email your Nexpublica contact — we cannot grade that layout.

## Step 1 — Install tools

- **JDK 17 or newer** — [Eclipse Temurin 17 (Adoptium)](https://adoptium.net/temurin/releases/?version=17)
- **Apache Maven 3.8+** — [Installation](https://maven.apache.org/install.html)  
  Or use the **Maven** integration in IntelliJ IDEA / VS Code and run the same goals.

Verify in a terminal:

```bash
java -version   # should show 17 or higher
mvn -version    # should show Maven 3.x
```

## Step 2 — Clone and run tests (baseline)

From the folder that contains `pom.xml`:

```bash
mvn test
```

**Expected at the start:** build runs, many tests **fail** or error with `UnsupportedOperationException` / `TODO`. That is **normal** until you implement the exercise.

## Step 3 — Read the specification

1. Open `src/main/java/com/nexpublica/stage/SessionAnalyzer.java`.
2. Read the **entire class-level Javadoc** (the specification is there).
3. Do **not** change `Event.java` or the test class — your work is only in `SessionAnalyzer`.

## Step 4 — Implement and verify

1. Replace the `TODO` / `UnsupportedOperationException` with your implementation of `totalSessionSecondsPerUser`.
2. Run again:

   ```bash
   mvn test
   ```

3. **Done when:** you see **`BUILD SUCCESS`** and **13 tests run, 0 failures** (some runs may show 12 failures + 1 passing at the start; goal is all green).

## Step 5 — Push

Commit and **push** to your Classroom repo (`main` or the branch requested in the email).

**GitHub Actions:** the workflow may stay **red** until tests pass — then it should turn **green**. Red CI before you finish is expected.

---

## Time and scope

- **Typical time:** 1–3 hours depending on experience (spec is precise; no hidden trick questions).
- **Optional:** `src/main/resources/events.sample.json` — not required for grading; all official tests build `Event` in code.

## AI tools (Copilot, ChatGPT, etc.)

**Allowed.** We expect you to **explain** your solution, edge cases, and complexity in a live discussion. Solutions you cannot justify may be rejected in review.

## More detail

See **[CANDIDATE_GUIDE.md](CANDIDATE_GUIDE.md)** (FAQ, IDE tips, common errors).

## Still stuck?

Contact the hiring / R&D contact who sent the Classroom link — **do not** share your full solution publicly.
