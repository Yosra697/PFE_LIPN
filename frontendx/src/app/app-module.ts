import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { provideHttpClient, withInterceptors } from '@angular/common/http';

import { App } from './app';
import { AppRoutingModule } from './app-routing-module';

import { PropositionsComponent } from './pages/propositions/propositions.component';
import { PropositionFormComponent } from './pages/proposition-form/proposition-form.component';
import { LoginComponent } from './pages/login/login.component';
import { authInterceptor } from './interceptors/auth-interceptor';
import { RegisterComponent } from './pages/register/register.component';
import { DoctorantsComponent } from './pages/doctorants/doctorants.component';
import { NavbarComponent } from './pages/navbar/navbar.component';

@NgModule({
  declarations: [
    App,
    PropositionsComponent,
    PropositionFormComponent,
    LoginComponent,
    RegisterComponent,
    DoctorantsComponent,
    NavbarComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule
  ],
  providers: [
    provideHttpClient(withInterceptors([authInterceptor]))
  ],
  bootstrap: [App]
})
export class AppModule {}
