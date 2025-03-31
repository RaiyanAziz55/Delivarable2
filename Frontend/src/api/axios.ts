// src/api/axios.ts
import axios from 'axios';


export interface Booking {
  id: number;
  status: string;
  checkInDate: string;
  checkOutDate: string;
}

interface Employee {
  id: number;
  fullName: string;
  email: string;
  role: string;
  // Add other fields as needed
}

interface Renting {
  id: number;
  dateIn: string;
  dateOut: string;
  payment: number;
  booking: number;
  // Add additional renting properties as needed
}
const api = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api',
});
const parseBooking = (data: any): Booking => ({
  id: data.id,
  status: data.status,
  checkInDate: data.checkInDate,
  checkOutDate: data.checkOutDate,
});
export const parseRenting = (data: any): Renting => ({
  id: data.id,
  dateIn: data.dateIn,
  dateOut: data.dateOut,
  payment: data.payment,
  booking: data.booking
});

api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token');
    if (token && config.headers) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

// Add a response interceptor for booking responses
api.interceptors.response.use((response) => {
  // If the response is for the bookings endpoint, map the data
  if (response.config.url?.includes('/bookings') && Array.isArray(response.data)) {
    response.data = response.data.map(parseBooking);
  }
  return response;
}, (error) => {
  return Promise.reject(error);
});

api.interceptors.response.use((response) => {
  // If the response is for the rentings endpoint and is an array
  if (response.config.url?.includes('/rentings') && Array.isArray(response.data)) {
    response.data = response.data.map(parseRenting);
  }
  return response;
}, (error) => {
  return Promise.reject(error);
});

export default api;
