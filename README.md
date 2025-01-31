# 🚀 KnitShop

KnitShop is a **Spring Boot**-based web application designed for knitting social media platform. Users can follow/unfollow each other, share their knitted products, comment/like posts, reply to comments and like replies. 🛠️

## 🏠 Project Structure

`com.fatih.KnitShop`

### 🔘 annotation
  - 🔧 `OptionalFieldValidation`    # Custom annotations for dto validations

### 🔘 consts
  - 🔧 `RecordStatus`               # Record Status Constants for soft delete mechanism
  - 🔧 `UrlConst`                   # URL Constants for API layer metgods

### 🔘 controller
  - 🔧 `CategoryController`         # Category operations
  - 🔧 `CommentController`          # Comment operations
  - 🔧 `CsrfController`             # CSRF handling
  - 🔧 `ImageController`            # Image management
  - 🔧 `LikeController`             # Like operations
  - 🔧 `PostController`             # Post operations

### 🔘 dto
  - 🔧 `CategoryDto`               # Category DTO
  - 🔧 `CommentDto`                # Comment DTO
  - 🔧 `ImageDto`                  # Image DTO
  - 🔧 `LikeDto`                   # Like DTO
  - 🔧 `PostDto`                   # Post DTO

### 🔘 entity
  - 🔧 `Category`                  # Category Entity
  - 🔧 `Comment`                   # Comment Entity
  - 🔧 `Image`                     # Image Entity
  - 🔧 `Like`                      # Like Entity
  - 🔧 `Post`                      # Post Entity

### 🔘 exception
  - 🔧 `GlobalExceptionHandler`     # Global Exception Handling

### 🔘 repository
  - 🔧 `CategoryRepository`         # Category Repository
  - 🔧 `CommentRepository`          # Comment Repository
  - 🔧 `ImageRepository`            # Image Repository
  - 🔧 `LikeRepository`             # Like Repository
  - 🔧 `PostRepository`             # Post Repository

### 🔘 service
  - 🔧 `CategoryService`            # Category Service
  - 🔧 `CommentService`             # Comment Service
  - 🔧 `ImageService`               # Image Service
  - 🔧 `LikeService`                # Like Service
  - 🔧 `PostService`                # Post Service

### 🔘 KnitShopApplication           # Main application class

## ⚙️ Setup and Run

### 1️⃣ Requirements
- 🖥️ **Java 17+**
- 🌐 **Spring Boot 3+**
- 🗄️ **PostgreSQL**

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

## 🔥 Features
✅ **Category Management** 🏷️
✅ **Comment and Reply System** 💬
✅ **Post, Comment and Reply Like System** ❤️
✅ **Image Uploading** 📸
✅ **JWT Authentication** 🔐
✅ **Spring Boot REST API** 🛠️
✅ **Follow System** 🛠️

## 📌 API Usage

### ➕ Create a Post
```http
POST /post
```
👥 **Request Body**
```json
{
  "postTitle": "Erkek Arkadaşınıza Sevgi Dolu Bir Atkı Örmek İçin Adım Adım Tarif",
  "postIngredients": "Öncelikle sevgili. İplik: Orta kalınlıkta (4 numara) yumuşak bir yün iplik seçin. Rengi, erkek arkadaşınızın tarzına uygun bir şey olsun. Örneğin, lacivert, gri veya bordo genelde şık durur. Şiş: 5 mm örgü şişi başlangıç için ideal. Makas: İpliği kesmek için. İğne: Örgüyü bitirdikten sonra uçları saklamak için kalın bir iğne.",
  "postContent": "Herkese merhaba! Bu yazımda, erkek arkadaşınıza sıcacık ve sevgi dolu bir atkı örmek için hem kolay hem de eğlenceli bir tarif paylaşacağım. El emeği bir hediye, sevginizi ifade etmenin en güzel yollarından biri, değil mi? O zaman başlayalım! 💙\n\nMalzemeler:\n- İplik: Orta kalınlıkta (4 numara) yumuşak bir yün iplik seçin. Rengi, erkek arkadaşınızın tarzına uygun bir şey olsun. Örneğin, lacivert, gri veya bordo genelde şık durur.\n- Şiş: 5 mm örgü şişi başlangıç için ideal.\n- Makas: İpliği kesmek için.\n- İğne: Örgüyü bitirdikten sonra uçları saklamak için kalın bir iğne.\n\nHazırlık:\n1. Ölçü Belirleme: Atkının uzunluğu genelde 150-180 cm arasında olur. Ancak, erkek arkadaşınızın boyuna göre bunu ayarlayabilirsiniz. Genişlik için 20-25 cm iyidir.\n2. Deneme İlmekleri: Seçeceğiniz iplikle 10 ilmek atıp küçük bir parça örerek ipliğin ve şişin uyumuna bakabilirsiniz.\n\nÖrgü Adımları:\n1. İlmek Atma: Şişlerinize 40 ilmek atın.\n2. Örgü Deseni: Atkınızın düz ve modern bir görünümde olması için \"haroşa örgü\" tekniğini kullanacağız.\n3. Uzunluğu Ayarlama: İstediğiniz uzunluğa ulaşana kadar haroşa örgü örmeye devam edin.\n4. Bitirme: İlmekleri kapatın ve uçları iğne yardımıyla saklayın.\n\nEkstra Dokunuşlar:\n- Püskül: Her iki ucuna küçük püsküller ekleyebilirsiniz.\n- Sevgi Notu: Minik bir kart yazıp atkıya iliştirerek sürprizinizi daha da tatlı hale getirebilirsiniz.",
  "categoryId": "0210941f-c347-4bd4-87c2-6c22bba14ecc",
  "youtubeLink": "https://www.youtube.com/watch?v=example",
  "postImages": [
    {
      "imagePath": "https://example.com/image1.jpg"
    },
    {
      "imagePath": "https://example.com/image2.jpg"
    }
  ],
  "ownerId": "ce382d00-87ad-4645-b27d-311965bc4714"
}
```
🔄 **Response**
```json
{
    "postId": "14e43ac1-f5c4-4db0-a225-0e0a8c7fb486",
    "createTime": "2025-01-31T01:57:09.1965557",
    "postTitle": "Erkek Arkadaşınıza Sevgi Dolu Bir Atkı Örmek İçin Adım Adım Tarif",
    "postContent": "Herkese merhaba! Bu yazımda, erkek arkadaşınıza sıcacık ve sevgi dolu bir atkı örmek için hem kolay hem de eğlenceli bir tarif paylaşacağım. El emeği bir hediye, sevginizi ifade etmenin en güzel yollarından biri, değil mi? O zaman başlayalım! 💙\n\nMalzemeler:\n- İplik: Orta kalınlıkta (4 numara) yumuşak bir yün iplik seçin. Rengi, erkek arkadaşınızın tarzına uygun bir şey olsun. Örneğin, lacivert, gri veya bordo genelde şık durur.\n- Şiş: 5 mm örgü şişi başlangıç için ideal.\n- Makas: İpliği kesmek için.\n- İğne: Örgüyü bitirdikten sonra uçları saklamak için kalın bir iğne.\n\nHazırlık:\n1. Ölçü Belirleme: Atkının uzunluğu genelde 150-180 cm arasında olur. Ancak, erkek arkadaşınızın boyuna göre bunu ayarlayabilirsiniz. Genişlik için 20-25 cm iyidir.\n2. Deneme İlmekleri: Seçeceğiniz iplikle 10 ilmek atıp küçük bir parça örerek ipliğin ve şişin uyumuna bakabilirsiniz.\n\nÖrgü Adımları:\n1. İlmek Atma: Şişlerinize 40 ilmek atın.\n2. Örgü Deseni: Atkınızın düz ve modern bir görünümde olması için \"haroşa örgü\" tekniğini kullanacağız.\n3. Uzunluğu Ayarlama: İstediğiniz uzunluğa ulaşana kadar haroşa örgü örmeye devam edin.\n4. Bitirme: İlmekleri kapatın ve uçları iğne yardımıyla saklayın.\n\nEkstra Dokunuşlar:\n- Püskül: Her iki ucuna küçük püsküller ekleyebilirsiniz.\n- Sevgi Notu: Minik bir kart yazıp atkıya iliştirerek sürprizinizi daha da tatlı hale getirebilirsiniz.",
    "youtubeLink": "https://www.youtube.com/watch?v=example",
    "userMiniProfileResponse": {
        "ownerId": "ce382d00-87ad-4645-b27d-311965bc4714",
        "name": "Seda Jur",
        "surname": "Özalpdemir",
        "avatarImage": {
            "imageId": "9c7aa382-774e-4f2c-9589-51edc3096e9a",
            "imagePath": "userexample.com.tr"
        }
    },
    "categoryResponse": {
        "categoryId": "0210941f-c347-4bd4-87c2-6c22bba14ecc",
        "categoryName": "örgü"
    },
    "coverImage": {
        "imageId": "69e16b50-50e7-49da-8999-996a00f3410b",
        "imagePath": "https://example.com/image1.jpg"
    },
    "postImages": [
        {
            "imageId": "69e16b50-50e7-49da-8999-996a00f3410b",
            "imagePath": "https://example.com/image1.jpg"
        },
        {
            "imageId": "81738c37-79d2-4f66-9c65-c58b1c60b7f8",
            "imagePath": "https://example.com/image2.jpg"
        }
    ],
    "likeCount": 0,
    "commentCount": 0
}
```

## 🚀 Upcoming Features

I am currently working on integrating the following technologies into the project:
- **Redis Caching** for improving data retrieval speed 🏎️
- **Docker Containerization** for deployment flexibility 🐳
- **JWT & Spring Security** for enhanced authentication 🔐
- **Elasticsearch** for efficient search capabilities 🔍
- **AWS S3** Image Management 📸

These enhancements will improve the performance, security, and scalability of KnitShop. Stay tuned for updates! 🚀

## 🤝 Contributing

Feel free to open a **pull request** if you’d like to contribute.

## 📝 License

This project is licensed under the **MIT License**.

📌 Developed by **@fatihhozkurt**. 😊
