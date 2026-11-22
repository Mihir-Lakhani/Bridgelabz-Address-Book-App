# Address Book App

A simple Spring Boot REST API built through the setup, service, application-settings, validation, and exception-handling sections of the supplied BridgeLabz PDFs. Code lives on [develop](https://github.com/Mihir-Lakhani/Bridgelabz-Address-Book-App/tree/develop); `main` contains this README only.

## Scope and use cases

Each use case was built in a separate `feature/uc-*` branch from `develop` and merged with `--no-ff`. The PDFs restart numbering in each section, so this repository uses one continuous sequence.

| UC | PDF section | Implementation |
| --- | --- | --- |
| 01 | Setup | Spring Boot project and Maven dependencies |
| 02 | REST controller | GET, GET by ID, POST, PUT, and DELETE routes |
| 03 | DTO and model | Separate contact request DTO and model |
| 04 | Service layer | Controller delegates business work to a service |
| 05 | In-memory data | CRUD over a list with generated IDs |
| 06 | Lombok | `@Data` generates DTO accessors |
| 07 | Logging | `@Slf4j` logs create, update, and delete |
| 08 | Profiles | Dev and prod logging properties |
| 09 | Database settings | H2 dev configuration and MySQL prod environment variables |
| 10 | Validation | Validate contact name on create and update |
| 11 | Validation errors | Friendly HTTP 400 JSON error |
| 12 | Missing ID | Custom exception and HTTP 404 JSON error |

**Current storage:** the service stores contacts in an in-memory list. The database settings demonstrate profiles and environment variables; the CRUD code does **not** use JDBC or save contacts to MySQL yet. Records disappear on application restart. This matches the supplied sections, which say database persistence comes later.

## Project flow

```mermaid
flowchart LR
    Client["Postman or curl"] --> Controller["AddressBookController"]
    Controller -->|validated AddressBookDto| Service["AddressBookService"]
    Service --> Model["AddressBookEntry model"]
    Service --> List[("In-memory list")]
    Service --> Controller
    Controller --> Client
    Errors["AddressBookExceptionHandler"] -->|400 or 404 JSON| Client
    Controller -.-> Errors
```

| Package or file | Purpose |
| --- | --- |
| `controller/AddressBookController` | Maps HTTP methods and paths |
| `dto/AddressBookDto` | Reads JSON and validates the name |
| `model/AddressBookEntry` | Contact ID, name, phone number, city |
| `service/AddressBookService` | CRUD, ID assignment, logging |
| `exception/*` | Validation and missing-contact responses |
| `application-*.properties` | Dev/prod logging and database settings |

## Run

Use Java 21 or newer and Maven, or IntelliJ IDEA's Maven tool window.

```bash
git switch develop
mvn spring-boot:run
```

The default `dev` profile starts on `http://localhost:8080` with H2 configuration and no external server. Run tests with `mvn clean test`.

To run with the `prod` profile, start a MySQL server and create a database and user first. Set these environment variables before starting:

| Variable | Meaning | Example |
| --- | --- | --- |
| `MYSQL_HOST` | MySQL host; defaults to localhost | `localhost` |
| `MYSQL_PORT` | MySQL port; defaults to 3306 | `3306` |
| `ADDRESS_DB_NAME` | Database name; defaults to address_book_db | `address_book_db` |
| `ADDRESS_DB_USERNAME` | MySQL username; required | Your DB user |
| `ADDRESS_DB_PASSWORD` | MySQL password; required | Your DB password |

Example PowerShell setup:

```powershell
$env:SPRING_PROFILES_ACTIVE = "prod"
$env:ADDRESS_DB_USERNAME = "your-user"
$env:ADDRESS_DB_PASSWORD = "your-password"
mvn spring-boot:run
```

The prod profile writes logs to `logs/address-book.log`. It establishes a MySQL datasource, but contact CRUD remains in memory.

## REST API

Base path: `/addressbookservice`. Contact JSON has `id`, `name`, `phoneNumber`, and `city`. Only `name` is required in the current sections.

| Method | Path | Request body | Success |
| --- | --- | --- | --- |
| GET | `/` | None | `200`, all contacts |
| GET | `/get/{id}` | None | `200`, one contact |
| POST | `/create` | Name, optional phone number/city | `201`, created contact with ID |
| PUT | `/update/{id}` | Name, optional phone number/city | `200`, updated contact |
| DELETE | `/delete/{id}` | None | `204` |

A missing ID returns `404 {"error":"Contact not found: 1"}`. Invalid create/update input returns `400` with an `error` message. The name must start with a capital letter and have at least three letters.

### Try it with curl

```bash
curl http://localhost:8080/addressbookservice/
curl -X POST http://localhost:8080/addressbookservice/create -H "Content-Type: application/json" -d '{"name":"Lisa","phoneNumber":"1234567890","city":"Pune"}'
curl http://localhost:8080/addressbookservice/get/1
curl -X PUT http://localhost:8080/addressbookservice/update/1 -H "Content-Type: application/json" -d '{"name":"Mark","phoneNumber":"9876543210","city":"Mumbai"}'
curl -X DELETE http://localhost:8080/addressbookservice/delete/1
```

```mermaid
flowchart TD
    Request["POST or PUT JSON"] --> Validation{"DTO valid?"}
    Validation -->|No| BadRequest["400 with error message"]
    Validation -->|Yes| Lookup{"Existing ID needed?"}
    Lookup -->|No| Create["Create contact in list"]
    Lookup -->|Yes| Found{"ID found?"}
    Found -->|No| NotFound["404 with error message"]
    Found -->|Yes| Update["Update contact in list"]
    Create --> Response["Return contact JSON"]
    Update --> Response
```

## Verification and PDF corrections

`mvn clean test` passes two HTTP tests covering CRUD, validation, and missing-ID handling. One Address Book PDF is an exact duplicate and was counted once. The PDFs include an outdated MySQL driver class, example hard-coded credentials, and a copy-paste reference to Employee Payroll in the Address Book section; this project uses the current MySQL connector, environment variables, and contact-specific names. Live MySQL connectivity was not part of the automated test because MySQL is optional for the current in-memory CRUD stage.
