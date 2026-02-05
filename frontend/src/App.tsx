import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Login from './pages/LoginPage';
import IndexUserPage from './pages/IndexUserPage'; 
import IndexCompanyPage from './pages/IndexCompanyPage';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        {/* This is your "home" or starting page */}
        <Route path="/" element={<Login />} />

        {/* These are the destinations for your navigate() calls */}
        <Route path="/indexUser" element={<IndexUserPage />} />
        <Route path="/indexCompany" element={<IndexCompanyPage />} />
      </Routes>
    </BrowserRouter>
  );
}


export default App;