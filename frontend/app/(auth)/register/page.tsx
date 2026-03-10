import Header from '@/components/Header';
import Link from 'next/link';
import RegisterForm from '@/components/RegisterForm';
import Image from 'next/image';
import logo from '@/public/Dailsy.svg';

export default function RegisterPage() {
	return (
		<div className='min-h-screen bg-gray-100 flex flex-col'>
			<Header />
			<main className='flex-grow flex items-center justify-center p-6'>
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
						Zarejestruj się w Dailsy
					</h2>
					<RegisterForm />
					<p className='text-center text-gray-600 mt-4'>
						Masz już konto?{' '}
						<Link href='/login' className='text-emerald-600 hover:underline'>
							Zaloguj się
						</Link>
					</p>
				</div>
			</main>
		</div>
	);
}
