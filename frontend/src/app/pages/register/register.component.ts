import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { User } from '../../models/user.model';
import { Role } from '../../enums/role.enum';
import { Equipe } from '../../enums/equipe.enum';

@Component({
  selector: 'app-register',
  standalone: false,
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {

  roles = Object.values(Role);
  equipes = Object.values(Equipe);

  user: User = {
    nom: '',
    email: '',
    password: '',
    role: Role.ENCADRANT,
    equipe: Equipe.A3
  };

  errorMessage = '';

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  register(): void {

    this.authService.register(this.user)
      .subscribe({
        next: () => {
          this.router.navigate(['/login']);
        },
        error: () => {
          this.errorMessage = 'Erreur lors de la création du compte';
        }
      });
  }
}
