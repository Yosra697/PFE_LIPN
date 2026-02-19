import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { PropositionsComponent } from './pages/propositions/propositions.component';
import { PropositionFormComponent } from './pages/proposition-form/proposition-form.component';
import { LoginComponent } from './pages/login/login.component';
import { authGuard } from './guards/auth-guard';
import {RegisterComponent} from './pages/register/register.component';
import {DoctorantsComponent} from './pages/doctorants/doctorants.component';

const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  {
    path: 'propositions',
    canActivate: [authGuard],
    children: [
      { path: '', component: PropositionsComponent },
      { path: 'nouvelle', component: PropositionFormComponent }
    ]
  },
  {path: 'doctorants', canActivate: [authGuard], component: DoctorantsComponent},
  { path: '**', redirectTo: 'login' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
