import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { User } from './models/user.model';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private userSubject: BehaviorSubject<User | null> = new BehaviorSubject<User | null>(this.getStoredUser());

  constructor() {}

  private getStoredUser(): User | null {
    const userId = localStorage.getItem('userId');
    const username = localStorage.getItem('username');
    const profilePicture = localStorage.getItem('profilePicture');

    if (userId && username && profilePicture) {
      return {
        id: userId,
        username,
        email: '', // Placeholder since email is not stored in localStorage
        profilePicture,
        bio: null,
        createdAt: new Date().toISOString(),
        updatedAt: new Date().toISOString(),
      };
    }
    return null;
  }

  getUser(): Observable<User | null> {
    return this.userSubject.asObservable();
  }

  setUser(user: User | null): void {
    if (user) {
      localStorage.setItem('userId', user.id);
      localStorage.setItem('username', user.username);
      localStorage.setItem('profilePicture', user.profilePicture);
    } else {
      localStorage.clear();
    }
    this.userSubject.next(user);
  }
  getUserSync(): User | null {
    const userId = localStorage.getItem('userId');
    const username = localStorage.getItem('username');
    const profilePicture = localStorage.getItem('profilePicture');
  
    if (userId && username && profilePicture) {
      return {
        id: userId,
        username,
        email: '', // Placeholder, since email isn't stored in localStorage
        profilePicture,
        bio: null,
        createdAt: new Date().toISOString(),
        updatedAt: new Date().toISOString()
      };
    }
  
    return null;
  }
  
}
