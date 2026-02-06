import Authentication from '../components/InitialComponent';
import { authenticate } from '../api/accountApi';


export default function Login() {
    return (
        <div>
            <h1>Welcome to the Register Page</h1>
            <Authentication onSubmit={authenticate} />
        </div>
    );
}