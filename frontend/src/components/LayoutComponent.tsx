import FooterComponent from "./FooterComponent";
import HeaderComponent from './HeaderComponent';
import { Outlet } from 'react-router-dom';

type LayoutProps = {
  type: string;
};

export default function LayoutComponent({ type }: LayoutProps) {
  return (
    <>
      <HeaderComponent type={type} />
      <main className="main-content">
        <Outlet />
      </main>
      <FooterComponent />
    </>
  );
}
