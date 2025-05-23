import React from 'react'
import "./Hero.css"
import hand_icon from "../assets/wave-removebg-preview.png"

const Hero = () => {
  return (
    <div className='hero'>
        <div className="hero-left">
            <h2>Best Deals! Best Prices</h2>
       
            <div className="hand-hand-icon">
            <p>new</p>
            <img src={hand_icon} alt="" height="60px" />
            </div>
            <p>collections</p>
            <p>for Everone</p>
        </div>

        <div className="hero-latest-button">
            <div>Latest Collection</div>
        </div>
        
        <div className="hero-right"></div>
    </div>
  )
}

export default Hero
