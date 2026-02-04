import { jobPosts } from "./data/jobPosts";
import JobList from "./components/JobList";

function App() {
  return (
    <div>
      <h1>Job Board</h1>
      <JobList jobs={jobPosts} />
    </div>
  );
}

export default App;
