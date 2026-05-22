# E-Commerce Marketplace Backend

A robust Spring Boot-based REST API for an online e-commerce platform that enables users to list, browse, and purchase used items locally. Built with modern Java practices, comprehensive validation, and a RESTful architecture.

## 🚀 Features

- **User Management**: User registration, authentication, and profile management
- **Item Listing**: Create, read, update, and delete item listings
- **Category Management**: Organize items by categories
- **Contact Methods**: Multiple contact options (Email, Phone, WhatsApp)
- **Listing Status Tracking**: Track item status (Active, Sold, Expired, Pending)
- **Data Validation**: Comprehensive input validation and error handling
- **RESTful API**: Clean, intuitive REST endpoints following best practices
- **Database Persistence**: JPA/Hibernate for object-relational mapping
- **Security**: Password hashing, input validation, and secure data handling

## 🛠 Technology Stack

- **Java 17+**
- **Spring Boot 3.x**
- **Spring Data JPA**
- **PostgreSQL 12+**
- **Maven** (Build tool)
- **Lombok** (Reduce boilerplate)
- **Validation API** (Input validation)

## 📋 Project Structure

```
E-commerce-backend/
├── src/
│   ├── main/
│   │   ├── java/com/example/E_commerce/backend/
│   │   │   ├── ECommerceBackendApplication.java     (Entry point)
│   │   │   ├── config/
│   │   │   │   └── SecurityConfig.java              (Security configuration)
│   │   │   ├── controller/                           (REST Controllers)
│   │   │   │   ├── UserController.java
│   │   │   │   ├── CategoryController.java
│   │   │   │   ├── ItemController.java
│   │   │   │   ├── ContactMethodController.java
│   │   │   │   └── ListingStatusController.java
│   │   │   ├── model/                                (JPA Entities)
│   │   │   │   ├── User.java
│   │   │   │   ├── Category.java
│   │   │   │   ├── Item.java
│   │   │   │   ├── ContactMethod.java
│   │   │   │   └── ListingStatus.java
│   │   │   ├── repository/                           (Data Access Layer)
│   │   │   │   ├── UserRepository.java
│   │   │   │   ├── CategoryRepository.java
│   │   │   │   ├── ItemRepository.java
│   │   │   │   ├── ContactMethodRepository.java
│   │   │   │   └── ListingStatusRepository.java
│   │   │   └── service/                              (Business Logic)
│   │   │       ├── UserService.java
│   │   │       ├── CategoryService.java
│   │   │       ├── ItemService.java
│   │   │       ├── ContactMethodService.java
│   │   │       └── ListingStatusService.java
│   │   └── resources/
│   │       ├── application.properties               (Configuration)
│   │       ├── db/migration/                        (Database migrations)
│   │       ├── static/                              (Static files)
│   │       └── templates/                           (Templates)
│   └── test/
│       └── java/com/example/E_commerce/backend/
│           └── ECommerceBackendApplicationTests.java
├── pom.xml                                           (Maven configuration)
├── mvnw & mvnw.cmd                                   (Maven wrapper)
└── README.md

```

## 🔧 Installation

### Prerequisites

- Java 17 or higher
- Maven 3.6.0 or higher (or use the included mvnw)
- PostgreSQL 12 or higher

### Steps

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/E-commerce-backend.git
   cd E-commerce-backend
   ```

2. **Configure Database**
   
   Edit `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/e-commerce_db
   spring.datasource.username=postgres
   spring.datasource.password=your_db_password
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
   ```

3. **Build the project**
   ```bash
   ./mvnw clean install
   ```
   Or on Windows:
   ```cmd
   mvnw.cmd clean install
   ```

4. **Run the application**
   ```bash
   ./mvnw spring-boot:run
   ```
   Or on Windows:
   ```cmd
   mvnw.cmd spring-boot:run
   ```

5. **Access the API**
   
   The API will be available at: `http://localhost:8080`

## 📚 API Documentation

### Base URL
```
http://localhost:8080/api
```

### Core Endpoints

#### Users (`/api/users`)
- `POST /api/users` - Create new user
- `GET /api/users` - List all active users
- `GET /api/users/{id}` - Get user by ID
- `GET /api/users/{id}/exists` - Check if user exists
- `GET /api/users/active/list` - List active users
- `PUT /api/users/{id}` - Update user
- `DELETE /api/users/{id}` - Deactivate user

#### Categories (`/api/categories`)
- `POST /api/categories` - Create category
- `GET /api/categories` - List all categories
- `GET /api/categories/{id}` - Get category by ID
- `GET /api/categories/{id}/exists` - Check if category exists
- `PUT /api/categories/{id}` - Update category
- `DELETE /api/categories/{id}` - Delete category

#### Items (`/api/items`)
- `POST /api/items` - Create item listing
- `GET /api/items` - List all items
- `GET /api/items/{id}` - Get item by ID
- `GET /api/items/{id}/exists` - Check if item exists
- `PUT /api/items/{id}` - Update item
- `DELETE /api/items/{id}` - Delete item

#### Listing Status (`/api/listing-statuses`)
- `GET /api/listing-statuses` - List all statuses
- `GET /api/listing-statuses/{id}` - Get status by ID
- `POST /api/listing-statuses` - Create status
- `PUT /api/listing-statuses/{id}` - Update status
- `DELETE /api/listing-statuses/{id}` - Delete status

#### Contact Methods (`/api/contact-methods`)
- `GET /api/contact-methods` - List all contact methods
- `GET /api/contact-methods/{id}` - Get contact method by ID
- `POST /api/contact-methods` - Create contact method
- `PUT /api/contact-methods/{id}` - Update contact method
- `DELETE /api/contact-methods/{id}` - Delete contact method

### Example Requests

**Create User**
```bash
POST /api/users
Content-Type: application/json

{
  "email": "john@example.com",
  "passwordHash": "SecurePass123!",
  "displayName": "John Doe"
}
```

**Response (201 Created)**
```json
{
  "userId": 1,
  "email": "john@example.com",
  "displayName": "John Doe",
  "createdAt": "2026-05-21T21:17:11.387448",
  "isActive": true
}
```

**Create Item Listing**
```bash
POST /api/items
Content-Type: application/json

{
  "user": { "userId": 1 },
  "category": { "categoryId": 1 },
  "status": { "statusId": 1 },
  "contactMethod": { "contactMethodId": 1 },
  "title": "iPhone 12",
  "description": "Used iPhone 12 in good condition",
  "condition": "Good",
  "price": 500.00,
  "isFree": false,
  "pickupLocation": "Downtown",
  "imageUrl": "https://example.com/image.jpg",
  "expireAt": "2026-06-21"
}
```

## 🔐 Validation Rules

### User
- **Email**: Valid format, must be unique
- **Password**: Min 8 chars, 1 uppercase, 1 lowercase, 1 number, 1 special character
- **Display Name**: 2-50 characters

### Category
- **Name**: 2-50 characters, must be unique
- **Description**: Optional

### Item
- **Title**: 3-200 characters
- **Price**: Must be > 0 if not free
- **Condition**: Valid condition value
- **Category**: Must exist
- **User**: Must exist
- **Status**: Must exist
- **Contact Method**: Must exist

### Contact Method
- **Method Type**: EMAIL, PHONE, or WHATSAPP
- **Contact Value**: Must be valid for type
- **User**: Must exist

## ⚙️ Configuration

### Application Properties

Edit `src/main/resources/application.properties`:

```properties
# Server Configuration
server.port=8080
server.servlet.context-path=/

# Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/e-commerce_db
spring.datasource.username=postgres
spring.datasource.password=your_password
spring.datasource.driver-class-name=org.postgresql.Driver

# JPA/Hibernate Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.properties.hibernate.format_sql=true

# Application Name
spring.application.name=E-Commerce Backend
```

## 🗄️ Database Setup

### Create Database

```sql
CREATE DATABASE e-commerce_db;
```

Connect to the database:
```bash
psql -U postgres -d e-commerce_db
```

The application will automatically create tables using Hibernate (set `spring.jpa.hibernate.ddl-auto=update`).

### Sample Data

Reference data is typically added via API:

```bash
# Create status
POST /api/listing-statuses
{"statusName": "ACTIVE"}
{"statusName": "SOLD"}
{"statusName": "EXPIRED"}
{"statusName": "PENDING"}

# Create category
POST /api/categories
{"name": "Electronics", "description": "Electronic items"}
{"name": "Furniture", "description": "Furniture items"}
```

## 🧪 Testing

Run tests with:
```bash
./mvnw test
```

Or on Windows:
```cmd
mvnw.cmd test
```

## 📖 Additional Documentation

- [API Design Guide](./API_DESIGN_GUIDE.md) - Detailed API design decisions and philosophy
- [Frontend Integration Guide](./FRONTEND_INTEGRATION.md) - How to integrate with frontend
- [Quick Reference](./QUICK_REFERENCE.md) - Quick API reference
- [Documentation Index](./DOCUMENTATION_INDEX.md) - Index of all documentation

## 🚢 Deployment

### Building for Production

```bash
./mvnw clean package -DskipTests
```

This creates a JAR file in `target/` directory.

### Running the JAR

```bash
java -jar target/E-commerce-backend-0.0.1-SNAPSHOT.jar
```

## 🤝 Contributing

1. Create a feature branch (`git checkout -b feature/AmazingFeature`)
2. Commit your changes (`git commit -m 'Add AmazingFeature'`)
3. Push to the branch (`git push origin feature/AmazingFeature`)
4. Open a Pull Request

## 📝 Commit History

Recent commits:
- ✅ Add ContactMethod entity, repository, service, and controller
- ✅ Update Category and User service and controller
- ✅ Add Item entity, repository, service, and controller

## 🐛 Troubleshooting

### Database Connection Issues
- Verify PostgreSQL is running
- Check connection string in `application.properties`
- Ensure database exists: `CREATE DATABASE "e-commerce_db";`
- Verify PostgreSQL user and password are correct
- Check if PostgreSQL is listening on port 5432

### Build Failures
- Clear cache: `./mvnw clean`
- Update dependencies: `./mvnw dependency:resolve`
- Check Java version: `java -version`

### Port Already in Use
- Change port in `application.properties`: `server.port=8081`
- Or kill process using port 8080

## 📞 Support

For issues and questions:
- Create an issue in the repository
- Check existing issues and documentation
- Review API Design Guide for endpoint details

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 👨‍💻 Author

Your Name / Your Team

---

**Last Updated**: May 22, 2026
**Backend Status**: ✅ Complete and Ready for Integration
**Next Phase**: Frontend Development & Integration
