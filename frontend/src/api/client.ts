import axios, { type AxiosInstance } from 'axios';

export const client: AxiosInstance = axios.create({
  baseURL: 'http://localhost:8080/',
  withCredentials: true,
});

client.interceptors.request.use((config) => {
  // We use Regex to extract the token from the cookie
  const token = document.cookie.match(/XSRF-TOKEN=([^;]+)/)?.[1];

  if (token) {
    config.headers['X-XSRF-TOKEN'] = decodeURIComponent(token);
  }

  return config;
});