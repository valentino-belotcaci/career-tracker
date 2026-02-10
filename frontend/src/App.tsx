import { BrowserRouter, Routes, Route } from 'react-router-dom';
import LayoutComponent from './components/Layout/LayoutComponent';
import OnBoardingPage from './pages/OnBoardingPage/OnBoardingPage';
import AuthPage from './pages/AuthPage/AuthPage';
import DashBoardPage from './pages/DashBoardPage/DashBoardPage';
import DisplayJobDataPage from './pages/DisplayJobPostsPage/DisplayJobPostsPage';
import { ContextProvider } from './components/Context';
import CreateJobDataPage from './pages/CreateJobPostPage/CreateJobPostPage';
import JobDataDetailsPage from './pages/JobDataDetails/JobDataDetailsPage';

function App() {

  return (
    <ContextProvider>
      <BrowserRouter>
        <Routes>
          <Route element={<LayoutComponent />}>
            <Route path="/" element={<OnBoardingPage />} />
            <Route path="/authentication/:type" element={<AuthPage/>}/> 
            <Route path="/dashboard" element={<DashBoardPage />} />
            <Route path="/display/:dataType" element={<DisplayJobDataPage/>}/>
            <Route path="/create/:dataType" element={<CreateJobDataPage/>} />
            {/*Defines the place for the useParams data to be expected */}
            <Route path="/:jobType/:jobId" element={<JobDataDetailsPage/>}/>
          </Route>
        </Routes>
      </BrowserRouter>
    </ContextProvider>
  );
}

export default App;