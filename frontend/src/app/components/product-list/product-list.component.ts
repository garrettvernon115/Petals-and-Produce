import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { ProductService } from '../../services/product.service';
import { Product } from '../../models/product.models';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';


@Component({
  selector: 'app-product-list',
  standalone: true,
  imports: [
      CommonModule,
  RouterModule,
  HttpClientModule,
  FormsModule,
  MatFormFieldModule,
  MatInputModule,
  MatIconModule,
  MatSelectModule,
  MatButtonModule
  ],
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {
  allProducts: Product[] = [];
  products: Product[] = [];

  categories: string[] = [];

  currentPage = 1;
  pageSize = 6;

  searchQuery = '';
  selectedCategory = '';
  sortOption = '';
  showCategoryDropdown = false;

  constructor(private productService: ProductService) {}

  ngOnInit() {
    this.productService.getAllProducts().subscribe(data => {
      this.allProducts = data;
      this.categories = [...new Set(data.map(p => p.category))];
      this.applyFilters();
    });
  }

  applyFilters() {
    let filtered = this.allProducts.filter(product =>
      product.name.toLowerCase().includes(this.searchQuery.toLowerCase()) &&
      (!this.selectedCategory || product.category === this.selectedCategory)
    );

    switch (this.sortOption) {
      case 'name-asc':
        filtered.sort((a, b) => a.name.localeCompare(b.name));
        break;
      case 'price-asc':
        filtered.sort((a, b) => a.price - b.price);
        break;
      case 'price-desc':
        filtered.sort((a, b) => b.price - a.price);
        break;
    }

    this.currentPage = 1;
    this.updateProducts(filtered);
  }

  updateProducts(filtered: Product[]) {
    const start = (this.currentPage - 1) * this.pageSize;
    const end = start + this.pageSize;
    this.products = filtered.slice(start, end);
  }

  nextPage() {
    const maxPage = Math.ceil(this.filteredProductCount() / this.pageSize);
    if (this.currentPage < maxPage) {
      this.currentPage++;
      this.applyFilters();
    }
  }

  prevPage() {
    if (this.currentPage > 1) {
      this.currentPage--;
      this.applyFilters();
    }
  }

  filteredProductCount(): number {
    return this.allProducts.filter(product =>
      product.name.toLowerCase().includes(this.searchQuery.toLowerCase()) &&
      (!this.selectedCategory || product.category === this.selectedCategory)
    ).length;
  }

  toggleCategoryFilter() {
    this.showCategoryDropdown = !this.showCategoryDropdown;
  }

  setSort(option: string) {
    this.sortOption = option;
    this.applyFilters();
  }
}
