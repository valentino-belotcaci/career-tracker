import { useEffect, useState } from "react";
import { getAccountById } from "../api/accountApi"; // Import your API call
import { type Account } from "../types/Account";

export default function Account() {
    const [account, setAccount] = useState<Account | null>(null);

    // 2. Use useEffect to trigger the fetch
    useEffect(() => {
        getAccountById("a0435947-a730-46ff-8a11-25c9c8938b81").then(data => setAccount(data));
    }, []); // Empty array means "run once on load"

    return (
        <div>
            <h2>Account Email: {account?.email}</h2>
        </div>
    );
}