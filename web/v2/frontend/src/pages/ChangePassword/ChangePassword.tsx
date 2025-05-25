// components/user/ChangePassword.tsx

import React, { useState } from 'react';
import {
  Box,
  TextField,
  Button,
  Typography,
  Alert,
} from '@mui/material';

import { useNavigate } from 'react-router';
import { useAuth } from '../../context/AuthContext';
import { userService } from '../../services/userServices';

const ChangePassword: React.FC = () => {
  const [currentPassword, setCurrentPassword] = useState('');
  const [newPassword, setNewPassword] = useState('');
  const [errorMessage, setErrorMessage] = useState('');

  const {user , logout} = useAuth();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setErrorMessage('');

    if (!user || !user.username || !currentPassword || !newPassword) {
      setErrorMessage('All fields are required');
      return;
    }

    try {
      const res = await userService.changePassword(user?.username, currentPassword, newPassword);

      if (res) {
        logout()
      } else {
        setErrorMessage('Password change failed.');
      }
    } catch (error: any) {
      console.error('API error:', error);
      setErrorMessage(
        error.response?.data?.info ||
        error.response?.data?.message ||
        error.message ||
        'Change failed'
      );
    }
  };

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
      <Typography variant="h5">Change Password</Typography>

      <TextField
        label="Current Password"
        type="password"
        value={currentPassword}
        onChange={(e) => setCurrentPassword(e.target.value)}
        required
        fullWidth
      />

      <TextField
        label="New Password"
        type="password"
        value={newPassword}
        onChange={(e) => setNewPassword(e.target.value)}
        required
        fullWidth
      />

      <Button variant="contained" color="primary" type="submit">
        Change Password
      </Button>

      {errorMessage && <Alert severity="error">{errorMessage}</Alert>}
    </Box>
  );
};

export default ChangePassword;