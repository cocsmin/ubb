import { io } from 'socket.io-client';
import auth from "./auth";

let socket: any = null;

export function connectSocket() {
    const token = auth.getToken();
    if (!token) return null;
    if (!navigator.onLine) return null;
    socket = io('http://localhost:4000', {
        auth: { token },
        transports: ['websocket', 'polling']
    });
    socket.on('connect', () => console.log('Connected to WebSocket server', socket.id));
    socket.on('connect_error', (err: any) => console.warn('Socket connect_error', err.message));
    return socket;
}

export function getSocket() {
    return socket;
}
