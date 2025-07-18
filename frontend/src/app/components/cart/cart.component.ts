import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatTableModule } from '@angular/material/table';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';


@Component({
  selector: 'app-cart',
  standalone: true,
  imports: [CommonModule, FormsModule,   MatTableModule,
  MatInputModule,
  MatButtonModule,   MatIconModule ],
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']


})
export class CartComponent {
  displayedColumns: string[] = ['image', 'name', 'price', 'quantity', 'subtotal', 'remove'];
  cartItems = [
    {
      productId: 1,
      name: 'Tomato',
      price: 1.5,
      quantity: 2,
      imageUrl: 'https://via.placeholder.com/100?text=Tomato'
    },
    {
      productId: 2,
      name: 'Carrot',
      price: 0.99,
      quantity: 3,
      imageUrl: 'https://via.placeholder.com/100?text=Carrot'
    }
  ];

  updateQuantity(item: any) {
    console.log(`Updated quantity for ${item.name}: ${item.quantity}`);
    // TODO: Call backend POST /cart/update with productId and newQuantity
  }

  removeItem(productId: number) {
    this.cartItems = this.cartItems.filter(item => item.productId !== productId);
    // TODO: Call backend DELETE /cart/remove/{productId}
  }

  getTotal(): number {
    return this.cartItems.reduce((total, item) => {
      return total + item.price * item.quantity;
    }, 0);
  }
}
