import axios from 'axios';
import type { APIResponse } from '../types/APIResponse';
import type { Category } from '../types/Category';
import { authService } from './authServices';

const API_BASE_URL = 'http://localhost:8080/api/category';

const getAuthHeaders = () => {
  const token = authService.getToken();
  return token
    ? { headers: { Authorization: `Bearer ${token}` } }
    : {};
};

export const categoryService = {
  createCategory: async (category: Omit<Category, 'id' | 'createdAt' | 'updatedAt' | 'createdBy'>): Promise<APIResponse<Category>> => {
    try {
      const res = await axios.post<APIResponse<Category>>(API_BASE_URL, category, getAuthHeaders());
      return res.data;
    } catch (error: any) {
      return {
        status: false,
        statusCode: error.response?.status || 500,
        info: error.response?.data?.info || 'Failed to create category',
        data: null as any
      };
    }
  },

  updateCategory: async (id: string, category: Partial<Category>): Promise<APIResponse<Category>> => {
    try {
      const res = await axios.put<APIResponse<Category>>(`${API_BASE_URL}/${id}`, category, getAuthHeaders());
      return res.data;
    } catch (error: any) {
      return {
        status: false,
        statusCode: error.response?.status || 500,
        info: error.response?.data?.info || 'Failed to update category',
        data: null as any
      };
    }
  },

  deleteCategory: async (id: string): Promise<APIResponse<string>> => {
    try {
      const res = await axios.delete<APIResponse<string>>(`${API_BASE_URL}/${id}`, getAuthHeaders());
      return res.data;
    } catch (error: any) {
      return {
        status: false,
        statusCode: error.response?.status || 500,
        info: error.response?.data?.info || 'Failed to delete category',
        data: null as any
      };
    }
  },

  getAllCategories: async (): Promise<APIResponse<Category[]>> => {
    try {
      const res = await axios.get<APIResponse<Category[]>>(`${API_BASE_URL}/retrieve/categories`, getAuthHeaders());
      return res.data;
    } catch (error: any) {
      return {
        status: false,
        statusCode: error.response?.status || 500,
        info: error.response?.data?.info || 'Failed to fetch categories',
        data: null as any
      };
    }
  },

  getCategoryById: async (id: string): Promise<APIResponse<Category>> => {
    try {
      const res = await axios.get<APIResponse<Category>>(`${API_BASE_URL}/${id}`, getAuthHeaders());
      return res.data;
    } catch (error: any) {
      return {
        status: false,
        statusCode: error.response?.status || 404,
        info: error.response?.data?.info || 'Category not found',
        data: null as any
      };
    }
  }
};
