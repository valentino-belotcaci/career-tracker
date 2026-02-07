import { BrowserRouter, Routes, Route } from 'react-router-dom';
import LayoutComponent from './components/LayoutComponent';
import OnBoardingPage from './pages/OnBoardingPage';
import AuthPage from './pages/AuthPage';
import DashBoardPage from './pages/DashboardPage';
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