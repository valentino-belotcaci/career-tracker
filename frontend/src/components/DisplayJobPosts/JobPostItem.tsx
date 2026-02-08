import { type JobPost } from '../../types/JobPost';



export default function JobPostItem({ jobPost }: { jobPost: JobPost }) {
    return (
        <tr>
            <td>{jobPost.name}</td>
            <td>{jobPost.companyId}</td>
            <td>{jobPost.duration}</td>
            <td>{jobPost.salary}</td>
            <td>{jobPost.createdAt}</td>
            <td>{jobPost.available}</td>
            <td>{jobPost.description}</td>
        </tr>
    );
}