import React from 'react';
import { 
  Box, 
  Button, 
  Typography, 
  Paper,
  Grid // Import Grid from @mui/material
} from '@mui/material';// Updated import
import { useNavigate } from 'react-router';

const AdminDashboard: React.FC = () => {
  const navigate = useNavigate();

  return (
    <Box sx={{ p: 3 }}>
      <Typography variant="h4" gutterBottom>
        Admin Dashboard
      </Typography>
      
      <Grid container spacing={3}>
        <Grid item xs={12} md={4}>
          <Paper elevation={3} sx={{ p: 2, textAlign: 'center' }}>
            <Typography variant="h6" gutterBottom>
              Categories
            </Typography>
            <Button 
              variant="contained" 
              onClick={() => navigate('/admin/categories')}
              fullWidth
            >
              Manage Categories
            </Button>
          </Paper>
        </Grid>
        
        <Grid item xs={12} md={4}>
          <Paper elevation={3} sx={{ p: 2, textAlign: 'center' }}>
            <Typography variant="h6" gutterBottom>
              SubCategories
            </Typography>
            <Button 
              variant="contained" 
              onClick={() => navigate('/admin/subcategories')}
              fullWidth
            >
              Manage SubCategories
            </Button>
          </Paper>
        </Grid>
        
        <Grid item xs={12} md={4}>
          <Paper elevation={3} sx={{ p: 2, textAlign: 'center' }}>
            <Typography variant="h6" gutterBottom>
              Products
            </Typography>
            <Button 
              variant="contained" 
              onClick={() => navigate('/admin/products')}
              fullWidth
            >
              Manage Products
            </Button>
          </Paper>
        </Grid>
      </Grid>
    </Box>
  );
};

export default AdminDashboard;