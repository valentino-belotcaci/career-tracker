import { Link } from "react-router-dom";
import styles from "./HeaderComponent.module.css";
import { Context } from "../Context";
import { useTranslation } from 'react-i18next';

export default function HeaderComponent() {
  const { t } = useTranslation();
  //check if the user is logged in by looking for the 'type' in storage
  const { accountType } = Context();

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