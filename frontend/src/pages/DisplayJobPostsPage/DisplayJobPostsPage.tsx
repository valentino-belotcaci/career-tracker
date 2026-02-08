import DisplayJobPostsComponent from "../../components/DisplayJobPosts/DisplayJobPostsComponent";
import { Link } from 'react-router-dom';

export default function DisplayJobPostsPage() {
    return (
        <div>
            <Link to="/createJobPost">new Post</Link>
            <DisplayJobPostsComponent />
        </div>
    );
}