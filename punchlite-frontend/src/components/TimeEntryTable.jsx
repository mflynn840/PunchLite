import React, {useEffect, useState} from "react";
const API_BASE = import.meta.env.VITE_API_BASE;


function TimeEntryTable({username, refreshTrigger}){
    
    //define variables
    const [punches, setPunches] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        if(!username) return;
        
        //async try to fetch the 5 most recent punches for user from the backend
        async function fetchPunches() {
            try{
                //Use bearer token to access the employees backend api
                const response = await fetch(`${API_BASE}/api/time-entries/employees/${username}/get-time-entries?limit=5`,{
                    method : 'GET',
                    headers : {
                        'Authorization' : `Bearer ${localStorage.getItem("token")}`,
                        'Content-Type' : "application/json" 
                    }
                });
                if(!response.ok) throw new Error("Failed to load time entries");

                const data = await response.json();
                setPunches(data);

            }catch(error){
                setError(error.message);
            }finally {
                setLoading(false);
            }
        }
        //call function on creation, and when username/refresh trigger are updated
        fetchPunches();
    }, [username, refreshTrigger]);

    if (loading) return <p>Loading recent punches for {username}...</p>;
    if (error) return <p style={{ color: "red" }}>Error: {error}</p>;

    return (
    <table border="1" cellPadding="6" style={{ borderCollapse: "collapse", width: "100%" }}>
        <thead>
        <tr>
            <th>Date</th>
            <th>Clock In Time</th>
            <th>Clock Out Time</th>
        </tr>
        </thead>
        <tbody>
        {punches.length === 0 ? (
            <tr>
            <td colSpan="3" style={{ textAlign: "center" }}>No punches found</td>
            </tr>
        ) : (
            punches.map((punch) => {
            const clockInDate = new Date(punch.clockIn);
            const clockOutDate = punch.clockOut ? new Date(punch.clockOut) : null;
            return (
                <tr key={punch.id}>
                <td>{clockInDate.toLocaleDateString()}</td>
                <td>{clockInDate.toLocaleTimeString()}</td>
                <td>{clockOutDate ? clockOutDate.toLocaleTimeString() : '-'}</td>
                </tr>
            );
            })
        )}
        </tbody>
    </table>
    );
}
export default TimeEntryTable;