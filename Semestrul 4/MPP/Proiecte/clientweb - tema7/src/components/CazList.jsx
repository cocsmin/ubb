import React from "react";

export default function CazList({ cazuri, onDelete, onEdit }) {
    return (
        <div className="p-4">
            <h2 className="text-xl font-semibold mb-2">Lista cazurilor</h2>
            <ul className="space-y-2">
                {cazuri.map((caz) => (
                    <li key={caz.id} className="border p-2 rounded shadow flex justify-between items-center">
                        <div>
                            <p><strong>Nume:</strong> {caz.numeCaz}</p>
                            <p><strong>Descriere:</strong> {caz.descriere}</p>
                        </div>
                        <div className="flex gap-2">
                            <button
                                className="bg-red-500 text-white px-2 py-1 rounded"
                                onClick={() => onDelete(caz.id)}
                            >
                                Șterge
                            </button>
                            <button
                                className="bg-yellow-400 px-2 py-1 rounded"
                                onClick={() => onEdit(caz)}
                            >
                                Editează
                            </button>
                        </div>
                    </li>
                ))}
            </ul>
        </div>
    );
}
