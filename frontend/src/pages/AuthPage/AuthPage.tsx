import AuthComponent from '../../components/Auth/AuthComponent';
import { authenticate, insertAccount } from '../../api/accountApi';
import {  useParams } from 'react-router-dom';

export default function AuthPage() {
    const {type} = useParams<{type: string}>();
    const isRegister = type === "register";
    // Set default type to login if actually type is login or undefined
    const modeName = (type === "register" ? "register" : "login") as "register" | "login";
    return (
        <div>
            <AuthComponent mode={modeName} onSubmit={isRegister? insertAccount: authenticate} />
        </div>
    );
}