import { client } from './client';
import { type JobPost } from '../types/JobPost';


export const getAllPosts = async () : (Promise<JobPost[]>) => {
    const response = await client.get<JobPost[]>(`/JobPost/getAllPosts`);
    return response.data;
}

export const insertPost = async (body: JobPost) : (Promise<JobPost>) => {
    const response = await client.post(`/JobPost/insertPost`, body);
    return response.data;
}

export const deletePost = async (id: string) : (Promise<void>) => {
    await client.delete(`/JobPost/deletePost/${id}`);
}

export const deleteAll = async () : (Promise<void>) => {
    await client.delete<void>(`/JobPost/deleteAll`);
}

export const getPostById = async (id: string) : (Promise<JobPost>) => {
    const response = await client.get<JobPost>(`/JobPost/getPostById/${id}`);
    return response.data;
}

export const getPostsByCompanyId = async (companyId: string) : (Promise<JobPost[]>) => {
    const response = await client.get<JobPost[]>(`/JobPost/getPostsByCompanyId/${companyId}`);
    return response.data;
}

export const updatePost = async (id: string, body: JobPost) : (Promise<JobPost>) => {
    const response = await client.put<JobPost>(`/JobPost/updatePost/${id}`, body);
    return response.data;
}   