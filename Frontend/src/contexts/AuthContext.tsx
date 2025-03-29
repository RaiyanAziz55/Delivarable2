// src/contexts/AuthContext.tsx
import React, { createContext, useState, useEffect, useContext, ReactNode } from 'react';
import api from '../api/axios';

interface User {
  id: number;
  email: string;
  role: 'customer' | 'employee' | 'manager';
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
      const { token: jwtToken, user: userData } = response.data;
      localStorage.setItem('token', jwtToken);
      setToken(jwtToken);
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
