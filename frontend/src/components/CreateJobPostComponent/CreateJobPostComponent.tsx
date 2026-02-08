import { useState } from 'react';
import {type JobPost} from "../../types/JobPost";
import { useNavigate } from 'react-router-dom';
import { insertPost } from '../../api/jobPostApi';
import { useAuth } from '../AuthContext';


export default function CreateJobPostComponent() {

    const {loggedId} = useAuth();

    const [jobPost, setJobPost] = useState<JobPost>({
        postId: "",
        companyId: loggedId ?? "",
        name: "",
        description: "",
        duration: "",
        salary: 0,
        available: true,
        createdAt: new Date().toISOString()
    });    
    const navigate = useNavigate();


    // Logic that handles actual insertion of payload in the db
    const handleInsertion = async () => {
        try {
            if (!jobPost) return null;
            await insertPost(jobPost);
            navigate("/displayJobPosts");
        } catch (error) {
            console.error("Error inserting job post:", error);
        }
    }

    // Logic that handles the string changes in the input fields and synchonizes the payload
    const handleChange = () => {
        
    }

    return (
        <>
        <input value={jobPost.name} onChange={}>Job title:</input>
        <input value={jobPost.description} onChange={}>description:</input>
        <input value={jobPost.duration} onChange={}>Duration:</input>
        <input value={jobPost.available.toString()} onChange={}>Available:</input>
        <input value={jobPost.salary} onChange={}>Salary:</input>

        <button onClick={handleInsertion}>Submit</button>
        </>
    )
}