import { client } from './client';
import { type JobApplication } from '../types/JobApplication';

export const getAllApplications = async () : (Promise<JobApplication[]>) => {
    const response = await client.get<JobApplication[]>(`/JobApplication/getAllApplications`);
    return response.data;
}

export const insertApplication = async (body: JobApplication) : (Promise<JobApplication>) => {
    const response = await client.post(`/JobApplication/insertApplication`, body);
    return response.data;
}

export const deleteApplication = async (id: string) : (Promise<void>) => {
    await client.delete(`/JobApplication/deleteApplication/${id}`);
} 

export const deleteAll = async () : (Promise<void>) => {
    await client.delete<void>(`/JobApplication/deleteAll`);
}

export const getApplicationById = async (id: string) : (Promise<JobApplication>) => {
    const response = await client.get<JobApplication>(`/JobApplication/getApplicationById/${id}`);
    return response.data;
}

export const getApplicationsByPostId = async (postId: string) : (Promise<JobApplication[]>) => {
    const response = await client.get<JobApplication[]>(`/JobApplication/getApplicationsByPostId/${postId}`);
    return response.data;
}

export const getApplicationsByUserId = async (userId: string) : (Promise<JobApplication[]>) => {
    const response = await client.get<JobApplication[]>(`/JobApplication/getApplicationsByUserId/${userId}`);
    return response.data;
}

export const getApplicationByIds = async (postId: string, userId: string) : (Promise<JobApplication>) => {
    const response = await client.get<JobApplication>(`/JobApplication/getApplicationByIds?postId=${postId}&userId=${userId}`);
    return response.data;
}

export const updateApplication = async (id: string, body: JobApplication) : (Promise<JobApplication>) => {
    const response = await client.put<JobApplication>(`/JobApplication/updateApplication/${id}`, body);
    return response.data;
}

