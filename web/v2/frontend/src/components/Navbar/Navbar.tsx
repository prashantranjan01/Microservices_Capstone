import React, { useEffect, useState } from 'react';
import { AppBar, Toolbar, Typography, Tooltip, IconButton, Avatar, Menu, MenuItem, Divider, ListItemIcon, TextField, Box, Button, Drawer } from '@mui/material';
import { Outlet, useNavigate } from 'react-router';
import { useAuth } from '../../context/AuthContext';
import ShoppingCartIcon from '@mui/icons-material/ShoppingCart';

type NavbarProps = {
  isAdmin: boolean;
};

const Navbar: React.FC<NavbarProps> = ({isAdmin}) => {
  const navigate = useNavigate();
  const { logout } = useAuth();

  const [search, setSearch] = useState('');
  const [debounced, setDebounced] = useState(search);

  const [cartOpen, setCartOpen] = useState(false);

  const handleLogout = () => {
    logout();
    navigate('/');
  };

  const [anchorEl, setAnchorEl] = React.useState<null | HTMLElement>(null);
  const open = Boolean(anchorEl);
  const handleClick = (event: React.MouseEvent<HTMLElement>) => {
    setAnchorEl(event.currentTarget);
  };
  const handleClose = () => {
    setAnchorEl(null);
  };

  useEffect(() => {
    const handler = setTimeout(() => {
      setDebounced(search);
    }, 300);

    return () => {
      clearTimeout(handler);
    };
  }, [search]);

  useEffect(() => {
    if (debounced.trim()) {
      setSearch('')
      navigate(`/s?q=${encodeURIComponent(debounced.trim())}`);
    }
  }, [debounced, navigate]);

  const handleCartToggle = () => {
    setCartOpen(!cartOpen);
  };

  return (
    <>
    <AppBar position="static">
      <Toolbar>
        <Typography
          variant="h6"
          sx={{ flexGrow: 1, cursor: 'pointer' }}
          onClick={() => navigate('/')}
        >
          Gofers
        </Typography>
        {!isAdmin && 
        <>
        <Box sx={{ mx: 2, width: 300 }}>
            <TextField
              fullWidth
              value={search}
              onChange={(e) => setSearch(e.target.value)}
              placeholder="Search…"
              variant="outlined"
              size="small"
              sx={{
                backgroundColor: 'white',
                borderRadius: 1,
                '& .MuiInputBase-input.Mui-disabled': {
                  WebkitTextFillColor: 'rgba(0, 0, 0, 0.87)',
                },
              }}
            />
          </Box>
          <Button
            variant="outlined"
            color="inherit"
            startIcon={<ShoppingCartIcon />}
            onClick={handleCartToggle}
            sx={{ mx: 1 }}
          >
            Cart
          </Button>
        </>
        }
        <Tooltip title="Account">
          <IconButton
            onClick={handleClick}
            size="small"
            sx={{ ml: 2 }}
            aria-controls={open ? 'account-menu' : undefined}
            aria-haspopup="true"
            aria-expanded={open ? 'true' : undefined}
          >
            <Avatar sx={{ width: 32, height: 32 }}>M</Avatar>
          </IconButton>
        </Tooltip>
        <Menu
        anchorEl={anchorEl}
        id="account-menu"
        open={open}
        onClose={handleClose}
        onClick={handleClose}
        slotProps={{
          paper: {
            elevation: 0,
            sx: {
              overflow: 'visible',
              filter: 'drop-shadow(0px 2px 8px rgba(0,0,0,0.32))',
              mt: 1.5,
              '& .MuiAvatar-root': {
                width: 32,
                height: 32,
                ml: -0.5,
                mr: 1,
              },
              '&::before': {
                content: '""',
                display: 'block',
                position: 'absolute',
                top: 0,
                right: 14,
                width: 10,
                height: 10,
                bgcolor: 'background.paper',
                transform: 'translateY(-50%) rotate(45deg)',
                zIndex: 0,
              },
            },
          },
        }}
        transformOrigin={{ horizontal: 'right', vertical: 'top' }}
        anchorOrigin={{ horizontal: 'right', vertical: 'bottom' }}
      >
        <MenuItem onClick={() => { navigate('/update-profile'); handleClose(); }}>Update Profile</MenuItem>
        <MenuItem onClick={() => { navigate('/change-password'); handleClose(); }}>Change Password</MenuItem>
        <MenuItem onClick={() => { handleLogout(); handleClose(); }}>Logout</MenuItem>
        
      </Menu>
      </Toolbar>
    </AppBar>
    <Drawer anchor="right" open={cartOpen} onClose={handleCartToggle}>
        <Box sx={{ width: 300, p: 2 }}>
          <Typography variant="h6">Your Cart</Typography>
          {/* You can replace this with real cart contents */}
          <Typography sx={{ mt: 2 }}>Cart is empty.</Typography>
        </Box>
      </Drawer>
    <Outlet />
    </>
  );
};

export default Navbar;
