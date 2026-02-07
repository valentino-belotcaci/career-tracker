import AuthComponent from '../components/AuthComponent';
import { insertAccount } from '../api/accountApi';
import { useLocation } from 'react-router-dom';

// USELOCATION
export default function AuthPage() {
    const location = useLocation();
    const type = location.state;
    const isRegister = type === "register";
    return (
        <div>
            <h1>{isRegister ? "Welcome to the registration page" : "Welcome to the login page"}</h1>
            <AuthComponent mode={type} onSubmit={insertAccount} />
        </div>
    );
}