import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import type { Account } from '../types/Account';
import styles from "./InitialComponent.module.css";
import { useAuth } from './AuthContext';

type AuthData = {
    email: string;
    password: string;
    type?: string;
}


type AuthProps = {
    onSubmit: (data: Record<string, string>) => Promise<Account>;
    mode: "register" | "login";
}

export default function Authentication({onSubmit, mode}: AuthProps) {
    const [email, setEmail] = useState<string>("");
    const [password, setPassword] = useState<string>("");
    const [type, setType] = useState<string>("USER");
    const navigate = useNavigate();

    const { login } = useAuth();

    const handleLogic = async () => {
        const payLoad: AuthData = {
            email,
            password,
            type
        };

        try {
            const data = await onSubmit(payLoad);

            login(data.type);

            navigate("/dashboard");
        } catch (error) {
            console.error("Failed", error);
        }
    };


    return (
        <div className={styles.container}>
            <input value={email} onChange={(e) => setEmail(e.target.value)} placeholder="Email" />
            <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} placeholder="Password" />
            
        {mode === "register" && (
            <select aria-label="Account type" title="Account type" value={type} onChange={(e) => setType(e.target.value)}>
                <option value="USER">User</option>
                <option value="COMPANY">Company</option>
            </select>
        )}
            
            <button onClick={handleLogic}>
                {mode === "login" ? "Login" : "Register"}
            </button>
  
        </div>
    );
}