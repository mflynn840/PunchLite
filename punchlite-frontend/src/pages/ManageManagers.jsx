import react from "react";
import ManageManagersTable from "../components/ManageManagersTable";

function ManageManagers({username, refreshTrigger}){
    return(
        <div style={{ padding: 20, fontFamily: "'Times New Roman', serif" }}>
        <div style={{ display: "flex", alignItems: "center", gap: 50, marginBottom: 40, flexGrow : 1 }}>

            <ManageManagersTable
                username = {username}
                refreshTrigger = {refreshTrigger}
            />

        </div>
        </div>
    )
}

export default ManageManagers;