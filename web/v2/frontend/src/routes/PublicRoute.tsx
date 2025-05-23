import { Navigate, Outlet } from 'react-router';
import { useAuth } from '../context/AuthContext';

const PublicRoute = () => {
  const { isAuthenticated, user } = useAuth();

  if (isAuthenticated) {
    return <Navigate to={user?.roleId === 1 ? '/admin' : '/'} replace />;
  }

  return <Outlet />;
};

export default PublicRoute;