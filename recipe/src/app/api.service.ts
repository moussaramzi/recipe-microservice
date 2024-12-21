import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../environments/environment';
import { Observable, throwError } from 'rxjs';
import { Recipe } from './models/recipe.model';
import { Comment } from './models/comment.model';
import { User } from './models/user.model';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  private apiUrl = environment.apiGatewayUrl;

  constructor(private http: HttpClient) {}

  // Get recipes (public)
  getRecipes(): Observable<Recipe[]> {
    return this.http.get<Recipe[]>(`${this.apiUrl}/api/recipes`);
  }
  getUserById(userId: string): Observable<User> {
    return this.http.get<User>(`http://localhost:8080/api/users/${userId}`);
  }
  

  postComment(recipeId: string, content: string): Observable<Comment> {
    const userId = localStorage.getItem('userId');
    const token = localStorage.getItem('token');
  
    if (!userId || !token) {
      console.error('User not logged in: Missing userId or token');
      return throwError(() => new Error('User not logged in'));
    }
  
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`
    });
  
    const commentData = {
      userId,
      recipeId,
      content
    };
  
    return this.http.post<Comment>(`${this.apiUrl}/api/comments`, commentData, { headers });
  }
  
  

  deleteComment(commentId: string): Observable<void> {
    const token = localStorage.getItem('token');
  
    if (!token) {
      console.error('User not logged in: Missing token');
      return throwError(() => new Error('User not logged in'));
    }
  
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`
    });
  
    return this.http.delete<void>(`${this.apiUrl}/api/comments/${commentId}`, { headers });
  }
  
  addRecipe(recipeData: any): Observable<any> {
    const token = localStorage.getItem('token');
    
    console.log('Token being used:', token); // Debug log to check the token
  
    if (!token) {
      console.error('User not logged in: Missing token');
      return throwError(() => new Error('User not logged in'));
    }
  
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`
    });
  
    return this.http.post<any>(`${this.apiUrl}/api/recipes`, recipeData, { headers });
  }
  
  getRecipeById(recipeId: string): Observable<Recipe> {
    return this.http.get<Recipe>(`${this.apiUrl}/api/recipes/${recipeId}`);
  }
  
  updateRecipe(recipeId: string, recipeData: any): Observable<any> {
    const token = localStorage.getItem('token');
  
    if (!token) {
      console.error('User not logged in: Missing token');
      return throwError(() => new Error('User not logged in'));
    }
  
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`
    });
  
    return this.http.put<any>(`${this.apiUrl}/api/recipes/${recipeId}`, recipeData, { headers });
  }
  
  
  
}
