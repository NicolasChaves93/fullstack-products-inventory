import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

// Importar material
import { MatCardModule } from '@angular/material/card';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatTableModule } from '@angular/material/table';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatPaginatorModule } from '@angular/material/paginator';

import { Product, ProductPage } from '../../models/product.model';
import { ProductService } from '../../services/product.service';
import { InventoryManagerComponent } from '../inventory-manager/inventory-manager.component';

@Component({
  selector: 'app-product-list',
  standalone: true,
  imports: [CommonModule, FormsModule, InventoryManagerComponent,
    MatCardModule,       
    MatToolbarModule,
    MatInputModule, 
    MatButtonModule,
    MatFormFieldModule,
    MatTableModule,
    MatSlideToggleModule,
    MatPaginatorModule
  ], // Importar el componente de inventario
  templateUrl: './product-list.component.html',
  styleUrl: './product-list.component.scss'
})
export class ProductListComponent implements OnInit {

  displayedColumns: string[] = ['code', 'name', 'price', 'active', 'actions', 'inventory'];
  
  // Data y Paginación
  products: Product[] = [];
  productPage: ProductPage | null = null;
  currentPage: number = 0;
  pageSize: number = 5;
  sort: string = 'name,asc';
  loading: boolean = true;

  // Formulario de Creación
  newProduct: Product = { name: '', code: '', description: '', price: 0, active: true };

  constructor(private productService: ProductService) { }

  ngOnInit(): void {
    this.fetchProducts(this.currentPage);
  }

  fetchProducts(page: number): void {
    this.loading = true;
    this.productService.getAllProducts(page, this.pageSize, this.sort).subscribe({
      next: (data) => {
        this.products = data.content;
        this.productPage = data;
        this.currentPage = data.number;
        this.loading = false;
      },
      error: (err) => {
        console.error('Error fetching products:', err);
        this.loading = false;
      }
    });
  }

  handleCreateProduct(): void {
    this.productService.createProduct(this.newProduct).subscribe({
      next: (response) => {
        alert(`Producto ${response.name} creado exitosamente.`);
        this.newProduct = { name: '', code: '', description: '', price: 0, active: true }; // Reset form
        this.fetchProducts(this.currentPage); // Reload current page
      },
      error: (err) => {
        console.error('Error creating product:', err);
        alert('Error al crear el producto. Verifique los datos.');
      }
    });
  }

  toggleActiveStatus(product: Product): void {
    const newStatus = !product.active;
    this.productService.updateProductActiveStatus(product.id!, newStatus).subscribe({
      next: (updatedProduct) => {
        // Actualizar la lista localmente
        const index = this.products.findIndex(p => p.id === product.id);
        if (index > -1) {
          this.products[index].active = updatedProduct.active;
        }
      },
      error: (err) => {
        console.error('Error updating status:', err);
      }
    });
  }

  handleDelete(id: number | undefined): void {
    if (!id || !window.confirm('¿Está seguro de que desea eliminar este producto?')) return;

    this.productService.deleteProduct(id).subscribe({
      next: () => {
        alert('Producto eliminado.');
        this.fetchProducts(this.currentPage);
      },
      error: (err) => {
        console.error('Error deleting product:', err);
      }
    });
  }

  changePage(delta: number): void {
    const newPage = this.currentPage + delta;
    if (this.productPage && newPage >= 0 && newPage < this.productPage.totalPages) {
      this.fetchProducts(newPage);
    }
  }
}