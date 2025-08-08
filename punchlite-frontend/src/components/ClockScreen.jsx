import react from "react";


//import components
import Clock from "./Clock";
import {ClockInStatus} from "./ClockInStatus"
import TimeEntryTable from "./TimeEntryTable";

export default function ClockScreen({username, isClockedIn, refreshTrigger}){
    return(
        <>
            <h3>Welcome, {username}</h3>
            <p className="lead">This is your time clock dashboard. Use the menu to manage your hours.</p>
            
            <div>
                <Clock />
            </div>
            
            <div className="mt-4">
                <ClockInStatus isClockedIn={isClockedIn} />
            </div>

            <div className="mt-4">
                <TimeEntryTable username={username} refreshTrigger={refreshTrigger} />
            </div>
    
        </>

    );
}