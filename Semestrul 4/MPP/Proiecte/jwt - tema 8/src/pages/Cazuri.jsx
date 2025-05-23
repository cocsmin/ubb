import { useEffect, useState } from "react";
import { fetchCazuri, createCaz, updateCaz, deleteCaz } from "../api";
import CazForm from "../components/CazForm";

export default function Cazuri() {
  const [list, setList] = useState([]);
  const [editing, setEditing] = useState(null);
  const [showForm, setShowForm] = useState(false);

  async function load() {
    let data = await fetchCazuri();
    setList(data);
  }

  useEffect(() => { load(); }, []);

  async function onSave(caz) {
    if (editing) await updateCaz(editing.id, caz);
    else await createCaz(caz);
    setShowForm(false);
    setEditing(null);
    await load();
  }

  return (
    <div>
      <h1>Lista cazuri</h1>
      <button onClick={()=>{setShowForm(true); setEditing(null);}}>
        Adaugă caz nou
      </button>
      <table border="1">
        <thead><tr><th>Nume</th><th>Descriere</th><th>Acțiuni</th></tr></thead>
        <tbody>
          {list.map(c =>
            <tr key={c.id_caz}>
              <td>{c.nume_caz}</td>
              <td>{c.descriere}</td>
              <td>
                <button onClick={()=>{ setEditing(c); setShowForm(true); }}>
                  Edit
                </button>
                <button onClick={async ()=>{ await deleteCaz(c.id); load(); }}>
                  Delete
                </button>
              </td>
            </tr>
          )}
        </tbody>
      </table>

      {showForm && (
        <CazForm
          initial={editing}
          onSave={onSave}
          onCancel={()=>{setShowForm(false); setEditing(null);}}
        />
      )}
    </div>
  );
}
