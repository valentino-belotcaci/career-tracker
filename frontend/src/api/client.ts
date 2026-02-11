import axios, { type AxiosInstance } from 'axios';

// Define the base configuration
export const client: AxiosInstance = axios.create({
  baseURL: 'http://localhost:8080/',
  withCredentials: true,
  xsrfCookieName: 'XSRF-TOKEN', // The name of the cookie Spring sends
  xsrfHeaderName: 'X-XSRF-TOKEN', // The header name Spring expects
  headers: {
    'Content-Type': 'application/json',
  },
});


