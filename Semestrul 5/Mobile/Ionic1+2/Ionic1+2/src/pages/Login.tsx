import React, { useState, useEffect } from 'react';
import { IonPage, IonContent } from '@ionic/react';
import auth from '../services/auth';
import { useHistory } from 'react-router-dom';

const Login: React.FC = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [name, setName] = useState('');
    const [loading, setLoading] = useState(false);
    const [online, setOnline] = useState<boolean>(navigator.onLine);
    const history = useHistory();

    useEffect(() => {
        const onOnline = () => setOnline(true);
        const onOffline = () => setOnline(false);
        window.addEventListener('online', onOnline);
        window.addEventListener('offline', onOffline);
        return () => {
            window.removeEventListener('online', onOnline);
            window.removeEventListener('offline', onOffline);
        };
    }, []);

    const doLogin = async () => {
        if (!email.trim() || !password) {
            alert('Email and password are required');
            return;
        }
        setLoading(true);
        try {
            await auth.login(email.trim(), password);
            history.replace('/albums');
        } catch (err: any) {
            alert(err?.response?.data?.error || 'Login failed');
        } finally {
            setLoading(false);
        }
    };

    const doRegister = async () => {
        if (!email.trim() || !password) {
            alert('Email and password are required');
            return;
        }
        setLoading(true);
        try {
            await auth.register(email.trim(), password, name?.trim() || undefined);
            history.replace('/albums');
        } catch (err: any) {
            alert(err?.response?.data?.error || 'Register failed');
        } finally {
            setLoading(false);
        }
    };

    return (
        <IonPage>
            <IonContent fullscreen style={{ '--background': '#0b0c0f' } as React.CSSProperties}>
                <div style={{
                    display: 'flex',
                    alignItems: 'center',
                    justifyContent: 'center',
                    minHeight: '100vh',
                    padding: 16,
                    fontFamily: 'Inter, system-ui, -apple-system, "Helvetica Neue", Arial',
                    color: '#e8e8e8'
                }}>
                    <div style={{
                        width: 380,
                        maxWidth: '95%',
                        borderRadius: 12,
                        padding: 22,
                        background: 'linear-gradient(180deg, rgba(255,255,255,0.02), rgba(255,255,255,0.01))',
                        boxShadow: '0 10px 30px rgba(0,0,0,0.6)',
                        border: '1px solid rgba(255,255,255,0.03)'
                    }}>
                        <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between', marginBottom: 14 }}>
                            <div>
                                <div style={{ fontSize: 18, fontWeight: 700 }}>Welcome back</div>
                                <div style={{ fontSize: 12, color: '#9e9e9e', marginTop: 4 }}>Sign in to manage your albums</div>
                            </div>

                            <div style={{
                                padding: '6px 10px',
                                borderRadius: 8,
                                background: online ? '#1bc52e' : '#980000',
                                color: '#0b0b0b',
                                fontWeight: 700,
                                fontSize: 12
                            }}>
                                {online ? 'Online' : 'Offline'}
                            </div>
                        </div>

                        <div style={{ marginBottom: 10 }}>
                            <label style={{ display: 'block', fontSize: 13, color: '#cfcfcf', marginBottom: 6 }}>Name (for register)</label>
                            <input
                                value={name}
                                onChange={e => setName(e.target.value)}
                                placeholder="Optional"
                                style={{
                                    width: '100%',
                                    padding: '10px 12px',
                                    borderRadius: 10,
                                    border: '1px solid #232426',
                                    background: '#0f1114',
                                    color: '#e8e8e8',
                                    outline: 'none',
                                    fontSize: 14
                                }}
                            />
                        </div>

                        <div style={{ marginBottom: 10 }}>
                            <label style={{ display: 'block', fontSize: 13, color: '#cfcfcf', marginBottom: 6 }}>Email</label>
                            <input
                                value={email}
                                onChange={e => setEmail(e.target.value)}
                                placeholder="you@example.com"
                                style={{
                                    width: '100%',
                                    padding: '10px 12px',
                                    borderRadius: 10,
                                    border: '1px solid #232426',
                                    background: '#0f1114',
                                    color: '#e8e8e8',
                                    outline: 'none',
                                    fontSize: 14
                                }}
                            />
                        </div>

                        <div style={{ marginBottom: 14 }}>
                            <label style={{ display: 'block', fontSize: 13, color: '#cfcfcf', marginBottom: 6 }}>Password</label>
                            <input
                                type="password"
                                value={password}
                                onChange={e => setPassword(e.target.value)}
                                placeholder="••••••••"
                                style={{
                                    width: '100%',
                                    padding: '10px 12px',
                                    borderRadius: 10,
                                    border: '1px solid #232426',
                                    background: '#0f1114',
                                    color: '#e8e8e8',
                                    outline: 'none',
                                    fontSize: 14
                                }}
                            />
                        </div>

                        <div style={{ display: 'flex', gap: 10, marginBottom: 10 }}>
                            <button
                                onClick={doLogin}
                                disabled={loading}
                                style={{
                                    flex: 1,
                                    padding: '10px 12px',
                                    borderRadius: 10,
                                    border: 'none',
                                    background: '#5f0265',
                                    color: '#031200',
                                    fontWeight: 700,
                                    boxShadow: '0 6px 14px rgba(58,241,82,0.12)',
                                    cursor: 'pointer'
                                }}
                            >
                                {loading ? 'Signing in…' : 'Sign in'}
                            </button>

                            <button
                                onClick={doRegister}
                                disabled={loading}
                                style={{
                                    flex: 1,
                                    padding: '10px 12px',
                                    borderRadius: 10,
                                    border: '1px solid rgba(255,255,255,0.06)',
                                    background: 'transparent',
                                    color: '#e8e8e8',
                                    fontWeight: 600,
                                    cursor: 'pointer'
                                }}
                            >
                                {loading ? 'Working…' : 'Register'}
                            </button>
                        </div>

                        <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', color: '#9e9e9e', fontSize: 13 }}>
                            <div>Don't have an account? Use Register</div>
                            <div style={{ opacity: 0.9, fontSize: 12 }}>v1.0</div>
                        </div>
                    </div>
                </div>
            </IonContent>
        </IonPage>
    );
};

export default Login;
