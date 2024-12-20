Recipe Platform Project

This project is a recipe platform built using a microservices architecture. It consists of three core microservices and an API Gateway to manage and coordinate requests. Below is a detailed description of the project structure, the databases used, and the features provided.

Project Structure

1. Recipe Service

Description: This service handles all operations related to recipes, including creation, retrieval, updating, and deletion.

Database: SQL (e.g., PostgreSQL or MySQL).

Key Features:

Add new recipes with details like title, description, ingredients, steps, category, and tags.

Update existing recipes.

Delete recipes by ID.

Retrieve all recipes or a single recipe by ID.

Fetch recipes along with associated comments and author information.

2. Comment Service

Description: This service manages comments on recipes, allowing users to leave feedback.

Database: MongoDB.

Key Features:

Add comments to a recipe, including the user ID, recipe ID, and content.

Fetch comments by recipe ID.

Fetch all comments associated with multiple recipe IDs in a single request.

3. User Service

Description: This service handles user-related operations such as registration and retrieval of user information.

Database: SQL (e.g., PostgreSQL or MySQL).

Key Features:

Register new users with details such as username, email, and profile picture.

Retrieve user information by email or ID.

4. API Gateway

Description: The API Gateway serves as the entry point for all client requests and routes them to the appropriate microservice. It also manages OAuth2 authentication and token verification.

Key Features:

Route requests to the appropriate microservice.

Secure endpoints using OAuth2 authentication.

Enable CORS for frontend communication.

Databases Used

Recipe Service: SQL database (e.g., PostgreSQL or MySQL).

Comment Service: MongoDB for flexible and scalable comment storage.

User Service: SQL database (e.g., PostgreSQL or MySQL).

Features

User Features

Login and Registration:

Log in using Google OAuth2.

Register a new account automatically upon the first Google login.

Recipe Management:

Add new recipes.

View all recipes with detailed information (ingredients, steps, author).

Edit or delete your recipes.

Commenting:

Add comments to recipes.

View comments with the username and profile picture of the commenter.

Admin Features

Admins can manage recipes and comments, including deleting inappropriate content.

Technologies Used

Backend

Spring Boot for all microservices.

WebClient for inter-service communication.

OAuth2 for secure authentication.

Frontend

Angular for the web interface.

Google One-Tap for login functionality.

Databases

PostgreSQL/MySQL for Recipe and User services.

MongoDB for the Comment service.

Infrastructure

API Gateway for request routing and security.

Docker for containerization.

Kubernetes for deployment and scaling.

Installation and Setup

Clone the repository:

git clone https://github.com/your-repo.git

Build and run the microservices:

mvn clean install
docker-compose up

Run the Angular frontend:

cd recipe-frontend
npm install
ng serve

Access the application:

Frontend: http://localhost:4200

API Gateway: http://localhost:8080

API Endpoints

Recipe Service

GET /api/recipes: Fetch all recipes.

POST /api/recipes: Create a new recipe.

PUT /api/recipes/{id}: Update a recipe.

DELETE /api/recipes/{id}: Delete a recipe.

Comment Service

POST /api/comments: Add a new comment.

GET /api/comments?recipeId={id}: Fetch comments for a recipe.

POST /api/comments/by-recipe-ids: Fetch comments for multiple recipes.

User Service

GET /api/users/{id}: Fetch user details by ID.

GET /api/users?email={email}: Fetch user details by email.

POST /api/users: Register a new user.

Future Enhancements

Add advanced filtering and sorting for recipes.

Implement real-time notifications for comments.

Add role-based access control for admin features.

Contributors

Moussa Ramzi: Backend, Frontend, and Architecture Design.

Feel free to contribute by submitting issues or pull requests!

