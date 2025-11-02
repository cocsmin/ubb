import Database from 'better-sqlite3';
import path from 'path';

const dbFile = path.join(__dirname, 'albums.db');
const db = new Database(dbFile);

// users table
db.prepare(`
    CREATE TABLE IF NOT EXISTS users (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        email TEXT UNIQUE,
        passwordHash TEXT,
        name TEXT
    )
`).run();

// albums table: ensure user_id column exists; if table not present, create with user_id
db.prepare(`
    CREATE TABLE IF NOT EXISTS albums (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        title TEXT,
        artist TEXT,
        releaseYear INTEGER,
        genre TEXT,
        trackCount INTEGER,
        coverUrl TEXT,
        addedAt TEXT,
        user_id INTEGER
    )
`).run();

// If older DB without column user_id - try to add (safe)
try {
    const cols = db.prepare("PRAGMA table_info('albums')").all();
    const hasUserId = cols.some((c: any) => c.name === 'user_id');
    if (!hasUserId) {
        db.prepare('ALTER TABLE albums ADD COLUMN user_id INTEGER').run();
    }
} catch (e) {
    // ignore
}

export default db;
