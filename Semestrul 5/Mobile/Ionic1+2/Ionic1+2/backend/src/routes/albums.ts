import { Router } from 'express';
import db from '../db/sqlite';
import { requireAuth, AuthRequest } from '../middleware/authMiddleware';
import { Request, Response } from 'express';

const router = Router();

router.get('/', requireAuth, (req: AuthRequest, res: Response) => {
    const page = Math.max(1, Number(req.query.page || 1));
    const limit = Math.max(2, Number(req.query.limit || 10));

    const search = (req.query.search || '').toString(); // 'search' va fi pentru titlu/artist
    const genre = (req.query.genre || '').toString();
    const minYear = Number(req.query.minYear || 0);
    const maxYear = Number(req.query.maxYear || 0);
    const minTracks = Number(req.query.minTracks || 0);

    const offset = (page - 1) * limit;
    try {
        const userId = req.user!.id;
        let where = 'WHERE user_id = ?';
        const params: any[] = [userId];

        if (search) {
            where += ' AND (title LIKE ? OR artist LIKE ?)';
            params.push(`%${search}%`, `%${search}%`);
        }
        if (genre) {
            where += ' AND genre = ?';
            params.push(genre);
        }
        if (minYear > 0) {
            where += ' AND releaseYear >= ?';
            params.push(minYear);
        }
        if (maxYear > 0) {
            where += ' AND releaseYear <= ?';
            params.push(maxYear);
        }
        if (minTracks > 0) {
            where += ' AND trackCount >= ?';
            params.push(minTracks);
        }

        const totalRow = db.prepare(`SELECT COUNT(*) as cnt FROM albums ${where}`).get(...params);
        const total = totalRow?.cnt ?? 0;

        const data = db.prepare(`
            SELECT * FROM albums
                              ${where}
            ORDER BY addedAt DESC
                LIMIT ? OFFSET ?
        `).all(...params, limit, offset);

        res.json({ data, page, limit, total });
    } catch (err: unknown) {
        const msg = err instanceof Error ? err.message : String(err);
        res.status(500).json({ error: msg });
    }
});

router.get('/:id', requireAuth, (req: AuthRequest, res: Response) => {
    const id = Number(req.params.id);
    const userId = req.user!.id;
    try {
        const album = db.prepare('SELECT * FROM albums WHERE id = ? AND user_id = ?').get(id, userId);
        if (!album) return res.status(404).json({ error: 'Not found' });
        res.json(album);
    } catch (err: unknown) {
        const msg = err instanceof Error ? err.message : String(err);
        res.status(500).json({ error: msg });
    }
});

router.post('/', requireAuth, (req: AuthRequest, res: Response) => {
    try {
        const userId = req.user!.id;
        const { title, artist, releaseYear, genre, trackCount, coverUrl } = req.body;
        const addedAt = new Date().toISOString();
        const info = db.prepare(`
            INSERT INTO albums (title, artist, releaseYear, genre, trackCount, coverUrl, addedAt, user_id)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        `).run(title, artist, releaseYear || null, genre || null, trackCount || null, coverUrl || null, addedAt, userId);

        const album = db.prepare('SELECT * FROM albums WHERE id = ?').get(info.lastInsertRowid);

        // emit event only to that user's room
        req.app.get('io')?.to(`user:${userId}`).emit('album:created', album);

        res.json(album);
    } catch (err: unknown) {
        const msg = err instanceof Error ? err.message : String(err);
        res.status(500).json({ error: msg });
    }
});

router.put('/:id', requireAuth, (req: AuthRequest, res: Response) => {
    const id = Number(req.params.id);
    const userId = req.user!.id;
    try {
        const { title, artist, releaseYear, genre, trackCount, coverUrl } = req.body;
        // ensure belongs to user
        const existing = db.prepare('SELECT * FROM albums WHERE id = ? AND user_id = ?').get(id, userId);
        if (!existing) return res.status(404).json({ error: 'Not found or not allowed' });

        db.prepare('UPDATE albums SET title=?, artist=?, releaseYear=?, genre=?, trackCount=?, coverUrl=? WHERE id=?')
            .run(title, artist, releaseYear || null, genre || null, trackCount || null, coverUrl || null, id);

        const album = db.prepare('SELECT * FROM albums WHERE id=?').get(id);
        req.app.get('io')?.to(`user:${userId}`).emit('album:updated', album);
        res.json(album);
    } catch (err: unknown) {
        const msg = err instanceof Error ? err.message : String(err);
        res.status(500).json({ error: msg });
    }
});

router.delete('/:id', requireAuth, (req: AuthRequest, res: Response) => {
    const id = Number(req.params.id);
    const userId = req.user!.id;
    try {
        const existing = db.prepare('SELECT * FROM albums WHERE id = ? AND user_id = ?').get(id, userId);
        if (!existing) return res.status(404).json({ error: 'Not found or not allowed' });

        db.prepare('DELETE FROM albums WHERE id = ?').run(id);
        req.app.get('io')?.to(`user:${userId}`).emit('album:deleted', { id });
        res.json({ success: true });
    } catch (err: unknown) {
        const msg = err instanceof Error ? err.message : String(err);
        res.status(500).json({ error: msg });
    }
});

export default router;
