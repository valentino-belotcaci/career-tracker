import Authentication from '../components/InitialComponent';
import { insertAccount } from '../api/accountApi';


export default function Login() {
    return (
        <div>
            <h1>Welcome to the Register Page</h1>
            <Authentication mode={"register"} onSubmit={insertAccount} />
        </div>
    );
}