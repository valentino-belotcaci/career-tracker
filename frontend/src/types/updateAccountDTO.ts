export interface UpdateAccountDTO {
    email: string;
    password: string;
    description?: string; 

    //user fields
    firstName?: string;
    lastName?: string;

    //company fields
    companyName?: string;
    city?: string;
    street?: string;
    number?: string;
}
