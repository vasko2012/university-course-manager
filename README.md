# University Course Manager (Spring Boot)

A server-rendered web app for managing university courses, teachers, students and grades. It was built during the *Web Programming* course at FINKI and covers the classic Spring stack end to end, from servlets up to Spring MVC, JPA and Spring Security.

## Features

- **Courses:** list (sorted by name), create, edit and delete; each course has a teacher, a type (winter, summer, mandatory, elective) and enrolled students
- **Search** across courses, teachers and students at once
- **Grades:** record a grade for a student in a course, with a timestamp
- **Accounts:** register and log in (passwords hashed with BCrypt); logout clears the session
- **Roles:** `ROLE_USER` and `ROLE_ADMIN`; only admins can change other users' roles, and the first account registered becomes the admin
- **Servlets and a filter:** the earlier labs, built with plain `@WebServlet`/`@WebFilter` before moving to Spring MVC

## Tech stack

| Layer | Used |
|---|---|
| Web | Spring MVC controllers, Thymeleaf templates, Java Servlets and Filters (`javax.servlet`) |
| Data | Spring Data JPA / Hibernate, PostgreSQL (prod) and H2 in-memory (test) |
| Security | Spring Security: form login, custom `AuthenticationProvider`, BCrypt, role-based access |
| Testing | JUnit, Selenium (HtmlUnit driver) page-object tests |
| Build | Maven, Java 17, Spring Boot 2.7 |

## Project structure

```
src/main/java/mk/ukim/finki/wp/lab
├── model/         JPA entities: Course, Student, Teacher, Grade, User (+ enums)
├── repository/    Spring Data JPA repositories (jpa/) and the earlier in-memory ones (impl/)
├── service/       Business logic interfaces and implementations
├── web/
│   ├── controller/   Spring MVC controller for /courses
│   ├── servlet/      Plain servlets from the earlier labs
│   └── filter/       Servlet filter
├── config/        Spring Security setup and the database AuthenticationProvider
└── converter/     Converter for the Teacher full-name value object
src/main/resources/templates   Thymeleaf views
src/test/java                  JUnit and Selenium page-object tests
```

## Running locally

**Quickest: in-memory H2 database, no setup**

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=test
```

**With PostgreSQL (the default `prod` profile)**: create a database called `university`, then:

```bash
export DB_PASSWORD=your_password    # optional: DB_URL, DB_USERNAME
./mvnw spring-boot:run
```

Then open the app (port `8081` on prod, `8080` on test) at `/courses`, register an account (the first one becomes admin) and log in.
