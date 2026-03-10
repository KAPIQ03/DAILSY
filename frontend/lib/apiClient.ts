import { getAuthToken, removeAuthToken } from './auth';

// const API_BASE_URL = "http://localhost:8080";
const API_BASE_URL = 'https://foka.wi.local:51672/Dailsy';

const apiClient = async (
	endpoint: string,
	method: string = 'GET',
	data: any = null,
) => {
	const token = getAuthToken();
	const headers: HeadersInit = {
		'Content-Type': 'application/json',
	};

	if (token) {
		headers['Authorization'] = `Bearer ${token}`;
	}

	const config: RequestInit = {
		method,
		headers,
	};

	if (data) {
		config.body = JSON.stringify(data);
	}

	try {
		const response = await fetch(`${API_BASE_URL}${endpoint}`, config);

		if (response.status === 401) {
			removeAuthToken();
			window.location.href = '/login';
			throw new Error('Sesja wygasła, proszę zalogować się ponownie.');
		}

		if (!response.ok) {
			const errorData = await response.json().catch(() => ({}));
			throw new Error(errorData.message || `Błąd HTTP: ${response.status}`);
		}

		const text = await response.text();
		return text ? JSON.parse(text) : {};
	} catch (error: any) {
		throw new Error(error.message || 'Błąd sieci lub serwera.');
	}
};

export default apiClient;
