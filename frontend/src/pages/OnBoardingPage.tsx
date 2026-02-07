import { Link } from "react-router-dom";



export default function OnBoardingPage() {
    return (
        <div>
            <h1>Welcome to the OnBoarding Page</h1>
            <Link to="register">Register</Link>
            <Link to="login">Login</Link>
        </div>
    );
}