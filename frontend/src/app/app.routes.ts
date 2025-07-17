import { Routes } from '@angular/router';
import { CartComponent } from './pages/cart/cart.component';

export const appRoutes: Routes = [
  { path: 'cart', component: CartComponent },
  { path: '', redirectTo: 'cart', pathMatch: 'full' }
];
