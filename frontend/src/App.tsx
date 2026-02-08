import { BrowserRouter, Routes, Route } from 'react-router-dom';
import LayoutComponent from './components/Layout/LayoutComponent';
import OnBoardingPage from './pages/OnBoardingPage/OnBoardingPage';
import AuthPage from './pages/AuthPage/AuthPage';
import DashBoardPage from './pages/DashBoardPage/DashBoardPage';
import DisplayJobPostsPage from './pages/DisplayJobPostsPage/DisplayJobPostsPage';
import { AuthProvider } from './components/AuthContext';
import CreateJobPostPage from './pages/CreateJobPostPage/CreateJobPostPage';

function App() {

  return (
    <AuthProvider>
      <BrowserRouter>
        <Routes>
          <Route element={<LayoutComponent />}>
            <Route path="/" element={<OnBoardingPage />} />
            <Route path="/authentication" element={<AuthPage/>}/> 
            <Route path="/dashboard" element={<DashBoardPage />} />
            <Route path="/displayJobPosts" element={<DisplayJobPostsPage/>}/>
            <Route path="/createJobPost" element={<CreateJobPostPage/>} />
          </Route>
        </Routes>
      </BrowserRouter>
    </AuthProvider>
  );
}

export default App;