import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { authenticate } from "../api/accountApi"; 



export default function Authentication() {
    const [email, setEmail] = useState<string>("");
    const [password, setPassword] = useState<string>("");
    const navigate = useNavigate();


    const handleLogin = async () => {
        const loginData = {
            email: email,
            password: password
        };

        try {
            const data = await authenticate(loginData);
            console.log("Authentication data:", data);
            if (data) {
                if (data.type === "USER") navigate("/indexUser");
                if (data.type === "COMPANY") navigate("/indexCompany");
            } 
                
        } catch (error) {
            console.error("Login failed", error);
        }
    };


    return (
        <div>
            <input value={email} onChange={(e) => setEmail(e.target.value)} placeholder="Email" />
            <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} placeholder="Password" />
            <button onClick={handleLogin}>Login</button>

        </div>
    );
}