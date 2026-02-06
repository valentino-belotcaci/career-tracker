import { Link } from "react-router-dom";


export default function HeaderComponent(type: string) {
    return (
        <header>
            <Link to={type === "USER" ? "/indexUser" : "/indexCompany"}>
                    Career Tracker </Link>        
        </header>
    );
}