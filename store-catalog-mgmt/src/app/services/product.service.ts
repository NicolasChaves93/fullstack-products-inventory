import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Product, ProductPage } from '../models/product.model';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private apiUrl = 'http://localhost:8080/api/v1/products';

  constructor(private http: HttpClient) { }

  // GET /api/v1/products?page=0&size=10&sort=name
  getAllProducts(page: number, size: number, sort: string): Observable<ProductPage> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString())
      .set('sort', sort);
    return this.http.get<ProductPage>(this.apiUrl, { params });
  }

  // POST /api/v1/products
  createProduct(product: Product): Observable<Product> {
    return this.http.post<Product>(this.apiUrl, product);
  }

  // PATCH /api/v1/products/{id}
  updateProductActiveStatus(id: number, active: boolean): Observable<Product> {
    return this.http.patch<Product>(`${this.apiUrl}/${id}`, { active });
  }

  // DELETE /api/v1/products/{id}
  deleteProduct(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }
}