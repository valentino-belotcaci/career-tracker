
import {type JobPost} from "../../types/JobPost";
import { getPostsByCompanyId } from '../../api/jobPostApi';
import JobPostItem from './JobPostItem';
import { useAuth } from '../AuthContext';
import { useEffect, useState } from "react";



export default function DisplayJobPosts() {
    const [jobPosts, setJobPosts] = useState<JobPost[]>([]);
    const { loggedId } = useAuth();

    // TO update the list every time a new jobPost is created
    useEffect(() => {
        const fetchPosts = async () => {
            try {
                if (!loggedId) return;
                const data = await getPostsByCompanyId(loggedId);
                setJobPosts(data);
            } catch (error) {
                console.error("Failed to load posts", error);
            }
        };
        
        if (loggedId) fetchPosts();
    }, [loggedId]);



    return (
        <div>
            <h1>Job Posts</h1>
            <table>
                <tbody>
                    {jobPosts.map((post) => (
                        <JobPostItem key={post.postId} jobPost={post} />
                        ))}
                </tbody>
            </table>
        </div>
    );
}