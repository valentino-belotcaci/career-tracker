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

    const fetchJobPost = async () => {

        try {
            const data = await getPostById(postId);
            setJobPost(data);
        } catch (error) {
            console.error("Failed to load job post details", error);
        }
    }

    useEffect(() => {
        async () => {
            await fetchJobPost();
        }
    }, [jobPost])

    return (
        <table>
            <tbody>
                <tr>
                    <td>name: {jobPost.name}</td>
                    
                </tr>
                
            </tbody>
        </table>
    )
}