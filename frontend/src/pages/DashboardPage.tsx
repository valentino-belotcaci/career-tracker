import IndexComponent from "../components/IndexComponent";

export default function DashboardPage() {
  const type = localStorage.getItem("type") || "USER";
  return <IndexComponent type={type} />;
}