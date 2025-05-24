import axios from 'axios';
import type { APIResponse } from '../types/APIResponse';
import type { User } from '../types/User';

const USER_API_URL = 'http://localhost:8080/api/user';

const getAuthHeader = () => {
  const token = localStorage.getItem('token');
  return {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  };
};

export const userService = {
  getUserByUsername: async (username: string): Promise<APIResponse<User>> => {
    const res = await axios.get<APIResponse<User>>(
      `${USER_API_URL}?username=${username}`,
      getAuthHeader()
    );
    return res.data;
  },

  updateUser: async (username: string, user: Partial<User>): Promise<APIResponse<User>> => {
    const res = await axios.put<APIResponse<User>>(
      `${USER_API_URL}?username=${username}`,
      user,
      getAuthHeader()
    );
    return res.data;
  },

  changePassword: async (
    username: string,
    currentPassword: string,
    newPassword: string
  ): Promise<APIResponse<string>> => {
    const res = await axios.put<APIResponse<string>>(
      `${USER_API_URL}/change-password?username=${username}`,
      { currentPassword, newPassword },
      getAuthHeader()
    );
    return res.data;
  }
};
