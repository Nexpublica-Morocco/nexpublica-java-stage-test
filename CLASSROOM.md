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

## CI expectations (campaign launch)

- **Template repo `main`** (reference solution) and **`mvn test` green** → GitHub Actions should be **green** before you duplicate the assignment.
- **Student repos** created from a **starter** branch (`candidate-starter` or equivalent) will show **CI red** until they implement `SessionAnalyzer` and push passing tests. That is normal; brief candidates in the invite email if you want to avoid panic.
- Point the Classroom assignment **default branch** to the branch that has the **TODO** body, not the reference `main` with the solution, unless you intentionally distribute the solved version (not recommended).

## After you update tests or spec on `main`

1. Run `mvn test` on `main` and push.
2. Rebase or cherry-pick **only** non-solution changes onto `candidate-starter`, or regenerate `candidate-starter` from the updated starter commit — **do not merge `main` into `candidate-starter`** if that would ship the reference implementation.
