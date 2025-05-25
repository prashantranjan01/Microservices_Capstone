export interface User {
  id: string;
  firstname: string | null;
  lastname: string | null;
  address: string | null;
  email: string;
  username: string;
  roleId: number | null;
}