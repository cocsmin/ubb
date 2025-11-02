import api from './api';

export interface User {
    id: number;
    email: string;
    name?: string;
}

const TOKEN_KEY = 'albumapp_token';
const USER_KEY = 'albumapp_user';

export default {
    async login(email: string, password: string) {
        const resp = await api.post('/auth/login', { email, password });
        const { token, user } = resp.data;
        localStorage.setItem(TOKEN_KEY, token);
        localStorage.setItem(USER_KEY, JSON.stringify(user));
        return user;
    },
    async register(email: string, password: string, name?: string) {
        const resp = await api.post('/auth/register', { email, password, name });
        const { token, user } = resp.data;
        localStorage.setItem(TOKEN_KEY, token);
        localStorage.setItem(USER_KEY, JSON.stringify(user));
        return user;
    },
    logout() {
        localStorage.removeItem(TOKEN_KEY);
        localStorage.removeItem(USER_KEY);
    },
    getToken(): string | null {
        return localStorage.getItem(TOKEN_KEY);
    },
    getUser(): User | null {
        const s = localStorage.getItem(USER_KEY);
        return s ? JSON.parse(s) : null;
    },
    isAuthenticated(): boolean {
        return !!localStorage.getItem(TOKEN_KEY);
    }
};
