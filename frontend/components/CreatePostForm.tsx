'use client';

import { useState } from 'react';
import { createPost } from '../lib/api';
import { Angry, Frown, Meh, Smile, Laugh } from 'lucide-react';

interface CreatePostFormProps {
	onPostCreated: () => void;
}

export default function CreatePostForm({ onPostCreated }: CreatePostFormProps) {
	const [content, setContent] = useState('');
	const [mood, setMood] = useState<number | null>(null); // No default mood
	const [loading, setLoading] = useState(false);
	const [error, setError] = useState<string | null>(null);

	const MOODS = [
		{ value: 1, icon: Angry, color: 'red' },
		{ value: 2, icon: Frown, color: 'orange' },
		{ value: 3, icon: Meh, color: 'yellow' },
		{ value: 4, icon: Smile, color: 'lime' },
		{ value: 5, icon: Laugh, color: 'green' },
	];

	const handleSubmit = async (e: React.FormEvent) => {
		e.preventDefault();
		setError(null);

		if (!mood) {
			setError('Proszę wybrać nastrój!');
			return;
		}

		setLoading(true);

		try {
			await createPost({ content, mood });
			setContent('');
			setMood(null);
			onPostCreated();
		} catch (err: any) {
			setError(err.message || 'Błąd podczas tworzenia posta.');
		} finally {
			setLoading(false);
		}
	};

	return (
		<form
			onSubmit={handleSubmit}
			className='bg-white p-6 rounded-xl shadow-sm border border-gray-100 mb-6'>
			{error && <p className='text-red-500 text-sm mb-4'>{error}</p>}
			<textarea
				className='w-full p-3 border border-gray-200 rounded-lg mb-4 focus:outline-none focus:ring-2 focus:ring-emerald-500 focus:border-transparent text-gray-900 placeholder-gray-400 transition-all resize-none'
				placeholder='Jak się dzisiaj czujesz?'
				value={content}
				onChange={e => setContent(e.target.value)}
				rows={3}
				required></textarea>
			<span className='text-sm items-center font-medium text-gray-500 mb-1 pl-2 block'>
				Oceń swój nastrój:
			</span>

			<div className='flex items-center justify-evenly w-full gap-2 mb-4'>
				{MOODS.map(m => (
					<button
						key={m.value}
						type='button'
						onClick={() => setMood(m.value)}
						className={`w-10 h-10 flex items-center justify-center rounded-full transition-all duration-200 ${
							mood === m.value
								? `scale-110 bg-${m.color}-100 ring-2 ring-${m.color}-600 shadow-sm text-${m.color}-600`
								: `hover:scale-110 hover:bg-gray-50 text-gray-400 hover:text-${m.color}-600`
						}`}
						title={`Nastrój: ${m.value}`}>
						<m.icon className='w-6 h-6' />
					</button>
				))}
			</div>
			<button
				type='submit'
				className='w-full py-2.5 px-4 rounded-lg shadow-sm text-sm font-semibold text-white bg-emerald-600 hover:bg-emerald-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-emerald-500 transition-colors disabled:opacity-50 disabled:cursor-not-allowed'
				disabled={loading || !mood}>
				{loading
					? 'Publikowanie...'
					: !mood
						? 'Wybierz nastrój'
						: 'Opublikuj wpis'}
			</button>
		</form>
	);
}
