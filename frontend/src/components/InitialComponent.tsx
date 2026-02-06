import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import type { Account } from '../types/Account';

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
    const [type, setType] = useState<string>("");
    const navigate = useNavigate();


    const handleLogin = async () => {
        const accountData: AuthData = {
            email,
            password,
            type 
        };

        try {
            const data = await onSubmit(accountData);

            
            navigate(data.type === "USER" ? "/indexUser" : "/indexCompany");
            
        } catch (error) {
            console.error("Failed", error);}
        

    }


    return (
        <div>
            <input value={email} onChange={(e) => setEmail(e.target.value)} placeholder="Email" />
            <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} placeholder="Password" />
            
        {mode === "register" && (
            <select aria-label="Account type" title="Account type" value={type} onChange={(e) => setType(e.target.value)}>
                <option value="USER">User</option>
                <option value="COMPANY">Company</option>
            </select>
        )}
            
            <button onClick={handleLogin}>Login</button>

            

        </div>
    );
}