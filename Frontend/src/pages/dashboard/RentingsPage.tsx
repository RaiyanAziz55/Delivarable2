// src/pages/dashboard/RentingsPage.tsx
import React, { useEffect, useState } from 'react';
import api from '../../api/axios';
import { useAuth } from '../../contexts/AuthContext';

interface Renting {
  id: number;
  dateIn: string; // Assume ISO string after parsing
  dateOut: string;
  payment: number;
  booking: number | null;
}

const RentingsPage: React.FC = () => {
  const [rentings, setRentings] = useState<Renting[]>([]);
  const [loading, setLoading] = useState<boolean>(false);
  const [error, setError] = useState<string>('');
  const { user } = useAuth();

  // State to control which form is active: "walkIn" or "fromBooking"
  const [activeForm, setActiveForm] = useState<'walkIn' | 'fromBooking'>('walkIn');

  // State for Walk-In Renting Form
  const [walkInForm, setWalkInForm] = useState({
    roomId: '',
    dateOut: '',
    payment: '',
    customerFullName: '',
    customerEmail: '',
    customerPhone: '',
    customerAddress: '',
    customerIdType: '',
  });

  // State for Renting From Booking Form
  const [bookingForm, setBookingForm] = useState({
    bookingId: '',
    dateOut: '',
    payment: '',
  });

  // Fetch rentings for the hotel on component mount (and whenever user changes)
  useEffect(() => {
    if (user?.hotelId) {
      setLoading(true);
      api.get(`/rentings/hotel/${user.hotelId}`)
        .then(response => {
          setRentings(response.data);
          setLoading(false);
        })
        .catch(() => {
          setError(`Error fetching rentings for hotel ${user.hotelId}`);
          setLoading(false);
        });
    }
  }, [user]);

  // Handlers for form input changes
  const handleWalkInChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setWalkInForm(prev => ({ ...prev, [name]: value }));
  };

  const handleBookingChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setBookingForm(prev => ({ ...prev, [name]: value }));
  };

  // Submit handler for Walk-In Renting
  const submitWalkInRenting = (e: React.FormEvent) => {
    e.preventDefault();
    // Construct payload for walk-in renting
    const payload = {
      roomId: Number(walkInForm.roomId),
      employeeId: user?.employeeId, // use logged in employee id
      customer: {
        fullName: walkInForm.customerFullName,
        email: walkInForm.customerEmail,
        phone: walkInForm.customerPhone,
        address: walkInForm.customerAddress,
        idType: walkInForm.customerIdType,
      },
      dateOut: walkInForm.dateOut,
      payment: Number(walkInForm.payment),
    };

    api.post('/rentings', payload)
      .then(response => {
        // Optionally append new renting to state
        setRentings(prev => [...prev, response.data]);
        // Clear form
        setWalkInForm({
          roomId: '',
          dateOut: '',
          payment: '',
          customerFullName: '',
          customerEmail: '',
          customerPhone: '',
          customerAddress: '',
          customerIdType: '',
        });
      })
      .catch(() => {
        setError('Error creating walk-in renting.');
      });
  };

  // Submit handler for Renting From Booking
  const submitBookingRenting = (e: React.FormEvent) => {
    e.preventDefault();
    // Construct payload for renting from booking
    const payload = {
      bookingId: Number(bookingForm.bookingId),
      employeeId: user?.employeeId,
      dateOut: bookingForm.dateOut,
      payment: Number(bookingForm.payment),
    };

    api.post('/rentings/from-booking', payload)
      .then(response => {
        setRentings(prev => [...prev, response.data]);
        // Clear form
        setBookingForm({
          bookingId: '',
          dateOut: '',
          payment: '',
        });
      })
      .catch(() => {
        setError('Error creating renting from booking.');
      });
  };

  return (
    <div>
      <h2 className="text-2xl font-bold mb-4">Rentings</h2>
      {loading && <p>Loading rentings...</p>}
      {error && <p className="text-red-500">{error}</p>}
      <ul>
        {rentings.map(renting => (
          <li key={renting.id} className="p-4 border rounded mb-2">
            <p><strong>ID:</strong> {renting.id}</p>
            <p><strong>Date In:</strong> {renting.dateIn}</p>
            <p><strong>Date Out:</strong> {renting.dateOut}</p>
            <p><strong>Payment:</strong> ${renting.payment}</p>
            <p><strong>Booking:</strong> {renting.booking ?? 'N/A'}</p>
          </li>
        ))}
      </ul>

      <div className="mt-8">
        <div className="flex space-x-4 mb-4">
          <button
            onClick={() => setActiveForm('walkIn')}
            className={`px-4 py-2 rounded ${activeForm === 'walkIn' ? 'bg-blue-500 text-white' : 'bg-gray-200 text-gray-700'}`}
          >
            Walk-In Renting
          </button>
          <button
            onClick={() => setActiveForm('fromBooking')}
            className={`px-4 py-2 rounded ${activeForm === 'fromBooking' ? 'bg-blue-500 text-white' : 'bg-gray-200 text-gray-700'}`}
          >
            Renting from Booking
          </button>
        </div>

        {activeForm === 'walkIn' && (
          <form onSubmit={submitWalkInRenting} className="space-y-4 border p-4 rounded">
            <h3 className="text-xl font-bold mb-2">Walk-In Renting</h3>
            <input
              type="number"
              name="roomId"
              placeholder="Room ID"
              value={walkInForm.roomId}
              onChange={handleWalkInChange}
              className="w-full border p-2 rounded"
              required
            />
            <input
              type="date"
              name="dateOut"
              placeholder="Date Out"
              value={walkInForm.dateOut}
              onChange={handleWalkInChange}
              className="w-full border p-2 rounded"
              required
            />
            <input
              type="number"
              name="payment"
              placeholder="Payment"
              value={walkInForm.payment}
              onChange={handleWalkInChange}
              className="w-full border p-2 rounded"
              required
            />
            <input
              type="text"
              name="customerFullName"
              placeholder="Customer Full Name"
              value={walkInForm.customerFullName}
              onChange={handleWalkInChange}
              className="w-full border p-2 rounded"
              required
            />
            <input
              type="email"
              name="customerEmail"
              placeholder="Customer Email"
              value={walkInForm.customerEmail}
              onChange={handleWalkInChange}
              className="w-full border p-2 rounded"
              required
            />
            <input
              type="text"
              name="customerPhone"
              placeholder="Customer Phone"
              value={walkInForm.customerPhone}
              onChange={handleWalkInChange}
              className="w-full border p-2 rounded"
              required
            />
            <input
              type="text"
              name="customerAddress"
              placeholder="Customer Address"
              value={walkInForm.customerAddress}
              onChange={handleWalkInChange}
              className="w-full border p-2 rounded"
              required
            />
            <input
              type="text"
              name="customerIdType"
              placeholder="Customer ID Type"
              value={walkInForm.customerIdType}
              onChange={handleWalkInChange}
              className="w-full border p-2 rounded"
              required
            />
            <button
              type="submit"
              className="w-full bg-blue-500 text-white py-2 rounded hover:bg-blue-600 transition-colors"
            >
              Submit Walk-In Renting
            </button>
          </form>
        )}

        {activeForm === 'fromBooking' && (
          <form onSubmit={submitBookingRenting} className="space-y-4 border p-4 rounded">
            <h3 className="text-xl font-bold mb-2">Renting from Booking</h3>
            <input
              type="number"
              name="bookingId"
              placeholder="Booking ID"
              value={bookingForm.bookingId}
              onChange={handleBookingChange}
              className="w-full border p-2 rounded"
              required
            />
            <input
              type="date"
              name="dateOut"
              placeholder="Date Out"
              value={bookingForm.dateOut}
              onChange={handleBookingChange}
              className="w-full border p-2 rounded"
              required
            />
            <input
              type="number"
              name="payment"
              placeholder="Payment"
              value={bookingForm.payment}
              onChange={handleBookingChange}
              className="w-full border p-2 rounded"
              required
            />
            <button
              type="submit"
              className="w-full bg-blue-500 text-white py-2 rounded hover:bg-blue-600 transition-colors"
            >
              Submit Renting from Booking
            </button>
          </form>
        )}
      </div>
    </div>
  );
};

export default RentingsPage;
