// Connect to the backend, present a breaer token and return the json response
const API_BASE = import.meta.env.VITE_API_BASE;

export async function apiFetch(endpoint, options = {}) {
    const token = localStorage.getItem("token");

    const response = await fetch(`${API_BASE}${endpoint}`, {
        ...options,
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`,
            ...(options.headers || {})
        }
    });

    if (!response.ok) {
        const errText = await response.text();
        throw new Error(errText || "API request failed");
    }

    return response.json();
}
