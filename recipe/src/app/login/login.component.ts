import { Component, AfterViewInit, ChangeDetectorRef } from '@angular/core';
import { environment } from '../../environments/environment';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { User } from '../models/user.model';
import { jwtDecode } from 'jwt-decode';
import { AuthService } from '../auth.service';
import { NgIf } from '@angular/common';

declare const google: any;

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [NgIf],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements AfterViewInit {
  user: User | null = null;

  constructor(
    private router: Router,
    private http: HttpClient,
    private authService: AuthService,
    private cdr: ChangeDetectorRef
  ) {}

  ngAfterViewInit(): void {
    this.checkLoginStatus();

    if (!this.user) {
      this.renderGoogleSignInButton();
    }
  }

  renderGoogleSignInButton(): void {
    const buttonContainer = document.getElementById('google-signin-button');
    if (buttonContainer) {
      google.accounts.id.initialize({
        client_id: environment.googleClientId,
        callback: this.handleCredentialResponse.bind(this)
      });
      google.accounts.id.renderButton(buttonContainer, { theme: 'outline', size: 'large' });
    } else {
      console.error('Google Sign-In button container not found');
    }
  }

  handleCredentialResponse(response: any) {
    const token = response.credential;
    const decodedToken: any = jwtDecode(token);

    const email = decodedToken.email;
    const username = decodedToken.name;
    const profilePicture = decodedToken.picture;

    this.http.get<User>(`${environment.apiGatewayUrl}/api/users?email=${email}`).subscribe(
      (user) => {
        this.storeUserData(user, token);
      },
      (error) => {
        if (error.status === 404) {
          this.registerUser(email, username, profilePicture, token);
        } else {
          console.error('Error checking user:', error);
        }
      }
    );
  }

  registerUser(email: string, username: string, profilePicture: string, token: string) {
    const newUser: Partial<User> = {
      username,
      email,
      profilePicture,
      bio: null,
      createdAt: new Date().toISOString(),
      updatedAt: new Date().toISOString()
    };

    this.http.post<User>(`${environment.apiGatewayUrl}/api/users`, newUser).subscribe(
      (user) => {
        this.storeUserData(user, token);
      },
      (error) => {
        console.error('Error registering user:', error);
      }
    );
  }

  storeUserData(user: User, token: string) {
    console.log('Storing user data:', user, token); // Debug log
    localStorage.setItem('token', token);
    localStorage.setItem('userId', user.id);
    localStorage.setItem('username', user.username);
    localStorage.setItem('profilePicture', user.profilePicture);
    this.user = user;
    this.authService.setUser(user);
    this.cdr.detectChanges(); // Force change detection
    this.router.navigate(['/']);
  }

  logout(): void {
    this.authService.setUser(null);
    this.user = null;
    localStorage.clear();
    this.cdr.detectChanges();
    setTimeout(() => {
      this.renderGoogleSignInButton();
    });
  }

  checkLoginStatus(): void {
    this.authService.getUser().subscribe((user) => {
      this.user = user;
      this.cdr.detectChanges();
    });
  }
}
