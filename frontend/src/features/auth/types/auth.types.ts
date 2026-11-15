export interface User {
  id: number;
  email: string;
  username: string;
}

export interface AuthResponse {
  user: User;
  token: string;
}

export interface SignInRequest {
  email: string;
  password: string;
}

export interface SignUpRequest {
  email: string;
  name: string;
  password: string;
}

export type AuthStatus =
  | "checking"
  | "authenticated"
  | "unauthenticated"
  | "error";

export interface AuthContextType {
  user: User | null;
  token: string | null;
  status: AuthStatus;
  isAuthenticated: boolean;
  signIn: (data: AuthResponse) => void;
  logout: () => void;
  refreshSession: () => Promise<void>;
}