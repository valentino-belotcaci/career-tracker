import { useState, useEffect } from 'react';
import type { JobPost } from "../../types/JobPost";
import { getPostById } from "../../api/jobPostApi";
import { Context } from '../Context';



export default function JobPostDetailsComponent({postId} : {postId: string}) {
    
    const { loggedId } = Context();
    
    const [jobPost, setJobPost] = useState<Partial<JobPost>>({
        companyId: loggedId ?? "",
        name: "",
        description: "",
        duration: "",
        available: "",
        salary: 0,
    });

    useEffect(() => {
        // If the given postId changes, refetch the data
        const fetchJobPost = async () => {
            try {
                const data = await getPostById(postId);
                setJobPost(data);
            } catch (error) {
                console.error("Failed to load job post details", error);
            }
        };

        // Actual function call only happens if the postId is present
        if (postId) {
            fetchJobPost();
        }
    }, [postId]);

    return (
        <table>
            <tbody>
                <tr>
                    <td> name: {jobPost.name}</td>
                    <td>id: {jobPost.companyId}</td>
                    <td>duration: {jobPost.duration}</td>
                    <td>salary: {jobPost.salary}</td>
                    <td>created at: {jobPost.createdAt}</td>
                    <td>available: {jobPost.available}</td>
                    <td>description: {jobPost.description}</td>
                    
                </tr>
                
            </tbody>
        </table>
    )
}