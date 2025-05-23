import React from 'react'
import "./Navbar.css"
import logo from "../../assets/Free.png"
import cart_icon from "../../assets/trolley.png"
import { useState } from 'react'
import {Link} from "react-router-dom"

const Navbar = () => {
    const [menu,setMenu]=useState("home")
  return (
    <div className='navbar'>Navbar
        <div className="nav-logo"><img src={logo} alt="" height="50px"/>
        </div>
        <ul className='nav-menu'>
            <li onClick={()=>{setMenu("home")}}><Link style={{textDecoration:"none",color:"#626262"}}to="/">Home</Link>
                {menu==="home" ? <hr/>:<></>}</li>
            <li onClick={()=>{setMenu("mens")}}><Link style={{textDecoration:"none",color:"#626262"}} to="/mens">Men</Link>
            {menu==="mens" ? <hr/>:<></>}
            </li>
            <li onClick={()=>{setMenu("womens")}}><Link style={{textDecoration:"none",color:"#626262"}} to="/womens">Women</Link>
            {menu==="womens" ? <hr/>:<></>}
            </li>
            <li onClick={()=>{setMenu("kids")}}><Link style={{textDecoration:"none",color:"#626262"}} to="/kids">Kids</Link>
            {menu==="kids" ? <hr/>:<></>}
            </li>
        </ul>
        <div className="nav-login-cart">
           <Link style={{textDecoration:"none",color:"#626262"}} to="/login"><button>Login</button></Link> 
           <Link style={{textDecoration:"none",color:"#626262"}} to="/carts"><img src={cart_icon} alt='' height="40px"/></Link>
            <div className="nav-cart-count">0</div>
        </div>
    </div>
  )
}

export default Navbar


