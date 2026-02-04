import {client} from "./client";
import {type Account} from "../types/Account";

// Basic method that takes (id: number) and returns (Promise<Account>)
export const getAccountById = async (id: string) : (Promise<Account>) => {
  const response = await client.get<Account>(`/Account/getAccountById/${id}`);
  return response.data;
};