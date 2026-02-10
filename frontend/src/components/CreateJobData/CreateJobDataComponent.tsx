import { useState } from 'react';
import {type JobPost} from "../../types/JobPost";
import { useNavigate, useParams } from 'react-router-dom';
import { insertPost } from '../../api/jobPostApi';
import { type JobApplication } from '../../types/JobApplication';
import { insertApplication } from '../../api/jobApplicationApi';
import { CreateJobPostItem, CreateJobApplicationItem } from './CreateDataItem';

import { Context } from '../Context'; // Make sure to import your Context

export default function CreateJobDataComponent() {
    const { dataType } = useParams();
    const { loggedId } = Context(); // Get the ID from Context
    
    const isPost = dataType === "JobPost";
    const [jobData, setJobData] = useState<Partial<JobPost | JobApplication>>({});    
    const navigate = useNavigate();

    const handleInsertion = async () => {
        try {
            if (!jobData || !loggedId) return;
            const payload = isPost 
                ? { ...jobData, companyId: loggedId } as JobPost
                : { ...jobData, userId: loggedId } as JobApplication;

            if (isPost) {
                await insertPost(payload as JobPost);
                navigate("/display/JobPost");
            } else {
                await insertApplication(payload as JobApplication);
                navigate("/display/JobApplication");
            }
        } catch (error) {
            console.error("Error inserting data:", error);
        }
    }

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value, type } = e.target;
        setJobData((prev) => ({
            ...prev, 
            [name]: type === "number" ? Number(value) : value 
        }));
    };

    return (
        <>
            <h1>{isPost ? "Create Post" : "Submit Application"}</h1>
            {isPost ? (
                <CreateJobPostItem jobPost={jobData as JobPost} changeFunction={handleChange}/>
            ) : (
                <CreateJobApplicationItem jobApplication={jobData as JobApplication} changeFunction={handleChange}/>
            )}
            <button onClick={handleInsertion}>Submit</button>
        </>
    )
}