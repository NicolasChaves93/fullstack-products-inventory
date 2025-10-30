export interface Product {
    id?: number; // Opcional para la creación
    name: string;
    code: string;
    description: string;
    price: number;
    active: boolean;
    createdAt?: string;
    updatedAt?: string;
}

// Interfaz para la respuesta paginada (Get All)
export interface ProductPage {
    content: Product[];
    pageable: any; // Se puede tipar mejor, pero 'any' es suficiente por ahora
    last: boolean;
    totalPages: number;
    totalElements: number;
    size: number;
    number: number;
    first: boolean;
    numberOfElements: number;
    empty: boolean;
}