import { Link } from 'react-router-dom';
import styles from "./OnBoardingComponent.module.css";


export default function OnBoardingComponent() {

    return (
        <div className={styles.container}>
            <h1>Welcome to the OnBoarding Page</h1>
            <Link to="/register"> Register</Link>
            <Link to="/login"> Login</Link>

        </div>
    );
}