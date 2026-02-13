import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import {client} from '../api/client.ts';
const AxiosInterceptor = ({ children }: { children: React.ReactNode }) => {
  const navigate = useNavigate();

  useEffect(() => {
    const interceptor = client.interceptors.response.use(
      (response) => response,
      (error) => {
        if (error.response && (error.response.status === 401 || error.response.status === 403)) {
          console.warn("Session token is invalid, redirecting to on boarding.");
          localStorage.clear(); // Clear local storage to remove any stale data
          navigate('/'); 
        }
        return Promise.reject(error);
      }
    );

    return () => client.interceptors.response.eject(interceptor);
  }, [navigate]);

  return <>{children}</>;
};

export default AxiosInterceptor;