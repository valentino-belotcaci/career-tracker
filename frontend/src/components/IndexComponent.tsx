import { Link } from "react-router-dom";
import styles from "./indexComponent.module.css";

export default function IndexComponent({ type }: { type: string }) {
  const isUser = type === "USER";

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
      <Link className={styles.link} to="/">
        Logout
      </Link>
    </div>
  );
}
