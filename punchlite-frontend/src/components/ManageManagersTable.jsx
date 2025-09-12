import {React, useEffect, useState} from "react";
import {useUserTable} from "../hooks/useUserTable";


function ManageManagersTable(){

    const {
        employees,
        managers,
        loading,
        error,
        getAllEmployees,
        getAllManagers,
        getEmployeesByManager
    }  = useUserTable();

    //dict of the selected manager for each user
    const [selectedManagers, setSelectedManagers] = useState({});

    //dict of the selected wage for each user
    const [wages, setWages] = useState({});

    function handleWageChange(employeeId, value){
        const newWages = {...wages}; //keep old same
        newWages[employeeId] = value; //update new
        setWages(newWages);

    }

    function handleInput(e, employeeId){
        const newManagers = {...selectedManagers};
        newManagers[employeeId] = e.target.value;
        setSelectedManagers(newManagers);

    }


    useEffect(() => {
        getAllEmployees();
        getAllManagers();
    }, []);
    

    return(
        <table border="1" style={{ borderCollapse: "collapse", width: "100%" }}>
            <thead>
                <tr>
                    <th>Employee: </th>
                    <th>Assign manager: </th>
                    <th>Set Wage: </th>
                </tr>
            </thead>

            <tbody>
                {employees.map((employee) => (
                    <tr key={employee.id}>

                        <td>{employee.username}</td>

                        <td>
                            <select name="Manager"
                            value={selectedManagers[employee.id] || ""}
                            onChange={(e) => handleInput(e, employee.id)}>
                                <option value="" disabled> Select Manager </option>
                                {managers.map((manager) => (
                                    <option key={manager.id} value={manager.username}>{manager.username}</option>
                                ))}
                            </select>
                        </td>

                        <td>
                            <input
                                type="number"
                                min="0"
                                step="0.01"
                                value={wages[employee.id] || ""}
                                onChange={(e) => handleWageChange(employee.id, e.target.value)}
                                placeholder="Hourly wage"
                            />
                        </td>

                    </tr>
                ))}
            </tbody>
        </table>
        //Draw a table of all employees, with the columns
            //Name | manager (if they have one) | assignManager button 
        //when the assignManager button is clicked it opens a popup 
        //  that has a table of all managers (each has name, select button which sends request to backend)
    )
}

export default ManageManagersTable;