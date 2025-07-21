import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

export interface CartItem {
  productId: number;
  name: string;
  price: number;
  quantity: number;
  total: number;
  imageUrl: string;
  imageUrl: string;
}

@Injectable({ providedIn: 'root' })
export class CartService {
  private baseUrl = '/api';

  constructor(private http: HttpClient) {}

  getCart(): Observable<CartItem[]> {
    return this.http.get<CartItem[]>(`${this.baseUrl}/cart`);
  }

  updateItem(productId: number, newQuantity: number): Observable<any> {
    return this.http.post(`${this.baseUrl}/cart/update`, { productId, newQuantity }, { responseType: 'text' });
  }

  removeItem(productId: number): Observable<any> {
    return this.http.delete(`/api/cart/remove/${productId}`, { responseType: 'text' });
  }


  addToCart(productId: number, quantity: number): Observable<string> {
    return this.http.post(`${this.baseUrl}/addToCart`, { productId, quantity }, { responseType: 'text' });
  }

  submitOrder(items: { productId: number; quantity: number }[]): Observable<number> {
    const token = localStorage.getItem('token');

    let headers = new HttpHeaders();
    if (token) {
      headers = headers.set('Authorization', `Bearer ${token}`);
    }

    return this.http.post<number>(`${this.baseUrl}/orders`, { items }, {
      headers,
      responseType: 'json'
    });
  }


  getOrders(): Observable<any[]> {
  const token = localStorage.getItem('token');
  let headers = new HttpHeaders();

  if (token) {
    headers = headers.set('Authorization', `Bearer ${token}`);
  }

  return this.http.get<any[]>(`${this.baseUrl}/orders`, { headers });
}


}