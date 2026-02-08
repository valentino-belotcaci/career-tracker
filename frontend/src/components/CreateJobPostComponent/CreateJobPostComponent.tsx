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
        available: "YES",
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
    // NOTE: this is needed beacuse our useState object is composed, not simpl like we did in the authComponent
    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value, type } = e.target;

        setJobPost((prev) => ({
            // Spread the existing state so we don't lose other fields
            ...prev, 
            // check if the type of some inputs need to be change from string to number for db acquisition
            [name]: type === "number" ? Number(value) : value 
        }));
    };

    return (
        <>
        <input value={jobPost.name} name="name"  onChange={handleChange}/>
        <input value={jobPost.description} name="description" onChange={handleChange}/>
        <input value={jobPost.duration} name="duration" onChange={handleChange}/>
        <input value={jobPost.available.toString()} type="number" name="available" onChange={handleChange}/>
        <input value={jobPost.salary} name="salary" onChange={handleChange}/>

        <button onClick={handleInsertion}>Submit</button>
        </>
    )
}