import type { Product } from "./Product";
import type { SubCategory } from "./SubCategory";

export interface Category {
    id: string;
    title: string;
    imageUrl?: string;
    createdAt?: string;
    updatedAt?: string;
    createdBy?: string;
    subCategories?: SubCategory[];
    products?: Product[];
  }