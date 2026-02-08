import { Link } from 'react-router-dom';
import styles from "./OnBoardingComponent.module.css";
import { useTranslation } from "react-i18next";


export default function OnBoardingComponent() {
      const { t } = useTranslation();

    return (
        <div className={styles.container}>
            <h1>{t('welcome')}</h1>
            <div>
                <Link to="/authentication/register" >
                    {t('register')}
                </Link>
                
                <Link to="/authentication/login">
                    {t('login')}
                </Link>
            </div>
        </div>
    );
}