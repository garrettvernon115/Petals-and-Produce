import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatTableModule } from '@angular/material/table';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { OrderConfirmationDialogComponent } from '../order-confirmation-dialog/order-confirmation-dialog.component'; 

@Component({
  selector: 'app-cart',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatTableModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    MatDialogModule,
    OrderConfirmationDialogComponent
  ],
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent {
  constructor(private dialog: MatDialog) {}

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
    // TODO: Integrate with backend API
  }

  removeItem(productId: number) {
    this.cartItems = this.cartItems.filter(item => item.productId !== productId);
    // TODO: Integrate with backend API
  }

  getTotal(): number {
    return this.cartItems.reduce((total, item) => total + item.price * item.quantity, 0);
  }

  placeOrder() {
    const orderNumber = 'ORD-' + Math.floor(Math.random() * 1000000).toString().padStart(6, '0');
    this.dialog.open(OrderConfirmationDialogComponent, { 
      data: { orderNumber },
      panelClass: 'custom-dialog-container'
    });
  }
}
