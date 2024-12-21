export interface User {
    id: string;
    username: string;
    email: string;
    profilePicture: string;
    bio: string | null;
    createdAt: string;
    updatedAt: string;
    author?: User;

  }
  