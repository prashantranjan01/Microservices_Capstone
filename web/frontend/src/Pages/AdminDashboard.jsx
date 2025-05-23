import React from 'react';
import { useAuth } from "../Context/AuthContext";
import './AdminDashboard.css';

const AdminDashboard = () => {
    const { user } = useAuth();

    return (
        <div className="admin-dashboard">
            <h2>Admin Dashboard</h2>
            <div className="admin-info">
                <p>Welcome, Admin {user.firstname}</p>
            </div>
            <div className="admin-actions">
                <button onClick={() => window.location.href="/admin/products"}>Manage Products</button>
                <button onClick={() => window.location.href="/admin/categories"}>Manage Categories</button>
                <button onClick={() => window.location.href="/admin/orders"}>View Orders</button>
            </div>
        </div>
    );
};

export default AdminDashboard;