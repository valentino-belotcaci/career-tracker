type statusTypes = 'SUBMITTED' | 'REJECTED' | 'ACCEPTED' | 'UNDER_REVIEW'


export interface JobApplication {
    applicationId: string;
    postId: string;
    userId: string;
    status: statusTypes; 
    userDescription: string;
    createdAt: string;
}