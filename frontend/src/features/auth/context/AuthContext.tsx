import {
  createContext,
  useCallback,
  useEffect,
  useMemo,
  useState,
  type ReactNode,
} from "react";

import type {
  AuthContextType,
  AuthResponse,
  AuthStatus,
  User,
} from "../types/auth.types";

import { authStorage } from "../utils/authStorage";
import { authApi } from "../services/authApi";

import axios from "axios";

export const AuthContext = createContext<AuthContextType | null>(null);

interface AuthProviderProps {
  children: ReactNode;
}

export function AuthProvider({ children }: AuthProviderProps) {
  const [user, setUser] = useState<User | null>(null);
  const [token, setToken] = useState<string | null>(null);
  const [status, setStatus] = useState<AuthStatus>("checking");

  const applyAuth = useCallback((data: AuthResponse) => {
    authStorage.save(data);

    setUser(data.user);
    setToken(data.token);
    setStatus("authenticated");
  }, []);

  const logout = useCallback(() => {
    authStorage.clear();

    setUser(null);
    setToken(null);
    setStatus("unauthenticated");
  }, []);

  const refreshSession = useCallback(async () => {
    const storedAuth = authStorage.get();

    if (!storedAuth?.token) {
      logout();
      return;
    }

    setStatus("checking");

    try {
      const refreshedAuth = await authApi.refresh();

      applyAuth(refreshedAuth);
    } catch (error) {
      if (axios.isAxiosError(error) && error.response?.status === 403) {
        logout();
        return;
      }

      setStatus("error");
    }
  }, [applyAuth, logout]);

  useEffect(() => {
    void refreshSession();
  }, [refreshSession]);

  const signIn = useCallback(
    (data: AuthResponse) => {
      applyAuth(data);
    },
    [applyAuth],
  );

  const isAuthenticated = status === "authenticated";

  const value = useMemo<AuthContextType>(
    () => ({
      user,
      token,
      status,
      isAuthenticated,
      signIn,
      logout,
      refreshSession,
    }),
    [user, token, status, isAuthenticated, signIn, logout, refreshSession],
  );

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}
