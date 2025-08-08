import {react, useState} from 'react';
const API_BASE = import.meta.env.VITE_API_BASE;


export function useClockPunch(username, setRefreshTrigger, setActivePage){

    /**
     * Send a request to endpoint (clock-in or clock-out) for some user
     * -Handle errors
     * -punch user in or out
     * -Reset the page to show the clock again
     */
    const clockRequest = async(endpoint) => {

        //try sending a POST request to the backend to clock in
        try{
            const response = await fetch(endpoint,{
                method: "POST",
                headers : {
                "Authorization" : `Bearer ${localStorage.getItem("token")}`,
                "Content-Type": "application/json"
                }
            });

            //handle backend returning errors
            if(!response.ok) throw new Error("Punch failed");

            //go back to main dashboard
            setActivePage('punch');
            //trigger the hook to update the displayed clock in status
            setRefreshTrigger(prev => prev+1);

        }catch(error){
            console.error("Punch failed", error.message);
        }
    }

    const handleClockIn = async(e) => {
        e.preventDefault() //prevent the page from reloading
        clockRequest(`${API_BASE}/api/time-entries/employees/${username}/clock-in`)
    };

    const handleClockOut = async(e) => {
        e.preventDefault() //prevent the page from reloading
        clockRequest(`${API_BASE}/api/time-entries/employees/${username}/clock-out`)
    };

    return {handleClockIn, handleClockOut};
}