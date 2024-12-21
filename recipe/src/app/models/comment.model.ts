import { User } from './user.model';

export interface Comment {
  id: string;
  userId: string;
  recipeId: string;
  content: string;
  createdAt: string;
  updatedAt: string;
  author?: User;
}
