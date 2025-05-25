import {
  Box,
  Button,
  Grid,
  Typography,
  Breadcrumbs,
  Link,
  Paper,
  IconButton,
  Container,
} from '@mui/material';
import RemoveIcon from '@mui/icons-material/Remove';
import AddIcon from '@mui/icons-material/Add';
import { useParams } from 'react-router';
import { useQuery } from '@tanstack/react-query';
import { useState } from 'react';
import { productService } from '../../services/productServices';
import type { Product } from '../../types/Product';

const ProductPage = () => {
  const { productId } = useParams();
  const [quantity, setQuantity] = useState(1);

  const { data: product } = useQuery<Product>({
    queryKey: ['products', productId],
    queryFn: () => productService.getProductById(productId!),
    enabled: !!productId,
  });

  if (!product) return null;

  const handleQuantityChange = (delta: number) => {
    setQuantity((prev) => Math.max(1, prev + delta));
  };

  return (
    <Container sx={{ p: 4}} >
      <Grid container spacing={4} alignItems='center' display='flex' flexDirection='row' >
        <Grid flex='1'>
          <Box
            component="img"
            src={product.imageUrl || '/placeholder.jpg'}
            alt={product.name}
            sx={{ width: '100%', maxHeight: 400, objectFit: 'contain' }}
          />
        </Grid>
        <Grid flex='1'>
          <Typography variant="h4" fontWeight="bold" gutterBottom>
            {product.name}
          </Typography>
            {product.description && <>
            <Typography variant="subtitle1" gutterBottom>
                Description
            </Typography>
            <Typography variant="body2" color="text.secondary" gutterBottom>
                {product.description}
          </Typography>
          </>}
          <Box mt={2} mb={1}>
            <Paper variant="outlined" sx={{ p: 2, width: 100, textAlign: 'center' }}>
              {/* <Typography variant="subtitle2">500 g</Typography> */}
              <Typography fontWeight="bold">₹{product.price}</Typography>
              {/* <Typography sx={{ textDecoration: 'line-through' }} color="text.secondary">
                ₹{product.originalPrice}
              </Typography> */}
              {/* <Typography variant="caption" color="success.main">
                {Math.round(
                  ((product.originalPrice - product.discountedPrice) /
                    product.originalPrice) *
                    100
                )}
                % off
              </Typography> */}
            </Paper>
          </Box>

          <Box display="flex" alignItems="center" gap={2} my={2}>
            <IconButton onClick={() => handleQuantityChange(-1)}>
              <RemoveIcon />
            </IconButton>
            <Typography>{quantity}</Typography>
            <IconButton onClick={() => handleQuantityChange(1)}>
              <AddIcon />
            </IconButton>
          </Box>

          <Box display="flex" gap={2} mt={2}>
            <Button variant="contained" color="warning">
              Add to Cart
            </Button>
          </Box>
        </Grid>
      </Grid>
    </Container>
  );
};

export default ProductPage;
