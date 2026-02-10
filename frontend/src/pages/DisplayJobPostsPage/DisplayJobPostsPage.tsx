import DisplayJobPostsComponent from "../../components/DisplayJobData/DisplayJobDataComponent";
import { Link } from 'react-router-dom';
import { Context } from '../../components/Context';

export default function DisplayJobDataPage() {
    const { accountType } = Context();
    const isCompany = accountType === "COMPANY";
    return (
        <div>
            {isCompany && <Link to="/create/JobPost">new Post</Link>}
            <DisplayJobPostsComponent />
        </div>
    );
}