import { Component, OnInit } from '@angular/core';
import { CommonModule, AsyncPipe, DatePipe, CurrencyPipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CartService } from '../../services/cart.service';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-admin-order-monitoring',
  standalone: true,
  imports: [CommonModule, FormsModule, AsyncPipe, DatePipe, CurrencyPipe],
  templateUrl: './admin-order-monitoring.component.html',
  styleUrls: ['./admin-order-monitoring.component.css']
})
export class AdminOrderMonitoringComponent implements OnInit {
  orders: any[] = [];
  statusOptions: string[] = [
    'PENDING',
    'READY_FOR_PICKUP',
    'PICKED_UP'
  ];

  constructor(private cartSvc: CartService, private http: HttpClient) {}

  ngOnInit(): void {
    this.cartSvc.getAllOrdersForAdmin().subscribe((res) => {
      this.orders = res;
    });
  }

  updateStatus(order: any, newStatus: string): void {
    const payload = { status: newStatus };

    this.http.put(`/api/orders/${order.orderId}/status`, payload).subscribe({
      next: () => {
        order.status = newStatus;
        console.log(`Order #${order.orderId} updated to ${newStatus}`);
      },
      error: (err) => {
        console.error(`Failed to update order #${order.orderId}:`, err);
      }
    });
  }

  total(order: any): number {
    return order.items.reduce((sum: number, item: any) => sum + item.unitPrice * item.quantity, 0);
  }
}
