import type { APIResponse } from '../types/APIResponse';
import type { SubCategory } from '../types/SubCategory';
import { axiosInstance } from '../utils/axiosInstance';

const SUB_CATEGORY_API_URL = 'http://localhost:8080/api/sub-category';

export const subCategoryService = {

  getAllSubCategoriesByCategoryId: async (categoryId : string): Promise<SubCategory[]> => {
    const res = await axiosInstance.get<APIResponse<SubCategory[]>>(`${SUB_CATEGORY_API_URL}/category/${categoryId}`);
    return res.data.data;
  },

  createSubCategory: async (subCategory: Omit<SubCategory, 'id' | 'createdAt' | 'updatedAt' | 'createdBy'>, categoryId: string): Promise<APIResponse<SubCategory>> => {
    try {
      const res = await axiosInstance.post<APIResponse<SubCategory>>(
        `${SUB_CATEGORY_API_URL}/category/${categoryId}`,
        subCategory
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

  updateSubCategory: async (subCategory: Omit<SubCategory, 'id' | 'createdAt' | 'updatedAt' | 'createdBy'>): Promise<APIResponse<SubCategory>> => {
    try {
      const res = await axiosInstance.put<APIResponse<SubCategory>>(
        SUB_CATEGORY_API_URL,
        subCategory
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
      const res = await axiosInstance.delete<APIResponse<string>>(
        `${SUB_CATEGORY_API_URL}/${id}`
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
      const res = await axiosInstance.get<APIResponse<SubCategory[]>>(
        SUB_CATEGORY_API_URL
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
      const res = await axiosInstance.get<APIResponse<SubCategory>>(
        `${SUB_CATEGORY_API_URL}/${id}`
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
  }
};
