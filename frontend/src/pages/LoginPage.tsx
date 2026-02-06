import Authentication from '../components/InitialComponent';
import { authenticate } from '../api/accountApi';


export default function Login() {
    return (
        <div>
            <h1>Welcome to the Login Page</h1>
            <Authentication mode={"login"} onSubmit={authenticate} />
        </div>
    );
}