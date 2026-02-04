import client from "./client";
import {type Account} from "../types/Account";

export const getAccountById = async (id: number) => {
  const response = await client.get<Account>(`/Account/getAccountById/${id}`);
  return response.data;
};