import axios from 'axios';
import type { APIResponse } from '../types/APIResponse';
import type { Product, ProductStatus } from '../types/Product';
import { authService } from './authServices';

const API_BASE_URL = 'http://localhost:8080/api/product';

const getAuthHeaders = () => {
  const token = authService.getToken();
  return token
    ? { headers: { Authorization: `Bearer ${token}` } }
    : {};
};

export const productService = {

  getProducts: async (): Promise<Product[]> => {
    const res = await axios.get<APIResponse<Product[]>>(`${PRODUCT_API_URL}/retrieve/products`);
    return res.data.data;
  },
  
  getProductsByQuery: async(query : string) : Promise<Product[]> => {
    const res = await axios.get<APIResponse<Product[]>>(`${PRODUCT_API_URL}/s/products?q=${query}`);
    return res.data.data;
  },

  getProductsByCategoryIdAndBySubCategoryId: async (categoryId : string , subCategoryId : string): Promise<Product[]> => {
    const res = await axios.get<APIResponse<Product[]>>(`${PRODUCT_API_URL}/category/${categoryId}/sub-category/${subCategoryId}`);
    return res.data.data;
  },

  getProductById: async(productId : string) => {
    const res = await axios.get<APIResponse<Product>>(`${PRODUCT_API_URL}/${productId}`);
    return res.data.data;
  createProduct: async (
    product: Product,
    categoryId: string,
    subCategoryId: string
  ): Promise<APIResponse<Product>> => {
    try {
      const res = await axios.post<APIResponse<Product>>(
        `${API_BASE_URL}/category/${categoryId}/sub-category/${subCategoryId}`,
        product,
        getAuthHeaders()
      );
      return res.data;
    } catch (error: any) {
      return {
        status: false,
        statusCode: error.response?.status || 500,
        info: error.response?.data?.info || 'Failed to create product',
        data: null as any
      };
    }
  },

  updateProduct: async (id: string, product: Product): Promise<APIResponse<Product>> => {
    try {
      const res = await axios.put<APIResponse<Product>>(
        `${API_BASE_URL}/${id}`,
        product,
        getAuthHeaders()
      );
      return res.data;
    } catch (error: any) {
      return {
        status: false,
        statusCode: error.response?.status || 500,
        info: error.response?.data?.info || 'Failed to update product',
        data: null as any
      };
    }
  },

  deleteProduct: async (id: string): Promise<APIResponse<string>> => {
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
        info: error.response?.data?.info || 'Failed to delete product',
        data: null as any
      };
    }
  },

  getAllProducts: async (): Promise<APIResponse<Product[]>> => {
    try {
      const res = await axios.get<APIResponse<Product[]>>(
        `${API_BASE_URL}/retrieve/products`,
        getAuthHeaders()
      );
      return res.data;
    } catch (error: any) {
      return {
        status: false,
        statusCode: error.response?.status || 500,
        info: error.response?.data?.info || 'Failed to fetch products',
        data: null as any
      };
    }
  },

  getProductById: async (id: string): Promise<APIResponse<Product>> => {
    try {
      const res = await axios.get<APIResponse<Product>>(
        `${API_BASE_URL}/${id}`,
        getAuthHeaders()
      );
      return res.data;
    } catch (error: any) {
      return {
        status: false,
        statusCode: error.response?.status || 404,
        info: error.response?.data?.info || 'Product not found',
        data: null as any
      };
    }
  },

  getProductsByStatus: async (status: ProductStatus): Promise<APIResponse<Product[]>> => {
    try {
      const res = await axios.get<APIResponse<Product[]>>(
        `${API_BASE_URL}?status=${status}`,
        getAuthHeaders()
      );
      return res.data;
    } catch (error: any) {
      return {
        status: false,
        statusCode: error.response?.status || 500,
        info: error.response?.data?.info || 'Failed to fetch products by status',
        data: null as any
      };
    }
  }
};
