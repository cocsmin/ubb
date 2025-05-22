// src/App.jsx
import React, { useState, useEffect } from 'react';
import './App.css';

export default function App() {
    const [cazuri, setCazuri] = useState([]);
    const [loading, setLoading] = useState(true);

    // Form adăugare / editare
    const [showForm, setShowForm] = useState(false);
    const [editingCaz, setEditingCaz] = useState(null);
    const [numeCaz, setNumeCaz] = useState('');
    const [descriere, setDescriere] = useState('');

    useEffect(() => {
        fetchCazuri();
    }, []);

    function fetchCazuri() {
        setLoading(true);
        fetch('http://localhost:8080/api/cazuri')
            .then(res => {
                if (!res.ok) throw new Error(res.status);
                return res.json();
            })
            .then(data => setCazuri(data))
            .catch(err => console.error('Fetch error:', err))
            .finally(() => setLoading(false));
    }

    function openAddForm() {
        setEditingCaz(null);
        setNumeCaz('');
        setDescriere('');
        setShowForm(true);
    }

    function openEditForm(caz) {
        setEditingCaz(caz);
        setNumeCaz(caz.numeCaz ?? caz.nume_caz);
        setDescriere(caz.descriere);
        setShowForm(true);
    }

    function handleDelete(id) {
        if (!window.confirm('Sigur vrei să ștergi?')) return;
        fetch(`http://localhost:8080/api/cazuri/${id}`, { method: 'DELETE' })
            .then(res => {
                if (res.status !== 204) throw new Error(res.status);
                fetchCazuri();
            })
            .catch(err => console.error('Delete error:', err));
    }

    function handleSave() {
        const method = editingCaz ? 'PUT' : 'POST';
        const url = editingCaz
            ? `http://localhost:8080/api/cazuri/${editingCaz.id}`
            : 'http://localhost:8080/api/cazuri';

        const payload = { nume_caz: numeCaz, descriere: descriere };
        fetch(url, {
            method,
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(payload)
        })
            .then(res => {
                if (![200, 201].includes(res.status)) throw new Error(res.status);
                return method === 'POST' ? res.json() : res.json();
            })
            .then(() => {
                setShowForm(false);
                fetchCazuri();
            })
            .catch(err => console.error('Save error:', err));
    }

    return (
        <div className="container">
            <header>
                <h1>Dashboard Cazuri</h1>
                <button className="btn btn-add" onClick={openAddForm}>
                    + Adaugă Caz
                </button>
            </header>

            {showForm && (
                <div className="form-overlay">
                    <div className="form-card">
                        <h2>{editingCaz ? 'Editează Caz' : 'Adaugă Caz nou'}</h2>
                        <label>
                            Nume:
                            <input
                                type="text"
                                value={numeCaz}
                                onChange={e => setNumeCaz(e.target.value)}
                            />
                        </label>
                        <label>
                            Descriere:
                            <textarea
                                value={descriere}
                                onChange={e => setDescriere(e.target.value)}
                            />
                        </label>
                        <div className="form-actions">
                            <button className="btn btn-save" onClick={handleSave}>
                                Salvează
                            </button>
                            <button
                                className="btn btn-cancel"
                                onClick={() => setShowForm(false)}
                            >
                                Anulează
                            </button>
                        </div>
                    </div>
                </div>
            )}

            <main>
                {loading ? (
                    <p>Se încarcă...</p>
                ) : (
                    <table>
                        <thead>
                        <tr>
                            <th>Nume</th>
                            <th>Descriere</th>
                            <th>Acțiuni</th>
                        </tr>
                        </thead>
                        <tbody>
                        {cazuri.map(caz => (
                            <tr key={caz.id}>
                                <td>{caz.numeCaz ?? caz.nume_caz}</td>
                                <td>{caz.descriere}</td>
                                <td>
                                    <button
                                        className="btn btn-edit"
                                        onClick={() => openEditForm(caz)}
                                    >
                                        Edit
                                    </button>
                                    <button
                                        className="btn btn-delete"
                                        onClick={() => handleDelete(caz.id)}
                                    >
                                        Delete
                                    </button>
                                </td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                )}
            </main>
        </div>
    );
}
