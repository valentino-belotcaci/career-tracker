import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import type { Account } from '../types/Account';

type AuthProps = {
    onSubmit: (data: Record<string, string>) => Promise<Account>;
}

export default function Authentication({onSubmit}: AuthProps) {
    const [email, setEmail] = useState<string>("");
    const [password, setPassword] = useState<string>("");
    const [type, setType] = useState<string>("");
    const navigate = useNavigate();


    const handleLogin = async () => {
        const loginData = {
            email: email,
            password: password,
            type?: type
        
        };

        try {
            const data = await onSubmit(loginData);

            if (data) {
                navigate(data.type === "USER" ? "/indexUser" : "/indexCompany");
            }
        } catch (error) {
            console.error("Failed", error);}
        

    }


    return (
        <div>
            <input value={email} onChange={(e) => setEmail(e.target.value)} placeholder="Email" />
            <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} placeholder="Password" />
            <button onClick={handleLogin}>Login</button>

        </div>
    );
}