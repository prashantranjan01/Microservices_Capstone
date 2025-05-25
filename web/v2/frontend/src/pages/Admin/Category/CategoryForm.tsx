import React, { useState, useEffect } from 'react';
import { TextField, Button, Box, Typography, CircularProgress } from '@mui/material';
import { useNavigate, useParams } from 'react-router';
import type { Category } from '../../../types/Category';
import { categoryService } from '../../../services/categoryServices';

const CategoryForm: React.FC = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [loading, setLoading] = useState(!!id);
  const [category, setCategory] = useState<Partial<Category>>({});
  const [error, setError] = useState('');

  useEffect(() => {
    if (id) {
      const fetchCategory = async () => {
        try {
          const res = await categoryService.getCategoryById(id);
          if (res.data) {
            setCategory(res.data);
          }
        } catch (err) {
          setError('Failed to fetch category');
        } finally {
          setLoading(false);
        }
      };
      fetchCategory();
    }
  }, [id]);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError('');
    try {
      if (id) {
        await categoryService.updateCategory(id, category as Category);
      } else {
        await categoryService.createCategory(category as Category);
      }
      navigate('/admin/categories');
    } catch (err) {
      setError(id ? 'Failed to update category' : 'Failed to create category');
    }
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setCategory({
      ...category,
      [e.target.name]: e.target.value
    });
  };

  if (loading) return <CircularProgress />;

  return (
    <Box component="form" onSubmit={handleSubmit} sx={{ maxWidth: 600, mx: 'auto', mt: 4 }}>
      <Typography variant="h5" gutterBottom>
        {id ? 'Edit Category' : 'Add New Category'}
      </Typography>
      
      {error && (
        <Typography color="error" gutterBottom>
          {error}
        </Typography>
      )}

      <TextField
        fullWidth
        margin="normal"
        label="Title"
        name="title"
        value={category.title || ''}
        onChange={handleChange}
        required
      />

      <TextField
        fullWidth
        margin="normal"
        label="Image URL"
        name="imageUrl"
        value={category.imageUrl || ''}
        onChange={handleChange}
      />

      <Box sx={{ mt: 2 }}>
        <Button type="submit" variant="contained" color="primary" sx={{ mr: 2 }}>
          {id ? 'Update' : 'Create'}
        </Button>
        <Button variant="outlined" onClick={() => navigate('/admin/categories')}>
          Cancel
        </Button>
      </Box>
    </Box>
  );
};

export default CategoryForm;