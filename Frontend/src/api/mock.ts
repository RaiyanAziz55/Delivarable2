// src/api/mock.ts
import axios from 'axios';
import MockAdapter from 'axios-mock-adapter';

// Fake data for hotel chains, hotels, and rooms.
export const fakeHotelChains = [
  {
    id: 1,
    name: "Luxury Stay",
    address: "123 Chain Blvd, City A",
    phone: "111-222-3333",
    chainNumber: "LS-001",
  },
  {
    id: 2,
    name: "Budget Inn",
    address: "789 Cheap Rd, Town B",
    phone: "444-555-6666",
    chainNumber: "BI-002",
  },
];

export const fakeHotels = [
  {
    id: 1,
    name: "Luxury Downtown",
    address: "456 Main St, City A",
    phone: "111-000-1111",
    category: "Luxury",
    starCount: 5,
    hotelChain: fakeHotelChains[0],
  },
  {
    id: 2,
    name: "Luxury Uptown",
    address: "789 Side St, City A",
    phone: "111-000-2222",
    category: "Luxury",
    starCount: 4,
    hotelChain: fakeHotelChains[0],
  },
  {
    id: 3,
    name: "Budget Inn North",
    address: "123 Budget Rd, Town B",
    phone: "444-555-6666",
    category: "Economy",
    starCount: 3,
    hotelChain: fakeHotelChains[1],
  },
];

export const fakeRooms = [
  {
    id: 1,
    roomNumber: "101",
    price: 150,
    capacity: "double",
    view: "sea",
    hotelId: 1,
  },
  {
    id: 2,
    roomNumber: "102",
    price: 170,
    capacity: "double",
    view: "city",
    hotelId: 1,
  },
  {
    id: 3,
    roomNumber: "201",
    price: 90,
    capacity: "single",
    view: "garden",
    hotelId: 3,
  },
];

// Create a mock adapter instance for Axios.
const mock = new MockAdapter(axios, { delayResponse: 500 });

// Intercept GET /hotel-chains request.
mock.onGet('/hotel-chains').reply(200, fakeHotelChains);

// Intercept GET /hotels/hotel-chain/{chainId} request.
mock.onGet(new RegExp('/hotels/hotel-chain/\\d+')).reply(config => {
  // Extract chainId from the URL.
  const match = config.url?.match(/\/hotels\/hotel-chain\/(\d+)/);
  const chainId = match ? parseInt(match[1], 10) : null;
  if (chainId) {
    // Filter hotels by matching hotelChain.id.
    const hotels = fakeHotels.filter(hotel => hotel.hotelChain.id === chainId);
    return [200, hotels];
  }
  return [404, { message: 'Hotel chain not found' }];
});

// Intercept GET /rooms/hotel/{hotelId} request.
mock.onGet(new RegExp('/rooms/hotel/\\d+')).reply(config => {
  const match = config.url?.match(/\/rooms\/hotel\/(\d+)/);
  const hotelId = match ? parseInt(match[1], 10) : null;
  if (hotelId) {
    const rooms = fakeRooms.filter(room => room.hotelId === hotelId);
    return [200, rooms];
  }
  return [404, { message: 'Hotel not found' }];
});

// Mock the room availability endpoint.
mock.onGet(new RegExp('/rooms/available')).reply(config => {
    // Extract the query parameters from the URL.
    const url = config.url || "";
    const queryString = url.split('?')[1] || "";
    const params = new URLSearchParams(queryString);
    const roomId = params.get('roomId');
    const checkIn = params.get('checkIn');
    const checkOut = params.get('checkOut');
  
    // If any parameter is missing, return a 400 error.
    if (!roomId || !checkIn || !checkOut) {
      return [400, { message: 'Missing booking parameters.' }];
    }
  
    // Here you can add conditions to simulate different responses.
    // For now, we return true for availability.
    return [200, true];
  });

  // POST /bookings mock
mock.onPost('/bookings').reply((config) => {
    try {
      const data = JSON.parse(config.data);
      // Validate that the request includes the required booking parameters
      const { customer, roomId, checkInDate, checkOutDate } = data;
      if (!customer || !roomId || !checkInDate || !checkOutDate) {
        return [400, { message: 'Missing booking data.' }];
      }
  
      // Optionally, you can add further validation on customer fields here.
  
      // Simulate a successful booking response.
      return [200, { bookingId: 123, message: 'Booking confirmed successfully.' }];
    } catch (error) {
      return [400, { message: 'Invalid booking data.' }];
    }
  });

  // Mock GET /bookings/hotel/3
mock.onGet('/bookings/hotel/3').reply(() => {
  const bookingResponse = {
    id: 1,
    bookingDate: "2025-03-25T15:47:47.323119",
    checkInDate: "2025-04-01",
    checkOutDate: "2025-04-05",
    status: "Pending",
    room: {
      id: 2,
      roomNumber: "102",
      price: 300,
      capacity: "double",
      extended: true,
      problems: null,
      view: null,
      hotel: {
        id: 1,
        name: "Luxury Stay Downtown",
        address: "Downtown, City A",
        phone: "111-000-1111",
        category: "Luxury",
        starCount: 5,
        hotelChain: {
          id: 1,
          name: "Luxury Stay",
          address: "123 Luxury Ave, City A",
          phone: "111-222-3333",
          chainNumber: "LS001"
        }
      },
      amenities: {
        id: 2,
        wifi: true,
        ac: true,
        pool: false,
        gym: true,
        spa: false,
        parking: true,
        fridge: true,
        coffee: false
      }
    },
    customer: {
      id: 2,
      fullName: "John Doe",
      address: "123 Main St",
      phone: "1234567890",
      email: "john@example.com",
      idType: null,
      registrationDate: "2025-03-25"
    },
    employee: 1
  };

  // Return the response as an array (assuming the backend returns an array of bookings)
  return [200, [bookingResponse]];
});

// Mock for confirming booking with booking id 1 (PUT /bookings/1/status)
mock.onPut('/bookings/1/status').reply((config) => {
    try {
      const data = JSON.parse(config.data);
      const { status } = data;
      if (status === 'Checked-In') {
        return [200, { message: 'Booking confirmed successfully.' }];
      } else {
        return [400, { message: 'Invalid status provided.' }];
      }
    } catch (error) {
      return [400, { message: 'Invalid data format.' }];
    }
  });

mock.onPost('/employees/login').reply((config) => {
    const { email, password } = JSON.parse(config.data);
    // You can adjust these credentials as needed.
    if (email === 'alice@hotel.com' && password === 'password') {
      return [
        200,
        {
          token: 'fake-jwt-token',
          employeeId: 1, 
          hotelId: 3,
          ROLE: 'Manager'}

      ];
    } else {
      return [401, { message: 'Invalid credentials' }];
    }
  });

  mock.onGet('/rentings/hotel/3').reply(200, [
    {
      "id": 2,
      "dateIn": "2025-03-25T16:56:51.285613",
      "dateOut": "2025-04-05T12:00:00",
      "payment": 500,
      "booking": null,
      "employee": {
        "id": 2,
        "fullName": "Bob Staff",
        "address": "102 Staff Ave, City A",
        "phone": "111-555-0202",
        "email": "bob.staff@luxurystay.com",
        "password": "$2a$10$h5jNlLaozmPyusV7.IAYo.Q2wDt4nCcIGPnX3doeY1Wv1l6kbHfl.",
        "role": "Receptionist",
        "manager": false,
        "ssnNumber": null,
        "hotel": {
          "id": 1,
          "name": "Luxury Stay Downtown",
          "address": "Downtown, City A",
          "phone": "111-000-1111",
          "category": "Luxury",
          "starCount": 5,
          "hotelChain": {
            "id": 1,
            "name": "Luxury Stay",
            "address": "123 Luxury Ave, City A",
            "phone": "111-222-3333",
            "chainNumber": "LS001"
          }
        },
        "firstName": null,
        "lastName": null,
        "position": null
      },
      "room": {
        "id": 1,
        "roomNumber": "101",
        "price": 250,
        "capacity": "single",
        "extended": false,
        "problems": null,
        "view": null,
        "hotel": {
          "id": 1,
          "name": "Luxury Stay Downtown",
          "address": "Downtown, City A",
          "phone": "111-000-1111",
          "category": "Luxury",
          "starCount": 5,
          "hotelChain": {
            "id": 1,
            "name": "Luxury Stay",
            "address": "123 Luxury Ave, City A",
            "phone": "111-222-3333",
            "chainNumber": "LS001"
          }
        },
        "amenities": {
          "id": 1,
          "wifi": true,
          "ac": true,
          "pool": true,
          "gym": false,
          "spa": false,
          "parking": true,
          "fridge": true,
          "coffee": true
        }
      },
      "customer": {
        "id": 4,
        "fullName": "Walkin Guest",
        "address": "Lobby",
        "phone": "555-0000",
        "email": "walkin@example.com",
        "idType": null,
        "registrationDate": "2025-03-25"
      }
    }
  ]);
// Mock GET /employees/hotel/3
mock.onGet('/employees/hotel/3').reply(200, [
    {
      id: 1,
      fullName: "Alice Manager",
      email: "alice.manager@hotel.com",
      role: "Manager",
    },
    {
      id: 2,
      fullName: "Bob Staff",
      email: "bob.staff@hotel.com",
      role: "Employee",
    },
    {
      id: 3,
      fullName: "Carol Clerk",
      email: "carol.clerk@hotel.com",
      role: "Employee",
    },
  ]);

  // Mock for POST /employees (create new employee)
mock.onPost('/employees').reply((config) => {
    try {
      const data = JSON.parse(config.data);
      // You can simulate creating an employee by assigning a new id.
      const newEmployee = {
        id: Math.floor(Math.random() * 1000) + 100, // a random id
        fullName: data.fullName,
        email: data.email,
        role: data.role,
        // Optionally, include hotel if needed
        hotel: data.hotel,
      };
      return [200, newEmployee];
    } catch (error) {
      return [400, { message: 'Invalid employee data' }];
    }
  });
  
  // Mock for DELETE /employees/{id}
  mock.onDelete(new RegExp('/employees/3')).reply((config) => {
    // For simplicity, assume the deletion is always successful.
    return [204];
  });

  // In src/api/mock.ts

// Mock for POST /rentings (Walk-In Renting)
mock.onPost('/rentings').reply((config) => {
    try {
      const data = JSON.parse(config.data);
      // Optionally validate data...
      // Simulate creation by returning a new renting object with an id and current dateIn
      const newRenting = {
        id: Math.floor(Math.random() * 1000) + 10,
        dateIn: new Date().toISOString(), // current date/time
        dateOut: data.dateOut,
        payment: data.payment,
        booking: null,
        // other properties from data can be included if desired
      };
      return [200, newRenting];
    } catch (error) {
      return [400, { message: 'Invalid renting data.' }];
    }
  });
  
  // Mock for POST /rentings/from-booking (Renting from Booking)
  mock.onPost('/rentings/from-booking').reply((config) => {
    try {
      const data = JSON.parse(config.data);
      // Optionally validate data...
      const newRenting = {
        id: Math.floor(Math.random() * 1000) + 10,
        dateIn: new Date().toISOString(),
        dateOut: data.dateOut,
        payment: data.payment,
        booking: data.bookingId, // Use bookingId from the payload
      };
      return [200, newRenting];
    } catch (error) {
      return [400, { message: 'Invalid renting data.' }];
    }
  });
  