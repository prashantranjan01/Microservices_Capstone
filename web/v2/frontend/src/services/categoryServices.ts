import axios from 'axios';
import type { APIResponse } from '../types/APIResponse';
import type { Category } from '../types/Category';

const CATEGORY_API_URL = 'http://localhost:8080/api/category';

export const categoryService = {
  getAllCategories: async (): Promise<Category[]> => {
    const res = await axios.get<APIResponse<Category[]>>(`${CATEGORY_API_URL}/retrieve/categories`);
    return res.data.data;
  }
};
