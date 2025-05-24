import axios from 'axios';
import type { Product } from '../types/Product';
import type { APIResponse } from '../types/APIResponse';


const PRODUCT_API_URL = 'http://localhost:8080/api/product';

export const productService = {
  getAllProducts: async (): Promise<APIResponse<Product[]>> => {
    const res = await axios.get<APIResponse<Product[]>>(`${PRODUCT_API_URL}/retrieve/products`);
    return res.data;
  }
};