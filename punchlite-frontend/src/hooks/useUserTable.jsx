import {React, useState} from "react";
const API_BASE = import.meta.env.VITE_API_BASE;


export function useUserTable() {
    
        const [employees, setEmployees] = useState([]);
        const [managers, setManagers] = useState([]);
        const [loading, setLoading] = useState(false);
        const[error, setError] = useState(null);


        //async try to fetch the 5 most recent punches for user from the backend
        const fetchUsers = async (endpoint, setter) => {
            try{
                //Use bearer token to access the backend
                const response = await fetch(`${API_BASE}/${endpoint}`,{
                    method : 'GET',
                    headers : {
                        'Authorization' : `Bearer ${localStorage.getItem("token")}`,
                        'Content-Type' : "application/json" 
                    }
                });
                if(!response.ok) throw new Error("Failed to get managed users");

                const data = await response.json();
                setter(data);

            }catch(error){
                setError(error.message);
            }finally{
                setLoading(false)
            }
        };

        const getAllEmployees = async() => {
            await fetchUsers(`api/users/employees`, setEmployees);

        };

        const getAllManagers = async() => {
            await fetchUsers(`api/users/managers`, setManagers);
        };

        const getEmployeesByManager = async() => {
            await fetchUsers(`api/users/get-users-by-manager`, setEmployees);
        };


        return{
            employees,
            managers,
            loading,
            error,
            getAllEmployees,
            getAllManagers,
            getEmployeesByManager
        };


}