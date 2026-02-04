export interface JobApplication {
    postId: string;
    userId: string;
    status: 'SUBMITTED' | 'REJECTED' | 'ACCEPTED' | 'UNDER_REVIEW';
    createdAt: string;
}