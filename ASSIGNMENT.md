# Java internship test — quick start (candidates)

## Before you start

1. **Repository check:** at the **root** of your repo you must see **`pom.xml`**, **`src/main/java`**, and **`src/test/java`**.  
   If you only see `events.json` and a `java/` folder without Maven, the assignment was created from the **wrong starter** — ask your contact to fix the GitHub Classroom assignment (starter must be `nexpublica-java-stage-test`).

2. **Install:** **JDK 17+** and **Apache Maven 3.8+** ([Adoptium Temurin](https://adoptium.net/), [Maven install](https://maven.apache.org/install.html)).

## What to do

1. Open `src/main/java/com/nexpublica/stage/SessionAnalyzer.java` and read the **class-level Javadoc** (full specification).
2. Implement `totalSessionSecondsPerUser` until all tests pass.
3. Run from the repository root:

   ```bash
   mvn test
   ```

4. Push your commits to your GitHub Classroom repository.

## Optional

- `src/main/resources/events.sample.json` — optional practice; **graded tests** build `Event` objects in code (see `SessionAnalyzerTest`).

## AI tools

Allowed (Copilot, ChatGPT, etc.). You should be ready to **explain** your code and edge cases in an interview.

## Help

- Failing tests with `UnsupportedOperationException` and `TODO` → you have not implemented the method yet (expected at the start).
- `mvn` not found → install Maven or use your IDE’s bundled Maven with the same command.
