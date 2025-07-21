import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { FormsModule } from '@angular/forms';
import { CartService } from '../services/cart.service'; // adjust path as needed

@Component({
  selector: 'app-product-detail',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    MatCardModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    FormsModule
  ],
  templateUrl: './product-detail.component.html',
  styleUrls: ['./product-detail.component.css']
})
export class ProductDetailComponent implements OnInit {
  productId!: number;
  product: any;
  error: string | null = null;
  quantity: number = 1;

  constructor(
    private route: ActivatedRoute,
    private http: HttpClient,
    private router: Router,
    private cartService: CartService
  ) {}

  ngOnInit(): void {
    this.productId = Number(this.route.snapshot.paramMap.get('id'));

    this.http.get(`/api/products/${this.productId}`).subscribe({
      next: (data) => {
        this.product = data;
        this.error = null;
      },
      error: () => {
        this.error = 'Product not found';
      }
    });
  }

  addToCart(): void {
    if (this.quantity <= 0 || !this.product?.id) return;

    this.cartService.addToCart(this.product.id, this.quantity).subscribe({
      next: (response) => {
        console.log('Response:', response);
        if (typeof response === 'string' && response.includes('success')) {
          alert(`${this.product.name} added to cart!`);
        } else {
          alert('Unexpected response. Please check again.');
        }
      },
      error: (err) => {
        console.error('Error adding to cart:', err);
        alert('Could not add item to cart.');
      }
    });
  }

  goBack(): void {
    this.router.navigate(['/products']);
  }
}
