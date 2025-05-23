export interface APIResponse<T> {
  status: boolean;
  statusCode: number;
  info: string;
  data: T;
}