import DisplayJobPostsComponent from "../../components/DisplayJobData/DisplayJobDataComponent";
import { Link } from 'react-router-dom';

export default function DisplayJobDataPage() {
    return (
        <div>
            <Link to="/createJobPost">new Post</Link>
            <DisplayJobPostsComponent />
        </div>
    );
}