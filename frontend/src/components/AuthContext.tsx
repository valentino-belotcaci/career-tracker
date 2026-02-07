import { createContext, useContext, useState, type ReactNode } from "react";

type AuthContextType = {
  accountType: string | null;
  login: (type: string) => void;
  logout: () => void;
};

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export function AuthProvider({ children }: { children: ReactNode }) {
  const [accountType, setAccountType] = useState<string | null>(localStorage.getItem("type"));

  const login = (type: string) => {
    localStorage.setItem("type", type);
    setAccountType(type);
  };

  const logout = () => {
    localStorage.removeItem("type");
    setAccountType(null);
  };

  return (
    <AuthContext.Provider value={{ accountType, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
}

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) throw new Error("useAuth must be used within AuthProvider");
  return context;
};