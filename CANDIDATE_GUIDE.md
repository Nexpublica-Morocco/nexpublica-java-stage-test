# Candidate guide — Java session analytics test

## What we assess

- You read a **written spec** (Javadoc) and implement **exact** behaviour.
- You use **Java 17**, **Maven**, and **JUnit** like in a real team.
- You can **run tests locally** and iterate until green.

We do **not** grade JSON parsing or UI — only `SessionAnalyzer.totalSessionSecondsPerUser`.

---

## FAQ

### Why does `mvn test` fail immediately?

The starter throws `UnsupportedOperationException` until you implement the method. After a correct implementation, all tests should pass.

### One test passes but twelve fail — is that OK?

Yes at the beginning. The test that checks **null input** can pass before you implement the rest. Aim for **13 tests, 0 failures** at the end.

### May I change `Event.java` or `SessionAnalyzerTest.java`?

**No.** Treat tests as a black box. Only edit `SessionAnalyzer.java` (unless an instructor explicitly says otherwise).

### Is `events.sample.json` mandatory?

**No.** It is for optional practice. Grading uses programmatic `Event` instances in tests.

### CI is red on GitHub — did I break something?

Not necessarily. **Red CI** while you are still implementing is normal. After you push passing tests, the **Java CI** workflow should succeed.

### Can I use AI?

**Yes**, but you must be able to **explain** your code and defend trade-offs in an interview.

---

## IDE tips

### IntelliJ IDEA

1. **File → Open** → select the folder containing `pom.xml`.
2. Let Maven import the project.
3. Open `SessionAnalyzer.java`, implement, then **right-click** `SessionAnalyzerTest` → **Run 'SessionAnalyzerTest'**  
   Or use the Maven tool window: **Lifecycle → test**.

### VS Code

1. Install **Extension Pack for Java**.
2. Open the repo root (with `pom.xml`).
3. Use the **Testing** view or terminal `mvn test`.

---

## Common errors

| Symptom | What to do |
|---------|------------|
| `mvn` is not recognized | Install Maven and add it to `PATH`, or run tests from the IDE. |
| `JAVA_HOME` / wrong Java version | Point to JDK **17+**. |
| `package com.nexpublica.stage does not exist` | Run commands from repo **root** (where `pom.xml` is). |
| Tests pass locally but fail on GitHub | Push all commits; check you did not change test files; use JDK 17 semantics. |

---

## Specification reminder (not a substitute for Javadoc)

The **authoritative** rules are in `SessionAnalyzer` Javadoc. In short:

- Sort events by time; **stable** order when timestamps tie (use input index).
- Handle **login** / **logout** (case-insensitive); ignore unknown actions.
- Durations in **whole seconds**, sub-second part **truncated toward zero**.
- If a user is still logged in after all events, close the session at the **latest timestamp in the entire feed** (not only that user’s events).

If your implementation disagrees with the Javadoc, **the Javadoc + tests win**.
