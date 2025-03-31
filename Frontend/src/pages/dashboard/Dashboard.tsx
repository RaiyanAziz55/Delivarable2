// src/pages/Dashboard.tsx
import React, { useEffect, useState } from 'react';
import { useAuth } from '../../contexts/AuthContext';
import BookingsPage from './BookingsPage';
import RentingsPage from './RentingsPage';
import EmployeesPage from './EmployeesPage';

const Dashboard: React.FC = () => {
  const { user, logout } = useAuth();
  const [activeTab, setActiveTab] = useState<'bookings' | 'rentings' | 'employees'>('bookings');

  const isManager = user?.ROLE === 'Manager';

  useEffect(() => {
    console.log("✅ Logged in user:", user);
  }, [user]);
  return (
    <div className="min-h-screen bg-gray-50 flex flex-col items-center p-4">
      <div className="w-full max-w-4xl bg-white shadow-lg rounded-lg p-6">
        <div className="flex justify-between items-center mb-6">
          <h1 className="text-3xl font-bold">Dashboard</h1>
          <button
            onClick={logout}
            className="bg-red-500 text-white px-4 py-2 rounded hover:bg-red-600 transition-colors"
          >
            Logout
          </button>
        </div>
        {/* Tab Navigation */}
        <div className="mb-4 border-b">
          <nav className="flex space-x-4">
            <button
              onClick={() => setActiveTab('bookings')}
              className={`pb-2 ${
                activeTab === 'bookings'
                  ? 'border-b-2 border-blue-500 text-blue-500'
                  : 'text-gray-600'
              }`}
            >
              Bookings
            </button>
            <button
              onClick={() => setActiveTab('rentings')}
              className={`pb-2 ${
                activeTab === 'rentings'
                  ? 'border-b-2 border-blue-500 text-blue-500'
                  : 'text-gray-600'
              }`}
            >
              Rentings
            </button>
            {isManager && (
              <button
                onClick={() => setActiveTab('employees')}
                className={`pb-2 ${
                  activeTab === 'employees'
                    ? 'border-b-2 border-blue-500 text-blue-500'
                    : 'text-gray-600'
                }`}
              >
                Employees
              </button>
            )}
          </nav>
        </div>
        {/* Render the Active Tab */}
        <div>
          {activeTab === 'bookings' && <BookingsPage />}
          {activeTab === 'rentings' && <RentingsPage />}
          {activeTab === 'employees' && isManager && <EmployeesPage />}
        </div>
      </div>
    </div>
  );
};

export default Dashboard;
