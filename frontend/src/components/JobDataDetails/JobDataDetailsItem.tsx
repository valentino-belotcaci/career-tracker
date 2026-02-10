// JobPostDetailsView.tsx
import { type JobPost } from "../../types/JobPost";
import { type JobApplication } from "../../types/JobApplication";


export const JobPostDetailsView = ({ data }: { data: Partial<JobPost> }) => (
    <>
        <td>Name: {data.name}</td>
        <td>Company Id: {data.companyId}</td>
        <td>Duration: {data.duration}</td>
        <td>Salary: {data.salary}</td>
        <td>Description: {data.description}</td>
        <td>Created at: {data.createdAt}</td>
        
    </>
);



export const JobApplicationDetailsView = ({ data }: { data: Partial<JobApplication> }) => (
    <>
        <td>Status: {data.status}</td>
        <td>Applied on: {data.createdAt}</td>
        <td>Your Pitch: {data.userDescription}</td>
        <td>Post ID: {data.postId}</td>
    </>
);