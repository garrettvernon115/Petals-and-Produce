import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface StockValidationResponse {
  valid: boolean;
  message: string;
  availableStock: number;
}

export interface LowStockAlert {
  productId: number;
  productName: string;
  currentStock: number;
  alertLevel: string;
}

export interface StockUpdateRequest {
  productId: number;
  newStock: number;
}

@Injectable({
  providedIn: 'root'
})
export class StockService {
  private apiUrl = 'http://localhost:8080/api/stock';

  constructor(private http: HttpClient) { }

  validateStock(productId: number, quantity: number): Observable<StockValidationResponse> {
    return this.http.get<StockValidationResponse>(`${this.apiUrl}/validate/${productId}/${quantity}`);
  }

  getCurrentStock(productId: number): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/${productId}`);
  }

  getLowStockAlerts(): Observable<LowStockAlert[]> {
    return this.http.get<LowStockAlert[]>(`${this.apiUrl}/alerts/low-stock`);
  }

  getOutOfStockProducts(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/out-of-stock`);
  }

  updateStock(request: StockUpdateRequest): Observable<string> {
    return this.http.put(`${this.apiUrl}/update`, request, { responseType: 'text' });
  }

  isOutOfStock(productId: number): Observable<boolean> {
    return this.http.get<boolean>(`${this.apiUrl}/check/out-of-stock/${productId}`);
  }

  isLowStock(productId: number): Observable<boolean> {
    return this.http.get<boolean>(`${this.apiUrl}/check/low-stock/${productId}`);
  }
}