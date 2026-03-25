# GitHub Classroom — Nexpublica Java stage test

## Repository layout (Maven)

- Candidates run **`mvn test`** from the repository root.
- Java 17+ required (GitHub Actions uses Temurin 17).

## Creating a candidate starter (no solution on `main`)

If `main` includes the reference solution for CI:

1. Create branch **`candidate-starter`** from the commit **before** the solution, or strip the method body on a new branch:
   ```bash
   git checkout -b candidate-starter main
   # edit SessionAnalyzer: replace method body with TODO / UnsupportedOperationException
   git commit -am "chore: starter without solution for Classroom"
   git push -u origin candidate-starter
   ```
2. In Classroom, set the **default branch** of the assignment to `candidate-starter`, or use a **template repository** generated from that branch.

## Update workflow

```bash
git checkout main
# edit src/main/java/.../SessionAnalyzer.java and/or tests
mvn test
git push origin main
```

Re-copy or merge into `candidate-starter` when you change **tests** or **spec**; avoid merging solution into `candidate-starter`.

## Assignment settings

- **Individual** assignment recommended.
- Enable **feedback pull requests** if you want review before merge.
- **Private** student repos recommended.
