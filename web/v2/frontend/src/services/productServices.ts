import axios from 'axios';
import type { Product } from '../types/Product';
import type { APIResponse } from '../types/APIResponse';


const PRODUCT_API_URL = 'http://localhost:8080/api/product';

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
  }
};