import axios, { type AxiosInstance } from 'axios';

// Define the base configuration
const client: AxiosInstance = axios.create({
  baseURL: 'http://localhost:8080/',
  headers: {
    'Content-Type': 'application/json',
  },
});


export default client;