import axios, { type AxiosInstance } from 'axios';

// Define the base configuration
export const client: AxiosInstance = axios.create({
  baseURL: 'http://localhost:8080/',
  withCredentials: true,
  headers: {
    'Content-Type': 'application/json',
  },
});


