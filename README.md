# ExploreWithMe

ExploreWithMe is a Spring Boot application that allows users to share information about interesting events and find
companions for participation. It helps users efficiently plan their leisure time by providing a platform to organize and
participate in various events.

## Features

- Public API for searching and filtering events with options for sorting by event views (retrieved from the statistics
  service) or event dates.
- Private API accessible only to authorized users, which includes the following features:
    - Adding, editing, and viewing events.
    - Submitting requests for event participation.
    - Confirming event participation requests by the event creator.
- Administrative API for service administrators, which includes the following features:
    - Adding, modifying, and deleting event categories.
    - Creating, removing, and pinning event collections on the main page.
    - Moderating user-submitted events (publication or rejection).
    - User management (adding, activation, viewing, and deletion).
- Event creation and management with a lifecycle that includes creation, awaiting publication, publication, and
  publication cancellation.
- User registration and authentication.
- Event rating system.
- Statistics service to track event views, user IPs, and endpoint accesses.
- Predefined event categories and collections created by administrators.
- Event view counts provided by the statistics service.

## Data Model

The event lifecycle includes the following stages:

1. Creation
2. Awaiting publication (automatically set after event creation)
3. Publication (set by an administrator)
4. Cancellation of publication (set either by the administrator or the event initiator during the awaiting publication
   stage)

## Authentication and Authorization

Both ExploreWithMe services operate within a VPN (Virtual Private Network), which allows for secure communication
between the services and the external world. A network gateway is responsible for contacting the authentication and
authorization system and forwarding requests to the services. If the gateway allows a request to access the private or
administrative API, it means the request has successfully passed authentication and authorization.

## Statistics Service

The statistics service collects information on user interactions, such as the number of user requests to event lists and
the number of requests for detailed event information. This data is used to generate statistics about the application's
performance.

## Technologies Used

- Spring Boot
- Hibernate
- Docker
- PostgreSQL

## Getting Started

### Prerequisites

Ensure you have the following installed on your system:

- Java Development Kit (JDK) 11 or later
- Docker
- PostgreSQL

### Building the Project

1. Clone the repository:

   ```git clone https://github.com/23mser/Explore-with-me.git```

2. Change the current directory to the project root:

   ```cd java-explore-with-me```

3. Build the project using Maven:

   ```./mvnw clean install```

4. Run the Docker container for PostgreSQL:

   ```docker-compose up -d```

### Running the Application

Run the application using the following command:

```./mvnw spring-boot:run```

The application will start on port 8080. Access the public API at `http://localhost:8080`, and the administrative API
at `http://localhost:8080/admin`.
