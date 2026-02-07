import i18n from 'i18next';
import { initReactI18next } from 'react-i18next';
import LanguageDetector from 'i18next-browser-languagedetector';


const resources = {
  en: {
    translation: {
    appTitle: "Career Tracker",
    welcome: "Welcome to Career Tracker",
    loginButton: "Login",
    registerContent: "Welcome to the registration page",
    loginContent: "Welcome to the login page",
    userOption: "User",
    companyOption: "Company",
    registerButton: "Register",
    logoutButton: "Logout",
    dashboard: "Dashboard",
    profileButton: "View Profile",
    dashBoardUserTitle: "User Dashboard",
    dashBoardCompanyTitle: "Company Dashboard",
    dashBoardUserContent: "Welcome, user. Use the navigation to manage your data.",
    dashBoardCompanyContent: "Welcome, company. Use the navigation to manage your company profile.",
    footerContent: "© 2026 CareerTracker"
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