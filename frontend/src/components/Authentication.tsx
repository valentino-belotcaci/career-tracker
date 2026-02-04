import { useEffect, useState } from "react";
import { authenticate } from "../api/accountApi"; // Import your API call
import { type Account } from "../types/Account";

export default Authentication() {
    const [email, setEmail] = useState<string>("");
    const [password, setPassword] = useState<string>("");

    Map<string, string> authMap = new Map<string, string>();
    authMap.set("email", email);
    authMap.set("password", password);

    useEffect(() => {
        authenticate().then(data => setAccount(data));
    }, []); // Empty array means "run once on load"

    return (
        <div>
            <h2>Account Email: {account?.email}</h2>
        </div>
    );
}

