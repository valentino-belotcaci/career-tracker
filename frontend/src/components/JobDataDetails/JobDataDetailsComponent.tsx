import { useState, useEffect } from 'react';
import type { JobPost } from "../../types/JobPost";
import { getPostById } from "../../api/jobPostApi";
import { Context } from '../Context';
import { JobPostDetailsView, JobApplicationDetailsView } from './JobDataDetailsItem';
import { type JobApplication } from '../../types/JobApplication';
import { useParams } from 'react-router-dom';
import { getApplicationById } from '../../api/jobApplicationApi';


export default function JobDataDetailsComponent({ jobId }: { jobId: string }) {
    const { loggedId } = Context();
    const { jobType } = useParams<{ jobType: string }>(); // Best practice to type the params
    const isPost = jobType === "JobPost";

    const [jobData, setJobData] = useState<Partial<JobPost & JobApplication>>({
        companyId: loggedId ?? "",
    });

    useEffect(() => {
        const fetchData = async () => {
            try {
                const data = isPost 
                    ? await getPostById(jobId) 
                    : await getApplicationById(jobId);
                setJobData(data);
            } catch (error) {
                console.error("Failed to load details", error);
            }
        };

        if (jobId) fetchData();
    }, [jobId, isPost]); 

    return (
        <table>
            <tbody>
                <tr>
                    {isPost ? (
                        <JobPostDetailsView data={jobData as JobPost} />
                    ) : (
                        <JobApplicationDetailsView data={jobData as JobApplication} />
                    )}
                </tr>
            </tbody>
        </table>
    );
}