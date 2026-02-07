import i18n from 'i18next';
import { initReactI18next } from 'react-i18next';
import LanguageDetector from 'i18next-browser-languagedetector';


const resources = {
  en: {
    translation: {
      login: "Login",
      register: "Register",
      dashboard: "Dashboard",
      logout: "Logout",
      dashBoardUser: "Welcome, user. Use the navigation to manage your data.",
      dashBoardCompany: "Welcome, company. Use the navigation to manage your company profile."

    }
  }
};

i18n
  .use(LanguageDetector) // Automatically detects user browser language
  .use(initReactI18next) // Passes i18n down to react-i18next
  .init({
    resources,
    fallbackLng: 'en', // Use English if the user language isn't available
    interpolation: {
      escapeValue: false // React already escapes values to prevent XSS
    }
  });

export default i18n;