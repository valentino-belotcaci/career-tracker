import JobPostDetailsComponent from '../../components/JobPostDetails/JobPostDetailsComponent';
import { useLocation } from 'react-router-dom';

export default function JobPostDetailsPage() {

const { postId } = useLocation().state;
    return (
        <div>
            <JobPostDetailsComponent postId={postId}></JobPostDetailsComponent>
        </div>
    );
}