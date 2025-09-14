import React, {useEffect, useState} from "react";
import {useEmployeeTable} from "../hooks/useEmployeeTable";
import SalaryManagementPopup from "./SalaryManagementPopup";
const API_BASE = import.meta.env.VITE_API_BASE;


function EmployeeTable({username, refreshTrigger}){
    
  // State for salary management popup
  const [isPopupOpen, setIsPopupOpen] = useState(false);
  const [selectedEmployee, setSelectedEmployee] = useState(null);

  //Use a hook to connect with the backend
  const { employees, loading, error} = useEmployeeTable(username, refreshTrigger);

  // Handler for opening the salary management popup
  const handleManageClick = (employee) => {
    setSelectedEmployee(employee);
    setIsPopupOpen(true);
  };

  // Handler for closing the popup
  const handleClosePopup = () => {
    setIsPopupOpen(false);
    setSelectedEmployee(null);
  };

  // Handler for successful salary update
  const handleSalaryUpdateSuccess = () => {
    // The refreshTrigger will cause the employee table to refresh
    // No need to do anything else here
  };

  if(loading) return <p>Loading Employees for {username}...</p>
  if(error) return <p>Error: failed to fetch </p>

  //render an employee table
  return (
    <>
      <table border="1" style={{ borderCollapse: "collapse", width: "100%" }}>

        <thead>
          <tr>
            <th>Username</th>
            <th>Hourly Rate</th>
            <th>Action</th>
          </tr>
        </thead>

        <tbody>
          {employees.length === 0 ? (
            <tr><td colSpan={3}>No Employees Found</td></tr>
          ): (
            employees.map((emp) => (
            <tr key={emp.id}>
              <td>{emp.username}</td>
              <td>${emp.hourlyRate ? emp.hourlyRate.toFixed(2) : '0.00'}</td>
              <td>
                <button onClick={() => handleManageClick(emp)}>Manage</button>
                </td>
              </tr>
            ))
          )}

        </tbody>
      </table>

      <SalaryManagementPopup
        isOpen={isPopupOpen}
        onClose={handleClosePopup}
        employee={selectedEmployee}
        managerUsername={username}
        onSuccess={handleSalaryUpdateSuccess}
      />
    </>
  );
}
export default EmployeeTable;