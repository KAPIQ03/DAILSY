'use client';

import { useState, useEffect } from 'react';
import { Search, UserPlus, UserMinus, Loader2 } from 'lucide-react';
import { searchUsers, followUser, unfollowUser, getMyProfile } from '@/lib/api';
import { User, UserProfile } from '@/lib/types';
import ProtectedLayout from '@/components/ProtectedLayout';

export default function SearchPage() {
	const [query, setQuery] = useState('');
	const [results, setResults] = useState<User[]>([]);
	const [isLoading, setIsLoading] = useState(false);
	const [error, setError] = useState<string | null>(null);
	const [currentUser, setCurrentUser] = useState<UserProfile | null>(null);

	useEffect(() => {
		const fetchCurrentUser = async () => {
			try {
				const profile = await getMyProfile();
				setCurrentUser(profile);
			} catch (err) {
				console.error('Błąd podczas pobierania profilu:', err);
			}
		};
		fetchCurrentUser();
	}, []);

	const handleSearch = async (e: React.FormEvent) => {
		e.preventDefault();
		if (!query.trim()) return;

		setIsLoading(true);
		setError(null);
		try {
			const data = await searchUsers(query);
			const filteredResults = data.filter(user => user.id !== currentUser?.id);
			setResults(filteredResults);
		} catch (err) {
			setError('Nie udało się pobrać wyników wyszukiwania.');
			console.error(err);
		} finally {
			setIsLoading(false);
		}
	};

	const handleFollowToggle = async (user: User) => {
		setResults(prev =>
			prev.map(u =>
				u.id === user.id ? { ...u, following: !u.following } : u
			)
		);

		try {
			if (user.following) {
				await unfollowUser(user.id);
			} else {
				await followUser(user.id);
			}
		} catch (err) {
			console.error('Błąd podczas zmiany statusu obserwowania:', err);
			setResults(prev =>
				prev.map(u =>
					u.id === user.id ? { ...u, following: !u.following } : u
				)
			);
		}
	};

	return (
		<ProtectedLayout>
			<div className='max-w-2xl mx-auto mt-8 p-4'>
				<h1 className='text-2xl font-bold mb-6 text-gray-800'>
					Znajdź znajomych
				</h1>

				<form onSubmit={handleSearch} className='relative mb-8'>
					<input
						type='text'
						placeholder='Wyszukaj użytkownika...'
						value={query}
						onChange={e => setQuery(e.target.value)}
						className='w-full pl-14 pr-4 py-3 rounded-full bg-white border border-gray-200 focus:border-gray-400 transition-all outline-none text-lg'
					/>
					<Search className='absolute left-4 top-1/2 -translate-y-1/2 text-gray-300 w-6 h-6' />
					<button
						type='submit'
						className='absolute right-2 top-1/2 -translate-y-1/2 bg-emerald-600 text-white px-6 py-2 rounded-full hover:bg-emerald-700 transition-colors font-medium cursor-pointer'>
						Szukaj
					</button>
				</form>

				{isLoading && (
					<div className='flex justify-center my-8'>
						<Loader2 className='w-8 h-8 animate-spin text-emerald-600' />
					</div>
				)}

				{error && (
					<div className='bg-red-50 text-red-600 p-4 rounded-xl mb-6'>
						{error}
					</div>
				)}

				<div className='space-y-4'>
					{!isLoading && results.length === 0 && (
						<p className='text-center text-gray-500 py-8'>
							Nie znaleziono użytkowników spełniających kryteria.
						</p>
					)}

					{results.map(user => (
						<div
							key={user.id}
							className='flex items-center justify-between p-4 bg-white rounded-2xl shadow-sm border border-gray-100 hover:border-emerald-200 transition-colors'>
							<div className='flex items-center space-x-4'>
								<div className='w-12 h-12 bg-emerald-100 rounded-full flex items-center justify-center text-emerald-700 font-bold text-xl'>
									{user.username.charAt(0).toUpperCase()}
								</div>
								<div>
									<h3 className='font-semibold text-gray-900'>
										{user.username}
									</h3>
									<p className='text-sm text-gray-500'>{user.email}</p>
								</div>
							</div>

							<button
								onClick={() => handleFollowToggle(user)}
								className={`flex items-center space-x-2 px-4 py-2 rounded-full transition-all font-medium ${
									user.following
										? 'bg-gray-100 text-gray-700 hover:bg-red-50 hover:text-red-600'
										: 'bg-emerald-50 text-emerald-700 hover:bg-emerald-100'
								}`}>
								{user.following ? (
									<>
										<UserMinus className='w-4 h-4' />
										<span>Obserwujesz</span>
									</>
								) : (
									<>
										<UserPlus className='w-4 h-4' />
										<span>Obserwuj</span>
									</>
								)}
							</button>
						</div>
					))}
				</div>
			</div>
		</ProtectedLayout>
	);
}
