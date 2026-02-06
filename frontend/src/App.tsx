import { BrowserRouter, Routes, Route } from 'react-router-dom';
import LayoutComponent from './components/LayoutComponent';
import IndexUserPage from './pages/IndexUserPage'; 
import IndexCompanyPage from './pages/IndexCompanyPage';
import OnBoardingPage from './pages/OnBoardingPage';
import LoginPage from './pages/LoginPage';
import RegisterPage from './pages/RegisterPage';
import DashboardPage from './pages/DashboardPage';
import { AuthProvider } from './components/AuthContext';

function App() {

  return (
    <AuthProvider>
      <BrowserRouter>
        <Routes>
          <Route element={<LayoutComponent />}>
            <Route path="/" element={<OnBoardingPage />} />
            <Route path="/login" element={<LoginPage/>}/> 
            <Route path="/register" element={<RegisterPage/>}/>         
            <Route path="/indexUser" element={<IndexUserPage />} />
            <Route path="/indexCompany" element={<IndexCompanyPage />} />
            <Route path="/dashboard" element={<DashboardPage />} />
          </Route>
        </Routes>
      </BrowserRouter>
    </AuthProvider>
  );
}

export default App;