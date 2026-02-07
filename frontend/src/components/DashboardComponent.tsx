import { Link, useNavigate } from "react-router-dom";
import { logout as logoutApi } from "../api/accountApi"; 
import styles from "./indexComponent.module.css";
import { useAuth } from "./AuthContext";

export default function DashboardComponent() {
    const navigate = useNavigate();
    const { logout, accountType } = useAuth(); //this is Context logout and accountType
    // Boolean version of accountype
    const isUser = accountType === "USER";
    const handleLogout = async (e: React.MouseEvent) => {
        e.preventDefault();
        try {
            // clean the cookies(logoutApi) and localstorage(logout)
            await logoutApi(); 
            logout(); 
            navigate("/", {replace:true});
        } catch (err) {
            console.warn("Server logout failed", err);
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
        <button className={styles.link} onClick={handleLogout}>Logout</button>
        </div>
    );
}