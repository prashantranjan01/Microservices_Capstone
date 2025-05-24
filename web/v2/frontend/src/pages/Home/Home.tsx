// import React, { useEffect, useState } from 'react';
// import type { Product } from '../../types/Product';
// import { productService } from '../../services/productServices';

// const Home: React.FC = () => {
//   const [products, setProducts] = useState<Product[]>([]);

//   useEffect(() => {
//     const fetchProducts = async () => {
//       try {
//         const response = await productService.getAllProducts();
//         setProducts(response.data); // Only use `data` from the response
//       } catch (error) {
//         console.error('Failed to fetch products:', error);
//       }
//     };

//     fetchProducts();
//   }, []);

//   return (
//     <div>
//       <h2>All Products</h2>
//       {products.map((product) => (
//         <div key={product.id}>
//           <h3>{product.name}</h3>
//           <p>{product.description || 'No description'}</p>
//           <p>Price: ₹{product.price}</p>
//           <p>Stock: {product.stockQuantity}</p>
//           <p>Status: {product.status}</p>
//           <img
//             src={product.imageUrl || 'https://via.placeholder.com/150'}
//             alt={product.name}
//             style={{ width: '150px', height: '150px' }}
//           />
//           <hr />
//         </div>
//       ))}
//     </div>
//   );
// };

// export default Home;

import React, { useEffect, useState } from 'react';
import { productService } from '../../services/productServices';
import type { Product } from '../../types/Product';

import {
  Grid,
  Card,
  CardContent,
  CardMedia,
  Typography,
  Container
} from '@mui/material';

const Home: React.FC = () => {
  const [products, setProducts] = useState<Product[]>([]);

  useEffect(() => {
    const fetchProducts = async () => {
      try {
        const response = await productService.getAllProducts();
        setProducts(response.data); // Extract only the actual product data
      } catch (error) {
        console.error('Failed to fetch products:', error);
      }
    };

    fetchProducts();
  }, []);

  return (
    <Container>
      <Typography variant="h4" gutterBottom>
        All Products
      </Typography>
      <Grid container spacing={3}>
        {products.map((product) => (
          <Grid item xs={12} sm={6} md={4} key={product.id}>
            <Card>
              <CardMedia
                component="img"
                height="200"
                image={product.imageUrl || 'https://via.placeholder.com/200'}
                alt={product.name}
              />
              <CardContent>
                <Typography variant="h6">{product.name}</Typography>
                <Typography variant="body2" color="text.secondary">
                  {product.description || 'No description'}
                </Typography>
                <Typography variant="subtitle1" color="text.primary">
                  ₹{product.price}
                </Typography>
                <Typography variant="body2" color="text.secondary">
                  Stock: {product.stockQuantity}
                </Typography>
                <Typography variant="body2" color="text.secondary">
                  Status: {product.status}
                </Typography>
              </CardContent>
            </Card>
          </Grid>
        ))}
      </Grid>
    </Container>
  );
};

export default Home;