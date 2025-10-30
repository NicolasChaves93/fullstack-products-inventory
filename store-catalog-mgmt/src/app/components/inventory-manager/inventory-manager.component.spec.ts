// src/app/components/inventory-manager/inventory-manager.component.spec.ts

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of, throwError } from 'rxjs'; // Para simular Observables
import { InventoryManagerComponent } from './inventory-manager.component';
import { InventoryService } from '../../services/inventory.service';

describe('InventoryManagerComponent', () => {
  let component: InventoryManagerComponent;
  let fixture: ComponentFixture<InventoryManagerComponent>;
  let inventoryServiceSpy: jasmine.SpyObj<InventoryService>;

  beforeEach(async () => {
    // 1. Crear un 'spy' (simulador) para el servicio
    inventoryServiceSpy = jasmine.createSpyObj('InventoryService', ['getInventoryByProductCode']);

    await TestBed.configureTestingModule({
      imports: [],
      providers: [
        { provide: InventoryService, useValue: inventoryServiceSpy }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(InventoryManagerComponent);
    component = fixture.componentInstance;
    component.productCode = 'LAP001'; // Definir un Input
  });

  it('debería cargar el stock y establecer newQuantity al iniciar', () => {
    const mockInventory = {
      id: 999,
      productCode: 'LAP001',
      quantity: 10,
    };
    // 2. Simular que el servicio devuelve datos exitosos
    inventoryServiceSpy.getInventoryByProductCode.and.returnValue(of(mockInventory));

    component.ngOnInit();
    
    // 3. Verificar el estado del componente
    expect(component.inventory).toEqual(mockInventory);
    expect(component.newQuantity).toBe(10);
    expect(component.loading).toBeFalse();
    expect(component.error).toBeNull();
  });

  it('debería mostrar un error si la carga inicial falla', () => {
    const errorResponse = new Error('404 Not Found');
    // 4. Simular que el servicio devuelve un error
    inventoryServiceSpy.getInventoryByProductCode.and.returnValue(throwError(() => errorResponse));

    component.ngOnInit();

    // 5. Verificar el estado del componente tras el error
    expect(component.inventory).toBeNull();
    expect(component.loading).toBeFalse();
    expect(component.error).not.toBeNull();
  });
});