// components/user/UserProfile.tsx

import React, { useEffect, useState } from 'react';
import {
  Box,
  TextField,
  Button,
  Typography,
  Alert,
  CircularProgress,
} from '@mui/material';

import { userService } from '../../../services/userService';
import type { User } from '../../../types/User';

const UserProfile: React.FC = () => {
  const [form, setForm] = useState<Partial<User>>({});
  const [loading, setLoading] = useState(true);
  const [errorMessage, setErrorMessage] = useState('');
  const [successMessage, setSuccessMessage] = useState('');

  const username = JSON.parse(localStorage.getItem('user') || '{}')?.username;

  useEffect(() => {
    if (username) {
      userService.getUserByUsername(username)
        .then((res) => {
          setForm(res.data);
          setErrorMessage('');
        })
        .catch((err) => {
          setErrorMessage('Error loading profile: ' + (err.message || 'Unknown error'));
        })
        .finally(() => setLoading(false));
    }
  }, [username]);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setErrorMessage('');
    setSuccessMessage('');

    if (!username) {
      setErrorMessage('User not found.');
      return;
    }

    try {
      await userService.updateUser(username, form);
      setSuccessMessage('Profile updated successfully!');
    } catch (error: any) {
      setErrorMessage(error.response?.data?.info || error.message || 'Update failed');
    }
  };

  if (loading) {
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

export default UserProfile;
