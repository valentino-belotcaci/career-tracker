import { createContext, useContext, useState, type ReactNode } from "react";

type AuthContextType = {
  accountType: string | null;
  loggedId: string | null;
  login: (type: string, loggedId: string) => void;
  logout: () => void;
};

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export function AuthProvider({ children }: { children: ReactNode }) {
  const [accountType, setAccountType] = useState<string | null>(localStorage.getItem("type"));
  const [loggedId, setLoggedId] = useState<string | null>(localStorage.getItem("loggedId"));
  
  const login = (type: string, loggedId: string) => {
    localStorage.setItem("type", type);
    localStorage.setItem("loggedId",loggedId);
    setAccountType(type);
    setLoggedId(loggedId);
  };

  const logout = () => {
    localStorage.removeItem("type");
    localStorage.removeItem("loggedId");
    setAccountType(null);
    setLoggedId(null);
  };


  return (
    <AuthContext.Provider value={{ accountType, loggedId, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
}

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) throw new Error("useAuth must be used within AuthProvider");
  return context;
};