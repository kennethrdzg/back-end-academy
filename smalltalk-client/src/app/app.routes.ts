import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { AuthComponent } from './pages/auth/auth.component';
import { PageNotFoundComponent } from './pages/page-not-found/page-not-found.component';
import { FeedComponent } from './pages/feed/feed.component';

export const routes: Routes = [
    { path: '', component: HomeComponent }, 
    { path: 'home', redirectTo: '/', pathMatch: 'full' },
    { path: 'auth', component: AuthComponent }, 
    { path: 'register', component: AuthComponent },
    { path: 'login', component: AuthComponent },
    { path: 'feed', component: FeedComponent},
    { path: '**', component: PageNotFoundComponent}
];
