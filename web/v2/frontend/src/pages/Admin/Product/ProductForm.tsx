// src/pages/Admin/Product/ProductForm.tsx
import React, { useState, useEffect } from 'react';
import { 
  TextField, 
  Button, 
  Box, 
  Typography, 
  CircularProgress, 
  MenuItem, 
  InputLabel, 
  FormControl, 
  Select,
  FormHelperText
} from '@mui/material';
import { useNavigate, useParams } from 'react-router';
import { categoryService } from '../../../services/categoryServices';
import { productService } from '../../../services/productServices';
import { ProductStatus, type Product } from '../../../types/Product';
import { subCategoryService } from '../../../services/subCategoryServices';


const ProductForm: React.FC = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [loading, setLoading] = useState(true);
  const [product, setProduct] = useState<Partial<Product>>({
    status: ProductStatus.ACTIVE,
    stockQuantity: 0,
    price: 0
  });
  const [categories, setCategories] = useState<any[]>([]);
  const [categoryId , setCategoryId] = useState("");
  const [subCategories, setSubCategories] = useState<any[]>([]);
  const [subCategoryId , setSubCategoryId] = useState("");
  const [error, setError] = useState('');

  useEffect(() => {
    const fetchData = async () => {
      try {
        const [categoriesRes] = await Promise.all([
          categoryService.getAllCategories()
        ]);
        
        if (categoriesRes) {
          setCategories(categoriesRes);
        }

        if (id) {
          const productRes = await productService.getProductById(id);
          if (productRes) {
            setProduct(productRes);
            setCategoryId(productRes.category?.id || '');
            setSubCategoryId(productRes.subCategory?.id || '');
           
            if (productRes?.category?.id) {
              const subCategoriesRes = await subCategoryService.getAllSubCategoriesByCategoryId(productRes.category.id);
              if (subCategoriesRes) {
                setSubCategories(subCategoriesRes);
              }
            }
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
    
    if (!categoryId || !subCategoryId) {
      setError('Please select both category and subcategory');
      return;
    }

    try {
      if (id) {
        await productService.updateProduct(id, product as Product);
      } else {
        await productService.createProduct(
          product as Product, 
          categoryId,
          subCategoryId
        );
      }
      navigate('/admin/products');
    } catch (err) {
      setError(id ? 'Failed to update product' : 'Failed to create product');
    }
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setProduct({
      ...product,
      [name]: name === 'price' || name === 'stockQuantity' 
        ? parseFloat(value) || 0 
        : value
    });
  };

  const handleStatusChange = (e: any) => {
    setProduct({
      ...product,
      status: e.target.value as ProductStatus
    });
  };


  const handleCategoryChange = async (e: any) => {
    setCategoryId(e.target.value);
    
    try {
      const res = await subCategoryService.getAllSubCategoriesByCategoryId(e.target.value);
      if (res) {
        setSubCategories(res);
      }
    } catch (err) {
      setError('Failed to fetch subcategories');
    }
  };

  if (loading) return <CircularProgress />;

  return (
    <Box component="form" onSubmit={handleSubmit} sx={{ maxWidth: 600, mx: 'auto', mt: 4 }}>
      <Typography variant="h5" gutterBottom>
        {id ? 'Edit Product' : 'Add New Product'}
      </Typography>
      
      {error && (
        <Typography color="error" gutterBottom>
          {error}
        </Typography>
      )}

      <TextField
        fullWidth
        margin="normal"
        label="Name"
        name="name"
        value={product.name || ''}
        onChange={handleChange}
        required
      />

      <TextField
        fullWidth
        margin="normal"
        label="Description"
        name="description"
        value={product.description || ''}
        onChange={handleChange}
        multiline
        rows={3}
      />

      <Box sx={{ display: 'flex', gap: 2 }}>
        <TextField
          fullWidth
          margin="normal"
          label="Price"
          name="price"
          type="number"
          value={product.price || 0}
          onChange={handleChange}
          required
          inputProps={{ step: "0.01", min: "0" }}
        />

        <TextField
          fullWidth
          margin="normal"
          label="Stock Quantity"
          name="stockQuantity"
          type="number"
          value={product.stockQuantity || 0}
          onChange={handleChange}
          required
          inputProps={{ min: "0" }}
        />
      </Box>

      <FormControl fullWidth margin="normal" required>
        <InputLabel>Status</InputLabel>
        <Select
          name="status"
          value={product.status || 'ACTIVE'}
          onChange={handleStatusChange}
          label="Status"
        >
          {Object.values(ProductStatus).map((status) => (
            <MenuItem key={status} value={status}>
              {status}
            </MenuItem>
          ))}
        </Select>
      </FormControl>

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

      <FormControl fullWidth margin="normal" required>
        <InputLabel>SubCategory</InputLabel>
        <Select
          name="subCategoryId"
          value={subCategoryId || ''}
          onChange={(e) => setSubCategoryId(e.target.value)}
          label="SubCategory"
          disabled={!categoryId || subCategories.length === 0}
        >
          {subCategories.length === 0 && (
            <MenuItem disabled value="">
              {categoryId ? 'No subcategories found' : 'Select a category first'}
            </MenuItem>
          )}
          {subCategories.map((subCategory) => (
            <MenuItem key={subCategory.id} value={subCategory.id}>
              {subCategory.title}
            </MenuItem>
          ))}
        </Select>
        {!categoryId && (
          <FormHelperText>Please select a category first</FormHelperText>
        )}
      </FormControl>

      <TextField
        fullWidth
        margin="normal"
        label="Image URL"
        name="imageUrl"
        value={product.imageUrl || ''}
        onChange={handleChange}
      />

      <Box sx={{ mt: 2 }}>
        <Button type="submit" variant="contained" color="primary" sx={{ mr: 2 }}>
          {id ? 'Update' : 'Create'}
        </Button>
        <Button variant="outlined" onClick={() => navigate('/admin/products')}>
          Cancel
        </Button>
      </Box>
    </Box>
  );
};

export default ProductForm;