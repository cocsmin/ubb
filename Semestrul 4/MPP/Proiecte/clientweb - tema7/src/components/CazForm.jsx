import React, { useState, useEffect } from "react";

export default function CazForm({ onSave, cazEditabil, onCancel }) {
    const [numeCaz, setNumeCaz] = useState("");
    const [descriere, setDescriere] = useState("");

    useEffect(() => {
        if (cazEditabil) {
            setNumeCaz(cazEditabil.numeCaz || "");
            setDescriere(cazEditabil.descriere || "");
        } else {
            setNumeCaz("");
            setDescriere("");
        }
    }, [cazEditabil]);

    const handleSubmit = (e) => {
        e.preventDefault();
        onSave({ ...cazEditabil, numeCaz, descriere });
        setNumeCaz("");
        setDescriere("");
    };

    return (
        <form onSubmit={handleSubmit} className="p-4 space-y-4">
            <input
                type="text"
                value={numeCaz}
                onChange={(e) => setNumeCaz(e.target.value)}
                placeholder="Nume caz"
                className="w-full border px-2 py-1"
                required
            />
            <textarea
                value={descriere}
                onChange={(e) => setDescriere(e.target.value)}
                placeholder="Descriere"
                className="w-full border px-2 py-1"
                required
            />
            <div className="flex gap-2">
                <button className="bg-blue-500 text-white px-3 py-1 rounded" type="submit">
                    {cazEditabil ? "Salvează modificările" : "Adaugă caz"}
                </button>
                {cazEditabil && (
                    <button className="bg-gray-400 px-3 py-1 rounded" onClick={onCancel}>
                        Anulează
                    </button>
                )}
            </div>
        </form>
    );
}
