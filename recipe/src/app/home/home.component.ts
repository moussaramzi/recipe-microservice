import { Component, OnInit } from '@angular/core';
import { ApiService } from '../api.service';
import { NgFor, NgIf } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { Recipe } from '../models/recipe.model';
import { Comment } from '../models/comment.model';
import { User } from '../models/user.model';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [NgFor, FormsModule, NgIf],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  recipes: Recipe[] = [];
  newComments: { [recipeId: string]: string } = {};
  currentUser: User | null = null;

  constructor(private apiService: ApiService, private authService: AuthService, private router: Router) {}

  ngOnInit(): void {
    this.loadCurrentUser();
    this.loadRecipes();
  }
  
  loadCurrentUser(): void {
    const userId = localStorage.getItem('userId');
    const username = localStorage.getItem('username');
    const profilePicture = localStorage.getItem('profilePicture');
  
    if (userId && username && profilePicture) {
      this.currentUser = {
        id: userId,
        username,
        email: '', // Placeholder since email is not stored in localStorage
        profilePicture,
        bio: null,
        createdAt: new Date().toISOString(),
        updatedAt: new Date().toISOString()
      };
    }
  }
  

  loadRecipes(): void {
    this.apiService.getRecipes().subscribe(
      (data: Recipe[]) => {
        this.recipes = data;
  
        // Populate the author field for each recipe
        this.recipes.forEach(recipe => {
          if (!recipe.author) {
            this.apiService.getUserById(recipe.authorId).subscribe(
              (user) => recipe.author = user,
              (error) => console.error('Error fetching recipe author:', error)
            );
          }
  
          // Populate the author field for each comment
          recipe.comments.forEach(comment => {
            if (!comment.author) {
              this.apiService.getUserById(comment.userId).subscribe(
                (user) => comment.author = user,
                (error) => console.error('Error fetching comment author:', error)
              );
            }
          });
        });
      },
      (error: HttpErrorResponse) => {
        console.error('Error fetching recipes:', error);
      }
    );
  }
  
  

  postComment(recipeId: string): void {
    const content = this.newComments[recipeId];
    if (!content) return;

    this.apiService.postComment(recipeId, content).subscribe(
      (comment: Comment) => {
        const recipe = this.recipes.find((r) => r.id === recipeId);
        if (recipe) {
          recipe.comments.push(comment);
        }
        this.newComments[recipeId] = '';
      },
      (error: HttpErrorResponse) => {
        console.error('Error posting comment:', error);
      }
    );
  }

 deleteComment(recipeId: string, commentId: string): void {
    if (!this.currentUser) return;

    this.apiService.deleteComment(commentId).subscribe(
      () => {
        const recipe = this.recipes.find((r) => r.id === recipeId);
        if (recipe) {
          recipe.comments = recipe.comments.filter((comment) => comment.id !== commentId);
        }
      },
      (error: HttpErrorResponse) => {
        console.error('Error deleting comment:', error);
      }
    );
  }

  replaceWithPlaceholder(event: Event): void {
    const imgElement = event.target as HTMLImageElement;
    const placeholderSrc = 'assets/default-profile.png';
  
    if (!imgElement.src.endsWith(placeholderSrc)) {
      imgElement.src = placeholderSrc;
    }
  }
  
  
  navigateToEditRecipe(recipe: Recipe): void {
    this.router.navigate(['/edit-recipe', recipe.id]);
  }
  

  navigateToAddRecipe(): void {
    this.router.navigate(['/add-recipe']);
  }
}
