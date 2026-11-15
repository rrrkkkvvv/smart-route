import { httpClient } from "../../../shared/api/htttpClient";
import type {
  AuthResponse,
  SignInRequest,
  SignUpRequest,
} from "../types/auth.types";

export const authApi = {
  async signIn(data: SignInRequest): Promise<AuthResponse> {
    const response = await httpClient.post<AuthResponse>("/auth/signIn", data);
    return response.data;
  },

  async signUp(data: SignUpRequest): Promise<AuthResponse> {
    const response = await httpClient.post<AuthResponse>("/auth/signUp", data);
    return response.data;
  },
  async refresh(): Promise<AuthResponse> {
    const response = await httpClient.post<AuthResponse>("/auth/refresh");
    return response.data;
  },
};
