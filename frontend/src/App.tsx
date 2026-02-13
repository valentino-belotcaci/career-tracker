import { BrowserRouter, Routes, Route } from 'react-router-dom';
import LayoutComponent from './components/Layout/LayoutComponent';
import OnBoardingPage from './pages/OnBoardingPage/OnBoardingPage';
import AuthPage from './pages/AuthPage/AuthPage';
import DashBoardPage from './pages/DashBoardPage/DashBoardPage';
import DisplayJobDataPage from './pages/DisplayJobDataPage/DisplayJobDataPage';
import { ContextProvider } from './components/Context';
import CreateJobDataPage from './pages/CreateJobDataPage/CreateJobDataPage';
import JobDataDetailsPage from './pages/JobDataDetails/JobDataDetailsPage';
import ProfileComponent from './components/Profile/ProfileComponent';

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
            <Route path="/profile" element={<ProfileComponent />} /> 
          </Route>
        </Routes>
      </BrowserRouter>
    </ContextProvider>
  );
}

export default App;