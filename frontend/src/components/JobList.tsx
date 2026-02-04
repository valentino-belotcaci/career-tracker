import type { JobPost } from "../types/JobPost";
import JobCard from "./JobCard";

interface Props  {
  jobs: JobPost[];
};

function JobList({ jobs }: Props) {
  return (
    <div>
      {jobs.map((job) => (
        <JobCard key={job.postId} job={job} />
      ))}
    </div>
  );
}

export default JobList;
