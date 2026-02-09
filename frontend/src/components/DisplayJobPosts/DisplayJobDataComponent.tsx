
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
    const [jobPosts, setJobPosts] = useState<JobPost[]>([]); 
    const [jobApplications, setJobApplications] = useState<JobApplication[]>([]); 
    const { loggedId } = Context();
    const { dataType } = useParams<{ dataType: string }>();

    const isPost = dataType === "displayJobPosts";
    const navigate = useNavigate();

    useEffect(() => {
        const fetchData = async () => {
            if (!loggedId) return;
            try {
                if (isPost) {
                    const posts = await getPostsByCompanyId(loggedId);
                    setJobPosts(posts);
                } else {
                    const applications = await getApplicationsByUserId(loggedId);
                    setJobApplications(applications);
                }
            } catch (error) {
                console.error("Failed to load data", error);
            }
        };
        
        fetchData();
    }, [loggedId, isPost]);

    const handlePostClick = (id: string, path: string) => {
        navigate(`/${path}/${id}`);
    }

    return (
        <div>
            <h1>{isPost ? "Job Posts" : "My Applications"}</h1>
            <table>
                <tbody>
                    {isPost 
                        ? jobPosts.map((post) => (
                            <JobPostItem 
                                key={post.postId} // Added key for React list optimization
                                onClick={() => handlePostClick(post.postId, "JobPostDetails")} 
                                jobPost={post} 
                            />
                        ))
                        : jobApplications.map((app) => (
                            <JobApplicationItem 
                                key={app.applicationId} // Assuming applicationId exists
                                onClick={() => handlePostClick(app.applicationId, "JobApplicationDetails")} 
                                jobApplication={app} 
                            />
                        ))
                    }
                </tbody>
            </table>
        </div>
    );
}