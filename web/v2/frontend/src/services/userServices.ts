import type { User } from '../types/User';
import type { APIResponse } from '../types/APIResponse';
import { axiosInstance } from '../utils/axiosInstance';

const USER_API_URL = 'http://localhost:8080/api/user'; 

export const userService = {
  getUser: async (userId: string | undefined): Promise<User> => {
    const res = await axiosInstance.get<APIResponse<User>>(`${USER_API_URL}?id=${userId}`);
    return res.data.data;
  },
  updateUser : async (updatedUser : User): Promise<User> => {
    const res = await axiosInstance.put<APIResponse<User>>(`/user?username=${updatedUser.username}`, updatedUser);
    return res.data.data;
  },

  changePassword : async (username : string, currentPassword : string, newPassword : string) : Promise<string> => {
    const res = await axiosInstance.put<APIResponse<string>>(`/user/change-password?username=${username}`, {currentPassword , newPassword});
    return res.data.data;
  }
};