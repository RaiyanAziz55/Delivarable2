// src/layouts/PublicLayout.tsx
import React from 'react';
import { Outlet } from 'react-router-dom';

const PublicLayout: React.FC = () => {
  return (
    <div>
      {/* Add your public header here */}
      <Outlet />
      {/* Add your public footer here */}
    </div>
  );
};

export default PublicLayout;
