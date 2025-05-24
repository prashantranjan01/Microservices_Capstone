export interface Product {
    id: string;
    name: string;
    description: string | null;
    price: number;
    stockQuantity: number;
    status: string;
    imageUrl: string | null;
    createdBy: string;
    createdAt: string;
    updatedAt: string;
  }