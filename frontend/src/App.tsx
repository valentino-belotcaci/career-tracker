import { jobPosts } from "./data/jobPosts";
import JobList from "./components/JobList";
import Account from "./components/AccountComponent";

function App() {
  return (
    // Wrap everything in a Fragment or a <div>
    <>
      <h1>Job Portal</h1>
      <JobList jobs={jobPosts} />
      <Account />
    </>
  );
}

export default App;