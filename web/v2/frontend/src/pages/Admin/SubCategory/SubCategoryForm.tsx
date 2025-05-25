import React, { useState, useEffect } from 'react';
import { 
  TextField, 
  Button, 
  Box, 
  Typography, 
  CircularProgress, 
  MenuItem, 
  Select, 
  InputLabel, 
  FormControl 
} from '@mui/material';
import { useNavigate, useParams } from 'react-router';
import type { SubCategory } from '../../../types/SubCategory';
import { categoryService } from '../../../services/categoryServices';
import { subCategoryService } from '../../../services/subCategoryServices';

const SubCategoryForm: React.FC = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [loading, setLoading] = useState(true);
  const [categoryId , setCategoryId] = useState("");
  const [subCategory, setSubCategory] = useState<any>();
  const [categories, setCategories] = useState<any[]>([]);
  const [error, setError] = useState('');

  useEffect(() => {
    const fetchData = async () => {
      try {
        const [categoriesRes, subCategoryRes] = await Promise.all([
          categoryService.getAllCategories(),
          id ? subCategoryService.getSubCategoryById(id) : Promise.resolve(null)
        ]);
        
        if (categoriesRes) {
          setCategories(categoriesRes);
        }

        if (id && subCategoryRes?.status && subCategoryRes.data) {
          setSubCategory(subCategoryRes.data);
          setCategoryId(subCategoryRes.data.category?.id || '');
        }
      } catch (err) {
        setError('Failed to fetch data');
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, [id]);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError('');
    
    if (!categoryId) {
      setError('Please select a category');
      return;
    }

    try {
      if (id) {
        const response = await subCategoryService.updateSubCategory(subCategory);
        if (!response.status) {
          throw new Error(response.info);
        }
      } else {
        const response = await subCategoryService.createSubCategory(subCategory , categoryId);
        if (!response.status) {
          throw new Error(response.info);
        }
      }
      navigate('/admin/subcategories');
    } catch (err: any) {
      setError(err.message || (id ? 'Failed to update subcategory' : 'Failed to create subcategory'));
    }
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    if(subCategory){
      setSubCategory({
        ...subCategory,
        [e.target.name]: e.target.value
      });
    }else{
      setSubCategory({
        [e.target.name]: e.target.value
      });
    }
    
  };

  const handleCategoryChange = (e: any) => {
    setCategoryId(e.target.value);
  };

  if (loading) return <CircularProgress />;

  return (
    <Box component="form" onSubmit={handleSubmit} sx={{ maxWidth: 600, mx: 'auto', mt: 4 }}>
      <Typography variant="h5" gutterBottom>
        {id ? 'Edit SubCategory' : 'Add New SubCategory'}
      </Typography>
      
      {error && (
        <Typography color="error" gutterBottom>
          {error}
        </Typography>
      )}

      <FormControl fullWidth margin="normal" required>
        <InputLabel>Category</InputLabel>
        <Select
          name="categoryId"
          value={categoryId || ''}
          onChange={handleCategoryChange}
          label="Category"
        >
          {categories.map((category) => (
            <MenuItem key={category.id} value={category.id}>
              {category.title}
            </MenuItem>
          ))}
        </Select>
      </FormControl>

      <TextField
        fullWidth
        margin="normal"
        label="Title"
        name="title"
        value={subCategory?.title || ''}
        onChange={handleChange}
        required
      />

      <Box sx={{ mt: 2 }}>
        <Button type="submit" variant="contained" color="primary" sx={{ mr: 2 }}>
          {id ? 'Update' : 'Create'}
        </Button>
        <Button variant="outlined" onClick={() => navigate('/admin/subcategories')}>
          Cancel
        </Button>
      </Box>
    </Box>
  );
};

export default SubCategoryForm;