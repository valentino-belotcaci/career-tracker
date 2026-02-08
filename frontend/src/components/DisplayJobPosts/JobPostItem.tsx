import { type JobPost } from '../../types/JobPost';



export default function JobPostItem({ jobPost, onClick }: { jobPost: JobPost, onClick: () => void }) {
    return (
        <tr onClick={onClick} style={{ cursor: "pointer" }}>
            <td> name: {jobPost.name}</td>
            <td>id: {jobPost.companyId}</td>
            <td>duration: {jobPost.duration}</td>
            <td>salary: {jobPost.salary}</td>
            <td>created at: {jobPost.createdAt}</td>
            <td>available: {jobPost.available}</td>
            <td>description: {jobPost.description}</td>
        </tr>
    );
}