import { BrowserRouter, Routes, Route } from 'react-router-dom';
import LayoutComponent from './components/Layout/LayoutComponent';
import OnBoardingPage from './pages/OnBoardingPage/OnBoardingPage';
import AuthPage from './pages/AuthPage/AuthPage';
import DashBoardPage from './pages/DashBoardPage/DashBoardPage';
import { AuthProvider } from './components/AuthContext';

function App() {

  return (
    <AuthProvider>
      <BrowserRouter>
        <Routes>
          <Route element={<LayoutComponent />}>
            <Route path="/" element={<OnBoardingPage />} />
            <Route path="/authentication" element={<AuthPage/>}/> 
            <Route path="/dashboard" element={<DashBoardPage />} />
          </Route>
        </Routes>
      </BrowserRouter>
    </AuthProvider>
  );
}

export default App;