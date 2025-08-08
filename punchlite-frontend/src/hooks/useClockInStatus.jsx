import {useState, useEffect} from "react";
const API_BASE = import.meta.env.VITE_API_BASE;

/**
 * 
 * @param {*} username 
 * @returns 
 * 
 * Fetch the employees clock in status
 * from the backend when username or refresh trigger changes
 * 
 */
export function useClockInStatus(username, refreshTrigger){

    //initilize a variable and set its update function for if user is clocked in
    const [isClockedIn, setIsClockedIn] = useState(null);


      
    //define what happens when this is drawn onto the screen
    useEffect(() => {
        if(!username) return //no username given

        //make a call to the backend to get clockIn status
        //define an asynch function
        const fetchStatus = async () => {
            try {
                const response = await fetch(`${API_BASE}/api/users/clock-status/${username}`,
                {
                    headers : {
                        "Authorization" : `Bearer ${localStorage.getItem("token")}`
                    }
                });
                if (!response.ok) throw new Error("Failed to fetch clock in status");

                const data = await response.json();
                setIsClockedIn(data.clockedIn);
            }catch(error) {
                console.error("Error fetching clock in status:", error.message);
                //do not set clock in status if there is an error
                setIsClockedIn(null);
            }
        };
        //call the async function
        fetchStatus();
    }, [username, refreshTrigger]);

    return isClockedIn;
}