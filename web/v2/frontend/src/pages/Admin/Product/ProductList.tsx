import React, { useEffect, useState } from 'react';
import { 
  Button, 
  Table, 
  TableBody, 
  TableCell, 
  TableContainer, 
  TableHead, 
  TableRow, 
  Paper, 
  IconButton,
  Chip
} from '@mui/material';
import { Edit, Delete } from '@mui/icons-material';

import { useNavigate } from 'react-router';
import type { Product, ProductStatus } from '../../../types/Product';
import { productService } from '../../../services/productServices';

const ProductList: React.FC = () => {
  const [products, setProducts] = useState<Product[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const navigate = useNavigate();

  useEffect(() => {
    const fetchProducts = async () => {
      try {
        const res = await productService.getProducts();
        if (res) {
          setProducts(res);
        }
      } catch (err) {
        setError('Failed to fetch products');
      } finally {
        setLoading(false);
      }
    };

    fetchProducts();
  }, []);

  const handleDelete = async (id: string) => {
    try {
      await productService.deleteProduct(id);
      setProducts(products.filter(product => product.id !== id));
    } catch (err) {
      setError('Failed to delete product');
    }
  };

  const getStatusColor = (status: ProductStatus) => {
    switch (status) {
      case 'ACTIVE': return 'success';
      case 'INACTIVE': return 'warning';
      case 'OUT_OF_STOCK': return 'error';
      default: return 'default';
    }
  };

  if (loading) return <div>Loading...</div>;
  if (error) return <div>{error}</div>;

  return (
    <div>
      <Button 
        variant="contained" 
        color="primary" 
        onClick={() => navigate('/admin/products/add')}
        sx={{ mb: 2 }}
      >
        Add Product
      </Button>

      <TableContainer component={Paper}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>Name</TableCell>
              <TableCell>Price</TableCell>
              <TableCell>Stock</TableCell>
              <TableCell>Status</TableCell>
              <TableCell>Category</TableCell>
              <TableCell>SubCategory</TableCell>
              <TableCell>Actions</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {products.map((product) => (
              <TableRow key={product.id}>
                <TableCell>{product.name}</TableCell>
                <TableCell>₹{product.price}</TableCell>
                <TableCell>{product.stockQuantity}</TableCell>
                <TableCell>
                  <Chip 
                    label={product.status} 
                    color={getStatusColor(product.status)} 
                    size="small" 
                  />
                </TableCell>
                <TableCell>{product.category?.title}</TableCell>
                <TableCell>{product.subCategory?.title}</TableCell>
                <TableCell>
                  <IconButton onClick={() => navigate(`/admin/products/edit/${product.id}`)}>
                    <Edit />
                  </IconButton>
                  <IconButton onClick={() => handleDelete(product.id)}>
                    <Delete />
                  </IconButton>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </div>
  );
};

export default ProductList;