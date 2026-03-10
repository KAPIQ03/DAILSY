import apiClient from './apiClient';
import { removeAuthToken } from './auth';
import { Post, Comment, UserProfile, User } from './types';

const AUTH_API_BASE_URL = 'https://foka.wi.local:51672/Dailsy/auth';

export const registerUser = async (userData: any) => {
	const response = await fetch(`${AUTH_API_BASE_URL}/register`, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json',
		},
		body: JSON.stringify(userData),
	});
	return response.json();
};

export const loginUser = async (credentials: any) => {
	const response = await fetch(`${AUTH_API_BASE_URL}/login`, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json',
		},
		body: JSON.stringify(credentials),
	});
	return response.json();
};

export const logoutUser = async () => {
	removeAuthToken();
	return { message: 'Wylogowano pomyślnie.' };
};

export const getMyProfile = async (): Promise<UserProfile> => {
	const profile = await apiClient('/users/me', 'GET');
	const followers = await apiClient('/follows/followers', 'GET');
	const following = await apiClient('/follows/following', 'GET');

	return {
		...profile,
		followersCount: followers?.length || 0,
		followingCount: following?.length || 0,
	};
};

export const getUserProfile = async (userId: string): Promise<UserProfile> => {
	const profile = await apiClient(`/users/${userId}`, 'GET');
	const followers = await apiClient('/follows/followers', 'GET');
	const following = await apiClient('/follows/following', 'GET');

	return {
		...profile,
		followersCount: followers?.length || 0,
		followingCount: following?.length || 0,
	};
};

export const updateUserProfile = async (data: {
	username?: string;
	email?: string;
}): Promise<UserProfile> => {
	return apiClient('/users/me', 'PUT', data);
};

export const followUser = async (
	userId: string,
): Promise<{ message: string }> => {
	return apiClient(`/follows/${userId}`, 'POST');
};

export const unfollowUser = async (
	userId: string,
): Promise<{ message: string }> => {
	return apiClient(`/follows/${userId}`, 'DELETE');
};

export const getFollowers = async (userId: string): Promise<User[]> => {
	return apiClient('/follows/followers', 'GET');
};

export const getFollowing = async (userId: string): Promise<User[]> => {
	return apiClient('/follows/following', 'GET');
};

export const searchUsers = async (query: string): Promise<User[]> => {
	const result = await apiClient(
		`/users/search?query=${encodeURIComponent(query)}`,
		'GET',
	);
	return result;
};

export const getPosts = async (): Promise<Post[]> => {
	return apiClient('/posts', 'GET');
};

export const getFeedPosts = async (): Promise<Post[]> => {
	return apiClient('/posts/feed', 'GET');
};

export const createPost = async (postData: {
	content: string;
	mood: number;
}): Promise<Post> => {
	return apiClient('/posts', 'POST', postData);
};

export const getCommentsForPost = async (
	postId: string,
): Promise<Comment[]> => {
	return apiClient(`/posts/${postId}/comments`, 'GET');
};

export const addCommentToPost = async (
	postId: string,
	commentData: { content: string },
): Promise<Comment> => {
	return apiClient(`/posts/${postId}/comments`, 'POST', commentData);
};

export const toggleReaction = async (
	postId: string,
): Promise<{ reactionsCount: number; hasReacted: boolean }> => {
	const toggleResponse = await apiClient(`/posts/${postId}/reactions`, 'POST');
	const countResponse = await apiClient(
		`/posts/${postId}/reactions/count`,
		'GET',
	);

	return {
		hasReacted: toggleResponse.liked,
		reactionsCount: countResponse.count || 0,
	};
};

export const getReactionCount = async (postId: string): Promise<number> => {
	const response = await apiClient(`/posts/${postId}/reactions/count`, 'GET');
	return response.count || 0;
};
