// src/pages/Dashboard.tsx
import React from 'react';
import { useAuth } from '../contexts/AuthContext';

const Dashboard: React.FC = () => {
  const { user, logout } = useAuth();

  return (
    <div>
      <h1 className="text-3xl font-bold mb-4">Dashboard</h1>
      <p>Welcome, {user?.email}!</p>
      <button onClick={logout} className="mt-4 bg-red-500 text-white p-2 rounded">
        Logout
      </button>
    </div>
  );
};

export default Dashboard;
