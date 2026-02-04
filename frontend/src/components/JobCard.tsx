import type { JobPost } from "../types/JobPost";

interface Props {
  job: JobPost;
};

function JobCard({ job }: Props) {
  return (
    <div>
      <h3>{job.name}</h3>
      <p>{job.companyId}</p>
      <p>{job.salary}</p>
      <p>{job.description}</p>
      <p>{job.duration}</p>
      <p>{job.available}</p>
      <p>{job.createdAt}</p>
    </div>
  );
}

export default JobCard;
