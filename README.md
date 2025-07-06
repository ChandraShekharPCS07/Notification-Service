# 📣 Notification Service

A **Java Spring Boot** application that consumes messages from a **Kafka** topic and sends SMS notifications using the **Twilio** API.

---

## 📑 Table of Contents

* 📜 Overview
* ⚙️ Prerequisites
* 🚀 Quick Start
  * 1️⃣ Clone the Repository
  * 2️⃣ Start Dependencies with Docker Compose
  * 3️⃣ Verify Services
  * 4️⃣ Run the Application
  * 5️⃣ Test with Postman
* 🧪 Postman Collection
* 🛠️ Configuration
  * 📦 Docker Compose Example
  * ⚙️ application.properties Example
  * 📨 Sample JSON Payload
* 🤝 Contributing
* 📬 Support
* 📄 License
* 📌 Notes

---

## 📜 Overview

This service consumes notification events from a Kafka topic and dispatches SMS messages via Twilio.  
It runs in a containerized environment using Docker Compose for local development.  
A Postman collection is included for easy API testing.

---

## ⚙️ Prerequisites

Make sure you have the following installed:

* Docker
* Docker Compose
* Java 21+
* Maven or Gradle
* Postman

---

## 🚀 Quick Start

### 1️⃣ Clone the Repository

```bash
git clone https://github.com/ChandraShekharPCS07/Notification-Service.git
cd Notification-Service
```

---

### 2️⃣ Start Dependencies with Docker Compose

Spin up the required services (e.g., Kafka):

```bash
docker-compose up -d
```

✅ Tip: Check running containers:

```bash
docker-compose ps
```

---

### 3️⃣ Verify Services

Check logs to ensure all services are healthy:

```bash
docker-compose logs -f
```

If your `docker-compose.yml` exposes any admin UIs (like Kafka UI), you can access them locally.

---

### 4️⃣ Run the Application

After the dependencies are running:

✅ Option A: Using Your IDE

* Open the project in IntelliJ IDEA (or your preferred IDE).
* Run the main Spring Boot application class.

✅ Option B: Using the Command Line

* With Maven:

```bash
./mvnw spring-boot:run
```

* With Gradle:

```bash
./gradlew bootRun
```

---

### 5️⃣ Test with Postman

* Open Postman.
* Import the collection:

```
postman/Notification-Service.postman_collection.json
```

* Ensure variables like `{{baseUrl}}` match your local server (e.g., http://localhost:8081).
* Use the Collection Runner or individual requests to test.

---

## 🧪 Postman Collection

The Postman assets are in:

```
postman/
├── Notification-Service.postman_collection.json
└── Notification-Service.postman_environment.json (optional)
```

Includes:

* Example notification payloads
* Health check endpoints
* Any custom APIs you expose

---

## 🛠️ Configuration

### 📦 Docker Compose Example

```yaml
version: '3.8'

services:
  kafka:
    image: bitnami/kafka:latest
    ports:
      - "9092:9092"
      - "9093:9093"
    environment:
      - KAFKA_KRAFT_CLUSTER_ID=abcdefghijklmno123456
      - KAFKA_CFG_PROCESS_ROLES=controller,broker
      - KAFKA_CFG_NODE_ID=1
      - KAFKA_CFG_CONTROLLER_LISTENER_NAMES=CONTROLLER
      - KAFKA_CFG_LISTENERS=PLAINTEXT://:9092,CONTROLLER://:9093
      - KAFKA_CFG_ADVERTISED_LISTENERS=PLAINTEXT://localhost:9092
      - KAFKA_CFG_CONTROLLER_QUORUM_VOTERS=1@localhost:9093
      - ALLOW_PLAINTEXT_LISTENER=yes
```

---

### ⚙️ application.properties Example

```properties
# Server
server.port=8081

# Database
spring.datasource.url=jdbc:postgresql://localhost:5432/notifications
spring.datasource.username=postgres
spring.datasource.password=pcs
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Kafka broker address
spring.kafka.bootstrap-servers=localhost:9092

# Consumer-specific settings
spring.kafka.consumer.group-id=notification-consumer-group
spring.kafka.consumer.auto-offset-reset=earliest

# Serialization/Deserialization
spring.kafka.producer.key-serializer=org.apache.kafka.common.serialization.StringSerializer
spring.kafka.producer.value-serializer=org.springframework.kafka.support.serializer.JsonSerializer
spring.kafka.consumer.key-deserializer=org.apache.kafka.common.serialization.StringDeserializer
spring.kafka.consumer.value-deserializer=org.springframework.kafka.support.serializer.JsonDeserializer
spring.kafka.consumer.properties.spring.json.trusted.packages=*

# Topic
app.kafka.topic.notification-events=notification_events

# Twilio
twilio.account-sid=your_twilio_account_sid
twilio.auth-token=your_twilio_auth_token
twilio.from-number=your_twilio_from_number
```

✅ Tip: Use environment variables or local `.env` files to store sensitive values securely.

---

### 📨 Sample JSON Payload

Below is an example notification event:

```json
{
  "eventId": "{{eventId}}",
  "userId": "{{userPhoneNumber}}",
  "eventType": "APPOINTMENT_REMINDER",
  "payload": {
    "patientName": "{{patientName}}",
    "appointmentTime": "{{appointmentTime}}"
  },
  "createdAt": "{{createdAt}}"
}
```

✅ Use this format to send test events through Postman.

---

## 🤝 Contributing

Contributions are welcome!

* Fork this repository.
* Create a feature branch: `git checkout -b feature/your-feature`.
* Commit your changes.
* Push to your fork.
* Open a **Pull Request**.

---

## 📬 Support

* Open an Issue: https://github.com/ChandraShekharPCS07/Notification-Service/issues
* Or contact: chandrashekharpcs07@gmail.com

---

## 📄 License

This project is licensed under the MIT License.
See the LICENSE file for details.

---

## 📌 Notes

✅ Always ensure Docker services are running before starting the Spring Boot app.  
✅ Use environment variables to manage secrets securely.  
✅ Avoid committing any sensitive data to version control.  
✅ Keep your Postman environment variables in sync with your local setup.

---

## 📝 Changelog
* **v1.0.0** - Initial release with basic Kafka consumer and Twilio SMS integration.


