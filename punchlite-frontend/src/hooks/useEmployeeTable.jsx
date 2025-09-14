// useManagedEmployees.js
import { useEffect, useState } from "react";
const API_BASE = import.meta.env.VITE_API_BASE;

export function useEmployeeTable(username, refreshTrigger) {
    const [employees, setEmployees] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        if (!username) return;

        const fetchEmployees = async () => {
            setLoading(true);
            setError(null);
            try {
                const token = localStorage.getItem("token");
                const response = await fetch(`${API_BASE}/api/users/managed-by/${username}`, {
                    headers: {
                        "Authorization": `Bearer ${token}`,
                        "Content-Type": "application/json"
                    }
                });
                if (!response.ok) throw new Error("Failed to get managed users");
                const data = await response.json();
                setEmployees(data);
            } catch (err) {
                setError(err?.message || "Unknown error");
            } finally {
                setLoading(false);
            }
        };

        fetchEmployees();
    }, [username, refreshTrigger]);

    return { employees, loading, error };
}
