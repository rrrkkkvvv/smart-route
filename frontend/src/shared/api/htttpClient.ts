import axios from "axios";
import { authStorage } from "../../features/auth/utils/authStorage";

export const httpClient = axios.create({
  baseURL: import.meta.env.VITE_API_URL,
  timeout: 15000,
  headers: {
    "Content-Type": "application/json",
  },
});

httpClient.interceptors.request.use((config) => {
  const authData = authStorage.get();

  if (authData?.token) {
    config.headers.Authorization = `Bearer ${authData.token}`;
  }

  return config;
});