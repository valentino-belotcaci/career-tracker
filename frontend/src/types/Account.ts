// The base fields common to everyone
export interface Account {
  id: number;
  email: string;

}

// User-specific fields
export interface User extends Account {
  firstName: string;
  lastName: string;
}

// Company-specific fields
export interface Company extends Account {
    companyName: string;
}