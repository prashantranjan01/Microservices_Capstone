import React from 'react';
import { useAuth } from "../Context/AuthContext";
import './UserDashboard.css';

const UserDashboard = () => {
    const { user } = useAuth();

    return (
        <div className="user-dashboard">
            <h2>Welcome to Your Dashboard</h2>
            <div className="user-info">
                <p><strong>Name:</strong> {user.firstname} {user.lastname}</p>
                <p><strong>Email:</strong> {user.email}</p>
                <p><strong>Username:</strong> {user.username}</p>
            </div>
            <div className="dashboard-actions">
                <button onClick={() => window.location.href="/cart"}>View Cart</button>
                <button onClick={() => window.location.href="/"}>Continue Shopping</button>
            </div>
        </div>
    );
};

export default UserDashboard;