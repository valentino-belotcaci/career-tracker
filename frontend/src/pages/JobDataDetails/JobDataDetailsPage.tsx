import JobDataDetailsComponent from '../../components/JobDataDetails/JobDataDetailsComponent';
import { useParams } from 'react-router-dom';

export default function JobDataDetailsPage() {

const { jobId } = useParams<{ jobId: string }>();
if (jobId) 

    return (
        <div>
            <JobDataDetailsComponent jobId={jobId}></JobDataDetailsComponent>
        </div>
    );
}