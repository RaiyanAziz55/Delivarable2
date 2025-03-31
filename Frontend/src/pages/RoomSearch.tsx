// src/pages/RoomSearch.tsx
import React, { useEffect, useState } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import api from '../api/axios';

interface HotelChain {
  id: number;
  name: string;
  address: string;
  phone: string;
  chainNumber: string;
}

interface Hotel {
  id: number;
  name: string;
  address: string;
  phone: string;
  category: string;
  starCount: number;
  hotelChain: HotelChain;
}

interface Room {
  id: number;
  roomNumber: string;
  price: number;
  capacity: string;
  view: string;
  // add other room properties as needed
}

const RoomSearch: React.FC = () => {
  const location = useLocation();
  const navigate = useNavigate();

  // Parse query parameters from the URL
  const queryParams = new URLSearchParams(location.search);
  const searchType = queryParams.get('type') || 'rooms'; // default type if needed
  const searchTerm = queryParams.get('q');
  const startDate = queryParams.get('startDate');
  const endDate = queryParams.get('endDate')

  // State for hierarchical navigation
  const [hotelChains, setHotelChains] = useState<HotelChain[]>([]);
  const [hotels, setHotels] = useState<Hotel[]>([]);
  const [rooms, setRooms] = useState<Room[]>([]);
  const [currentChain, setCurrentChain] = useState<HotelChain | null>(null);
  const [currentHotel, setCurrentHotel] = useState<Hotel | null>(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  // Fetch hotel chains if the search type is "hotelChains" and nothing is selected yet.
  useEffect(() => {
    if (searchType === 'hotelChains' && !currentChain) {
      setLoading(true);
      api
        .get('/hotel-chains')
        .then((response) => {
          setHotelChains(response.data);
          setLoading(false);
        })
        .catch((err) => {
          setError('Error fetching hotel chains');
          setLoading(false);
        });
    }
  }, [searchType, currentChain]);

  // When a hotel chain is clicked, fetch hotels for that chain.
  const handleChainClick = (chain: HotelChain) => {
    setCurrentChain(chain);
    setLoading(true);
    // Assuming an endpoint like /hotels/hotel-chain/{chainId}
    api
      .get(`/hotels/hotel-chain/${chain.id}`)
      .then((response) => {
        setHotels(response.data);
        setLoading(false);
      })
      .catch((err) => {
        setError('Error fetching hotels');
        setLoading(false);
      });
  };

  // When a hotel is clicked, fetch available rooms for that hotel.
  const handleHotelClick = (hotel: Hotel) => {
    setCurrentHotel(hotel);
    setLoading(true);
    // Assuming an endpoint like /rooms/hotel/{hotelId}
    api
      .get(`/rooms/hotel/${hotel.id}`)
      .then((response) => {
        setRooms(response.data);
        setLoading(false);
      })
      .catch((err) => {
        setError('Error fetching rooms');
        setLoading(false);
      });
  };

  // Back navigation: if we're viewing rooms, go back to hotels; if hotels, go back to chains.
  const handleBack = () => {
    if (rooms.length > 0) {
      // Go back from rooms to hotels
      setRooms([]);
      setCurrentHotel(null);
    } else if (hotels.length > 0) {
      // Go back from hotels to chains
      setHotels([]);
      setCurrentChain(null);
    }
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

      <div className="max-w-3xl mx-auto bg-white p-8 shadow rounded mt-16">
        <h1 className="text-2xl font-bold mb-4">Search Results</h1>

        {loading && <p>Loading...</p>}
        {error && <p className="text-red-500">{error}</p>}

        {/* Back button if not at the initial hotel chain level */}
        {(currentChain || currentHotel) && (
          <button
            onClick={handleBack}
            className="mb-4 text-blue-500 hover:underline"
          >
            &larr; Back
          </button>
        )}

        {/* Step 1: Display list of hotel chains */}
        {!currentChain && searchType === 'hotelChains' && (
          <ul>
            {hotelChains.map((chain) => (
              <li
                key={chain.id}
                className="mb-2 p-2 border rounded hover:bg-gray-50 cursor-pointer"
                onClick={() => handleChainClick(chain)}
              >
                <p className="font-bold">{chain.name}</p>
                <p>{chain.address}</p>
                <p>{chain.phone}</p>
              </li>
            ))}
          </ul>
        )}

        {/* Step 2: Display list of hotels for the selected chain */}
        {currentChain && !currentHotel && hotels.length > 0 && (
          <div>
            <h2 className="text-xl font-bold mb-2">
              Hotels in {currentChain.name}
            </h2>
            <ul>
              {hotels.map((hotel) => (
                <li
                  key={hotel.id}
                  className="mb-2 p-2 border rounded hover:bg-gray-50 cursor-pointer"
                  onClick={() => handleHotelClick(hotel)}
                >
                  <p className="font-bold">{hotel.name}</p>
                  <p>{hotel.address}</p>
                  <p>{hotel.phone}</p>
                </li>
              ))}
            </ul>
          </div>
        )}

        {/* Step 3: Display list of rooms for the selected hotel */}
        {currentHotel && rooms.length > 0 && (
          <div>
            <h2 className="text-xl font-bold mb-2">
              Rooms at {currentHotel.name}
            </h2>
            <ul>
              {rooms.map((room) => (
                <li
                  key={room.id}
                  className="mb-2 p-2 border rounded hover:bg-gray-50 cursor-pointer"
                  onClick={() => navigate(`/booking?roomId=${room.id}&startDate=${startDate}&endDate=${endDate}`)}
                >
                  <p className="font-bold">Room {room.roomNumber}</p>
                  <p>${room.price} per night</p>
                  <p>{room.capacity} capacity</p>
                  <p>View: {room.view}</p>
                </li>
              ))}
            </ul>
          </div>
        )}
      </div>
    </div>
  );
};

export default RoomSearch;
