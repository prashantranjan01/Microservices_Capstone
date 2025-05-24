import React from 'react';
import Navbar from '../pages/Navbar/Navbar';


const NavRoute: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  return (
    <>
      <Navbar />
      {children}
    </>
  );
};

export default NavRoute;
