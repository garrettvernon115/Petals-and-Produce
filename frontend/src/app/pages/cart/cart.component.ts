import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-cart',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent {
  // Sample dummy data for testing
  cartItems = [
    { id: 1, name: 'Tomato', price: 1.5, quantity: 2 },
    { id: 2, name: 'Carrot', price: 0.99, quantity: 3 }
  ];


  updateQuantity(item: any) {
    console.log(`Updated quantity for ${item.name}: ${item.quantity}`);
    // Add logic here to update total or backend
  }

  getTotal(): number {
    return this.cartItems.reduce((total, item) => {
      return total + item.price * item.quantity;
    }, 0);
  }

  removeItem(itemId: number) {
    this.cartItems = this.cartItems.filter(item => item.id !== itemId);
  }
}
