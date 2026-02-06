import { Link } from "react-router-dom";
import styles from "./HeaderComponent.module.css";

export default function HeaderComponent({ type }: { type: string }) {
  return (
    <header className={styles.header}>
      <Link className={styles.logo} to="./dashboard">
        Career Tracker
      </Link>
    </header>
  );
}
