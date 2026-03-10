import Link from 'next/link';
import Header from '../components/Header';

export default function Home() {
	return (
		<div className='min-h-screen bg-gray-100 flex flex-col'>
			<Header />
			<main className='grow flex flex-col items-center justify-center p-6 text-center'>
				<h1 className='text-5xl font-bold text-gray-800 mb-4'>
					Witaj w Dailsy!
				</h1>
				<p className='text-xl text-gray-600 mb-8 max-w-2xl'>
					Twoje miejsce do dzielenia się autentycznymi doświadczeniami i
					łączenia się z innymi.
				</p>
				<div className='flex space-x-4'>
					<Link
						href='/register'
						className='px-6 py-3 bg-emerald-600 text-white text-lg rounded-full hover:bg-emerald-700 transition-colors'>
						Zarejestruj się
					</Link>
					<Link
						href='/login'
						className='px-6 py-3 border border-emerald-600 text-emerald-600 text-lg rounded-full hover:bg-emerald-50 transition-colors'>
						Zaloguj się
					</Link>
				</div>
			</main>
			<footer className='py-4 text-center text-gray-500'>
				&copy; {new Date().getFullYear()} Dailsy. Wszelkie prawa zastrzeżone.
			</footer>
		</div>
	);
}
