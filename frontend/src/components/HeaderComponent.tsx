import { Link } from "react-router-dom";
import styles from "./HeaderComponent.module.css";
import { useAuth } from "./AuthContext";

export default function HeaderComponent() {
  //check if the user is logged in by looking for the 'type' in storage
  const { userType } = useAuth();

  return (
    <header className={styles.header}>
      {/*if userType exists link to /dashboard. 
          if userType is null link to / .
      */}
      <Link className={styles.logo} to={userType ? "/dashboard" : "/"}>
        Career Tracker
      </Link>
    </header>
  );
}