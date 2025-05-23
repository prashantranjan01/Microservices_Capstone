import { BrowserRouter, Navigate, Route, Routes } from 'react-router'
import Login from '../pages/Auth/Login/Login'
import Register from '../pages/Auth/Register/Register'
import Home from '../pages/Home/Home'
import AdminDashboard from '../pages/Admin/AdminDashboard'
import RoleRoute from './RoleRoute'
import PublicRoute from './PublicRoute'

const Router = () => {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="auth" element={<PublicRoute/>}>
            <Route index element={<Navigate to="login" replace />} />
            <Route path="login" element={<Login />} />
            <Route path="register" element={<Register />} />
        </Route>
        <Route element={<RoleRoute allowedRoles={[0]} />}>
          <Route path="/" element={<Home />} />
        </Route>
        <Route element={<RoleRoute allowedRoles={[1]} />}>
          <Route path="/admin" element={<AdminDashboard />} />
       </Route>
       <Route path="*" element={<h1>404 Not Found</h1>} />
      </Routes>
    </BrowserRouter>
  )
}

export default Router