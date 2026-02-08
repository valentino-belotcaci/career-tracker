import JobPostDetailsComponent from '../../components/JobPostDetails/JobPostDetailsComponent';
import { useParams } from 'react-router-dom';

export default function JobPostDetailsPage() {

const { postId } = useParams<{ postId: string }>();
if (postId) 

    return (
        <div>
            <JobPostDetailsComponent postId={postId}></JobPostDetailsComponent>
        </div>
    );
}