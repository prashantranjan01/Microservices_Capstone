// src/pages/Admin/SubCategory/SubCategoryForm.tsx
import React, { useState, useEffect } from 'react';
import { TextField, Button, Box, Typography, CircularProgress, MenuItem, Select, InputLabel, FormControl } from '@mui/material';
import { useNavigate, useParams } from 'react-router';
import type { SubCategory } from '../../../types/SubCategory';
import type { Category } from '../../../types/Category';
import { categoryService } from '../../../services/categoryService';
import { subCategoryService } from '../../../services/subCategoryService';


const SubCategoryForm: React.FC = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [loading, setLoading] = useState(true);
  const [subCategory, setSubCategory] = useState<Partial<SubCategory>>({});
  const [categories, setCategories] = useState<Category[]>([]);
  const [error, setError] = useState('');

  useEffect(() => {
    const fetchData = async () => {
      try {
        const [categoriesRes] = await Promise.all([
          categoryService.getAllCategories()
        ]);
        
        if (categoriesRes.data) {
          setCategories(categoriesRes.data);
        }

        if (id) {
          const subCategoryRes = await subCategoryService.getSubCategoryById(id);
          if (subCategoryRes.data) {
            setSubCategory(subCategoryRes.data);
          }
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
    
    if (!subCategory.categoryId) {
      setError('Please select a category');
      return;
    }

    try {
      if (id) {
        await subCategoryService.updateSubCategory(subCategory as SubCategory);
      } else {
        await subCategoryService.createSubCategory(
          subCategory as SubCategory, 
          subCategory.categoryId
        );
      }
      navigate('/admin/subcategories');
    } catch (err) {
      setError(id ? 'Failed to update subcategory' : 'Failed to create subcategory');
    }
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setSubCategory({
      ...subCategory,
      [e.target.name]: e.target.value
    });
  };

  const handleCategoryChange = (e: any) => {
    setSubCategory({
      ...subCategory,
      categoryId: e.target.value
    });
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
          value={subCategory.categoryId || ''}
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
        value={subCategory.title || ''}
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