import type { Category } from "./Category";
import type { SubCategory } from "./SubCategory";

export enum ProductStatus {
  ACTIVE = 'ACTIVE',
  INACTIVE = 'INACTIVE',
  OUT_OF_STOCK = 'OUT_OF_STOCK'
}

export interface Product {
  id: string;
  name: string;
  description: string;
  price: number;
  stockQuantity: number;
  status: ProductStatus;
  imageUrl?: string;
  category?: Category;
  subCategory?: SubCategory;
  categoryId?: string;
  subCategoryId?: string;
  createdAt?: string;
  updatedAt?: string;
  createdBy?: string;
}