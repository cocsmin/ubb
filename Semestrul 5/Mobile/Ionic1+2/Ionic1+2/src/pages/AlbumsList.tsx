// src/pages/AlbumsList.tsx
import React, { useEffect, useState, useCallback, useRef } from 'react';
import {
    IonPage,
    IonContent,
    IonInfiniteScroll,
    IonInfiniteScrollContent,
    useIonViewWillEnter,
} from '@ionic/react';
import { useHistory } from 'react-router-dom';
import api from '../services/api';
import auth from '../services/auth';
import { connectSocket, getSocket } from '../services/socket';

interface Album {
    id?: number | string;
    title: string;
    artist: string;
    releaseYear?: number | null;
    genre?: string | null;
    trackCount?: number | null;
    coverUrl?: string | null;
    addedAt?: string | null;
}

type PendingItem =
    | { method: 'POST' | 'PUT'; album: any; createdAt: string }
    | { method: 'DELETE'; id: string | number; createdAt: string };

const PENDING_KEY = 'albumapp_pending';
const CACHE_KEY = 'albumapp_cache';

const HARDCODED_GENRES = [
    'Rock', 'Pop', 'Jazz', 'Electronic', 'Hip-Hop', 'Classical', 'Folk', 'Metal', 'R&B', 'Country'
];

const normalize = (a: any): Album => ({
    id: a.id ?? a._id ?? '',
    title: a.title ?? '',
    artist: a.artist ?? '',
    releaseYear: a.releaseYear !== undefined && a.releaseYear !== null ? Number(a.releaseYear) : new Date().getFullYear(),
    genre: a.genre ?? null,
    trackCount: a.trackCount !== undefined && a.trackCount !== null ? Number(a.trackCount) : 0,
    coverUrl: a.coverUrl ?? null,
    addedAt: a.addedAt ?? null
});

const AlbumsList: React.FC = () => {
    const history = useHistory();

    const [albums, setAlbums] = useState<Album[]>([]);

    const [page, setPage] = useState(1);
    const [limit] = useState(8);
    const [hasMore, setHasMore] = useState(true);
    const [loading, setLoading] = useState(false);

    const [title, setTitle] = useState('');
    const [genreFilter, setGenreFilter] = useState('');
    const [minYear, setMinYear] = useState<string>('');
    const [maxYear, setMaxYear] = useState<string>('');
    const [minTracks, setMinTracks] = useState<string>('');

    const [online, setOnline] = useState<boolean>(navigator.onLine);
    const [unsentCount, setUnsentCount] = useState(0);

    const [genreOptions, setGenreOptions] = useState<string[]>([]);

    const infiniteScrollRef = useRef<HTMLIonInfiniteScrollElement>(null);

    const [scrollKey, setScrollKey] = useState(Date.now());

    const saveCache = (arr: Album[]) => {
        try { localStorage.setItem(CACHE_KEY, JSON.stringify(arr)); } catch { /* ignore */ }
    };

    const addPending = (item: PendingItem) => {
        try {
            const current: PendingItem[] = JSON.parse(localStorage.getItem(PENDING_KEY) || '[]');
            current.push(item);
            localStorage.setItem(PENDING_KEY, JSON.stringify(current));
            setUnsentCount(current.length);
        } catch (e) {
            console.warn('Failed to add pending item', e);
        }
    };

    const sendPending = async () => {
        const queued: PendingItem[] = JSON.parse(localStorage.getItem(PENDING_KEY) || '[]');
        if (!queued.length) return;
        const remaining: PendingItem[] = [];
        let anySuccess = false;

        for (const item of queued) {
            try {
                if (item.method === 'POST') {
                    await api.post('/albums', item.album);
                    anySuccess = true;
                } else if (item.method === 'PUT') {
                    await api.put(`/albums/${item.album.id}`, item.album);
                    anySuccess = true;
                } else if (item.method === 'DELETE') {
                    await api.delete(`/albums/${item.id}`);
                    anySuccess = true;
                }
            } catch (err) {
                remaining.push(item);
            }
        }

        localStorage.setItem(PENDING_KEY, JSON.stringify(remaining));
        setUnsentCount(remaining.length);

        if (anySuccess) {
            loadPage(1, true);
        }
    };

    const updateGenreOptionsFromRaw = (raw: Album[]) => {
        const setG = new Set<string>(HARDCODED_GENRES.map(g => String(g)));
        for (const a of raw) {
            if (a.genre) setG.add(String(a.genre));
        }
        setGenreOptions(Array.from(setG).sort());
    };


    const buildParamsForPage = (p: number) => ({
        page: p,
        limit,
        search: title, // Backend se așteaptă la 'search'
        genre: genreFilter,
        minYear,
        maxYear,
        minTracks
    });

    const loadPage = async (p = 1, replace = false) => {
        if (loading) return; // Prevenim încărcările multiple
        setLoading(true);
        try {
            const params = buildParamsForPage(p);
            const resp = await api.get('/albums', { params });
            const respData = resp.data;
            const serverList: Album[] = Array.isArray(respData.data) ? respData.data.map(normalize) : [];
            const total = typeof respData.total === 'number' ? respData.total : (Array.isArray(respData.data) ? respData.data.length : 0);

            if (replace) {
                setAlbums(serverList);
                saveCache(serverList);
                setPage(p + 1);
                setScrollKey(Date.now());
            } else {
                setAlbums(prev => {
                    const existing = new Set(prev.map(a => String(a.id)));
                    const toAdd = serverList.filter(s => !existing.has(String(s.id)));
                    if (toAdd.length === 0) return prev;
                    const next = [...prev, ...toAdd];
                    saveCache(next);
                    return next;
                });
                setPage(p + 1);
            }

            try {
                const cached = JSON.parse(localStorage.getItem(CACHE_KEY) || '[]') as Album[];
                const merged = Array.isArray(cached) ? [...cached, ...serverList] : serverList;
                updateGenreOptionsFromRaw(merged);
            } catch {
                updateGenreOptionsFromRaw(serverList);
            }

            const newHasMore = (p * limit < total);
            setHasMore(newHasMore);


        } catch (err) {
            console.warn('Load page failed', err);
            setHasMore(false); // useEffect-ul se va ocupa de dezactivare


            try {
                const cached = JSON.parse(localStorage.getItem(CACHE_KEY) || '[]') as Album[];
                if (Array.isArray(cached) && cached.length) {
                    setAlbums(cached);
                    updateGenreOptionsFromRaw(cached);
                } else {
                    setAlbums([]);
                }
                setHasMore(false);
            } catch {
                setAlbums([]);
                setHasMore(false);
            }
        } finally {
            setLoading(false);
        }
    };

    const loadMore = async (event: CustomEvent<void>) => {

        try {
            // Așteptăm încărcarea paginii următoare (care este deja în starea 'page')
            await loadPage(page, false);
        } catch (err) {
            console.error('loadMore failed', err);
            // Dacă apare o eroare, oprim scroll-ul
            setHasMore(false);
        } finally {
            infiniteScrollRef.current?.complete();
        }
    };


    // Funcție pentru încărcarea datelor (cache + server)
    const loadInitialData = () => {
        const cached = localStorage.getItem(CACHE_KEY);
        if (cached) {
            try {
                const parsed = JSON.parse(cached) as Album[];
                if (Array.isArray(parsed) && parsed.length) {
                    setAlbums(parsed);
                    updateGenreOptionsFromRaw(parsed);
                }
            } catch { /* ignore */ }
        }

        // Verifică acțiunile în așteptare
        try {
            const pending = JSON.parse(localStorage.getItem(PENDING_KEY) || '[]') as PendingItem[];
            setUnsentCount(Array.isArray(pending) ? pending.length : 0);
        } catch {
            setUnsentCount(0);
        }

        if (auth.isAuthenticated() && !getSocket()?.connected) {
            connectSocket();
        }

        // Trimite acțiunile offline și reîncarcă pagina 1 de pe server
        if (navigator.onLine) {
            sendPending().finally(() => {
                loadPage(1, true);
            });
        } else {
            loadPage(1, true);
        }
    };

    useIonViewWillEnter(() => {
        loadInitialData();
    });


    useEffect(() => {
        if (auth.isAuthenticated()) connectSocket();
        const s = getSocket();

        const onCreated = () => { loadPage(1, true); };
        const onUpdated = () => { loadPage(1, true); };
        const onDeleted = () => { loadPage(1, true); };

        s?.on?.('album:created', onCreated);
        s?.on?.('album:updated', onUpdated);
        s?.on?.('album:deleted', onDeleted);

        const onOnline = () => {
            setOnline(true);
            if (auth.isAuthenticated()) connectSocket();
            sendPending(); // Trimite ce e în coadă
            loadPage(1, true); // Reîncarcă de pe server
        };
        const onOffline = () => setOnline(false);

        window.addEventListener('online', onOnline);
        window.addEventListener('offline', onOffline);

        // Funcția de cleanup
        return () => {
            window.removeEventListener('online', onOnline);
            window.removeEventListener('offline', onOffline);
            s?.off?.('album:created', onCreated);
            s?.off?.('album:updated', onUpdated);
            s?.off?.('album:deleted', onDeleted);
        };
    }, []);

    const logout = () => {
        auth.logout();
        history.replace('/login');
    };

    const deleteAlbum = async (id: string | number) => {
        if (!window.confirm('Delete this album?')) return;
        const pendingDel: PendingItem = { method: 'DELETE', id, createdAt: new Date().toISOString() };

        // Logica offline
        const localDelete = () => {
            setAlbums(prev => prev.filter(a => String(a.id) !== String(id)));
            saveCache(albums.filter(a => String(a.id) !== String(id))); // Actualizăm cache-ul
        };

        if (!online) {
            addPending(pendingDel);
            localDelete(); // Ștergem direct din UI
            alert('You are offline — the delete was queued and will be sent when online.');
            return;
        }

        try {
            await api.delete(`/albums/${id}`);
            localDelete(); // Ștergem din UI
        } catch (err) {
            console.warn('Delete failed, queueing', err);
            addPending(pendingDel);
            localDelete(); // Ștergem din UI
            alert('Delete failed — queued for retry.');
        }
    };

    const handleAdd = () => {
        history.push('/albums/new');
    };

    const doSearch = useCallback(() => {
        setPage(1); // Resetăm pagina la 1
        loadPage(1, true); // Încărcăm pagina 1 cu filtrele noi
    }, [limit, title, genreFilter, minYear, maxYear, minTracks]);

    useEffect(() => {
        const t = setTimeout(() => {
            setPage(1);
            loadPage(1, true);
        }, 450);
        return () => clearTimeout(t);
    }, [title, genreFilter, minYear, maxYear, minTracks]);

    const clearFilters = () => {
        setTitle('');
        setGenreFilter('');
        setMinYear('');
        setMaxYear('');
        setMinTracks('');
        setPage(1);
    };

    return (
        <IonPage>
            <IonContent fullscreen style={{ '--background': '#0b0c0f', padding: 12 } as React.CSSProperties}>
                <div style={{ maxWidth: 960, margin: '10px auto', color: '#e8e8e8', fontFamily: 'Inter, system-ui, -apple-system, "Helvetica Neue", Arial' }}>
                    <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', gap: 12, marginBottom: 12 }}>
                        <h1 style={{ margin: 0, fontSize: 20 }}>My Albums</h1>
                        <div style={{ display: 'flex', gap: 8, alignItems: 'center' }}>
                            <input
                                placeholder="Search title/artist..."
                                value={title}
                                onChange={e => setTitle(e.target.value)}
                                style={{ padding: 8, borderRadius: 8, border: '1px solid #2b2b2b', background: '#0f1012', color: '#fff' }}
                            />
                            {/* Restul filtrelor */}
                            <select
                                value={genreFilter}
                                onChange={e => setGenreFilter(e.target.value)}
                                style={{ padding: 8, borderRadius: 8, border: '1px solid #2b2b2b', background: '#0f1012', color: '#fff' }}
                            >
                                <option value=''>All genres</option>
                                {genreOptions.map(g => <option key={g} value={g}>{g}</option>)}
                            </select>

                            <input
                                placeholder="min year"
                                value={minYear}
                                onChange={e => setMinYear(e.target.value)}
                                style={{ width: 90, padding: 8, borderRadius: 8, border: '1px solid #2b2b2b', background: '#0f1012', color: '#fff' }}
                            />
                            <input
                                placeholder="max year"
                                value={maxYear}
                                onChange={e => setMaxYear(e.target.value)}
                                style={{ width: 90, padding: 8, borderRadius: 8, border: '1px solid #2b2b2b', background: '#0f1012', color: '#fff' }}
                            />
                            <input
                                placeholder="min tracks"
                                value={minTracks}
                                onChange={e => setMinTracks(e.target.value)}
                                style={{ width: 90, padding: 8, borderRadius: 8, border: '1px solid #2b2b2b', background: '#0f1012', color: '#fff' }}
                            />

                            {/* Indicatori Online/Offline/Unsent */}
                            <div style={{ padding: '6px 10px', borderRadius: 6, background: online ? '#1bc52e' : '#980000', color: '#0b0b0b', fontWeight: 600 }}>
                                {online ? 'Online' : 'Offline'}
                            </div>
                            {unsentCount > 0 && (
                                <div style={{ padding: '6px 10px', borderRadius: 6, background: '#ffcc00', color: '#000', fontWeight: 600 }}>
                                    {unsentCount} unsent
                                </div>
                            )}
                            <button onClick={handleAdd} style={{ background: '#2f3340', color: '#fff', padding: '8px 12px', borderRadius: 8, border: 'none' }}>+ Add</button>
                            <button onClick={clearFilters} style={{ background: 'transparent', border: '1px solid #3a3a3a', color: '#fff', padding: '8px 10px', borderRadius: 8 }}>Clear</button>
                        </div>
                    </div>

                    <div style={{ display: 'grid', gap: 12 }}>
                        {albums.length === 0 && !loading && ( // Adăugat !loading
                            <div style={{ textAlign: 'center', color: '#9e9e9e', padding: 28, borderRadius: 8 }}>
                                No albums found.
                            </div>
                        )}

                        {loading && albums.length === 0 && ( // Indicator de încărcare inițială
                            <div style={{ textAlign: 'center', color: '#9e9e9e', padding: 28, borderRadius: 8 }}>
                                Loading albums...
                            </div>
                        )}

                        {albums.map(album => (
                            <div key={String(album.id)} style={{ display: 'flex', gap: 12, alignItems: 'center', background: '#121318', padding: 12, borderRadius: 12 }}>
                                <div style={{ width: 84, height: 84, borderRadius: 8, overflow: 'hidden', background: '#1a1b1f', flexShrink: 0 }}>
                                    {album.coverUrl ? (
                                        <img src={album.coverUrl} alt={album.title || 'cover'} style={{ width: '100%', height: '100%', objectFit: 'cover' }} />
                                    ) : (
                                        <div style={{ width: '100%', height: '100%', display: 'flex', alignItems: 'center', justifyContent: 'center', color: '#7a7a7a' }}>No cover</div>
                                    )}
                                </div>

                                <div style={{ flex: 1, minWidth: 0 }}>
                                    <div style={{ display: 'flex', justifyContent: 'space-between', gap: 12 }}>
                                        <div style={{ overflow: 'hidden' }}>
                                            <div style={{ color: '#fff', fontWeight: 700, fontSize: 16, whiteSpace: 'nowrap', textOverflow: 'ellipsis', overflow: 'hidden' }}>{album.title}</div>
                                            <div style={{ color: '#bdbdbd', marginTop: 4, fontSize: 13 }}>{album.artist}</div>
                                        </div>

                                        <div style={{ textAlign: 'right', flexShrink: 0 }}>
                                            <div style={{ color: '#bdbdbd', fontSize: 13 }}>{album.releaseYear}</div>
                                            <div style={{ color: '#9e9e9e', fontSize: 12, marginTop: 6 }}>
                                                {album.addedAt ? new Date(album.addedAt).toLocaleDateString() : '—'}
                                            </div>
                                        </div>
                                    </div>

                                    <div style={{ marginTop: 8, display: 'flex', gap: 8, alignItems: 'center', flexWrap: 'wrap' }}>
                                        <div style={{ background: '#17181b', color: '#dcdcdc', padding: '6px 8px', borderRadius: 8, fontSize: 13 }}>{album.genre || 'Unknown genre'}</div>
                                        <div style={{ background: '#17181b', color: '#dcdcdc', padding: '6px 8px', borderRadius: 8, fontSize: 13 }}>{album.trackCount ?? 0} tracks</div>
                                    </div>

                                    <div style={{ marginTop: 10, display: 'flex', gap: 8 }}>
                                        <button onClick={() => history.push(`/albums/${album.id}`, { album })} style={{ background: '#3a78f1', color: '#fff', padding: '8px 10px', borderRadius: 8, border: 'none' }}> Edit </button>
                                        <button onClick={() => deleteAlbum(album.id ?? '')} style={{ background: 'transparent', border: '1px solid #3a3a3a', color: '#fff', padding: '8px 10px', borderRadius: 8 }}>Delete</button>
                                    </div>
                                </div>
                            </div>
                        ))}
                    </div>

                    <IonInfiniteScroll
                        key={scrollKey}
                        ref={infiniteScrollRef}
                        onIonInfinite={loadMore}
                        disabled={!hasMore || loading}
                    >
                        <IonInfiniteScrollContent
                            loadingSpinner="bubbles"
                            loadingText="Loading more albums..."
                        >
                        </IonInfiniteScrollContent>
                    </IonInfiniteScroll>

                    {!hasMore && !loading && albums.length > 0 && (
                        <div style={{ textAlign: 'center', marginTop: 12, color: '#9e9e9e' }}>
                            No more albums
                        </div>
                    )}


                    <div style={{ marginTop: 18, display: 'flex', justifyContent: 'center' }}>
                        <button
                            onClick={logout}
                            style={{
                                width: '100%',
                                maxWidth: 420,
                                padding: '10px 14px',
                                borderRadius: 10,
                                background: '#a04caf',
                                color: '#fff',
                                border: 'none',
                                fontWeight: 700
                            }}
                        >
                            Logout
                        </button>
                    </div>
                </div>
            </IonContent>
        </IonPage>
    );
};

export default AlbumsList;