import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { AuthComponent } from './pages/auth/auth.component';
import { PageNotFoundComponent } from './pages/page-not-found/page-not-found.component';

export const routes: Routes = [
    { path: 'home', component: HomeComponent }, 
    { path: '', redirectTo: '/home', pathMatch: 'full' },
    { path: 'auth', component: AuthComponent }, 
    { path: 'register', redirectTo: 'auth', pathMatch: 'full' },
    { path: 'login', redirectTo: 'auth', pathMatch: 'full' },
    { path: '**', component: PageNotFoundComponent}
];
