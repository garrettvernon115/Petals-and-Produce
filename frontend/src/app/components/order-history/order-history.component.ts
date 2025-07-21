import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AsyncPipe, CurrencyPipe, DatePipe } from '@angular/common';
import { Observable } from 'rxjs';
import { Order } from '../../models/order.model';
import { OrderService } from '../../services/order.service';

@Component({
  selector: 'app-order-history',
  standalone: true,
  imports: [CommonModule, AsyncPipe, CurrencyPipe, DatePipe],
  templateUrl: './order-history.component.html',
  styleUrls: ['./order-history.component.css']
})
export class OrderHistoryComponent implements OnInit {
  orders$!: Observable<Order[]>;

  constructor(private orderSvc: OrderService) {}

  ngOnInit(): void {
    this.orders$ = this.orderSvc.getOrders();
  }

  total(o: Order): number {
    return o.items.reduce(
      (sum: number, i: { unitPrice: number; quantity: number }) =>
      sum + i.unitPrice * i.quantity,
    0
  );
}
}