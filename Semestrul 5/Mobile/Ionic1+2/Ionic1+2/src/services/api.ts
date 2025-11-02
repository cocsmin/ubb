import axios from 'axios';
import auth from "./auth";

const api = axios.create({
    baseURL: 'http://localhost:4000/api',
    timeout: 10000
});

api.interceptors.request.use((config) => {
    const token = auth.getToken();
    if (token && config.headers) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
}, (err) => Promise.reject(err));

export default api;
