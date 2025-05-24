import React from 'react';
import Navbar from '../pages/Navbar/Navbar';
import { Outlet } from 'react-router';

const NavRoute: React.FC = () => {
  return (
    <>
      <Navbar />
      <Outlet />
    </>
  );
};

export default NavRoute;
