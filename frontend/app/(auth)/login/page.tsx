'use client';

import { useEffect, useState } from 'react';
import Header from '@/components/Header';
import Link from 'next/link';
import LoginForm from '@/components/LoginForm';
import Image from 'next/image';
import logo from '@/public/Dailsy.svg';

export default function LoginPage() {
	const [registrationSuccessMessage, setRegistrationSuccessMessage] = useState<
		string | null
	>(null);

	useEffect(() => {
		if (typeof window !== 'undefined') {
			const message = sessionStorage.getItem('registrationSuccess');
			if (message) {
				setRegistrationSuccessMessage(message);
				sessionStorage.removeItem('registrationSuccess');
			}
		}
	}, []);

	return (
		<div className='min-h-screen bg-gray-100 flex flex-col'>
			<Header />
			<main className='grow flex items-center justify-center p-6'>
				<div className='bg-white p-8 rounded-lg shadow-md w-full max-w-md'>
					<div>
						<Image
							src={logo}
							alt='Dailsy Logo'
							width={60}
							height={60}
							className='mx-auto mb-4'
						/>
					</div>
					<h2 className='text-3xl font-bold text-gray-800 mb-6 text-center'>
						Zaloguj się do Dailsy
					</h2>
					{registrationSuccessMessage && (
						<div
							className='bg-green-100 border border-green-400 text-green-700 px-4 py-3 rounded relative mb-4'
							role='alert'>
							<span className='block sm:inline'>
								{registrationSuccessMessage}
							</span>
						</div>
					)}
					<LoginForm />
					<p className='text-center text-gray-600 mt-4'>
						Nie masz konta?{' '}
						<Link href='/register' className='text-emerald-600 hover:underline'>
							Zarejestruj się
						</Link>
					</p>
				</div>
			</main>
		</div>
	);
}
