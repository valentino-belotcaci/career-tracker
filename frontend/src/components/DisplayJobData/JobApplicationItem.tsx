import { type JobApplication } from '../../types/JobApplication';


export default function JobApplicationItem({ jobApplication, onClick }: { jobApplication: JobApplication , onClick: () => void }) {
    return (
        <tr onClick={onClick}>
            <td>UserId: {jobApplication.userId}</td>
            <td>PostId: {jobApplication.postId}</td>
            <td>userDescription: {jobApplication.userDescription}</td>
            <td>status: {jobApplication.status}</td>
            <td>created at: {jobApplication.createdAt}</td>
        </tr>
    );
}