import JobDataDetailsComponent from '../../components/JobDataDetails/JobDataDetailsComponent';
import { Link, useParams } from 'react-router-dom';
import { Context } from '../../components/Context';

export default function JobDataDetailsPage() {

    const { jobId } = useParams<{ jobId: string }>();
    const {accountType} = Context();
    const isUser = accountType === "USER" ? true : false;
    if (jobId) 

        return (
            <div>
                <JobDataDetailsComponent jobId={jobId}/>
                {isUser && <Link to="/create/JobApplication" state={{jobId}}>Apply</Link>} 
            </div>
        );
    
}