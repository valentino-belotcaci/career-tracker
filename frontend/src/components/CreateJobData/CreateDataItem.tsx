import type { JobApplication } from "../../types/JobApplication"
import type { JobPost } from "../../types/JobPost"


export const CreateJobPostItem = ({jobPost, changeFunction} : {jobPost : JobPost, changeFunction: (e: React.ChangeEvent<HTMLInputElement>) => void}) => {
    return (
        <>
        <input value={jobPost.name} name="name"  onChange={changeFunction}/>
        <input value={jobPost.description} name="description" onChange={changeFunction}/>
        <input value={jobPost.duration} name="duration" onChange={changeFunction}/>
        <input value={jobPost.available}  name="available" onChange={changeFunction}/>
        <input value={jobPost.salary} name="salary" type="number" onChange={changeFunction}/>

        
        </>
    )
}


export const CreateJobApplicationItem = ({jobApplication, changeFunction} : {jobApplication : JobApplication, changeFunction: (e: React.ChangeEvent<HTMLInputElement>) => void}) => {
    return (
        <>
        <input value={jobApplication.userDescription} name="userDescription" onChange={changeFunction}/>        
        </>
    )
}