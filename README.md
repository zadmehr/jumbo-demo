# Jumbo Demo

## Build and Run

1. Clean and package the application:
    ```sh
    ./mvnw clean install package
    ```

2. Start the application using Docker Compose:
    ```sh
    docker compose up -d
    ```

3. Access the application:
    Open your browser and go to [http://localhost:8080/api/v1/nearest-stores?latitude=52.37867&longitude=4.883832](http://localhost:8080/api/v1/nearest-stores?latitude=52.37867&longitude=4.883832)