
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
    const { dataType } = useParams<{ dataType: string }>();

    const isPost = dataType === "displayJobPosts";
    const navigate = useNavigate();

    useEffect(() => {
        const fetchPosts = async () => {
            if (!loggedId) return;
            try {
                const data = isPost 
                    ? await getPostsByCompanyId(loggedId) 
                    : await getApplicationsByUserId(loggedId); 
                setJobData(data);
            } catch (error) {
                console.error("Failed to load data", error);
            }
        };
        fetchPosts();
    }, [loggedId, isPost]);

    const handleItemClick = (id: string) => {
        const path = isPost ? "JobPost" : "JobApplication";
        navigate(`/${path}/${id}`);
    };

    return (
        <div>
            <h1>{isPost ? "Job Posts" : "My Applications"}</h1>
            <table>
                <tbody>
                    {jobData.map((item) => {
                        // Determine the ID to pass as key based on the current mode
                        const itemId = isPost 
                            ? (item as JobPost).postId 
                            : (item as JobApplication).applicationId;

                        return isPost ? (
                            <JobPostItem 
                                key={itemId}
                                onClick={() => handleItemClick(itemId)} 
                                jobPost={item as JobPost} 
                            />
                        ) : (
                            <JobApplicationItem 
                                key={itemId}
                                onClick={() => handleItemClick(itemId)} 
                                jobApplication={item as JobApplication} 
                            />
                        );
                    })}
                </tbody>
            </table>
        </div>
    );
}