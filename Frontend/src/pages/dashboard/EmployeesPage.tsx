// src/pages/dashboard/EmployeesPage.tsx
import React, { useEffect, useState } from 'react';
import api from '../../api/axios';
import { useAuth } from '../../contexts/AuthContext';

interface Employee {
  id: number;
  fullName: string;
  email: string;
  role: string;
  
  // Add other fields as needed
}

const EmployeesPage: React.FC = () => {
  const [employees, setEmployees] = useState<Employee[]>([]);
  const [loading, setLoading] = useState<boolean>(false);
  const [error, setError] = useState<string>('');
  const { user } = useAuth();

  // State for new employee form
  const [newEmployee, setNewEmployee] = useState({
    fullName: '',
    email: '',
    role: 'Employee',
    password: '', // ✅ add this line
  });
  const [creating, setCreating] = useState<boolean>(false);

  useEffect(() => {
    fetchEmployees();
  }, [user]);

  const fetchEmployees = () => {
    if (!user?.hotelId) return;
    setLoading(true);
    // Make sure to use the correct endpoint; we assume /employees/hotel/{hotelId}
    api.get(`/employees/hotel/${user.hotelId}`)
      .then(response => {
        setEmployees(response.data);
        setLoading(false);
      })
      .catch(() => {
        setError('Error fetching employees');
        setLoading(false);
      });
  };

  const handleInputChange = (
    e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>
  ) => {
    const { name, value } = e.target;
    setNewEmployee(prev => ({ ...prev, [name]: value }));
  };

  const createEmployee = (e: React.FormEvent) => {
    e.preventDefault();
    setCreating(true);
    // Adjust payload as needed. Here we assume the POST /employees endpoint expects an employee object.
    api.post('/employees', { ...newEmployee, hotel: { id: user?.hotelId } })
      .then(response => {
        // Append new employee to the list
        setEmployees(prev => [...prev, response.data]);
        setNewEmployee({ fullName: '', email: '', role: 'Employee', password: '' });
        setCreating(false);
      })
      .catch(() => {
        setError('Error creating employee');
        setCreating(false);
      });
  };

  const deleteEmployee = (employeeId: number) => {
    api.delete(`/employees/${employeeId}`)
      .then(() => {
        // Remove the employee from the list
        setEmployees(prev => prev.filter(emp => emp.id !== employeeId));
      })
      .catch(() => {
        setError('Error deleting employee');
      });
  };

  return (
    <div>
      <h2 className="text-2xl font-bold mb-4">Employees</h2>
      {loading && <p>Loading employees...</p>}
      {error && <p className="text-red-500">{error}</p>}
      <ul>
        {employees.map(emp => (
          <li key={emp.id} className="p-4 border rounded mb-2 flex justify-between items-center">
            <div>
              <p>
                <strong>{emp.fullName}</strong> - {emp.email}
              </p>
              <p>{emp.role}</p>
            </div>
            <button
              onClick={() => deleteEmployee(emp.id)}
              className="bg-red-500 text-white px-3 py-1 rounded hover:bg-red-600 transition-colors"
            >
              Delete
            </button>
          </li>
        ))}
      </ul>

      <h3 className="text-xl font-bold mt-8 mb-4">Add New Employee</h3>
      <form onSubmit={createEmployee} className="space-y-4">
        <input
          type="text"
          name="fullName"
          placeholder="Full Name"
          value={newEmployee.fullName}
          onChange={handleInputChange}
          className="w-full border border-gray-300 p-2 rounded"
          required
        />
        <input
          type="email"
          name="email"
          placeholder="Email"
          value={newEmployee.email}
          onChange={handleInputChange}
          className="w-full border border-gray-300 p-2 rounded"
          required
        />
      <input
      type="password"
      name="password"
      placeholder="Password"
      value={newEmployee.password}
      onChange={handleInputChange}
      className="w-full border border-gray-300 p-2 rounded"
      required
    />

        <select
          name="role"
          value={newEmployee.role}
          onChange={handleInputChange}
          className="w-full border border-gray-300 p-2 rounded"
        >
          <option value="Employee">Employee</option>
          <option value="Manager">Manager</option>
        </select>
        <button
          type="submit"
          disabled={creating}
          className="w-full bg-blue-500 text-white py-2 rounded hover:bg-blue-600 transition-colors"
        >
          {creating ? 'Creating...' : 'Add Employee'}
        </button>
      </form>
    </div>
  );
};

export default EmployeesPage;
