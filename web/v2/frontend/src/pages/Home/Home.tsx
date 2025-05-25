import React from 'react';

import {
  Grid,
  Container,
  CardContent,
  Typography,
  CardMedia,
  CardActionArea,
  Card,
} from '@mui/material';
import { useQuery } from '@tanstack/react-query';
import { categoryService } from '../../services/categoryServices';
import type { Category}  from '../../types/Category';
import { useNavigate } from 'react-router';
import type { SubCategory } from '../../types/SubCategory';
import { subCategoryService } from '../../services/subCategoryServices';

const Home: React.FC = () => {
  
  const navigate = useNavigate();

  const { data: categories, isLoading, isError } = useQuery<Category[]>({
    queryKey: ['categories'],
    queryFn: categoryService.getAllCategories,
  });

  if (isLoading) return <div>Loading...</div>;
  if (isError) return <div>Error fetching categories.</div>;

  const handleCategory = async (category : Category) => {
        const subCategories : SubCategory[] = await subCategoryService.getAllSubCategoriesByCategoryId(category.id);
        if(!subCategories || subCategories.length == 0) return;
        navigate(`/cid/${category.id}/scid/${subCategories[0].id}`)
    }

    return (
      <Container sx={{paddingTop:"2rem"}}>
        <Grid container spacing={2}>
          {categories && categories.map((cat: Category) => (
              <Grid key={cat.id}>
              <Card variant="outlined" sx={{ width: 200}}>
              <CardActionArea onClick={() => handleCategory(cat)}>
                  <CardMedia
                  component="img"
                  height="200"
                  width="200"
                  image={cat.imageUrl || ""}
                  alt={cat.title}
              />
              <CardContent>
                  <Typography variant='body2'>{cat.title}</Typography>
              </CardContent>
              </CardActionArea>
              </Card>
            </Grid>
            ))}
        </Grid>
      </Container>
    );
};

export default Home;