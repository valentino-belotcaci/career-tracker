import { Link } from "react-router-dom";
import { useTranslation } from "react-i18next";

// USELOCATION
export default function OnBoardingPage() {
    const { t } = useTranslation();

    return (
        <div >
            <h1>{t('welcome')}</h1>
            <div >
                <Link to="/authentication" state={"register"} >
                    {t('register')}
                </Link>
                
                <Link to="/authentication" state={"login"} >
                    {t('login')}
                </Link>
            </div>
        </div>
    );
}