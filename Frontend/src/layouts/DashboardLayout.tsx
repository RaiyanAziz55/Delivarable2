// src/layouts/DashboardLayout.tsx
import React from 'react';
import { Outlet, Link } from 'react-router-dom';

const DashboardLayout: React.FC = () => {
  return (
    <div className="flex">
      <aside className="w-64 bg-gray-800 text-white min-h-screen">
        <nav>
          <ul>
            <li>
              <Link to="/dashboard" className="block p-4 hover:bg-gray-700">Dashboard</Link>
            </li>
            {/* Add additional sidebar links here */}
          </ul>
        </nav>
      </aside>
      <main className="flex-1 p-4">
        <Outlet />
      </main>
    </div>
  );
};

export default DashboardLayout;
