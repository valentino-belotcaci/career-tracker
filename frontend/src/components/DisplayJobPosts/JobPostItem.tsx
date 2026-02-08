import { type JobPost } from '../../types/JobPost';



export default function JobPostItem({ jobPost }: { jobPost: JobPost }) {
    return (
        <tr>
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