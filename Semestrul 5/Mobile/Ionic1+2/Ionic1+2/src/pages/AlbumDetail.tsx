import React, { useEffect, useState } from 'react';
import { IonPage, IonContent } from '@ionic/react';
import { useParams, useHistory, useLocation } from 'react-router-dom';
import api from '../services/api';

interface Album {
    id?: string;
    title: string;
    artist: string;
    releaseYear: number;
    genre?: string;
    trackCount?: number;
    coverUrl?: string;
    addedAt?: string;
}

const defaultAlbum = (): Album => ({
    title: '',
    artist: '',
    releaseYear: new Date().getFullYear(),
    genre: '',
    trackCount: 0,
    coverUrl: '',
    addedAt: undefined
});

// Local storage
const PENDING_KEY = 'albumapp_pending';
const CACHE_KEY = 'albumapp_cache';

type PendingItem =
    | { method: 'POST' | 'PUT'; album: any; createdAt: string }
    | { method: 'DELETE'; id: string | number; createdAt: string };

const makeTempId = () => `tmp_${Date.now()}_${Math.floor(Math.random() * 10000)}`;

function addPendingItem(item: PendingItem) {
    try {
        const cur: PendingItem[] = JSON.parse(localStorage.getItem(PENDING_KEY) || '[]');
        cur.push(item);
        localStorage.setItem(PENDING_KEY, JSON.stringify(cur));
    } catch (e) {
        console.warn('Failed to add pending item', e);
    }
}

function updateLocalCacheAfterCreateOrUpdate(album: any, isCreate = true, tempId?: string) {
    try {
        const raw = localStorage.getItem(CACHE_KEY);
        const arr = raw ? JSON.parse(raw) : [];
        if (isCreate) {
            const entry = tempId ? { ...album, id: tempId, addedAt: new Date().toISOString() } : album;
            const idx = arr.findIndex((a: any) => String(a.id) === String(entry.id));
            if (idx >= 0) {
                arr[idx] = entry;
            } else {
                arr.unshift(entry);
            }
        } else {
            const idx = arr.findIndex((a: any) => String(a.id) === String(album.id));
            if (idx >= 0) arr[idx] = { ...arr[idx], ...album };
            else arr.unshift(album);
        }
        localStorage.setItem(CACHE_KEY, JSON.stringify(arr));
    } catch (e) {
        console.warn('Failed to update local cache', e);
    }
}

function removeFromLocalCacheById(id: string | number) {
    try {
        const raw = localStorage.getItem(CACHE_KEY);
        const arr = raw ? JSON.parse(raw) : [];
        const next = arr.filter((a: any) => String(a.id) !== String(id));
        localStorage.setItem(CACHE_KEY, JSON.stringify(next));
    } catch (e) {
        console.warn('Failed to remove from local cache', e);
    }
}

const AlbumDetail: React.FC = () => {
    const { id } = useParams<{ id?: string }>();
    const history = useHistory();
    const location = useLocation();

    const [album, setAlbum] = useState<Album>(defaultAlbum());
    const [loading, setLoading] = useState<boolean>(false);

    const [yearInput, setYearInput] = useState<string>(String(defaultAlbum().releaseYear));

    useEffect(() => {
        setYearInput(String(album.releaseYear ?? new Date().getFullYear()));
    }, [album.releaseYear]);

    useEffect(() => {
        let cancelled = false;

        setAlbum(defaultAlbum());

        // folosim location.state doar daca e la fel cu idu
        const stateAlbum = (location.state as any)?.album as Album | undefined;
        if (stateAlbum && String(stateAlbum.id) === String(id)) {
            setAlbum({
                ...stateAlbum,
                releaseYear: stateAlbum.releaseYear !== undefined ? Number(stateAlbum.releaseYear) : new Date().getFullYear(),
                trackCount: stateAlbum.trackCount !== undefined ? Number(stateAlbum.trackCount) : 0,
                id: stateAlbum.id ? String(stateAlbum.id) : stateAlbum.id
            });
            return;
        }

        if (!id || id === 'new') return;

        const fetchAlbum = async () => {
            setLoading(true);
            try {
                const res = await api.get(`/albums/${id}`);
                if (cancelled) return;
                if (res.data) {
                    const a = res.data;
                    setAlbum({
                        id: String(a.id ?? a._id ?? id),
                        title: a.title ?? '',
                        artist: a.artist ?? '',
                        releaseYear: a.releaseYear !== undefined && a.releaseYear !== null ? Number(a.releaseYear) : new Date().getFullYear(),
                        genre: a.genre ?? '',
                        trackCount: a.trackCount !== undefined && a.trackCount !== null ? Number(a.trackCount) : 0,
                        coverUrl: a.coverUrl ?? '',
                        addedAt: a.addedAt ?? undefined
                    });
                }
            } catch (err) {
                console.error('Failed to load album', err);
            } finally {
                if (!cancelled) setLoading(false);
            }
        };

        fetchAlbum();
        return () => { cancelled = true; };
    }, [id, location]);

    const onYearInputChange = (v: string) => {
        setYearInput(v);
        const parsed = parseInt(v, 10);
        if (!Number.isNaN(parsed)) {
            setAlbum(prev => ({ ...prev, releaseYear: parsed }));
        }
    };

    const incrementTrack = () => setAlbum(prev => ({ ...prev, trackCount: (prev.trackCount ?? 0) + 1 }));
    const decrementTrack = () => setAlbum(prev => ({ ...prev, trackCount: Math.max(0, (prev.trackCount ?? 0) - 1) }));

    const save = async () => {
        let yearToSave = album.releaseYear;
        const parsedYear = parseInt(yearInput, 10);
        if (!Number.isNaN(parsedYear)) yearToSave = parsedYear;

        if (!album.title.trim() || !album.artist.trim()) {
            alert('Title and Artist are required');
            return;
        }
        if (!Number.isInteger(yearToSave) || yearToSave <= 0) {
            alert('Enter a valid release year');
            return;
        }
        if (!Number.isInteger(album.trackCount ?? 0) || (album.trackCount ?? 0) < 0) {
            alert('Enter a valid track count');
            return;
        }

        const payload = {
            title: album.title,
            artist: album.artist,
            releaseYear: Number(yearToSave),
            genre: album.genre ?? '',
            trackCount: Number(album.trackCount ?? 0),
            coverUrl: album.coverUrl ?? '',
            addedAt: album.addedAt ?? new Date().toISOString()
        };

        //offline flow
        if (!navigator.onLine) {
            if (!id || id === 'new') {
                const tempId = makeTempId();
                addPendingItem({ method: 'POST', album: { ...payload, __tempId: tempId }, createdAt: new Date().toISOString() });
                updateLocalCacheAfterCreateOrUpdate(payload, true, tempId);
                alert('Saved locally — will be sent when online.');
            } else {
                addPendingItem({ method: 'PUT', album: { ...payload, id }, createdAt: new Date().toISOString() });
                updateLocalCacheAfterCreateOrUpdate({ ...payload, id }, false);
                alert('Changes saved locally — will be sent when online.');
            }
            history.push('/albums');
            return;
        }

        try {
            if (id && id !== 'new') {
                const resp = await api.put(`/albums/${id}`, payload);
                const updated = resp.data;
                updateLocalCacheAfterCreateOrUpdate(updated, false);
                alert('Album updated.');
            } else {
                const resp = await api.post('/albums', payload);
                const created = resp.data;
                updateLocalCacheAfterCreateOrUpdate(created, true);
                alert('Album created.');
            }
            history.push('/albums');
        } catch (err: any) {
            console.error('Save failed', err);
            const isNetworkErr = !err?.response;
            if (isNetworkErr) {
                if (!id || id === 'new') {
                    const tempId = makeTempId();
                    addPendingItem({ method: 'POST', album: { ...payload, __tempId: tempId }, createdAt: new Date().toISOString() });
                    updateLocalCacheAfterCreateOrUpdate(payload, true, tempId);
                    alert('Server unreachable — saved locally and queued.');
                } else {
                    addPendingItem({ method: 'PUT', album: { ...payload, id }, createdAt: new Date().toISOString() });
                    updateLocalCacheAfterCreateOrUpdate({ ...payload, id }, false);
                    alert('Server unreachable — changes saved locally and queued.');
                }
                history.push('/albums');
                return;
            }
            alert(err?.response?.data?.error || 'Failed to save album');
        }
    };

    const remove = async () => {
        if (!id || id === 'new') return;
        if (!window.confirm('Delete this album?')) return;

        // offline queue delete and remove from local cache
        if (!navigator.onLine) {
            addPendingItem({ method: 'DELETE', id, createdAt: new Date().toISOString() });
            removeFromLocalCacheById(id);
            alert('Delete queued and will be sent when online.');
            history.push('/albums');
            return;
        }

        try {
            await api.delete(`/albums/${id}`);
            removeFromLocalCacheById(id);
            history.push('/albums');
        } catch (err: any) {
            console.error('Delete failed', err);
            const isNetworkErr = !err?.response;
            if (isNetworkErr) {
                addPendingItem({ method: 'DELETE', id, createdAt: new Date().toISOString() });
                removeFromLocalCacheById(id);
                alert('Server unreachable — delete queued.');
                history.push('/albums');
                return;
            }
            alert('Failed to delete album');
        }
    };

    if (loading) {
        return (
            <IonPage>
                <IonContent fullscreen style={{ '--background': '#0b0c0f', padding: 14 } as React.CSSProperties}>
                    <div style={{ color: '#fff', textAlign: 'center', paddingTop: 60 }}>Loading…</div>
                </IonContent>
            </IonPage>
        );
    }

    return (
        <IonPage>
            <IonContent fullscreen style={{ '--background': '#0b0c0f', padding: 14 } as React.CSSProperties}>
                <div style={{
                    maxWidth: 920,
                    margin: '12px auto',
                    color: '#e8e8e8',
                    fontFamily: 'Inter, system-ui, -apple-system, "Helvetica Neue", Arial'
                }}>
                    <div style={{ display: 'flex', alignItems: 'center', gap: 12, marginBottom: 12 }}>
                        <button onClick={() => history.push('/albums')} style={{
                            background: 'transparent', border: '1px solid #2a2b30', color: '#cfcfcf', padding: '8px 10px', borderRadius: 8
                        }}>← Back</button>
                        <h2 style={{ margin: 0, fontSize: 20 }}>{id && id !== 'new' ? 'Edit Album' : 'New Album'}</h2>
                    </div>

                    <div style={{ background: '#0f1114', borderRadius: 12, padding: 14, boxShadow: '0 6px 18px rgba(0,0,0,0.6)' }}>
                        <div style={{ display: 'flex', gap: 12, alignItems: 'flex-start', flexWrap: 'wrap' }}>
                            <div style={{ width: 120, minWidth: 96 }}>
                                <div style={{
                                    width: 120, height: 120, borderRadius: 8, overflow: 'hidden', background: '#1a1b1f',
                                    display: 'flex', alignItems: 'center', justifyContent: 'center'
                                }}>
                                    {album.coverUrl ? (
                                        <img src={album.coverUrl} alt="cover" style={{ width: '100%', height: '100%', objectFit: 'cover' }} />
                                    ) : (
                                        <div style={{ color: '#7a7a7a', fontSize: 12, textAlign: 'center', padding: 6 }}>No cover</div>
                                    )}
                                </div>
                            </div>

                            <div style={{ flex: 1, minWidth: 200 }}>
                                <div style={{ color: '#ffffff', fontSize: 18, fontWeight: 600 }}>{album.title || 'Untitled'}</div>
                                <div style={{ color: '#bdbdbd', marginTop: 4 }}>{album.artist || 'Unknown artist'}</div>

                                <div style={{ marginTop: 10, display: 'flex', gap: 8, flexWrap: 'wrap' }}>
                                    <span style={{ background: '#17181b', color: '#dcdcdc', padding: '6px 8px', borderRadius: 8 }}>{album.genre || 'Unknown genre'}</span>
                                    <span style={{ background: '#17181b', color: '#dcdcdc', padding: '6px 8px', borderRadius: 8 }}>{album.trackCount ?? 0} tracks</span>
                                    <span style={{ background: '#17181b', color: '#dcdcdc', padding: '6px 8px', borderRadius: 8 }}>{album.releaseYear}</span>
                                </div>

                                <div style={{ color: '#9e9e9e', fontSize: 13, marginTop: 8 }}>
                                    Added: {album.addedAt ? new Date(album.addedAt).toLocaleDateString() : '—'}
                                </div>
                            </div>
                        </div>

                        <div style={{ marginTop: 14 }}>
                            <label style={{ display: 'block', color: '#cfcfcf', marginBottom: 6 }}>Title</label>
                            <input
                                value={album.title ?? ''}
                                onChange={e => setAlbum({ ...album, title: e.target.value })}
                                style={{ width: '100%', padding: 10, borderRadius: 8, border: '1px solid #232426', background: '#0b0c0f', color: '#e8e8e8' }}
                            />

                            <label style={{ display: 'block', color: '#cfcfcf', marginBottom: 6, marginTop: 12 }}>Artist</label>
                            <input
                                value={album.artist ?? ''}
                                onChange={e => setAlbum({ ...album, artist: e.target.value })}
                                style={{ width: '100%', padding: 10, borderRadius: 8, border: '1px solid #232426', background: '#0b0c0f', color: '#e8e8e8' }}
                            />

                            <div style={{ display: 'flex', gap: 10, marginTop: 12, alignItems: 'center', flexWrap: 'wrap' }}>
                                <div style={{ flex: '1 1 120px' }}>
                                    <label style={{ display: 'block', color: '#cfcfcf', marginBottom: 6 }}>Release year</label>
                                    <input
                                        type="text"
                                        value={yearInput}
                                        onChange={e => onYearInputChange(e.target.value)}
                                        style={{ width: '100%', padding: 10, borderRadius: 8, border: '1px solid #232426', background: '#0b0c0f', color: '#e8e8e8' }}
                                    />
                                </div>

                                <div style={{ flex: '1 1 120px' }}>
                                    <label style={{ display: 'block', color: '#cfcfcf', marginBottom: 6 }}>Track count</label>
                                    <div style={{ display: 'flex', alignItems: 'center', gap: 8 }}>
                                        <button
                                            onClick={decrementTrack}
                                            style={{ padding: '8px 10px', borderRadius: 8, background: '#2b2b2b', color: '#fff', border: 'none' }}
                                        >−</button>
                                        <div style={{ minWidth: 60, textAlign: 'center', padding: '8px 10px', borderRadius: 8, border: '1px solid #232426', background: '#0b0c0f', color: '#e8e8e8' }}>
                                            {album.trackCount ?? 0}
                                        </div>
                                        <button
                                            onClick={incrementTrack}
                                            style={{ padding: '8px 10px', borderRadius: 8, background: '#2b2b2b', color: '#fff', border: 'none' }}
                                        >+</button>
                                    </div>
                                </div>
                            </div>

                            <label style={{ display: 'block', color: '#cfcfcf', marginBottom: 6, marginTop: 12 }}>Genre</label>
                            <input
                                value={album.genre ?? ''}
                                onChange={e => setAlbum({ ...album, genre: e.target.value })}
                                style={{ width: '100%', padding: 10, borderRadius: 8, border: '1px solid #232426', background: '#0b0c0f', color: '#e8e8e8' }}
                            />

                            <label style={{ display: 'block', color: '#cfcfcf', marginBottom: 6, marginTop: 12 }}>Cover URL</label>
                            <input
                                value={album.coverUrl ?? ''}
                                onChange={e => setAlbum({ ...album, coverUrl: e.target.value })}
                                style={{ width: '100%', padding: 10, borderRadius: 8, border: '1px solid #232426', background: '#0b0c0f', color: '#e8e8e8' }}
                            />
                        </div>

                        <div style={{ display: 'flex', gap: 10, marginTop: 16 }}>
                            <button onClick={save} style={{ flex: 1, padding: '10px 12px', borderRadius: 10, background: '#5f0265', color: '#fff', border: 'none' }}>Save</button>
                            {id && id !== 'new' && (
                                <button onClick={remove} style={{ flex: 1, padding: '10px 12px', borderRadius: 10, background: '#8a0000', color: '#fff', border: 'none' }}>Delete</button>
                            )}
                        </div>
                    </div>
                </div>
            </IonContent>
        </IonPage>
    );
};

export default AlbumDetail;
