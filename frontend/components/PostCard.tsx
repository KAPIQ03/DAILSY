'use client';

import { useState, useEffect } from 'react';
import Link from 'next/link';
import {
	Heart,
	MessageCircle,
	Angry,
	Frown,
	Meh,
	Smile,
	Laugh,
} from 'lucide-react';
import { Post } from '../lib/types';
import CommentList from './CommentList';
import CommentForm from './CommentForm';
import { toggleReaction, getReactionCount } from '../lib/api';

interface PostCardProps {
	post: Post;
}

export default function PostCard({ post }: PostCardProps) {
	const [showComments, setShowComments] = useState(false);
	const [refreshComments, setRefreshComments] = useState(false);
	const [reactionsCount, setReactionsCount] = useState('0');
	const [hasReacted, setHasReacted] = useState(post.hasReacted);

	useEffect(() => {
		const fetchReactionStatus = async () => {
			try {
				const count = await getReactionCount(post.id);
				setReactionsCount(count.toString());
			} catch (error) {
				console.error('Błąd podczas ładowania statusu reakcji:', error);
			}
		};
		fetchReactionStatus();
	}, [post.id]);

	const handleCommentAdded = () => {
		setRefreshComments(prev => !prev);
	};

	const handleToggleReaction = async () => {
		try {
			const { reactionsCount, hasReacted } = await toggleReaction(post.id);
			setReactionsCount(reactionsCount.toString());
			setHasReacted(hasReacted);
		} catch (error) {
			console.error('Błąd podczas przełączania reakcji:', error);
		}
	};

	const getMoodIcon = (moodValue: number) => {
		switch (moodValue) {
			case 1:
				return <Angry className='w-8 h-8 text-red-600' />;
			case 2:
				return <Frown className='w-8 h-8 text-orange-600' />;
			case 3:
				return <Meh className='w-8 h-8 text-yellow-600' />;
			case 4:
				return <Smile className='w-8 h-8 text-lime-600' />;
			case 5:
				return <Laugh className='w-8 h-8 text-green-600' />;
			default:
				return null;
		}
	};

	return (
		<div className='bg-white p-6 rounded-xl shadow-sm border border-gray-100 mb-6 hover:shadow-md transition-shadow'>
			<div className='flex items-center justify-between'>
				<div className='font-bold text-gray-900 text-lg'>
					{post.authorUsername}
				</div>
				<div className='inline-flex items-center'>{getMoodIcon(post.mood)}</div>
			</div>
			<div className='text-gray-400 text-sm mb-5'>
				{new Date(post.createdAt).toLocaleString()}
			</div>
			<p className='text-gray-800 mb-4 text-base leading-relaxed'>
				{post.content}
			</p>

			<div className='mt-2 pt-4 border-t border-gray-300 flex justify-between items-center'>
				<button
					onClick={handleToggleReaction}
					className='flex items-center space-x-2 cursor-pointer group focus:outline-none'>
					<Heart
						className={`w-6 h-6 ${hasReacted ? 'text-red-500 transition-transform transform group-active:scale-95' : 'text-gray-400 hover:text-red-500 transition-colors'}`}
					/>
					<span className='text-gray-500'>{reactionsCount}</span>
				</button>
				<button
					onClick={() => setShowComments(!showComments)}
					className='flex items-center space-x-2 group focus:outline-none text-gray-500 hover:text-emerald-600 transition-colors'>
					<MessageCircle className='w-6 h-6 group-hover:stroke-emerald-600' />
					<span className='font-medium group-hover:text-emerald-600'>
						{showComments ? 'Ukryj' : 'Komentarze'}
					</span>
				</button>
			</div>

			{showComments && (
				<div className='mt-4 animate-in fade-in slide-in-from-top-2 duration-200'>
					<CommentList
						postId={post.id}
						key={refreshComments ? 'refresh' : 'no-refresh'}
					/>
					<CommentForm postId={post.id} onCommentAdded={handleCommentAdded} />
				</div>
			)}
		</div>
	);
}
