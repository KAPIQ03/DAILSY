export interface User {
	id: string;
	username: string;
	email: string;
	following?: boolean;
}

export interface UserProfile extends User {
	followersCount: number;
	followingCount: number;
	following?: boolean;
}

export interface Comment {
	id: string;
	content: string;
	createdAt: string;
	authorId: string;
	authorUsername: string;
}

export interface Post {
	id: string;
	content: string;
	mood: number;
	createdAt: string;
	authorId: string;
	authorUsername: string;
	hasReacted: boolean;
}
