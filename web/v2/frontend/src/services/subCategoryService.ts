import axios from 'axios';
import type { APIResponse } from '../types/APIResponse';
import type { SubCategory } from '../types/SubCategory';
import { authService } from './authServices';

const API_BASE_URL = 'http://localhost:8080/api/sub-category';

const getAuthHeaders = () => {
  const token = authService.getToken();
  return token ? { headers: { Authorization: `Bearer ${token}` } } : {};
};

export const subCategoryService = {
  createSubCategory: async (subCategory: SubCategory, categoryId: string): Promise<APIResponse<SubCategory>> => {
    try {
      const res = await axios.post<APIResponse<SubCategory>>(
        `${API_BASE_URL}/category/${categoryId}`,
        subCategory,
        getAuthHeaders()
      );
      return res.data;
    } catch (error: any) {
      return {
        status: false,
        statusCode: error.response?.status || 500,
        info: error.response?.data?.info || 'Failed to create sub-category',
        data: null as any
      };
    }
  },

  updateSubCategory: async (subCategory: SubCategory): Promise<APIResponse<SubCategory>> => {
    try {
      const res = await axios.put<APIResponse<SubCategory>>(
        API_BASE_URL,
        subCategory,
        getAuthHeaders()
      );
      return res.data;
    } catch (error: any) {
      return {
        status: false,
        statusCode: error.response?.status || 500,
        info: error.response?.data?.info || 'Failed to update sub-category',
        data: null as any
      };
    }
  },

  deleteSubCategory: async (id: string): Promise<APIResponse<string>> => {
    try {
      const res = await axios.delete<APIResponse<string>>(
        `${API_BASE_URL}/${id}`,
        getAuthHeaders()
      );
      return res.data;
    } catch (error: any) {
      return {
        status: false,
        statusCode: error.response?.status || 500,
        info: error.response?.data?.info || 'Failed to delete sub-category',
        data: null as any
      };
    }
  },

  getAllSubCategories: async (): Promise<APIResponse<SubCategory[]>> => {
    try {
      const res = await axios.get<APIResponse<SubCategory[]>>(
        API_BASE_URL,
        getAuthHeaders()
      );
      return res.data;
    } catch (error: any) {
      return {
        status: false,
        statusCode: error.response?.status || 500,
        info: error.response?.data?.info || 'Failed to fetch sub-categories',
        data: null as any
      };
    }
  },

  getSubCategoryById: async (id: string): Promise<APIResponse<SubCategory>> => {
    try {
      const res = await axios.get<APIResponse<SubCategory>>(
        `${API_BASE_URL}/${id}`,
        getAuthHeaders()
      );
      return res.data;
    } catch (error: any) {
      return {
        status: false,
        statusCode: error.response?.status || 404,
        info: error.response?.data?.info || 'Sub-category not found',
        data: null as any
      };
    }
  },

  getSubCategoriesByCategory: async (categoryId: string): Promise<APIResponse<SubCategory[]>> => {
    try {
      const res = await axios.get<APIResponse<SubCategory[]>>(
        `${API_BASE_URL}/category/${categoryId}`,
        getAuthHeaders()
      );
      return res.data;
    } catch (error: any) {
      return {
        status: false,
        statusCode: error.response?.status || 500,
        info: error.response?.data?.info || 'Failed to fetch sub-categories by category',
        data: null as any
      };
    }
  }
};
