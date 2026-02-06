import { Link, useNavigate } from "react-router-dom";
import { logout as logoutApi } from "../api/accountApi"; 
import styles from "./indexComponent.module.css";
import { useAuth } from "./AuthContext";

export default function IndexComponent({ type }: { type: string }) {
    const isUser = type === "USER";
    const navigate = useNavigate();
    const { logout } = useAuth(); //this is  Context logout

    const handleLogout = async (e: React.MouseEvent) => {
        e.preventDefault();
        try {
            //call the API first
            await logoutApi(); 
        } catch (err) {
            console.warn("Server logout failed", err);
        } finally {
            //then clear the local React state
            logout(); 
            navigate("/", { replace: true });
        }
    };

    return (
        <div className={styles.container}>
            <h2>{isUser ? "User Dashboard" : "Company Dashboard"}</h2>
        <p>
            {isUser
            ? "Welcome, user. Use the navigation to manage your data."
            : "Welcome, company. Use the navigation to manage your company profile."}
        </p>
        <Link className={styles.link} to={isUser ? "/profileUser" : "/profileCompany"}>
            View profile
        </Link>
        <Link className={styles.link} to={isUser ? "/jobPosts" : "/jobApplications"}>
            {isUser ? "Job Applications" : "Job Posts"}
        </Link>
        <button className={styles.link} onClick={handleLogout} style={{cursor: 'pointer'}}>Logout</button>
        </div>
    );
}