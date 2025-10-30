import { ComponentFixture, TestBed } from '@angular/core/testing';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ProductListComponent } from './product-list.component';
import { ProductService } from '../../services/product.service';
import { Product, ProductPage } from '../../models/product.model';
import { of, throwError } from 'rxjs';
import { PageEvent } from '@angular/material/paginator';

describe('ProductListComponent', () => {
  let component: ProductListComponent;
  let fixture: ComponentFixture<ProductListComponent>;
  let productService: jasmine.SpyObj<ProductService>;

  const mockProduct: Product = {
    id: 1,
    name: 'Test Product',
    code: 'TEST001',
    description: 'Test Description',
    price: 99.99,
    active: true
  };

  const mockProductPage: ProductPage = {
    content: [mockProduct],
    pageable: {},
    last: false,
    totalPages: 3,
    totalElements: 15,
    size: 5,
    number: 0,
    first: true,
    numberOfElements: 5,
    empty: false
  };

  beforeEach(async () => {
    const spy = jasmine.createSpyObj('ProductService', [
      'getAllProducts',
      'createProduct',
      'updateProductActiveStatus',
      'deleteProduct'
    ]);
    
    await TestBed.configureTestingModule({
      imports: [
        ProductListComponent,
        BrowserAnimationsModule // Necesario para Material
      ],
      providers: [
        { provide: ProductService, useValue: spy }
      ]
    }).compileComponents();

    productService = TestBed.inject(ProductService) as jasmine.SpyObj<ProductService>;
    productService.getAllProducts.and.returnValue(of(mockProductPage));

    fixture = TestBed.createComponent(ProductListComponent);
    component = fixture.componentInstance;
  });

  it('debería crear el componente', () => {
    expect(component).toBeTruthy();
  });

  it('debería cargar productos al inicializar', () => {
    productService.getAllProducts.and.returnValue(of(mockProductPage));
    fixture.detectChanges(); // Trigger ngOnInit

    expect(productService.getAllProducts).toHaveBeenCalledWith(0, 5, 'name,asc');
    expect(component.products).toEqual(mockProductPage.content);
    expect(component.loading).toBeFalse();
  });

  it('debería manejar errores al cargar productos', () => {
    const errorMessage = 'Error loading products';
    productService.getAllProducts.and.returnValue(throwError(() => new Error(errorMessage)));
    spyOn(console, 'error');
    
    fixture.detectChanges();

    expect(console.error).toHaveBeenCalled();
    expect(component.loading).toBeFalse();
  });

  it('debería crear un nuevo producto', () => {
    const newProduct: Product = {
      name: 'New Product',
      code: 'NEW001',
      description: 'New Description',
      price: 149.99,
      active: true
    };
    
    productService.createProduct.and.returnValue(of({ ...newProduct, id: 2 }));
    spyOn(window, 'alert');
    spyOn(component, 'fetchProducts');

    component.newProduct = newProduct;
    component.handleCreateProduct();

    expect(productService.createProduct).toHaveBeenCalledWith(newProduct);
    expect(window.alert).toHaveBeenCalled();
    expect(component.fetchProducts).toHaveBeenCalledWith(component.currentPage);
    expect(component.newProduct).toEqual({ name: '', code: '', description: '', price: 0, active: true });
  });

  it('debería actualizar el estado activo de un producto de activo a inactivo', () => {
    // Empezamos con un producto activo (active: true)
    const product = { ...mockProduct, id: 1, active: true };
    // El servicio devolverá el producto con estado inactivo
    const updatedProduct = { ...product, active: false };
    
    productService.updateProductActiveStatus.and.returnValue(of(updatedProduct));
    component.products = [product];

    component.toggleActiveStatus(product);

    // Debería llamar al servicio con (id, false) porque estamos cambiando de true a false
    expect(productService.updateProductActiveStatus).toHaveBeenCalledWith(1, false);
    expect(component.products[0].active).toBeFalse();
  });

  it('debería actualizar el estado activo de un producto de inactivo a activo', () => {
    // Empezamos con un producto inactivo (active: false)
    const product = { ...mockProduct, id: 1, active: false };
    // El servicio devolverá el producto con estado activo
    const updatedProduct = { ...product, active: true };
    
    productService.updateProductActiveStatus.and.returnValue(of(updatedProduct));
    component.products = [product];

    component.toggleActiveStatus(product);

    // Debería llamar al servicio con (id, true) porque estamos cambiando de false a true
    expect(productService.updateProductActiveStatus).toHaveBeenCalledWith(1, true);
    expect(component.products[0].active).toBeTrue();
  });

  it('debería eliminar un producto después de confirmar', () => {
    spyOn(window, 'confirm').and.returnValue(true);
    spyOn(window, 'alert');
    productService.deleteProduct.and.returnValue(of(void 0));
    spyOn(component, 'fetchProducts');

    component.handleDelete(1);

    expect(productService.deleteProduct).toHaveBeenCalledWith(1);
    expect(window.alert).toHaveBeenCalledWith('Producto eliminado.');
    expect(component.fetchProducts).toHaveBeenCalledWith(component.currentPage);
  });

  it('debería manejar la paginación', () => {
    const pageEvent: PageEvent = {
      pageIndex: 1,
      pageSize: 10,
      length: 15
    };

    productService.getAllProducts.and.returnValue(of({
      ...mockProductPage,
      number: 1,
      size: 10
    }));

    component.onPage(pageEvent);

    expect(productService.getAllProducts).toHaveBeenCalledWith(1, 10, 'name,asc');
    expect(component.pageSize).toBe(10);
  });

  it('debería cambiar de página usando delta', () => {
    component.productPage = mockProductPage;
    component.currentPage = 1;

    component.changePage(-1); // Retroceder una página

    expect(productService.getAllProducts).toHaveBeenCalledWith(0, component.pageSize, component.sort);
  });
});
