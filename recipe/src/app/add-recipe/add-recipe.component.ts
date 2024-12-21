import { Component, OnInit } from '@angular/core';
import { ApiService } from '../api.service';
import { Router, ActivatedRoute } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { User } from '../models/user.model';
import { Recipe } from '../models/recipe.model';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-add-recipe',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './add-recipe.component.html',
  styleUrls: ['./add-recipe.component.css']
})
export class AddRecipeComponent implements OnInit {
  newRecipe = {
    title: '',
    description: '',
    ingredients: '',
    steps: '',
    category: '',
    tags: ''
  };

  isEditMode = false;
  recipeId: string | null = null;

  constructor(
    private apiService: ApiService,
    private router: Router,
    private route: ActivatedRoute,
    private authService: AuthService
  ) {}
  
  currentUser: User | null = null;
  
  ngOnInit(): void {
    this.currentUser = this.authService.getUserSync(); // Synchronously get the current user
    this.recipeId = this.route.snapshot.paramMap.get('id');
  
    if (this.recipeId) {
      this.isEditMode = true;
      this.apiService.getRecipeById(this.recipeId).subscribe(
        (recipe: Recipe) => {
          this.newRecipe = {
            title: recipe.title,
            description: recipe.description,
            ingredients: recipe.ingredients.join(', '),
            steps: recipe.steps.join('. '),
            category: recipe.category,
            tags: recipe.tags.join(', ')
          };
        },
        (error) => {
          console.error('Error fetching recipe:', error);
        }
      );
    }
  }
  
  saveRecipe(): void {
    if (!this.currentUser) {
      console.error('User not logged in');
      return;
    }
  
    const recipeData = {
      ...this.newRecipe,
      ingredients: this.newRecipe.ingredients.split(',').map(i => i.trim()),
      steps: this.newRecipe.steps.split('.').map(s => s.trim()),
      tags: this.newRecipe.tags.split(',').map(t => t.trim()),
      authorId: this.currentUser.id // Ensure the authorId is set to the current user's ID
    };
  
    if (this.isEditMode && this.recipeId) {
      this.apiService.updateRecipe(this.recipeId, recipeData).subscribe(
        () => this.router.navigate(['/']),
        (error) => console.error('Error updating recipe:', error)
      );
    } else {
      this.apiService.addRecipe(recipeData).subscribe(
        () => this.router.navigate(['/']),
        (error) => console.error('Error adding recipe:', error)
      );
    }
  }
}