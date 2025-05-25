import type { APIResponse } from '../types/APIResponse';
import type { Category } from '../types/Category';
import { axiosInstance } from '../utils/axiosInstance';

const CATEGORY_API_URL = 'http://localhost:8080/api/category';

export const categoryService = {
  getAllCategories: async (): Promise<Category[]> => {
    const res = await axiosInstance.get<APIResponse<Category[]>>(`${CATEGORY_API_URL}/retrieve/categories`);
    return res.data.data;
  },

  createCategory: async (category: Omit<Category, 'id' | 'createdAt' | 'updatedAt' | 'createdBy'>): Promise<APIResponse<Category>> => {
      const res = await axiosInstance.post<APIResponse<Category>>(CATEGORY_API_URL, category);
      return res.data;
  },

  updateCategory: async (id: string, category: Partial<Category>): Promise<APIResponse<Category>> => {
      const res = await axiosInstance.put<APIResponse<Category>>(`${CATEGORY_API_URL}/${id}`, category);
      return res.data;
  },

  deleteCategory: async (id: string): Promise<APIResponse<string>> => {
      const res = await axiosInstance.delete<APIResponse<string>>(`${CATEGORY_API_URL}/${id}`);
      return res.data;
  },

  getCategoryById: async (id: string): Promise<APIResponse<Category>> => {
      const res = await axiosInstance.get<APIResponse<Category>>(`${CATEGORY_API_URL}/${id}`);
      return res.data;
  }
};
