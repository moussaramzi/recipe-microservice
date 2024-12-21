import { Comment } from './comment.model';
import { User } from './user.model';

export interface Recipe {
  id: string;
  title: string;
  description: string;
  ingredients: string[];
  steps: string[];
  category: string;
  tags: string[];
  createdAt: string;
  updatedAt: string;
  authorId: string;
  author?: User;
  comments: Comment[];
}
