import type { Metadata } from 'next';
import './globals.css';

export const metadata: Metadata = {
	title: 'Dailsy',
	description:
		'Aplikacja stworzona na potrzeby projektu z przedmiotu Programowanie Aplikacji Webowych',
	icons: {
		icon: 'http://foka.wi.local/~s51672/PAW/projekt/frontend/public/Dailsy.svg',
		apple:
			'http://foka.wi.local/~s51672/PAW/projekt/frontend/public/Dailsy.svg',
	},
};

export default function RootLayout({
	children,
}: Readonly<{
	children: React.ReactNode;
}>) {
	return (
		<html lang='pl'>
			<body>{children}</body>
		</html>
	);
}
