import React, {useEffect, useState} from "react";
const API_BASE = import.meta.env.VITE_API_BASE;


function EmployeeTable({username, refreshTrigger}){
    
    //define variables
    const [employees, setEmployees] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        if(!username) return;
        
        //async try to fetch the 5 most recent punches for user from the backend
        async function fetchEmployees() {
            try{
                //Use bearer token to access the employees backend api
                const response = await fetch(`${API_BASE}/api/users/managed-by/${username}`,{
                    method : 'GET',
                    headers : {
                        'Authorization' : `Bearer ${localStorage.getItem("token")}`,
                        'Content-Type' : "application/json" 
                    }
                });
                if(!response.ok) throw new Error("Failed to get managed users");

                const data = await response.json();
                setEmployees(data);

            }catch(error){
                setError(error.message);
            }finally {
                setLoading(false);
            }
        }
        //call function on creation, and when username/refresh trigger are updated
        fetchEmployees();
    }, [username, refreshTrigger]);

    if (loading) return <p>Loading Employees for {username}...</p>;
    if (error) return <p style={{ color: "red" }}>Error: {error}</p>;

    return (
    <table border="1" style={{ borderCollapse: "collapse", width: "100%" }}>

      <thead>
        <tr>
          <th>Username</th>
          <th>Action</th>
        </tr>
      </thead>

      <tbody>
        {/* Map each employee to a cell with username and a cell with manage button */}
        {employees.map((emp) => (
          <tr key={emp.id}>
            <td>{emp.username}</td>
            <td>
              <button onClick={() => alert(`Manage ${emp.username}`)}>
                Manage
              </button>
            </td>
          </tr>
        ))}

      </tbody>
    </table>
  );
}
export default EmployeeTable;