import React, { useEffect, useState } from 'react';
import { Button, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper, IconButton } from '@mui/material';
import { Edit, Delete } from '@mui/icons-material';
import { subCategoryService } from '../../../services/subCategoryService';
import { useNavigate } from 'react-router';
import type { SubCategory } from '../../../types/SubCategory';

const SubCategoryList: React.FC = () => {
  const [subCategories, setSubCategories] = useState<SubCategory[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const navigate = useNavigate();

  useEffect(() => {
    const fetchSubCategories = async () => {
      try {
        const res = await subCategoryService.getAllSubCategories();
        if (res.data) {
          setSubCategories(res.data);
        }
      } catch (err) {
        setError('Failed to fetch subcategories');
      } finally {
        setLoading(false);
      }
    };

    fetchSubCategories();
  }, []);

  const handleDelete = async (id: string) => {
    try {
      await subCategoryService.deleteSubCategory(id);
      setSubCategories(subCategories.filter(subCategory => subCategory.id !== id));
    } catch (err) {
      setError('Failed to delete subcategory');
    }
  };

  if (loading) return <div>Loading...</div>;
  if (error) return <div>{error}</div>;

  return (
    <div>
      <Button 
        variant="contained" 
        color="primary" 
        onClick={() => navigate('/admin/subcategories/add')}
        sx={{ mb: 2 }}
      >
        Add SubCategory
      </Button>

      <TableContainer component={Paper}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>Title</TableCell>
              <TableCell>Category</TableCell>
              <TableCell>Actions</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {subCategories.map((subCategory) => (
              <TableRow key={subCategory.id}>
                <TableCell>{subCategory.title}</TableCell>
                <TableCell>{subCategory.category?.title}</TableCell>
                <TableCell>
                  <IconButton onClick={() => navigate(`/admin/subcategories/edit/${subCategory.id}`)}>
                    <Edit />
                  </IconButton>
                  <IconButton onClick={() => handleDelete(subCategory.id)}>
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

export default SubCategoryList;