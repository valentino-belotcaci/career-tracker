import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import DashboardComponent from "../../components/DashBoard/DashboardComponent";
import { Context } from "../../components/Context";
export default function DashboardPage() {
    const navigate = useNavigate();
    const {accountType} = Context();

    useEffect(() => {
        if (!accountType) {
            navigate("/"); 
        }
    }, [accountType, navigate]);

    if (!accountType) return null;

    return <DashboardComponent />;
}