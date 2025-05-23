import Navbar from "./components/Navbar/Navbar"
import {BrowserRouter,Route,Routes} from "react-router-dom"
import ShopCategory from "./Pages/ShopCategory"
import Product from "./Pages/Product"
import LoginSignup from "./Pages/LoginSignup"
import Shop from "./Pages/shop"
import Cart from "./Pages/cart"


function App() {
  return (
    <div>
    <BrowserRouter>
      <Navbar/>
      <Routes>
        <Route path="/" element={<Shop/>}/> {/* Fixed component name */}
        <Route path="/mens" element={<ShopCategory category="mens"/>}/>
        <Route path="/womens" element={<ShopCategory category="womens"/>}/> {/* Fixed path */}
        <Route path="/kids" element={<ShopCategory category="kids"/>}/> {/* Fixed path */}
        <Route path="/product" element={<Product/>}/>
        <Route path=":productId" element={<Product/>}/> {/* Nested route for product details */}
        <Route path="/cart" element={<Cart/>}/> 
        <Route path="/login" element={<LoginSignup/>}/>
      </Routes>
    </BrowserRouter>
  </div>
  )
}

export default App

// import Navbar from "./components/Navbar/Navbar"
// import {BrowserRouter,Route,Routes} from "react-router-dom"
// import ShopCategory from "./Pages/ShopCategory"
// import Product from "./Pages/Product"
// import LoginSignup from "./Pages/LoginSignup"
// import Shop from "./Pages/shop"
// import Cart from "./Pages/cart"

// import { AuthProvider } from "./Context/AuthContext";
// import UserDashboard from "./Pages/UserDashboard";
// import AdminDashboard from "./Pages/AdminDashboard";
// import ProtectedRoute from "./components/ProtectedRoute";

// function App() {
//     return (
//         <AuthProvider>
//             <BrowserRouter>
//                 <Navbar/>
//                 <Routes>
//                     <Route path="/" element={<Shop/>}/>
//                     <Route path="/mens" element={<ShopCategory category="mens"/>}/>
//                     <Route path="/womens" element={<ShopCategory category="womens"/>}/>
//                     <Route path="/kids" element={<ShopCategory category="kids"/>}/>
//                     <Route path="/product" element={<Product/>}/>
//                     <Route path="/product/:productId" element={<Product/>}/>
//                     <Route path="/cart" element={
//                         <ProtectedRoute>
//                             <Cart/>
//                         </ProtectedRoute>
//                     }/>
//                     <Route path="/login" element={<LoginSignup/>}/>
//                     <Route path="/user" element={
//                         <ProtectedRoute>
//                             <UserDashboard/>
//                         </ProtectedRoute>
//                     }/>
//                     <Route path="/admin" element={
//                         <ProtectedRoute requiredRole={1}>
//                             <AdminDashboard/>
//                         </ProtectedRoute>
//                     }/>
//                 </Routes>
//             </BrowserRouter>
//         </AuthProvider>
//     );
// }

// export default App;