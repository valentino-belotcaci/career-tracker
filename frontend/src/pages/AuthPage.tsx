import AuthComponent from '../components/Auth/AuthComponent';
import { authenticate, insertAccount } from '../api/accountApi';
import { useLocation } from 'react-router-dom';

// USELOCATION
export default function AuthPage() {
    const location = useLocation();
    const type = location.state;
    const isRegister = type === "register";
    return (
        <div>
            <AuthComponent mode={type} onSubmit={isRegister? insertAccount: authenticate} />
        </div>
    );
}