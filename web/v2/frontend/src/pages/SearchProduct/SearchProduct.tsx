import {Box, Card, CardActionArea, CardContent, CardMedia, Container, Grid, Typography } from "@mui/material";
import { useQuery } from "@tanstack/react-query";
import { useLocation, useNavigate } from "react-router";
import type { Product } from "../../types/Product";
import { productService } from "../../services/productServices";

const SearchProduct = () => {

  const navigate = useNavigate();
  const location = useLocation();

  const queryParams = new URLSearchParams(location.search);
  const query = queryParams.get('q');

  if(!query){
    return <Container sx={{height:'80vh' , width:'100%', display:'flex', alignItems:'center', justifyContent:'center'}}>
        <Typography variant="subtitle1" >Seach Product...</Typography>
     </Container>
  }

  const {data: products , isLoading } = useQuery<Product[]>({
      queryKey: ['products',query],
      queryFn: () => productService.getProductsByQuery(query),
      enabled: !!query,
  });

  const handleProduct = (product : Product) => {
      navigate(`/product/${product.id}`);
  }

  return (
    <Container>
      <Box sx={{ p: 3 }}>
        <Grid container spacing={2}>
            {isLoading && <Typography>Loading products...</Typography>}
            {!isLoading && products?.map((product: Product) => (
                <Grid key={product.id}>
                    <Card variant="outlined" sx={{ width: 200}}>
                        <CardActionArea onClick={() => handleProduct(product)}>
                            <CardMedia
                            component="img"
                            height="200"
                            width="200"
                            image={product.imageUrl || ""}
                            alt={product.name}
                        />
                        <CardContent>
                            <Typography variant='subtitle1' noWrap 
                                sx={{
                                overflow: 'hidden',
                                textOverflow: 'ellipsis',
                                whiteSpace: 'nowrap',
                                width: '100%'
                                }}>{product.name}</Typography>
                            <Typography variant='body2'>₹{product.price}</Typography>
                        </CardContent>
                        </CardActionArea>
                    </Card>
                </Grid>
            ))}
        </Grid>
      </Box>
    </Container>
  )
}

export default SearchProduct