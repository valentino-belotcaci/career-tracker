export interface JobApplication {
    applicationId: string;
    postId: string;
    userId: string;
    status: 'SUBMITTED' | 'REJECTED' | 'ACCEPTED' | 'UNDER_REVIEW';
    userDescription: string;
    createdAt: string;
}