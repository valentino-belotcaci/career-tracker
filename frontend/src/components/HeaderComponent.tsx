import { Link } from "react-router-dom";
import styles from "./HeaderComponent.module.css";
import { useAuth } from "./AuthContext";
import { useTranslation } from 'react-i18next';

export default function HeaderComponent() {
  const { t } = useTranslation();
  //check if the user is logged in by looking for the 'type' in storage
  const { accountType } = useAuth();

  return (
    <header className={styles.header}>
      {/*if userType exists link to /dashboard. 
          if userType is null link to / .
      */}
      <Link className={styles.logo} to={accountType ? "/dashboard" : "/"}>
       {t('appTitle')}
      </Link>
    </header>
  );
}