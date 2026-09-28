# Online Quiz Conducting System

Spring Boot REST API using Java 21, Spring Data JPA, and MySQL. Open this folder in Visual Studio Code and import it as a Maven project.

## Run it

1. Install a JDK 21 and Maven, then install MySQL Server and MySQL Workbench.
2. In Workbench, run `CREATE DATABASE online_quiz;` (the application URL can also create it when the MySQL account has permission).
3. Open this folder in VS Code and choose **Terminal → Run Task → Run Online Quiz API**. Enter your MySQL password in the secure prompt. The task supplies the local `root` username without saving your password in the project.
4. Hibernate creates/updates the tables; refresh Workbench's Schemas view to inspect them.

When the application is running, open Swagger UI at `http://localhost:8080/swagger-ui/index.html`. It lists the API operations and lets you send requests from the page with **Try it out**. The OpenAPI document is at `http://localhost:8080/v3/api-docs`.

The default connection is `localhost:3306`, schema `online_quiz`, username `root`. Change the username in `.vscode/tasks.json` if you use a different MySQL account. Never commit real database passwords.

## Use the API in Swagger UI

Open `http://localhost:8080/swagger-ui/index.html`, expand an operation, click **Try it out**, enter any required IDs and JSON body, then click **Execute**. Create a quiz first so the response provides a quiz ID and question IDs. Use those IDs to start an attempt, submit its answers, and view results.

The API base path is `/api`:

- `POST /quizzes` — create a quiz and its multiple-choice questions.
- `GET /quizzes` and `GET /quizzes/{quizId}` — list quizzes or view one. Correct answers are never included in quiz responses.
- `POST /quizzes/{quizId}/attempts` — start a timed attempt with `{ "studentName": "Asha" }`.
- `POST /quizzes/attempts/{attemptId}/submit` — submit answers as `{ "answers": { "1": 0, "2": 2 } }`. Keys are question IDs; values are zero-based option indexes.
- `GET /quizzes/{quizId}/results` — get scores for a quiz.

Example quiz request:

```json
{
  "title": "Java Basics",
  "description": "A short practice quiz",
  "durationMinutes": 15,
  "questions": [
    {
      "text": "Which keyword creates a subclass?",
      "options": ["implements", "extends", "imports", "package"],
      "correctOptionIndex": 1,
      "points": 2
    }
  ]
}
```

Question option indexes start at zero. An attempt can be submitted once and only before its quiz timer expires. A database uniqueness constraint prevents repeat attempts for the same quiz and student name. This starter has no login or authorization; student names are not verified identities, so add authentication before using it for real assessments.
