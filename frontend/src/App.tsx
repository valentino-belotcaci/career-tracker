import { BrowserRouter, Routes, Route } from 'react-router-dom';
import OnBoardingPage from './pages/OnBoardingPage';
import IndexUserPage from './pages/IndexUserPage'; 

function App() {
  return (
    <BrowserRouter>
      <Routes>
        {/* This is your "home" or starting page */}
        <Route path="/" element={<OnBoardingPage />} />

        {/* These are the destinations for your navigate() calls */}
        <Route path="/indexUser" element={<IndexUserPage />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;