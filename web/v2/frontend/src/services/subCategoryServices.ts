import axios from 'axios';
import type { APIResponse } from '../types/APIResponse';
import type { SubCategory } from '../types/SubCategory';

const SUB_CATEGORY_API_URL = 'http://localhost:8080/api/sub-category';

export const subCategoryService = {
  getAllSubCategoriesByCategoryId: async (categoryId : string): Promise<SubCategory[]> => {
    const res = await axios.get<APIResponse<SubCategory[]>>(`${SUB_CATEGORY_API_URL}/category/${categoryId}`);
    return res.data.data;
  }
};
