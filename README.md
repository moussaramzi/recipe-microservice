# Recipe Platform Project

This project is a recipe platform built using a microservices architecture. It consists of three core microservices and an API Gateway to manage and coordinate requests. Below is a detailed description of the project structure, the databases used, and the features provided.

## Project Structure

### Recipe Service

- **Description**: This service handles all operations related to recipes, including creation, retrieval, updating, and deletion.
- **Database**: SQL (MySQL).
- **Key Features**:
  - Add new recipes with details like title, description, ingredients, steps, category, and tags.
  - Update existing recipes.
  - Delete recipes by ID.
  - Retrieve all recipes or a single recipe by ID.
  - Fetch recipes along with associated comments and author information.

### Comment Service

- **Description**: This service manages comments on recipes, allowing users to leave feedback.
- **Database**: MongoDB.
- **Key Features**:
  - Add comments to a recipe, including the user ID, recipe ID, and content.
  - Fetch comments by recipe ID.
  - Fetch all comments associated with multiple recipe IDs in a single request.

### User Service

- **Description**: This service handles user-related operations such as registration and retrieval of user information.
- **Database**: MongoDB.
- **Key Features**:
  - Register new users with details such as username, email, and profile picture.
  - Retrieve user information by email or ID.


### API Gateway
- **Description**: The API Gateway serves as the entry point for all client requests and routes them to the appropriate microservice. It also manages OAuth2 authentication and token verification.
- **Key Features**:
  - Route requests to the appropriate microservice.
  - Secure endpoints using OAuth2 authentication.
  - Enable CORS for frontend communication.

### Recipe Frontend
- **Description**: This is a frontend application using the api gateway to get and add data.
- **Key Features**:
    - Login
    - Overview of all the recipes
    - Add ur own recipe
    - Edit ur recipe
    - Add a comment to a recipe
    - Delete u own comment

## Databases Used

- **Recipe Service**: SQL database (MySQL).
- **Comment Service**: MongoDB for flexible and scalable comment storage.
- **User Service**: MongoDB for user data.


## Features

### User Features
1. **Login and Registration**:
   - Log in using Google OAuth2.
   - Register a new account automatically upon the first Google login.

2. **Recipe Management**:
   - Add new recipes.
   - View all recipes with detailed information (ingredients, steps, author).
   - Edit or delete your recipes.

3. **Commenting**:
   - Add comments to recipes.
   - View comments with the username and profile picture of the commenter.

## Technologies Used

### Backend
- Spring Boot for all microservices.
- WebClient for inter-service communication.
- OAuth2 for secure authentication.

### Frontend
- Angular for the web interface.
- Google One-Tap for login functionality.

### Databases
- MySQL for the Recipe service.
- MongoDB for the Comment and User services.

### Infrastructure
- API Gateway for request routing and security.
- Docker for containerization.
  
## Installation and Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/moussaramzi/recipe-microservice.git
   cd recipe-microservice
   ```

2. Build and run the microservices:
   ```bash
   mvn clean install
   docker-compose build
   docker-compose up
   ```
   
4. Access the application:
   - Frontend: `http://localhost:4200`
   - API Gateway: `http://localhost:8080`

## API Endpoints

### Recipe Service
- `GET /api/recipes`: Fetch all recipes.
  ![image](https://github.com/user-attachments/assets/80d631a1-498a-41fd-8057-4198a8a676a1)

- `POST /api/recipes`: Create a new recipe.
  ![image](https://github.com/user-attachments/assets/d5566d6e-736a-48df-a481-a36e50928dba)

- `PUT /api/recipes/{id}`: Update a recipe.
  ![image](https://github.com/user-attachments/assets/99f0d6d4-b413-4dc8-a81a-59cd81dc4ddc)

- `DELETE /api/recipes/{id}`: Delete a recipe.
  ![image](https://github.com/user-attachments/assets/236a3994-bc30-4d28-ad08-75abe261f3b7)


### Comment Service
- `POST /api/comments`: Add a new comment.
  ![image](https://github.com/user-attachments/assets/2a285671-0a6b-4871-a12b-fedb0743c077)

- `POST /api/comments/by-recipe-ids`: Fetch comments for multiple recipes.
  ![image](https://github.com/user-attachments/assets/ed94f73e-7164-4449-b608-66a1af8f8c11)
  ![image](https://github.com/user-attachments/assets/e59685ce-b7e2-4991-9cfc-1d6c99ea4072)



### User Service
- `GET /api/users`: Fetch all users.
  ![image](https://github.com/user-attachments/assets/35159fb7-6c1f-48e7-8463-2ac662096345)

- `GET /api/users/{id}`: Fetch user details by ID.
  ![image](https://github.com/user-attachments/assets/069e2a9c-2343-46ef-b911-3eb6f6199860)

- `GET /api/users?email={email}`: Fetch user details by email.
  ![image](https://github.com/user-attachments/assets/0cd84310-f4d3-434f-ad0a-ced4f2f75cca)

- `POST /api/users`: Register a new user.
  ![image](https://github.com/user-attachments/assets/dfb85b6c-f1ab-499e-a5c0-7d293e36245d)
  
- `DELETE /api/users/{id}`: Delete a user.
  ![image](https://github.com/user-attachments/assets/ab52b30c-3adb-4489-a155-eb9efec94b72)







