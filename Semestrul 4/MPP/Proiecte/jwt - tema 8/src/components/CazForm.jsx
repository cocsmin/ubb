import { useState } from "react";

export default function CazForm({ initial, onSave, onCancel }) {
  const [nume, setNume] = useState(initial?.numeCaz || "");
  const [desc, setDesc] = useState(initial?.descriere || "");

  return (
    <form onSubmit={e=>{ e.preventDefault(); onSave({ numeCaz: nume, descriere: desc }); }}>
      <h3>{initial ? "Editare" : "Adăugare"} caz</h3>
      <input
        placeholder="Nume caz"
        value={nume}
        onChange={e=>setNume(e.target.value)}
        required
      />
      <textarea
        placeholder="Descriere"
        value={desc}
        onChange={e=>setDesc(e.target.value)}
        required
      />
      <button type="submit">Salvează</button>
      <button type="button" onClick={onCancel}>Renunță</button>
    </form>
  );
}
