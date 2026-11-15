import type { AuthResponse } from "../types/auth.types";

const AUTH_KEY = "auth_ls";

export const authStorage = {
  save(data: AuthResponse): void {
    localStorage.setItem(AUTH_KEY, JSON.stringify(data));
  },

  get(): AuthResponse | null {
    const storedData = localStorage.getItem(AUTH_KEY);

    if (!storedData) {
      return null;
    }

    try {
      return JSON.parse(storedData) as AuthResponse;
    } catch {
      localStorage.removeItem(AUTH_KEY);
      return null;
    }
  },

  clear(): void {
    localStorage.removeItem(AUTH_KEY);
  },
};
