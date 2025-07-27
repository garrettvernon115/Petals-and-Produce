import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatDialogModule } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';
import { MatCard } from "@angular/material/card";
import { ProductDTO, ProductService } from '../../../services/product.service';


@Component({
  selector: 'app-edit-product-dialog',
  standalone: true,
  templateUrl: './edit-product-dialog.component.html',
  styleUrls: ['./edit-product-dialog.component.css'],
  imports: [
    CommonModule,
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatDialogModule,
    MatButtonModule,
    MatCard
]
})
export class EditProductDialogComponent {
  isEditMode = false;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: ProductDTO | null,
    private dialogRef: MatDialogRef<EditProductDialogComponent>,
    private productService: ProductService
  ) {
    this.isEditMode = !!data && data.id !== 0;
    console.log('Dialog opened with data:', data);
    console.log('isEditMode =', this.isEditMode);

    if (data) {
      this.product = { ...data };
    } else {
      this.product = {
        id: 0,
        name: '',
        category: '',
        price: 0,
        stock: 0,
        imageUrl: '',
        description: '',
      };
    }
  }

  product: ProductDTO;

  save(): void {
    if (this.isEditMode) {
      this.productService.updateProduct(this.product.id, this.product).subscribe(() => {
        this.dialogRef.close(this.product);
      });
    } else {
      this.productService.addProduct(this.product).subscribe((createdProduct) => {
        console.log('Adding product:', this.product);
        console.log('Token in localStorage:', localStorage.getItem('token'));
        this.dialogRef.close(createdProduct);
      });
    }
  }
}

