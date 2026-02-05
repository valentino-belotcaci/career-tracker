// The base account fields
export interface Account {
    id: number;
    email: string;
    password: string;
    description: string;
}

// User fields
export interface User extends Account {
  firstName: string;
  lastName: string;
}

// Company fields
export interface Company extends Account {
    companyName: string;
    city: string;
    street: string;
    number: string;
}

