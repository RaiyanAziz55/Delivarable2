// src/pages/RoomBooking.tsx
import React, { useEffect, useState } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import api from '../api/axios';

interface BookingForm {
  fullName: string;
  email: string;
  phone: string;
  address: string;
  idType: string;
}

const RoomBooking: React.FC = () => {
  const location = useLocation();
  const navigate = useNavigate();

  // Retrieve query parameters
  const queryParams = new URLSearchParams(location.search);
  const roomId = queryParams.get('roomId');
  const checkInDate = queryParams.get('startDate');  // Assuming your home page passes "startDate"
  const checkOutDate = queryParams.get('endDate');     // Assuming your home page passes "endDate"

  // Local states
  const [isAvailable, setIsAvailable] = useState<boolean | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [bookingSuccess, setBookingSuccess] = useState(false);
  const [formData, setFormData] = useState<BookingForm>({
    fullName: '',
    email: '',
    phone: '',
    address: '',
    idType: '',
  });

  // Check room availability on component mount
  useEffect(() => {
    if (roomId && checkInDate && checkOutDate) {
      setLoading(true);
      api
        .get(`/rooms/available?roomId=${roomId}&checkIn=${checkInDate}&checkOut=${checkOutDate}`)
        .then((response) => {
          // Assume response.data is a boolean indicating availability
          setIsAvailable(response.data);
          setLoading(false);
        })
        .catch(() => {
          setError('Error checking room availability.');
          setLoading(false);
        });
    } else {
      setError('Missing booking parameters.');
      setLoading(false);
    }
  }, [roomId, checkInDate, checkOutDate]);

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    // Submit the booking. The payload includes customer info plus room, check-in, and check-out dates.
    api
      .post('/bookings', {
        customer: formData,
        roomId: Number(roomId),
        checkInDate,
        checkOutDate,
      })
      .then(() => {
        setBookingSuccess(true);
      })
      .catch(() => {
        setError('Error processing booking. Please try again.');
      });
  };

  return (
    <div className="min-h-screen bg-gray-50 flex flex-col">
      {/* Navigation Bar */}
      <nav className="bg-white shadow-sm py-4 px-6">
        <div className="max-w-6xl mx-auto flex justify-between items-center">
          <Link 
            to="/"
            className="font-semibold text-xl text-gray-800 hover:underline"
          >
            Hotel Finder
          </Link>
        </div>
      </nav>
    
    {/* Main Content */}
    <div className="flex-grow flex items-center justify-center p-4">
      <div className="w-full max-w-md bg-white p-8 shadow-lg rounded-lg">
        {loading ? (
          <p>Checking room availability...</p>
        ) : error ? (
          <p className="text-red-500">{error}</p>
        ) : isAvailable === false ? (
          <div>
            <p className="text-red-500 mb-4">
              Unfortunately, this room is not available for the selected dates.
            </p>
            <button
              onClick={() => navigate(-1)}
              className="w-full bg-blue-500 text-white py-2 rounded hover:bg-blue-600 transition-colors"
            >
              Go Back
            </button>
          </div>
        ) : bookingSuccess ? (
          <div>
            <p className="text-green-500 text-center mb-4">Booking confirmed! Thank you.</p>
            <button
              onClick={() => navigate('/')}
              className="w-full bg-blue-500 text-white py-2 rounded hover:bg-blue-600 transition-colors"
            >
              Return Home
            </button>
          </div>
        ) : (
          <div>
            <h1 className="text-2xl font-bold mb-4 text-center">Book Room {roomId}</h1>
            <p className="mb-4 text-center">
              Check-In: {checkInDate} | Check-Out: {checkOutDate}
            </p>
            <form onSubmit={handleSubmit} className="space-y-4">
              <input
                type="text"
                name="fullName"
                placeholder="Full Name"
                value={formData.fullName}
                onChange={handleInputChange}
                className="w-full border border-gray-300 p-2 rounded"
                required
              />
              <input
                type="email"
                name="email"
                placeholder="Email"
                value={formData.email}
                onChange={handleInputChange}
                className="w-full border border-gray-300 p-2 rounded"
                required
              />
              <input
                type="text"
                name="phone"
                placeholder="Phone"
                value={formData.phone}
                onChange={handleInputChange}
                className="w-full border border-gray-300 p-2 rounded"
                required
              />
              <input
                type="text"
                name="address"
                placeholder="Address"
                value={formData.address}
                onChange={handleInputChange}
                className="w-full border border-gray-300 p-2 rounded"
                required
              />
              <input
                type="text"
                name="idType"
                placeholder="ID Type (e.g., Driver License)"
                value={formData.idType}
                onChange={handleInputChange}
                className="w-full border border-gray-300 p-2 rounded"
                required
              />
              <button
                type="submit"
                className="w-full bg-blue-500 text-white py-2 rounded hover:bg-blue-600 transition-colors"
              >
                Confirm Booking
              </button>
            </form>
          </div>
        )}
      </div>
      </div>
    </div>
  );
};

export default RoomBooking;
