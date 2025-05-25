// src/types/SubCategory.ts
import { Category } from './Category';
import { Product } from './Product';

export interface SubCategory {
  id: string;
  title: string;
  category?: Category;
  categoryId?: string;
  createdAt?: string;
  updatedAt?: string;
  createdBy?: string;
  products?: Product[];
}