🚀 KnitShop
KnitShop is a **Spring Boot**-based web application designed for selling and sharing knitting products. Users can share their knitted products, leave comments, and like posts. 🛠️
🏠 Project Structure
```
com.fatih.KnitShop
🔘 annotation
    🔧 OptionalFieldValidation    # Custom annotations
🔘 consts
    🔧 RecordStatus               # Record Status Constants
    🔧 UrlConst                   # URL Constants
🔘 controller
    🔧 CategoryController         # Category operations
    🔧 CommentController          # Comment operations
    🔧 CsrfController             # CSRF handling
    🔧 ImageController            # Image management
    🔧 LikeController             # Like operations
    🔧 PostController             # Post operations
🔘 dto
    🔧 CategoryDto               # Category DTO
    🔧 CommentDto                # Comment DTO
    🔧 ImageDto                  # Image DTO
    🔧 LikeDto                   # Like DTO
    🔧 PostDto                   # Post DTO
🔘 entity
    🔧 Category                  # Category Entity
    🔧 Comment                   # Comment Entity
    🔧 Image                     # Image Entity
    🔧 Like                      # Like Entity
    🔧 Post                      # Post Entity
🔘 exception
    🔧 GlobalExceptionHandler     # Global Exception Handling
🔘 repository
    🔧 CategoryRepository         # Category Repository
    🔧 CommentRepository          # Comment Repository
    🔧 ImageRepository            # Image Repository
    🔧 LikeRepository             # Like Repository
    🔧 PostRepository             # Post Repository
🔘 service
    🔧 CategoryService            # Category Service
    🔧 CommentService             # Comment Service
    🔧 ImageService               # Image Service
    🔧 LikeService                # Like Service
    🔧 PostService                # Post Service
🔘 util
    🔧 FileUtils                  # File Utility Class
🔘 KnitShopApplication           # Main application class
```
⚙️ Setup and Run
### 1️⃣ Requirements
- 🖥️ **Java 17+**
- 🌐 **Spring Boot 3+**
- 🗄️ **PostgreSQL / H2 Database**
### 2️⃣ Clone the Project
```sh
git clone https://github.com/fatihhozkurt/OrguEvim.git
cd OrguEvim
```
### 3️⃣ Install Dependencies
```sh
mvn clean install
```
### 4️⃣ Run the Application
```sh
mvn spring-boot:run
```
🔥 Features
✅ **Category Management** 🏷️
✅ **Commenting** 💬
✅ **Like System** ❤️
✅ **Image Uploading** 📸
✅ **JWT Authentication** 🔐
✅ **Spring Boot REST API** 🛠️
📌 API Usage
➕ Create a Post
```http
POST /api/posts
```
👥 **Request Body**
```json
{
  "title": "Handmade Knitted Scarf",
  "content": "This scarf is completely handmade.",
  "categoryId": 1
}
```
🔄 **Response**
```json
{
  "id": "1",
  "title": "Handmade Knitted Scarf",
  "content": "This scarf is completely handmade.",
  "likes": 0
}
```
🔍 Get a Specific Post
```http
GET /api/posts/{id}
```
🔄 **Response**
```json
{
  "id": "1",
  "title": "Handmade Knitted Scarf",
  "content": "This scarf is completely handmade.",
  "likes": 5
}
```
🎮 Database Operations
For PostgreSQL users, to view tables:
```sh
SELECT * FROM posts;
```
To access H2 Console, visit `http://localhost:8080/h2-console` and enter the required credentials.
🚀 Upcoming Features
I am currently working on integrating the following technologies into the project:
- **Redis Caching** for improving data retrieval speed 🏎️
- **Docker Containerization** for deployment flexibility 🐳
- **JWT & Spring Security** for enhanced authentication 🔐
- **Elasticsearch** for efficient search capabilities 🔍
These enhancements will improve the performance, security, and scalability of KnitShop. Stay tuned for updates! 🚀
🤝 Contributing
Feel free to open a **pull request** if you’d like to contribute.
📝 License
This project is licensed under the **MIT License**.

📌 Developed by **@fatihhozkurt**. 😊
