import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { AuthGuard } from './auth.guard';
import { AddRecipeComponent } from './add-recipe/add-recipe.component';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'add-recipe', component: AddRecipeComponent },
  { path: 'edit-recipe/:id', component: AddRecipeComponent },
  { path: '**', redirectTo: '' }
];
