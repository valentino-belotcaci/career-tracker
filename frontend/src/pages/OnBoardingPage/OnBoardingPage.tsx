import OnBoardingComponent from '../../components/OnBoarding/OnBoardingComponent';
import { Link } from 'react-router-dom';

export default function OnBoardingPage() {

    return (
        <div >
            <OnBoardingComponent/>
            <Link to="/messages">Messages</Link>
        </div>
    );
}