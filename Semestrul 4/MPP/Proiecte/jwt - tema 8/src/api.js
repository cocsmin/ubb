const API = "http://localhost:8080";

export async function login(username, password) {
  const res = await fetch(`${API}/auth/login`, {
    method: 'POST',
  headers: {
    'Content-Type': 'application/json'
  },
  body: JSON.stringify({ username, password }),
  credentials: 'include' // doar dacă ai nevoie de cookie-uri; altfel omit
})
.then(res => {
  if (!res.ok) throw new Error('Login failed');
  return res.json();
})
.then(data => {
  localStorage.setItem('jwt', data.token);
})
.catch(err => console.error(err)); // { token: "..." }
}

function getAuthHeaders() {
  const token = localStorage.getItem("jwt");
  return { Authorization: `Bearer ${token}` };
}

export async function fetchCazuri() {
  const res = await fetch(`${API}/api/cazuri`, {
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error("Fetch error");
  return res.json();
}
export async function createCaz(caz) {
  const res = await fetch(`${API}/api/cazuri`, {
    method: "POST",
    headers: {
      "Content-Type":"application/json",
      ...getAuthHeaders()
    },
    body: JSON.stringify(caz)
  });
  if (!res.ok) throw new Error("Create error");
  return res.json();
}
export async function updateCaz(id, caz) {
  const res = await fetch(`${API}/api/cazuri/${id}`, {
    method: "PUT",
    headers: {
      "Content-Type":"application/json",
      ...getAuthHeaders()
    },
    body: JSON.stringify(caz)
  });
  if (!res.ok) throw new Error("Update error");
}
export async function deleteCaz(id) {
  const res = await fetch(`${API}/api/cazuri/${id}`, {
    method: "DELETE",
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error("Delete error");
}
