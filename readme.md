# NewsApp Backend

This is the backend service for the **NewsApp** application, built using **Spring Boot**. It provides endpoints to fetch news articles based on categories from an external news API and serves them to the frontend.

## Features

- **Fetch News Articles by Category**: The backend exposes an endpoint to fetch news articles filtered by category.
- **Serve Articles to Frontend**: Provides data in the expected JSON format for consumption by the React frontend.

## Prerequisites

Before running the backend, make sure you have the following installed:

- **Java 8+** (Recommended: OpenJDK 11)
- **Maven** (or use the Maven wrapper provided)
- **IDE** (Optional): IntelliJ IDEA, Eclipse, or any text editor of your choice

## Getting Started

### Clone the Repository

```bash
git clone https://github.com/shaansuraj/insightify-springboot.git
cd insightify-springboot
```

### Set Up Configuration

1. **API Key for News API**: You'll need to set up an API key from a news service like [NewsAPI](https://newsapi.org/). After registering, get your API key and add it to the `application.properties` file.

Example:
```properties
newsapi.apiKey=your_news_api_key_here
```

2. **Application Properties**: You may configure the backend service by editing the `src/main/resources/application.properties` file. Here, you can set up things like the API key, default category, etc.

### Build the Project

You can build the project using Maven:

```bash
mvn clean install
```

### Run the Application

Run the application using the Maven command:

```bash
mvn spring-boot:run
```

Alternatively, if you are using an IDE, you can simply run the `NewsAppBackendApplication` class.

### Access the API

The backend will run on the default port `8000`. You can access the API at:

- `GET /news?category={category}`: Fetch articles filtered by the specified category.

Example:
- `http://localhost:8000/news?category=technology`

## API Documentation

### `GET /news?category={category}`

Fetches a list of articles based on the specified category.

#### Request Parameters:

- `category` (string): The category of news to fetch. Possible values include:
  - `general`
  - `business`
  - `entertainment`
  - `health`
  - `science`
  - `sports`
  - `technology`

#### Response Example:

```json
{
  "status": "ok",
  "articles": [
    {
      "source": {
        "id": null,
        "name": "TechCrunch"
      },
      "author": "John Doe",
      "title": "Latest Technology Trends",
      "description": "A roundup of the most exciting tech innovations this year.",
      "url": "https://techcrunch.com/...",
      "urlToImage": "https://techcrunch.com/image.jpg",
      "publishedAt": "2024-12-01T12:00:00Z",
      "content": "Tech content goes here..."
    },
    ...
  ]
}
```

### Error Handling

If an error occurs (e.g., invalid category or API issue), the backend will return a JSON response like this:

```json
{
  "status": "error",
  "message": "Failed to fetch articles."
}
```

### CORS (Cross-Origin Resource Sharing)

If you're developing the frontend locally (on a different port, like `3000`), you may encounter CORS issues. To resolve this, you can configure CORS in the backend by adding a filter or configuration class. You can also use the default Spring Boot CORS settings for development purposes.

Example configuration for CORS in Spring Boot:
```java
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000") // Frontend URL
                .allowedMethods("GET", "POST", "PUT", "DELETE");
    }
}
```

## Testing

You can test the endpoints using any API testing tool like **Postman** or **cURL**. For example, to test fetching articles:

```bash
curl -X GET "http://localhost:8000/news?category=technology"
```

### Unit Tests

The project comes with basic unit tests, which can be run using Maven:

```bash
mvn test
```

### Endpoints Summary

- `GET /news?category={category}`: Fetch articles for a specific category.

## Deployment

To deploy the backend to a server or cloud service like AWS, GCP, or Azure, you can build a JAR file and deploy it as a Spring Boot application.

To create a runnable JAR file:

```bash
mvn clean package
```

This will create a JAR file in the `target` directory, which can be deployed to your server.

## Contributing

Contributions are welcome! If you have any bug fixes or new features, feel free to fork the repository and create a pull request.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

