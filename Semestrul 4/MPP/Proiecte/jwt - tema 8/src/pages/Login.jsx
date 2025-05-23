// src/pages/Login.jsx
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';

export default function Login() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError]     = useState(null);
  const navigate = useNavigate();

  const handleSubmit = async e => {
    e.preventDefault();
    setError(null);

    try {
      const response = await fetch('http://localhost:8080/auth/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Accept': 'application/json'
        },
        mode: 'cors',            // CORS activat
        body: JSON.stringify({ username, password })
      });

      if (!response.ok) {
        // 401 sau alt cod → aruncăm eroare
        throw new Error('Username/parolă invalidă');
      }

      const { token } = await response.json();
      // Salvăm token în localStorage
      localStorage.setItem('jwt', token);

      window.dispatchEvent(new Event("storage"));

      // Redirecționăm la pagina principală
      navigate('/cazuri');
    } catch (err) {
      setError(err.message);
    }
  };

  return (
    <div className="lflex items-center justify-center h-screen bg-gray-100">
      <h2 className="text-2xl font-bold text-center">Login</h2>
      {error && <div className="error">{error}</div>}
      <form onSubmit={handleSubmit} 
          className="bg-white p-6 rounded shadow-md w-80 space-y-4">
        <label>
          Username:
          <input
            type="text"
            value={username}
            onChange={e => setUsername(e.target.value)}
            required
          />
        </label>
        <label>
          Parolă:
          <input
            type="password"
            value={password}
            onChange={e => setPassword(e.target.value)}
            required
          />
        </label>
        <button type="submit">Loghează-te</button>
      </form>
    </div>
  );
}
