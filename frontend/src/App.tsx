import { BrowserRouter, Routes, Route } from 'react-router-dom';
import LayoutComponent from './components/Layout/LayoutComponent';
import OnBoardingPage from './pages/OnBoardingPage/OnBoardingPage';
import AuthPage from './pages/AuthPage/AuthPage';
import DashBoardPage from './pages/DashBoardPage/DashBoardPage';
import DisplayJobPostsPage from './pages/DisplayJobPostsPage/DisplayJobPostsPage';
import { ContextProvider } from './components/Context';
import CreateJobPostPage from './pages/CreateJobPostPage/CreateJobPostPage';
import JobPostDetailsPage from './pages/JobPostDetails/JobPostDetailsPage';

function App() {

  return (
    <ContextProvider>
      <BrowserRouter>
        <Routes>
          <Route element={<LayoutComponent />}>
            <Route path="/" element={<OnBoardingPage />} />
            <Route path="/authentication/:type" element={<AuthPage/>}/> 
            <Route path="/dashboard" element={<DashBoardPage />} />
            <Route path="/displayJobPosts" element={<DisplayJobPostsPage/>}/>
            <Route path="/createJobPost" element={<CreateJobPostPage/>} />
            {/*Defines the place for the useParams data to be expected */}
            <Route path="/jobPostDetails/:postId" element={<JobPostDetailsPage/>}/>
          </Route>
        </Routes>
      </BrowserRouter>
    </ContextProvider>
  );
}

export default App;