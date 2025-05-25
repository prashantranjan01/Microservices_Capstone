import type { Category } from "./Category";
import type { SubCategory } from "./SubCategory";

export const ProductStatus = {
  ACTIVE: 'ACTIVE',
  INACTIVE: 'INACTIVE',
  OUT_OF_STOCK: 'OUT_OF_STOCK',
} as const;

export type ProductStatus = (typeof ProductStatus)[keyof typeof ProductStatus];

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
  createdAt?: string;
  updatedAt?: string;
  createdBy?: string;
}