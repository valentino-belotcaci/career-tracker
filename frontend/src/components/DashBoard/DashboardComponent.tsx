import { Link, useNavigate } from "react-router-dom";
import { logout as logoutApi } from "../../api/accountApi"; 
import styles from "./DashBoardComponent.module.css";
import { Context } from "../Context";
import { useTranslation } from 'react-i18next';


export default function DashboardComponent() {
    const navigate = useNavigate();
    const { t } = useTranslation();
    const { logout, accountType } = Context(); //this is Context logout and accountType
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
            <h2>{isUser ? t('dashBoardUserTitle') : t('dashBoardCompanyTitle')}</h2>
        <p>
            {isUser
            ? t('dashBoardUserContent')
            : t('dashBoardCompanyContent')}
        </p>
        <Link className={styles.link} to={isUser ? "/profileUser" : "/profileCompany"}>
            {t('profileButton')}
        </Link>
        <Link className={styles.link} to={isUser ? "/display/JobApplications" : "/display/JobPosts"}>
            {isUser ? "Job Applications" : "Job Posts"}
        </Link>
        <button className={styles.link} onClick={handleLogout}>Logout</button>
        </div>
    );
}