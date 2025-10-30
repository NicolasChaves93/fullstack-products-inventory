import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { InventoryService } from './inventory.service';
import { provideHttpClient } from '@angular/common/http';

describe('InventoryService', () => {
  let service: InventoryService;
  let httpMock: HttpTestingController;
  const baseUrl = 'http://localhost:8081/api/v1/inventories';

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        InventoryService,
        provideHttpClient(),
        provideHttpClientTesting(),
      ]
    });
    service = TestBed.inject(InventoryService);
    httpMock = TestBed.inject(HttpTestingController);
  });
  
  afterEach(() => {
    httpMock.verify();
  });

  it('debería obtener el inventario por código de producto usando GET /{code}', () => {
    const testCode = 'LAP001';
    const mockInventory = {
      id: 999,
      productCode: 'LAP001',
      quantity: 10,
    };

    service.getInventoryByProductCode(testCode).subscribe(inventory => {
      expect(inventory).toEqual(mockInventory);
      expect(inventory.quantity).toBe(10);
    });

    const req = httpMock.expectOne(`${baseUrl}/${testCode}`);
    expect(req.request.method).toBe('GET');
    req.flush(mockInventory); 
  });

  it('debería actualizar la cantidad del inventario usando PATCH /update', () => {
    const testCode = 'LAP001';
    const newQuantity = 5;
    const mockUpdatedInventory = {
      id: 999,
      productCode: 'LAP001',
      quantity: newQuantity,
    };

    service.updateInventoryQuantity(testCode, newQuantity).subscribe(inventory => {
      expect(inventory).toEqual(mockUpdatedInventory);
      expect(inventory.quantity).toBe(newQuantity);
    });

    const req = httpMock.expectOne(`${baseUrl}/update`);
    expect(req.request.method).toBe('PATCH');
    expect(req.request.body).toEqual({ productCode: testCode, quantity: newQuantity });
    req.flush(mockUpdatedInventory);
  });
});