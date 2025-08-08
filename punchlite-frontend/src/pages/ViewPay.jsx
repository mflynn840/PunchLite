import React, {useState} from "react";
import PayTaxesPieChart from "../components/PayTaxesPieChart";
import TimeEntryTable from "../components/TimeEntryTable";

const API_BASE = import.meta.env.VITE_API_BASE;

export default function ViewPay({username, refreshTrigger}){

    
    //back to dashboard button handler


    //return the HTML embed for rendering the form

    return (
        <div style={{ padding: 20, fontFamily: "'Times New Roman', serif" }}>
        <div style={{ display: "flex", alignItems: "center", gap: 50, marginBottom: 40, flexGrow : 1 }}>

            {/* Green circle with pie chart text */}
            <div>
                <PayTaxesPieChart pay={3500} taxes={1500} />
            </div>

            {/* Pay information text */}
            <div style={{ fontWeight: "bold", fontSize: 18, lineHeight: 1.3 }}>
                Pay information:<br />
                Gross:<br />
                Taxes<br />
                Net:
            </div>
        </div>

        {/* Table filters box */}
        <div>
            Table filters here
        </div>

        {/* Time entry table object box */}
        <div style = {{flexgrow : 1}}>
            <TimeEntryTable username={username} refreshTrigger={refreshTrigger} />
        </div>
        </div>
    );
}
