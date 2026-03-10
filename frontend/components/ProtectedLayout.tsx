'use client';

import React, { useEffect } from 'react';
import { useRouter } from 'next/navigation';
import { getAuthToken } from '../lib/auth';
import Header from './Header';

export default function ProtectedLayout({
	children,
}: {
	children: React.ReactNode;
}) {
	const router = useRouter();

	useEffect(() => {
		const token = getAuthToken();
		if (!token) {
			router.push('/login');
		}
	}, [router]);

	return (
		<div className='min-h-screen bg-gray-100 flex flex-col'>
			<Header />
			<main className='grow'>{children}</main>
			<footer className='py-4 text-center text-gray-500'>
				&copy; {new Date().getFullYear()} Dailsy. Wszelkie prawa zastrzeżone.
			</footer>
		</div>
	);
}
