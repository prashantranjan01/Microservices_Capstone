import { BrowserRouter, Navigate, Route, Routes } from 'react-router'
import Home from '../pages/Home/Home'
import AdminDashboard from '../pages/Admin/AdminDashboard'
import RoleRoute from './RoleRoute'
import PublicRoute from './PublicRoute'
import SearchProduct from '../pages/SearchProduct/SearchProduct'
import Navbar from '../components/Navbar/Navbar'
import SubCategoryPage from '../pages/SubCategory/SubCategoryPage'
import ProductPage from '../pages/Product/ProductPage'
import UpdateProfile from '../pages/Profile/Update/UpdateProfile'
import ChangePassword from '../pages/ChangePassword/ChangePassword'
import Login from '../pages/Auth/Login/Login'
import Register from '../pages/Auth/Register/Register'

const Router = () => {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="auth" element={<PublicRoute/>}>
            <Route index element={<Navigate to="login" replace />} />
            <Route path="login" element={<Login  />} />
            <Route path="register" element={<Register/>} />
        </Route>
        <Route element={<RoleRoute allowedRoles={[0]} />}>
          <Route element={<Navbar isAdmin={false}/>}>
            <Route path="/" element={<Home />} />
            <Route path="/cid/:categoryId/scid/:subCategoryId" element={<SubCategoryPage />} />
            <Route path="/cid/:categoryId/scid/:subCategoryId/product/:productId" element={<ProductPage />} />
            <Route path="/product/:productId" element={<ProductPage />} />
            <Route path="/s" element={<SearchProduct />} />
          </Route>
        </Route>
        <Route element={<RoleRoute allowedRoles={[1]} />}>
          <Route element={<Navbar isAdmin={true}/>}>
            <Route path="/admin" element={<AdminDashboard />} />
          </Route>
        </Route>
        <Route element={<RoleRoute allowedRoles={[0,1]} />}>
          <Route element={<Navbar isAdmin={true}/>}>
            <Route path="/update-profile" element={<UpdateProfile />} />
            <Route path="/change-password" element={<ChangePassword />} />
          </Route>
        </Route>
       <Route path="*" element={<h1>404 Not Found</h1>} />
      </Routes>
    </BrowserRouter>
  )
}

export default Router