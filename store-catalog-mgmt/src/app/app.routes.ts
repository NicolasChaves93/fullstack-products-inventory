import { Routes } from '@angular/router';
import { ProductListComponent } from './components/product-list/product-list.component';

export const routes: Routes = [
  {
    // Ruta Principal: Carga el componente de lista al acceder a la raíz (/)
    path: '',
    component: ProductListComponent,
    title: 'Gestión de Productos'
  },
  {
    // ⚠️ Ruta Wildcard: Redirige al componente principal si la URL no existe
    path: '**',
    redirectTo: '',
    pathMatch: 'full'
  }
];
