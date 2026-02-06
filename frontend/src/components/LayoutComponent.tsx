import FooterComponent from "./FooterComponent";
import HeaderComponent from './HeaderComponent';
import { Outlet } from 'react-router-dom';

type props = {
    type: string;
}


export default function LayoutComponent({type}: props) {
    return (
        <>
            <HeaderComponent type={type}/>
            <Outlet/>
            <FooterComponent/>
        </>
    );
}