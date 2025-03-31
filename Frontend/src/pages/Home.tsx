// src/pages/Home.tsx
import React, { useEffect, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext';
import { Calendar, Search, User } from 'lucide-react'; // You'll need to install lucide-react

const Home: React.FC = () => {
  const { user } = useAuth();
  const navigate = useNavigate();

  // Auto-redirect for employees/managers if already logged in
  useEffect(() => {
    if (user && (user.ROLE === 'Employee' || user.ROLE === 'Manager')) {
      navigate('/dashboard');
    }
  }, [user, navigate]);

  // State for search form
  const [searchType, setSearchType] = useState('rooms');
  const [searchTerm, setSearchTerm] = useState('');
  const [startDate, setStartDate] = useState('');
  const [endDate, setEndDate] = useState('');

  const handleSearch = (e: React.FormEvent) => {
    e.preventDefault();
    navigate(
      `/rooms?type=${encodeURIComponent(searchType)}&q=${encodeURIComponent(
        searchTerm
      )}&startDate=${startDate}&endDate=${endDate}`
    );
  };

  return (
    <div className="min-h-screen bg-gray-50 flex flex-col">
      {/* Navigation Bar */}
      <nav className="bg-white shadow-sm py-4 px-6">
        <div className="max-w-6xl mx-auto flex justify-between items-center">
          <div className="font-semibold text-xl text-gray-800">Hotel Finder</div>
          <Link 
            to="/login" 
            className="flex items-center gap-2 text-blue-600 hover:text-blue-800 transition-colors"
          >
            <User size={18} />
            <span>Employee Login</span>
          </Link>
        </div>
      </nav>

      {/* Hero Section */}
      <div className="flex-grow flex flex-col items-center justify-center px-4 py-12">
        <div className="max-w-3xl w-full">
          <h1 className="text-4xl md:text-5xl font-bold text-center text-gray-900 mb-8">
            Find Your Perfect Stay
          </h1>
          
          {/* Search Form */}
          <div className="bg-white rounded-lg shadow-md p-6">
            <form onSubmit={handleSearch}>
              <div className="space-y-6">
                {/* Search Type Selector */}
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-2">
                    Search by
                  </label>
                  <select
                    value={searchType}
                    onChange={(e) => setSearchType(e.target.value)}
                    className="w-full border border-gray-300 p-3 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 outline-none"
                  >
                    <option value="rooms">Rooms</option>
                    <option value="hotels">Hotels</option>
                    <option value="hotelChains">Hotel Chains</option>
                  </select>
                </div>
                
                {/* Search Field */}
                <div className="relative">
                  <input
                    type="text"
                    placeholder="Enter name, location, or keyword"
                    value={searchTerm}
                    onChange={(e) => setSearchTerm(e.target.value)}
                    className="w-full p-4 pl-10 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 outline-none"
                  />
                  <Search className="absolute left-3 top-4 text-gray-400" size={20} />
                </div>
                
                {/* Date Fields */}
                <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                  <div className="relative">
                    <label className="block text-sm font-medium text-gray-700 mb-2">
                      Check-in Date
                    </label>
                    <div className="relative">
                      <input
                        type="date"
                        value={startDate}
                        onChange={(e) => setStartDate(e.target.value)}
                        className="w-full p-4 pl-10 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 outline-none"
                      />
                      <Calendar className="absolute left-3 top-4 text-gray-400" size={20} />
                    </div>
                  </div>
                  
                  <div className="relative">
                    <label className="block text-sm font-medium text-gray-700 mb-2">
                      Check-out Date
                    </label>
                    <div className="relative">
                      <input
                        type="date"
                        value={endDate}
                        onChange={(e) => setEndDate(e.target.value)}
                        className="w-full p-4 pl-10 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 outline-none"
                      />
                      <Calendar className="absolute left-3 top-4 text-gray-400" size={20} />
                    </div>
                  </div>
                </div>
                
                {/* Search Button */}
                <button
                  type="submit"
                  className="w-full bg-blue-600 hover:bg-blue-700 text-white py-4 px-6 rounded-lg font-medium transition-colors"
                >
                  Search
                </button>
              </div>
            </form>
          </div>
        </div>
      </div>
      
      {/* Footer */}
      <footer className="bg-white py-6 px-4 border-t">
        <div className="max-w-6xl mx-auto text-center text-gray-500 text-sm">
          &copy; {new Date().getFullYear()} Hotel Finder. All rights reserved.
        </div>
      </footer>
    </div>
  );
};

export default Home;