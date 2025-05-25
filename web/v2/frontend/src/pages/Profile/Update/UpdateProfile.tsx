import React, { useEffect, useState } from 'react';
import {
  Box,
  TextField,
  Button,
  Typography,
  Alert,
  CircularProgress,
} from '@mui/material';

import type { User } from '../../../types/User';
import { useAuth } from '../../../context/AuthContext';
import { userService } from '../../../services/userServices';
import { useQuery } from '@tanstack/react-query';

const UpdateProfile: React.FC = () => {
  
  const [errorMessage, setErrorMessage] = useState('');
  const [successMessage, setSuccessMessage] = useState('');

  const {user} = useAuth();

  const { data: userData, isLoading } = useQuery<User>({
    queryKey: ['userData', user?.id],
    queryFn: () => userService.getUser(user?.id),
    enabled: !!user?.id,
  });

  const [form, setForm] = useState<User>({} as User);

  useEffect(() => {
    if (userData) {
      setForm(userData);
    }
  }, [userData]);


  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setErrorMessage('');
    setSuccessMessage('');

    try {
      await userService.updateUser(form);
      setSuccessMessage('Profile updated successfully!');
    } catch (error: any) {
      setErrorMessage('Failed to update profile.');
    }
  };

  if (isLoading) {
    return (
      <Box sx={{ textAlign: 'center', mt: 4 }}>
        <CircularProgress />
      </Box>
    );
  }

  return (
    <Box
      component="form"
      onSubmit={handleSubmit}
      sx={{
        maxWidth: 400,
        margin: 'auto',
        mt: 4,
        display: 'flex',
        flexDirection: 'column',
        gap: 2,
      }}
    >
      <Typography variant="h5">Update Profile</Typography>
      
      <TextField
        label="First Name"
        name="firstname"
        value={form.firstname || ''}
        onChange={handleChange}
        fullWidth
      />

      <TextField
        label="Last Name"
        name="lastname"
        value={form.lastname || ''}
        onChange={handleChange}
        fullWidth
      />

      <TextField
        label="Username"
        name="username"
        value={form.username || ''}
        disabled
        fullWidth
      />

      <TextField
        label="Email"
        name="email"
        value={form.email || ''}
        disabled
        fullWidth
      />

      <TextField
        label="Address"
        name="address"
        value={form.address || ''}
        onChange={handleChange}
        multiline
        rows={3}
        fullWidth
      />

      <Button variant="contained" color="primary" type="submit">
        Update Profile
      </Button>

      {successMessage && <Alert severity="success">{successMessage}</Alert>}
      {errorMessage && <Alert severity="error">{errorMessage}</Alert>}
    </Box>
  );
};

export default UpdateProfile