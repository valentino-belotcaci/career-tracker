import FooterComponent from "../Footer/FooterComponent";
import HeaderComponent from '../Header/HeaderComponent';
import { Outlet } from 'react-router-dom';

export default function LayoutComponent() {
  return (
    <>
      <HeaderComponent /> {}
      <main className="main-content">
        <Outlet />
      </main>
      <FooterComponent />
    </>
  );
}
