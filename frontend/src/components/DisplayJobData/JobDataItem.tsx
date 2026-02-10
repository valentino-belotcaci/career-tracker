import { type JobApplication } from '../../types/JobApplication';
import { type JobPost } from '../../types/JobPost';
// Corrected syntax: const Name = ({ props }: Type) => { return ... }
export const JobApplicationItem = ({ jobApplication, onClick }: { jobApplication: JobApplication; onClick: () => void }) => {
    return (
        <tr onClick={onClick} style={{ cursor: 'pointer' }}>
            <td>UserId: {jobApplication.userId}</td>
            <td>PostId: {jobApplication.postId}</td>
            <td>userDescription: {jobApplication.userDescription}</td>
            <td>status: {jobApplication.status}</td>
            <td>created at: {jobApplication.createdAt}</td>
        </tr>
    );
};

export const JobPostItem = ({ jobPost, onClick }: { jobPost: JobPost; onClick: () => void }) => {
    return (
        <tr onClick={onClick} style={{ cursor: 'pointer' }}>
            <td>Name: {jobPost.name}</td>
            <td>ID: {jobPost.companyId}</td>
            <td>Duration: {jobPost.duration}</td>
            <td>Salary: {jobPost.salary}</td>
            <td>Created at: {jobPost.createdAt}</td>
            <td>Available: {jobPost.available}</td>
            <td>Description: {jobPost.description}</td>
        </tr>
    );
};