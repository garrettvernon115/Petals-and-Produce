import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface CartItem {
  productId: number;
  name: string;
  price: number;
  quantity: number;
  total: number;
}

@Injectable({ providedIn: 'root' })
export class CartService {
  private baseUrl = '/api';

  constructor(private http: HttpClient) {}

  getCart(): Observable<CartItem[]> {
    return this.http.get<CartItem[]>(`${this.baseUrl}/cart`);
  }

  updateItem(productId: number, newQuantity: number): Observable<any> {
    return this.http.post(`${this.baseUrl}/cart/update`, { productId, newQuantity });
  }

  removeItem(productId: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/cart/remove/${productId}`);
  }
  addToCart(productId: number, quantity: number): Observable<string> {
    return this.http.post(`${this.baseUrl}/addToCart`, { productId, quantity }, { responseType: 'text' });
  }

   submitOrder(items: { productId: number; quantity: number }[]): Observable<any> {
    return this.http.post(`${this.baseUrl}/orders`, { items }, { responseType: 'text' });
  }
}
