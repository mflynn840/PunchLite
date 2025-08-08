import React, {useState, useEffect} from 'react';


function Clock(){

    //create a useState "react hook" (it allows you to store and update data)
        //initilize it with the current time "time"
        // call the "updateTime" function to update the data
    const [time, updateTime] = useState(new Date());

    //useEffect react hook runs code after the component is drawn on the screen
        //-we will use it to tell react to start calling updateTime once the clock is displayed
    useEffect(() => {
        //tell javascript to run the updateTime function every 1 second
        const intervalId = setInterval(() => {
            updateTime(new Date()); 
        }, 1000);

        //cleanup function (react tells JS to stop updating time)
        return () => clearInterval(intervalId);
        

    }, []); //only run when the component is first loaded

    //define how to draw the object (a  styled text box)
    return (
        <div
            style={{
                fontSize: '2rem',        
                textAlign: 'center',  
                fontFamily: 'monospace'     
            }}
        >

            {time.toLocaleTimeString()}  
        </div>
    );
}

//export component so it is visible to the rest of the app
export default Clock;