import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { ProductService } from './product.service';
import { provideHttpClient } from '@angular/common/http';
import { Product, ProductPage } from '../models/product.model';

describe('ProductService', () => {
  let service: ProductService;
  let httpMock: HttpTestingController;
  const baseUrl = 'http://localhost:8080/api/v1/products';

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        ProductService,
        provideHttpClient(),
        provideHttpClientTesting()
      ]
    });
    service = TestBed.inject(ProductService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('debería obtener productos paginados usando GET con parámetros', () => {
    const mockPage: ProductPage = {
      content: [{
        id: 1,
        name: 'Test Product',
        code: 'TEST001',
        description: 'Test Description',
        price: 99.99,
        active: true,
        createdAt: '2025-10-30T12:00:00Z',
        updatedAt: '2025-10-30T12:00:00Z'
      }],
      pageable: {},
      last: true,
      totalPages: 1,
      totalElements: 1,
      size: 10,
      number: 0,
      first: true,
      numberOfElements: 1,
      empty: false
    };

    service.getAllProducts(0, 10, 'name,asc').subscribe(response => {
      expect(response).toEqual(mockPage);
    });

    // Usar HttpParams para asegurar que la codificación de URL sea consistente
    const req = httpMock.expectOne(request => 
      request.url === baseUrl &&
      request.params.get('page') === '0' &&
      request.params.get('size') === '10' &&
      request.params.get('sort') === 'name,asc'
    );
    
    expect(req.request.method).toBe('GET');
    req.flush(mockPage);
  });

  it('debería crear un producto usando POST', () => {
    const newProduct: Product = {
      name: 'New Product',
      code: 'NEW001',
      description: 'New Description',
      price: 149.99,
      active: true
    };

    const mockResponse: Product = {
      ...newProduct,
      id: 1,
      createdAt: '2025-10-30T12:00:00Z',
      updatedAt: '2025-10-30T12:00:00Z'
    };

    service.createProduct(newProduct).subscribe(response => {
      expect(response).toEqual(mockResponse);
    });

    const req = httpMock.expectOne(baseUrl);
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(newProduct);
    req.flush(mockResponse);
  });

  it('debería actualizar el estado activo de un producto usando PATCH', () => {
    const productId = 1;
    const newActiveStatus = false;
    const mockResponse: Product = {
      id: productId,
      name: 'Test Product',
      code: 'TEST001',
      description: 'Test Description',
      price: 99.99,
      active: newActiveStatus,
      updatedAt: '2025-10-30T12:00:00Z'
    };

    service.updateProductActiveStatus(productId, newActiveStatus).subscribe(response => {
      expect(response).toEqual(mockResponse);
      expect(response.active).toBe(newActiveStatus);
    });

    const req = httpMock.expectOne(`${baseUrl}/${productId}`);
    expect(req.request.method).toBe('PATCH');
    expect(req.request.body).toEqual({ active: newActiveStatus });
    req.flush(mockResponse);
  });

  it('debería eliminar un producto usando DELETE', () => {
    const productId = 1;

    service.deleteProduct(productId).subscribe(response => {
      expect(response).toBeNull();
    });

    const req = httpMock.expectOne(`${baseUrl}/${productId}`);
    expect(req.request.method).toBe('DELETE');
    req.flush(null);
  });
});