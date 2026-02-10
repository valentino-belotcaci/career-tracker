import JobDataDetailsComponent from '../../components/JobDataDetails/JobDataDetailsComponent';
import { useParams } from 'react-router-dom';
//import { useContext } from 'react';
//import { Context } from '../../components/Context';

export default function JobDataDetailsPage() {

const { jobId } = useParams<{ jobId: string }>();
//const {loggedId} = Context();
//const isUser = loggedId === "USER" ? true : false;
if (jobId) 

    return (
        <div>
            <JobDataDetailsComponent jobId={jobId}></JobDataDetailsComponent>
            {/*{isUser && <Link ></Link>} */}
        </div>
    );
}