import react from "react";
import EmployeeTable from "../components/EmployeeTable";

function ManageEmployees({username, refreshTrigger}){
    return(
        <div style={{ padding: 20, fontFamily: "'Times New Roman', serif" }}>
        <div style={{ display: "flex", alignItems: "center", gap: 50, marginBottom: 40, flexGrow : 1 }}>

            <EmployeeTable
                username = {username}
                refreshTrigger = {refreshTrigger}
            />

        </div>
        </div>
    )
}

export default ManageEmployees;