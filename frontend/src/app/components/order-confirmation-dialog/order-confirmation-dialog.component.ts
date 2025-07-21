import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef, MatDialogModule } from '@angular/material/dialog';
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-order-confirmation-dialog',
  standalone: true,
  imports: [CommonModule, MatDialogModule, MatIconModule, MatButtonModule],
  templateUrl: './order-confirmation-dialog.component.html',
  styleUrls: ['./order-confirmation-dialog.component.css']
})
export class OrderConfirmationDialogComponent {
  constructor(
    @Inject(MAT_DIALOG_DATA)
    public data: {
      orderNumber: string;
      items: {
        name: string;
        price: number;
        quantity: number;
        total: number;
      }[];
      total: number;
    },
    private dialogRef: MatDialogRef<OrderConfirmationDialogComponent>
  ) {}

  closeDialog(): void {
    this.dialogRef.close();
  }
}

