import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-admin-order-monitoring',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './admin-order-monitoring.component.html',
  styleUrls: ['./admin-order-monitoring.component.css']
})
export class AdminOrderMonitoringComponent implements OnInit {
  orders: any[] = [
    {
      id: 1,
      userName: 'Test User1',
      email: 'testuser1@example.com',
      fulfilled: false,
      items: [
        { name: 'Roses', quantity: 2 },
        { name: 'Lillies', quantity: 4 }
      ]
    },
    {
      id: 2,
      userName: 'Test User2',
      email: 'TU2@example.com',
      fulfilled: true,
      items: [
        { name: 'Pink roses', quantity: 1 }
      ]
    }
  ];

  constructor() {}

  ngOnInit(): void {}

toggleFulfilled(order: any): void {
  order.fulfilled = !order.fulfilled;
  console.log(`Order #${order.id} now ${order.fulfilled ? 'fulfilled' : 'pending'}`);
  // TODO: Add backend sync if needed
}
}