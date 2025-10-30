import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Inventory } from '../models/inventory.model';

@Injectable({
  providedIn: 'root'
})
export class InventoryService {
  private apiUrl = 'http://localhost:8081/api/v1/inventories';

  constructor(private http: HttpClient) { }

  // GET /api/v1/inventories/{productCode}
  getInventoryByProductCode(productCode: string): Observable<Inventory> {
    return this.http.get<Inventory>(`${this.apiUrl}/${productCode}`);
  }

  // PATCH /api/v1/inventories/update
  updateInventoryQuantity(productCode: string, quantity: number): Observable<Inventory> {
    const body = { productCode, quantity };
    return this.http.patch<Inventory>(`${this.apiUrl}/update`, body); 
  }
}