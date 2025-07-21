import { Component, OnInit, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatTableModule, MatTable, MatTableDataSource } from '@angular/material/table';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { OrderConfirmationDialogComponent } from '../order-confirmation-dialog/order-confirmation-dialog.component';
import { CartService, CartItem } from '../../services/cart.service';

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
export class CartComponent implements OnInit {
  displayedColumns: string[] = ['image', 'name', 'price', 'quantity', 'subtotal', 'remove'];
  dataSource = new MatTableDataSource<CartItem>();

  @ViewChild(MatTable) table!: MatTable<any>;

  constructor(
    private cartService: CartService,
    private dialog: MatDialog,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadCart();
  }

  loadCart(): void {
    this.cartService.getCart().subscribe({
      next: (items) => {
        this.dataSource.data = items;
      },
      error: (err) => console.error('Error loading cart:', err)
    });
  }

  updateQuantity(item: CartItem): void {
    this.cartService.updateItem(item.productId, item.quantity).subscribe({
      next: () => this.loadCart(),
      error: (err) => console.error('Error updating quantity:', err)
    });
  }

  removeItem(productId: number): void {
    this.cartService.removeItem(productId).subscribe({
      next: () => {
        const updatedItems = this.dataSource.data.filter(item => item.productId !== productId);
        this.dataSource.data = updatedItems; // triggers table refresh
      },
      error: (err) => console.error('Error removing item:', err)
    });
  }

  getTotal(): number {
    return this.dataSource.data.reduce((total, item) => total + item.total, 0);
  }

  placeOrder(): void {
    const items = this.dataSource.data.map(item => ({
      productId: item.productId,
      quantity: item.quantity
    }));

    this.cartService.submitOrder(items).subscribe({
      next: (orderId: number) => {
        this.dialog.open(OrderConfirmationDialogComponent, {
          data: {
            orderNumber: `ORD-${orderId.toString().padStart(6, '0')}`,
            items: this.dataSource.data,
            total: this.getTotal()
          },
          panelClass: 'custom-dialog-container'
        });

        this.dataSource.data = [];
      },
      error: (err) => console.error('Order submission failed:', err)
    });
  }



}
