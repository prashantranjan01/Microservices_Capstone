import React from 'react'
import { replace, useNavigate, useParams } from 'react-router';
import { subCategoryService } from '../../services/subCategoryServices';
import type { SubCategory } from '../../types/SubCategory';
import { useQuery } from '@tanstack/react-query';
import type { Product } from '../../types/Product';
import { productService } from '../../services/productServices';
import { Box, Container, Grid } from '@mui/system';
import { Card, CardActionArea, CardContent, CardMedia, Tab, Tabs, Typography } from '@mui/material';

const SubCategoryPage = () => {
    const params = useParams();
    const navigate = useNavigate();
    const categoryId = params.categoryId;
	const initialSubCategoryId = params.subCategoryId;

    if(!categoryId || !initialSubCategoryId) return;

    const [subCategoryId, setSubCategoryId] = React.useState(initialSubCategoryId);
    
    const { data: subCategories} = useQuery<SubCategory[]>({
        queryKey: ['subCategories'],
        queryFn: () => subCategoryService.getAllSubCategoriesByCategoryId(categoryId),
        enabled: !!categoryId,
    });

    const {data: products , isLoading } = useQuery<Product[]>({
        queryKey: ['products', categoryId, subCategoryId],
        queryFn: () => productService.getProductsByCategoryIdAndBySubCategoryId(categoryId! , subCategoryId),
        enabled: !!categoryId && !!subCategoryId,
    });

    const handleChange = (event: React.SyntheticEvent, subCategoryId: string) => {
        setSubCategoryId(subCategoryId);
        navigate(`/cid/${categoryId}/scid/${subCategoryId}` , { replace: true });
    };

    const handleProduct = (product : Product) => {
        navigate(`/cid/${categoryId}/scid/${subCategoryId}/product/${product.id}`);
    }

    return (
        <Container>
            <Box sx={{paddingTop:"2rem", flexGrow: 1, bgcolor: 'background.paper', display: 'flex' }}
                >
                <Tabs
                    orientation="vertical"
                    variant="standard"
                    value={subCategoryId}
                    onChange={handleChange}
                    aria-label="Vertical tabs example"
                    sx={{ borderRight: 1, borderColor: 'divider' , flex:0.2}}
                >
                    {subCategories && subCategories.map((subCategory : SubCategory) => {
                        return <Tab key={subCategory.id} value={subCategory.id} label={subCategory.title}/>
                    })}
                </Tabs>
                <div
                    role="tabpanel"
                    style={{ flex:0.8}}
                    >
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
                </div>
            </Box>
        </Container>
    )
}

export default SubCategoryPage