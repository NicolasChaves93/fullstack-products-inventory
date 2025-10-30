import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Inventory } from '../../models/inventory.model';
import { InventoryService } from '../../services/inventory.service';

// Importación de Material
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

@Component({
  selector: 'app-inventory-manager',
  standalone: true,
  imports: [CommonModule, FormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    MatProgressSpinnerModule
  ],
  templateUrl: './inventory-manager.component.html',
  styleUrl: './inventory-manager.component.scss'
})
export class InventoryManagerComponent implements OnInit {
  @Input() productCode!: string; // Recibe el código del producto del componente padre
  
  inventory: Inventory | null = null;
  newQuantity: number = 0;
  loading: boolean = false;
  error: string | null = null;

  constructor(private inventoryService: InventoryService) { }

  get currentStock(): number {
    return this.inventory?.quantity ?? 0;
  }

  ngOnInit(): void {
    if (this.productCode) {
      this.fetchInventory();
    }
  }

  fetchInventory(): void {
    this.loading = true;
    this.error = null;
    this.inventoryService.getInventoryByProductCode(this.productCode).subscribe({
      next: (data) => {
        this.inventory = data;
        this.newQuantity = data.quantity;
        this.loading = false;
      },
      error: (err) => {
        // En caso de error (ej: 404), la lógica del backend debe devolver 0
        // Si hay un error de conexión, mostramos el mensaje.
        this.error = `Error al cargar inventario: ${err.message}`;
        this.loading = false;
      }
    });
  }

  updateInventory(): void {
    this.loading = true;
    this.error = null;

    this.inventoryService.updateInventoryQuantity(this.productCode, this.newQuantity).subscribe({
      next: (data) => {
        this.inventory = data;
        this.loading = false;
        alert(`Inventario de ${this.productCode} actualizado a ${data.quantity}`);
      },
      error: (err) => {
        this.error = `Error al actualizar: ${err.message}`;
        this.loading = false;
      }
    });
  }

  decrement(): void {
    const current = this.newQuantity ?? 0;
    this.newQuantity = Math.max(0, current - 1);
  }

  increment(): void {
    const current = this.newQuantity ?? 0;
    this.newQuantity = current + 1;
  }
}