import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import type { Account } from '../../types/Account';
import styles from "./AuthComponent.module.css";
import { Context } from '../Context';
import { useTranslation } from 'react-i18next';


type AuthData = {
    email: string;
    password: string;
    type?: string;
}


type AuthProps = {
    onSubmit: (data: Record<string, string>) => Promise<Account>;
    mode: "register" | "login";
}

export default function AuthComponent({onSubmit, mode}: AuthProps) {
    const [email, setEmail] = useState<string>("");
    const [password, setPassword] = useState<string>("");
    const [type, setType] = useState<string>("USER");

    const { t } = useTranslation();
    const navigate = useNavigate();

    const { login } = Context();

    const isRegister = mode === "register"; 

    const handleLogic = async () => {
        const payLoad: AuthData = {
            email,
            password,
            type
        };

        try {
            const data = await onSubmit(payLoad);
            // use type and id to be set in localstorage and context
            login(data.type, data.id);

            navigate("/dashboard");
        } catch (error) {
            console.error("Failed", error);
        }
    };


    return (
        
        <div className={styles.container}>
            <h1>{isRegister ? t('registerContent') : t('loginContent')}</h1> 
            <input value={email} onChange={(e) => setEmail(e.target.value)} placeholder="Email" />
            <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} placeholder="Password" />
            
        {isRegister && (
            <select aria-label="Account type" title="Account type" value={type} onChange={(e) => setType(e.target.value)}>
                <option value="USER">{t('userOption')}</option>
                <option value="COMPANY">{t('companyOption')}</option>
            </select>
        )}
            
            <button onClick={handleLogic}>
                {isRegister ? t('registerButton') : t('loginButton')}
            </button>
  
        </div>
    );
}