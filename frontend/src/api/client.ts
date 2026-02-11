import axios, { type AxiosInstance } from 'axios';

export const client: AxiosInstance = axios.create({
  baseURL: 'http://localhost:8080/',
  withCredentials: true,
  // Rimuoviamo xsrfCookieName e xsrfHeaderName se usiamo l'interceptor manuale 
  // per avere il controllo totale, oppure lasciamoli come fallback.
  headers: {
    'Content-Type': 'application/json',
  },
});

// Interceptor per iniettare il token CSRF aggiornato ad ogni richiesta
client.interceptors.request.use((config) => {
  // Funzione helper per leggere i cookie in JS puro
  const getCookie = (name: string) => {
    const value = `; ${document.cookie}`;
    const parts = value.split(`; ${name}=`);
    if (parts.length === 2) return parts.pop()?.split(';').shift();
    return null;
  };

  const csrfToken = getCookie('XSRF-TOKEN');
  
  if (csrfToken) {
    // Forza l'header con il valore freschissimo del cookie
    config.headers['X-XSRF-TOKEN'] = csrfToken;
    console.log("CSRF Token inviato correttamente:", csrfToken);
  } else {
    console.warn("Attenzione: Cookie XSRF-TOKEN non trovato nel browser!");
  }

  return config;
}, (error) => {
  return Promise.reject(error);
});