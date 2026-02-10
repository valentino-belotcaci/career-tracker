
import {type JobPost} from "../../types/JobPost";
import { getPostsByCompanyId } from '../../api/jobPostApi';
import JobPostItem from './JobPostItem';
import { Context } from '../Context';
import { useEffect, useState } from "react";
import { useNavigate } from 'react-router-dom';



export default function DisplayJobPosts() {
    const [jobPosts, setJobPosts] = useState<JobPost[]>([]);
    const { loggedId } = Context();
    const navigate = useNavigate();

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

    const handlePostClick = (postId: string) => {
        navigate(`/jobPostDetails/${postId}`);
    }


    return (
        <div>
            <h1>Job Posts</h1>
            <table>
                <tbody>
                    {jobPosts.map((post) => (
                        <JobPostItem onClick={() => handlePostClick(post.postId)} jobPost={post} />
                        ))}
                </tbody>
            </table>
        </div>
    );
}