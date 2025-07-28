import { Component, OnInit, ViewChild } from '@angular/core';
import { ProductService, ProductDTO } from '../../../services/product.service';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { MatDialog } from '@angular/material/dialog';
import { EditProductDialogComponent } from '../edit-product-dialog/edit-product-dialog.component';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-admin-product-management',
  standalone: true,
  imports: [
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatIconModule,
    MatButtonModule,
    CommonModule
  ],
  templateUrl: './admin-product-management.component.html',
  styleUrls: ['./admin-product-management.component.css']
})
export class AdminProductManagementComponent implements OnInit {
  displayedColumns: string[] = ['id', 'name', 'category', 'price', 'stock', 'actions'];
  allProducts: ProductDTO[] = [];
  pagedProducts: ProductDTO[] = [];

  currentPage = 1;
  pageSize = 5;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(
    private productService: ProductService,
    private dialog: MatDialog
  ) {}

  ngOnInit() {
    this.fetchProducts();
  }

  fetchProducts() {
  this.productService.getAllProducts().subscribe((data) => {
    this.allProducts = data;
    this.applyPagination();
  });
}

  applyPagination() {
    const start = (this.currentPage - 1) * this.pageSize;
    const end = start + this.pageSize;
    this.pagedProducts = this.allProducts.slice(start, end);
  }

  nextPage() {
    const maxPage = Math.ceil(this.allProducts.length / this.pageSize);
    if (this.currentPage < maxPage) {
      this.currentPage++;
      this.applyPagination();
    }
  }

  prevPage() {
    if (this.currentPage > 1) {
      this.currentPage--;
      this.applyPagination();
    }
  }

  setPageSize(event: Event) {
    this.pageSize = +(event.target as HTMLSelectElement).value;
    this.currentPage = 1;
    this.applyPagination();
  }

  openEditDialog(product: ProductDTO) {
    const dialogRef = this.dialog.open(EditProductDialogComponent, {
      width: '600px',
      data: product,
      panelClass: 'edit-product-dialog'
    });

    dialogRef.afterClosed().subscribe((updatedProduct: ProductDTO | undefined) => {
      if (updatedProduct) {
        this.productService.updateProduct(updatedProduct.id, updatedProduct).subscribe(() => {
          this.fetchProducts();
        });
      }
    });
  }

  addProduct() {
    const dialogRef = this.dialog.open(EditProductDialogComponent, {
      width: '500px',
      data: null,
      panelClass: 'edit-product-dialog',
    });

    dialogRef.afterClosed().subscribe((newProduct: ProductDTO | undefined) => {
      if (newProduct) {
        this.fetchProducts();
      }
    });
  }

  delete(product: ProductDTO): void {
    if (confirm(`Are you sure you want to delete "${product.name}"?`)) {
      this.productService.deleteProduct(product.id).subscribe(() => {
        this.fetchProducts();
      });
    }
  }
}
