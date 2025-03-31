// src/pages/dashboard/BookingsPage.tsx
import React, { useEffect, useState } from 'react';
import api from '../../api/axios';
import { useAuth } from '../../contexts/AuthContext';

interface Booking {
  id: number;
  status: string;
  checkInDate: string;
  checkOutDate: string;
  // Add other booking properties as needed
}


const BookingsPage: React.FC = () => {
  const [bookings, setBookings] = useState<Booking[]>([]);
  const [loading, setLoading] = useState<boolean>(false);
  const [error, setError] = useState<string>('');
  const { user } = useAuth();

  useEffect(() => {
    setLoading(true);
    api.get(`/bookings/hotel/${user?.hotelId}`)
      .then(response => {
        setBookings(response.data);
        setLoading(false);
      })
      .catch(() => {
        setError(`Error fetching bookings ${user?.hotelId}`);
        setLoading(false);
      });
  }, [user]);

  const confirmBooking = (bookingId: number) => {
    api.put(`/bookings/${bookingId}/status`, { status: 'Checked-In' })
      .then(() => {
        setBookings(prev =>
          prev.map(b => b.id === bookingId ? { ...b, status: 'Checked-In' } : b)
        );
      })
      .catch(() => alert('Error confirming booking'));
  };

  return (
    <div>
      <h2 className="text-2xl font-bold mb-4">Bookings</h2>
      {loading && <p>Loading bookings...</p>}
      {error && <p className="text-red-500">{error}</p>}
      <ul>
        {bookings.map(booking => (
          <li key={booking.id} className="p-4 border rounded mb-2">
            <p><strong>ID:</strong> {booking.id}</p>
            <p><strong>Status:</strong> {booking.status}</p>
            <p><strong>Check In:</strong> {booking.checkInDate}</p>
            <p><strong>Check Out:</strong> {booking.checkOutDate}</p>
            {booking.status !== 'Confirmed' && (
              <button
                onClick={() => confirmBooking(booking.id)}
                className="mt-2 bg-blue-500 text-white px-3 py-1 rounded hover:bg-blue-600 transition-colors"
              >
                Confirm Booking
              </button>
            )}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default BookingsPage;
