export interface Inventory {
    id: number | null; // Nullable
    productCode: string;
    quantity: number;
    lastUpdated?: string;
    version?: number;
}