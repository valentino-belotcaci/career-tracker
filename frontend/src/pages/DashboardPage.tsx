import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import IndexComponent from "../components/IndexComponent";

export default function DashboardPage() {
    const navigate = useNavigate();
    const type = localStorage.getItem("type");

    useEffect(() => {
        if (!type) {
            navigate("/"); 
        }
    }, [type, navigate]);

    if (!type) return null;

    return <IndexComponent type={type} />;
}