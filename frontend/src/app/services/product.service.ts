import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface ProductDTO {
  id: number;
  name: string;
  category: string;
  price: number;
  imageUrl?: string;
  description: string;
  stock: number;
}

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private apiUrl = '/api/products';

  constructor(private http: HttpClient) {}

  getAllProducts(): Observable<ProductDTO[]> {
    return this.http.get<ProductDTO[]>(this.apiUrl);
  }

  updateProduct(id: number, product: ProductDTO) {
  return this.http.put(`/api/products/${id}`, product);
}
  addProduct(product: ProductDTO): Observable<ProductDTO> {
  return this.http.post<ProductDTO>('/api/products', product);
}

  deleteProduct(id: number): Observable<void> {
  return this.http.delete<void>(`${this.apiUrl}/${id}`);
}

}
