import type { Category } from "./Category";

export interface SubCategory {
    id: string;
    title: string;
    category : Category
    createdBy: string;
    createdAt: string;
    updatedAt: string;
  }
