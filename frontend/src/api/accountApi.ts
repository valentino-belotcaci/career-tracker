import {client} from "./client";
import { type Account } from "../types/Account";
import { type UpdateAccountDTO } from '../types/updateAccountDTO';



export const getAllAccounts = async (): Promise<Account[]> => {
  const response = await client.get<Account[]>('/Account/getAllAccounts');
  return response.data;
};

// Use a plain object for JSON bodies (axios won't JSON.stringify a Map automatically)
export const insertAccount = async (body: Record<string, string>): Promise<Account> => {
  const response = await client.post<Account>('/Account/insertAccount', body);
  return response.data;
}

export const authenticate = async (body: Record<string, string>): Promise<Account> => {
  const response = await client.post<Account>(`/Account/authenticate`, body);
  return response.data;
}


export const getAccountById = async (id: string): Promise<Account> => {
  const response = await client.get<Account>(`/Account/getAccountById/${id}`);
  return response.data;
};

export const getAccountByEmail = async (email: string): Promise<Account> => {
  const response = await client.get<Account>(`/Account/getAccountByEmail/${email}`);
  return response.data;
};

export const deleteAccount = async (id: string): Promise<void> => {
  await client.delete<void>(`/Account/deleteAccount/${id}`);
};

export const deleteAll = async (): Promise<void> => {
  await client.delete<void>(`/Account/deleteAll`);
}

export const logout = async (): Promise<Record<string, string>> => {
  const response = await client.post<Record<string, string>>(`/Account/logout`);
  return response.data;
}

export const updateAccount = async (id: string, body: UpdateAccountDTO): Promise<Account> => {
  const response = await client.put<Account>(`/Account/updateAccount/${id}`, body);
  return response.data;
}