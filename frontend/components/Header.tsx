'use client';

import { useState, useEffect } from 'react';
import Link from 'next/link';
import Image from 'next/image';
import logo from '@/public/Dailsy.svg';
import { useRouter } from 'next/navigation';
import { Home, Search, User, LogOut } from 'lucide-react';
import { getAuthToken } from '../lib/auth';
import { logoutUser } from '../lib/api';

export default function Header() {
	const [isAuthenticated, setIsAuthenticated] = useState(false);
	const router = useRouter();

	useEffect(() => {
		setIsAuthenticated(!!getAuthToken());
	}, []);

	const handleLogout = async () => {
		await logoutUser();
		setIsAuthenticated(false);
		router.push('/login');
	};

	if (isAuthenticated) {
		return (
			<header className='py-2 px-6 bg-white shadow-sm border-b border-gray-100 sticky top-0 z-50'>
				<nav className='grid grid-cols-3 items-center gap-4'>
					<div className='flex items-center'>
						<Link
							href='/search'
							className='text-gray-500 hover:text-emerald-600 transition-colors'
							title='Szukaj'>
							<Search className='w-6 h-6' />
						</Link>
					</div>
					<div className='flex items-center justify-center'>
						<Link
							href='/dashboard'
							className='flex items-center hover:scale-110 hover:rotate-360 transition-transform'>
							<Image
								src={logo}
								alt='Dailsy Logo'
								width={45}
								height={45}
							/>
						</Link>
					</div>
					<div className='flex items-center justify-end space-x-4'>
						<Link
							href='/profile'
							className='text-gray-500 hover:text-emerald-600 transition-colors'
							title='Mój Profil'>
							<User className='w-6 h-6' />
						</Link>
						<button
							onClick={handleLogout}
							className='text-gray-500 hover:text-red-500 transition-colors cursor-pointer'
							title='Wyloguj'>
							<LogOut className='w-6 h-6' />
						</button>
					</div>
				</nav>
			</header>
		);
	}
}
