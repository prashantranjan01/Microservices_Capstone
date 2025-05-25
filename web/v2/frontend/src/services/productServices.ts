import axios from 'axios';
import type { APIResponse } from '../types/APIResponse';
import type { Product } from '../types/Product';
import { axiosInstance } from '../utils/axiosInstance';

const PRODUCT_API_URL = 'http://localhost:8080/api/product';

export const productService = {

  getProducts: async (): Promise<Product[]> => {
    const res = await axiosInstance.get<APIResponse<Product[]>>(`${PRODUCT_API_URL}/retrieve/products`);
    return res.data.data;
  },
  
  getProductsByQuery: async(query : string) : Promise<Product[]> => {
    const res = await axiosInstance.get<APIResponse<Product[]>>(`${PRODUCT_API_URL}/s/products?q=${query}`);
    return res.data.data;
  },

  getProductsByCategoryIdAndBySubCategoryId: async (categoryId : string , subCategoryId : string): Promise<Product[]> => {
    const res = await axiosInstance.get<APIResponse<Product[]>>(`${PRODUCT_API_URL}/category/${categoryId}/sub-category/${subCategoryId}`);
    return res.data.data;
  },

  getProductById: async(productId : string) => {
    const res = await axiosInstance.get<APIResponse<Product>>(`${PRODUCT_API_URL}/${productId}`);
    return res.data.data;
  },

  createProduct: async (product: Omit<Product,'id' | 'createdAt' | 'updatedAt' | 'createdBy' | 'category' | 'subCategory'>, categoryId: string, subCategoryId: string): Promise<Product> => {
    const res = await axiosInstance.post<APIResponse<Product>>(`${PRODUCT_API_URL}/category/${categoryId}/sub-category/${subCategoryId}`, product);
    return res.data.data;
  },

  updateProduct: async (id: string, product: Product): Promise<Product> => {
      const res = await axiosInstance.put<APIResponse<Product>>(`${PRODUCT_API_URL}/${id}`,product);
      return res.data.data;
  },

  deleteProduct: async (id: string): Promise<string> => {
      const res = await axiosInstance.delete<APIResponse<string>>(`${PRODUCT_API_URL}/${id}`);
      return res.data.data;
  },
};
