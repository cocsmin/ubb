// backend/src/index.ts
import dotenv from 'dotenv';
dotenv.config();

import express from 'express';
import cors from 'cors';
import http from 'http';
import { Server } from 'socket.io';
import { verify } from 'jsonwebtoken';

import albumsRoutes from './routes/albums';
import authRoutes from './routes/auth';

const app = express();

app.use(cors({
    origin: 'http://localhost:8100',
    methods: ['GET', 'POST', 'PUT', 'DELETE'],
    credentials: true
}));

app.use(express.json());

app.use('/api/auth', authRoutes);
app.use('/api/albums', albumsRoutes);

const server = http.createServer(app);

const io = new Server(server, {
    cors: {
        origin: 'http://localhost:8100',
        methods: ['GET', 'POST', 'PUT', 'DELETE'],
        credentials: true
    }
});

app.set('io', io);

// socket auth middleware: client sends { auth: { token } }
io.use((socket, next) => {
    const token = socket.handshake.auth?.token;
    const JWT_SECRET = 'dsjanbdsaiob213uy1od2713921312321';
    if (!token) return next(new Error('Authentication error: token missing'));
    try {
        const payload = verify(token, JWT_SECRET) as any;
        socket.data.user = { id: payload.id, email: payload.email };
        socket.join(`user:${payload.id}`);
        next();
    } catch (err) {
        next(new Error('Authentication error: invalid token'));
    }
});

io.on('connection', (socket) => {
    console.log('Client connected:', socket.id, 'user:', socket.data.user ?? 'unknown');

    socket.on('disconnect', () => {
        console.log('Client disconnected:', socket.id);
    });
});

const PORT = process.env.PORT || 4000;
server.listen(PORT, () => {
    console.log(`Backend listening on port ${PORT}`);
});
