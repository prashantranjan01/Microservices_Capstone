import { Navigate, Outlet } from 'react-router';
import { useAuth } from '../context/AuthContext';
import NavRoute from './NavRoute';

interface RoleRouteProps {
  allowedRoles: number[];
  redirectPath?: string;
}

const RoleRoute = ({ allowedRoles, redirectPath = '/' }: RoleRouteProps) => {
  const { isAuthenticated, user } = useAuth();

  if (!isAuthenticated) {
    return <Navigate to="/auth/login" replace />;
  }

  if (user && allowedRoles.includes(user.roleId)) {
    return (
    
        <Outlet />
      
    );
  }

  return <Navigate to={user?.roleId === 1 ? '/admin' : '/'} replace />;
};

export default RoleRoute;