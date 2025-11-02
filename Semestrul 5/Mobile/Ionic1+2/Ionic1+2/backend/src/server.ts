import express from 'express';
import http from 'http';
import { Server } from 'socket.io';
import albumsRouter from './routes/albums';
import cors from 'cors';

const app = express();

app.use(cors({
    origin: 'http://localhost:8100',
    methods: ['GET', 'POST', 'PUT', 'DELETE'],
    credentials: true
}));

app.use(express.json());

app.use('/api/albums', albumsRouter);

const server = http.createServer(app);
const io = new Server(server, {
    cors: {
        origin: 'http://localhost:8100',
        methods: ['GET', 'POST', 'PUT', 'DELETE'],
        credentials: true
    }
});

io.on('connection', (socket) => {
    console.log('Client connected:', socket.id);

    socket.on('disconnect', () => {
        });
});

const PORT = 4000;
server.listen(PORT, () => {
    });
