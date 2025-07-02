import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  standalone: true,
  imports: [CommonModule, RouterModule],
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})

export class ProductListComponent { //sample product data to show pagination
  allProducts = [
  {
    id: 1,
    name: 'Roses',
    price: 12.99,
    imageUrl: 'https://cdn.pixabay.com/photo/2018/01/09/22/51/roses-3072698_1280.jpg'
  },
  {
    id: 2,
    name: 'Tulips',
    price: 9.49,
    imageUrl: 'https://cdn.pixabay.com/photo/2018/02/15/18/28/flower-3155965_1280.jpg'
  },
  {
    id: 3,
    name: 'Sunflowers',
    price: 7.99,
    imageUrl: 'https://cdn.pixabay.com/photo/2018/07/17/21/49/flower-bouquet-3545096_1280.jpg'
  },
  {
    id: 4,
    name: 'Lilies',
    price: 10.50,
    imageUrl: 'https://cdn.pixabay.com/photo/2021/03/13/12/27/flowers-6091612_1280.jpg'
  },
  {
    id: 5,
    name: 'Peonies',
    price: 13.75,
    imageUrl: 'https://images.pexels.com/photos/931167/pexels-photo-931167.jpeg'
  },
  {
    id: 6,
    name: 'Orchids',
    price: 15.99,
    imageUrl: 'https://images.pexels.com/photos/6431888/pexels-photo-6431888.jpeg'
  },
  {
    id: 7,
    name: 'Hydrangeas',
    price: 11.25,
    imageUrl: 'https://images.pexels.com/photos/931179/pexels-photo-931179.jpeg'
  },
  {
    id: 8,
    name: 'Daisies',
    price: 6.99,
    imageUrl: 'https://cdn.pixabay.com/photo/2015/06/05/23/28/bouquet-798950_1280.jpg'
  }
  ];

  products: any[] = [];
  currentPage = 1;
  pageSize = 6;

  ngOnInit() {
    this.updateProducts();
  }

  updateProducts() {
    const start = (this.currentPage - 1) * this.pageSize;
    const end = start + this.pageSize;
    this.products = this.allProducts.slice(start, end);
  }

  nextPage() {
    if ((this.currentPage * this.pageSize) < this.allProducts.length) {
      this.currentPage++;
      this.updateProducts();
    }
  }

  prevPage() {
    if (this.currentPage > 1) {
      this.currentPage--;
      this.updateProducts();
    }
  }
}
