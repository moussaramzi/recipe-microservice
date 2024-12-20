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
- Kubernetes for deployment and scaling.

## Installation and Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/your-repo.git
   ```

2. Build and run the microservices:
   ```bash
   mvn clean install
   docker-compose up
   ```

3. Run the Angular frontend:
   ```bash
   cd recipe-frontend
   npm install
   ng serve
   ```

4. Access the application:
   - Frontend: `http://localhost:4200`
   - API Gateway: `http://localhost:8080`

## API Endpoints

### Recipe Service
- `GET /api/recipes`: Fetch all recipes.
- `POST /api/recipes`: Create a new recipe.
- `PUT /api/recipes/{id}`: Update a recipe.
- `DELETE /api/recipes/{id}`: Delete a recipe.

### Comment Service
- `POST /api/comments`: Add a new comment.
- `GET /api/comments?recipeId={id}`: Fetch comments for a recipe.
- `POST /api/comments/by-recipe-ids`: Fetch comments for multiple recipes.

### User Service
- `GET /api/users/{id}`: Fetch user details by ID.
- `GET /api/users?email={email}`: Fetch user details by email.
- `POST /api/users`: Register a new user.

## Future Enhancements
- Add advanced filtering and sorting for recipes.
- Implement real-time notifications for comments.
- Add role-based access control for admin features.



