
import { type JobPost } from '../../types/JobPost';
import { getPostsByCompanyId } from '../../api/jobPostApi';
import JobPostItem from './JobPostItem';
import { Context } from '../Context';
import { useEffect, useState } from "react";
import { useNavigate, useParams } from 'react-router-dom';
import { getApplicationsByUserId } from "../../api/jobApplicationApi";
import JobApplicationItem from './JobApplicationItem';
import { type JobApplication } from '../../types/JobApplication';



export default function DisplayJobPosts() {
    const [jobData, setJobData] = useState<JobPost[] | JobApplication[]>([]);
    const { loggedId } = Context();
    const { dataType } = useParams<string>();

    const isPost = dataType === "displayJobPosts";

    const jobPostUrl = "JobPostDetails";
    const jobApplicationUrl = "JobApplicationDetails";


    const navigate = useNavigate();

    // TO update the list every time a new jobPost is created
    useEffect(() => {
        const fetchPosts = async () => {
            try {
                if (!loggedId) return;
                const data = isPost ? await getPostsByCompanyId(loggedId) : await getApplicationsByUserId(loggedId); 
                setJobData(data);
            } catch (error) {
                console.error("Failed to load posts", error);
            }
        };
        
        if (loggedId) fetchPosts();
    }, [loggedId]);

    const handlePostClick = (postId: string, pageType: string) => {
        navigate(`/${pageType}/${postId}`);
    }

    return (
        <div>
            <h1>Job Posts</h1>
            <table>
                <tbody>
                    {jobData.map((data) => (
                        isPost ?
                        <JobPostItem onClick={() => handlePostClick(data.postId, jobPostUrl)} jobPost={data as JobPost} />
                        :
                        <JobApplicationItem onClick={() => handlePostClick(data.postId, jobApplicationUrl)} jobApplication={data as JobApplication} />
                        ))}

                </tbody>
            </table>
        </div>
    );
}