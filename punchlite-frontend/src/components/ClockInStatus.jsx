import React from "react";

//display the clock in status using isClockIn hook
export function ClockInStatus({isClockedIn}) {
    if (isClockedIn === null){
        return <p>Loading...</p>;
    }
    if (isClockedIn) {
        return <p> You are currently clocked <strong> IN</strong> </p>;
    }
    return <p> You are currently clocked <strong> OUT </strong></p>;
}