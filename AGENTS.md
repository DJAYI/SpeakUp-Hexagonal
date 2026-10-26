# AGENTS.md

Spring Boot 4.1.2 (Java 21, Maven) app `speakup` using **hexagonal architecture**, enforced by ArchUnit tests.

## Commands

- Build/test: `.\mvnw.cmd test`
- Run: `.\mvnw.cmd spring-boot:run`
- Single test: `.\mvnw.cmd -Dtest=<ClassName> test` (e.g. `-Dtest=HexagonalArchitectureTests`)
- Wrapper is `distributionType=only-script` (no `maven-wrapper.jar` committed); it downloads Maven 3.10.0 on first run.

## Architecture (enforced, not just convention)

Packages under `com.electiva.speakup`:

- `domain.model` — pure Java, no Spring, no dependencies on `application`/`infrastructure`.
- `application.port.in` — use cases as **interfaces** (`*UseCase`).
- `application.port.out` — repository/output ports as **interfaces** (`*Port`).
- `application.service` — implementations of the in-ports.
- `infrastructure.adapter.in` — REST controllers (`*Controller` must live here).
- `infrastructure.adapter.out.persistence.memory` — in-memory adapters implementing out-ports.

Rules verified by `src/test/java/architecture/` (run on every `mvn test`; break them and the build fails):
- `domain` must not depend on `infrastructure`, `application`, or `org.springframework..`.
- `application` must not depend on `infrastructure`; event publishing belongs to infrastructure, not `application`.
- `*UseCase` and `*Port` must be interfaces in their respective `port.in`/`port.out` packages.
- `*Controller` must reside in `infrastructure.adapter.in`.
- No dependency cycles in `domain.model` or top-level modules.

## Gotchas

- Uses `spring-boot-starter-webmvc`, NOT `spring-boot-starter-web` (Boot 4.x modular starters).
- Lombok is configured via explicit `annotationProcessorPaths` in `maven-compiler-plugin`; keep both `default-compile` and `default-testCompile` executions in sync.
- Persistence adapters are unfinished stubs (return `null`/empty); no real DB configured (`application.yaml` only sets the app name).
- Domain models use `@Builder`/`@SuperBuilder` + `@EqualsAndHashCode(onlyExplicitlyIncluded = true)` with `id` as the only field.
- `domain.model.answer.MultpleOptionAnswer` has an intentional "Multple" spelling — do not rename without checking usages.
