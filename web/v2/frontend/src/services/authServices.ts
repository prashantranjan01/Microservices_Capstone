import type { AuthResponse } from '../types/Auth/AuthResponse';
import type { User } from '../types/User';
import type { APIResponse } from '../types/APIResponse';
import { axiosInstance } from '../utils/axiosInstance';

const AUTH_API_URL = 'http://localhost:8080/api/auth'; 

export const authService = {
  login: async (username: string, password: string): Promise<AuthResponse> => {
    const res = await axiosInstance.post<APIResponse<AuthResponse>>(`${AUTH_API_URL}/login?username=${username}&password=${password}`);
    return res.data.data;
  },

  register: async (firstName: string, lastName : string, email: string, username: string, password: string): Promise<AuthResponse> => {
    const user = {
      firstName,
      lastName,
      email,
      username,
      password,
    }
    const res = await axiosInstance.post<APIResponse<AuthResponse>>(`${AUTH_API_URL}/register` , user);
    return res.data.data;
  },

  logout: () => {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
  },

  saveSession: (token: string, user: User) => {
    localStorage.setItem('token', token);
    localStorage.setItem('user', JSON.stringify(user));
  },

  getToken: (): string | null => localStorage.getItem('token'),

  getUser: (): User | null => {
    const user = localStorage.getItem('user');
    return user ? JSON.parse(user) : null;
  },

  isAuthenticated: (): boolean => !!localStorage.getItem('token'),
};