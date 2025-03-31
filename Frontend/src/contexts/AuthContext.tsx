// src/contexts/AuthContext.tsx
import React, { createContext, useState, useEffect, useContext, ReactNode } from 'react';
import api from '../api/axios';

interface User {
  id: number;
  email: string;
  ROLE: 'customer' | 'Employee' | 'Manager';
  hotelId?: number;    // New: the associated hotel ID
  employeeId?: number; // New: the employee's ID
}

interface AuthContextType {
  user: User | null;
  token: string | null;
  login: (email: string, password: string) => Promise<void>;
  logout: () => void;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const AuthProvider = ({ children }: { children: ReactNode }) => {
  const [user, setUser] = useState<User | null>(null);
  const [token, setToken] = useState<string | null>(localStorage.getItem('token'));

  useEffect(() => {
    if (token) {
      // Optionally decode the token or fetch user info to update the state.
      // For example: setUser(decodeToken(token));
    }
  }, [token]);

  const login = async (email: string, password: string) => {
    try {
      const response = await api.post('/employees/login', { email, password });
      // Destructure the new fields from the response
      const { token: jwtToken, employeeId, hotelId, role } = response.data;
      console.log(response.data)
      localStorage.setItem('token', jwtToken);
      setToken(jwtToken);
  
      // Construct a User object using the new fields
      const userData: User = {
        id: employeeId,      // Use employeeId as the unique id
        email: email,        // The email provided during login
        ROLE: role,    // Default role; adjust if needed
        hotelId: hotelId,
        employeeId: employeeId,
      };
  
      setUser(userData);
    } catch (error) {
      console.error('Login failed', error);
      throw error;
    }
  };
  

  const logout = () => {
    localStorage.removeItem('token');
    setToken(null);
    setUser(null);
  };

  return (
    <AuthContext.Provider value={{ user, token, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) throw new Error('useAuth must be used within an AuthProvider');
  return context;
};
