# Spring AI Code

A Spring Boot starter project that demonstrates integrating AI capabilities using the Spring AI framework. Ideal for experimenting with chat models, embeddings, RAG (retrieval-augmented generation), tool/function calls, and more.

---

## 🚀 Features

- Spring Boot integration using `org.springframework.ai`
- Support for chat, embeddings, and other AI model interfaces
- Configurable via `application.yml` or `config.yml`
- Includes tools/functions autoconfiguration and vector store support
- Clean, modular example code to get started quickly

---

## 🔧 Getting Started

### Prerequisites

- Java 17+
- Maven 3.8+
- (Optional) Docker if using vector stores or local model environments

### 1. Clone the repository

```bash
git clone https://github.com/namangarg2021/SpringAiCode.git
cd SpringAiCode
```

### 2. Set up your configuration

Create a `config.yml` or modify `application.yml` under `src/main/resources/`:

```yaml
tavily:
  api-key: YOUR_TAVILY_API_KEY

spring:
  ai:
    openai:
      api-key: YOUR_OPENAI_API_KEY
```

Alternatively, you can use environment variables to inject keys securely.

### 3. Run the application

```bash
mvn spring-boot:run
```

---

## 🧪 Usage

Once the app is running, you can test the AI endpoints via Postman, `curl`, or a browser.

#### Example:

```bash
curl -X GET "http://localhost:8080/ask?prompt=Hello"
```

Or try the following endpoints if available:

- `/chat`
- `/stream-chat`
- `/tools`

---

## 📁 Project Structure

```
src/
├── main/
│   ├── java/com/yourorg/
│   │   ├── config/             # Configuration classes
│   │   ├── controller/         # REST endpoints
│   │   ├── service/            # AI interaction logic
│   └── resources/
│       └── config.yml / application.yml
└── pom.xml
```

---

## 🔗 Resources

- [Spring AI Documentation](https://docs.spring.io/spring-ai/)
- [Spring AI GitHub](https://github.com/spring-projects/spring-ai)
- [LangChain4j GitHub](https://github.com/langchain4j/langchain4j)

---

## ✨ Happy Building with Spring AI! 🚀
