import FooterComponent from "./FooterComponent";
import HeaderComponent from './HeaderComponent';
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
