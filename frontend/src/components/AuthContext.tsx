import { createContext, useContext, useState, type ReactNode } from "react";

type AuthContextType = {
  userType: string | null;
  login: (type: string) => void;
  logout: () => void;
};

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export function AuthProvider({ children }: { children: ReactNode }) {
  const [userType, setUserType] = useState<string | null>(localStorage.getItem("type"));

  const login = (type: string) => {
    localStorage.setItem("type", type);
    setUserType(type);
  };

  const logout = () => {
    localStorage.removeItem("type");
    setUserType(null);
  };

  return (
    <AuthContext.Provider value={{ userType, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
}

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) throw new Error("useAuth must be used within AuthProvider");
  return context;
};