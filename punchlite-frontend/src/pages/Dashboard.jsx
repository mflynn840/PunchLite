import {useState} from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import {useParams} from "react-router-dom";
import logoImg from '../assets/logo.png'
import Clock from '../components/Clock';

import {useClockInStatus} from "../hooks/useClockInStatus";
import {ClockInStatus} from "../components/ClockInStatus";
import TimeEntryTable from "../components/TimeEntryTable";
import ViewPay from "./ViewPay";
const API_BASE = import.meta.env.VITE_API_BASE;


export default function Dashboard() {

  //get username from the url
  const {username} = useParams();
  const [refreshTrigger, setRefreshTrigger] = useState(0);
  const isClockedIn = useClockInStatus(username, refreshTrigger);
  const [activePage, setActivePage] = useState('dashboard');


  const handleClockIn = async(e) => {


    e.preventDefault(); //prevent the page from reloading
    
    //try sending a POST request to the backend to clock in
    try{
      const response = await fetch(`${API_BASE}/api/time-entries/employees/${username}/clock-in`,
      {
        method: "POST",
        headers : {
          "Authorization" : `Bearer ${localStorage.getItem("token")}`,
          "Content-Type": "application/json"
        }
      });

      //handle backend returning errors
      if(!response.ok) throw new Error("Failed to clock in");

      //go back to main dashboard
      setActivePage('dashboard');
      //trigger the hook to update the displayed clock in status
      setRefreshTrigger(prev => prev+1);

    }catch(error){
      console.error("Failed to clock in", error.message);
    }
  };

  const handleClockOut = async(e) => {
    e.preventDefault(); //prevent the page from reloading
    
    //try sending a POST request to the backend to clock out
    try{
      const response = await fetch(`${API_BASE}/api/time-entries/employees/${username}/clock-out`,
      {
        method: "POST",
        headers : {
          "Authorization" : `Bearer ${localStorage.getItem("token")}`,
          "Content-Type": "application/json"
        }
      });
      if (!response.ok) throw new Error("Error: failed to clock out");

      //go back to main dashboard
      setActivePage('dashboard');

      //update clock in status and time entry table
      setRefreshTrigger(prev => prev+1);


    }catch(error){
      console.error("Failed to clock out", error.message);
    }
  };
  

  return (
    <div className="d-flex flex-column vh-100">
      {/* Top Navigation Bar */}
      <header className="bg-secondary text-white px-4 py-2">
        <h5 className="mb-0">PunchLite User Dashboard</h5>
      </header>

      {/* Main content area */}
      <div className="d-flex flex-grow-1">

        {/* Sidebar */}
        <aside className="bg-primary text-white p-3" style={{ width: "250px" }}>
          {/* Logo */}
          <div className="text-center mb-4">
            <img
              src={logoImg}
              alt="Logo Image"
              style={{ width: '100%', height: 'auto' }}
            />
          </div>

          {/* Navigation Buttons */}
          <div className="d-grid gap-2">
            <button className="btn btn-outline-light" onClick={handleClockIn}>Clock In</button>
            <button className="btn btn-outline-light" onClick={handleClockOut}>Clock Out</button>
            <button className="btn btn-outline-light" onClick={() => setActivePage('pay')}>View Pay</button>
            <button className="btn btn-outline-light">Logout</button>
          </div>
        </aside>


        {/* Page Content */}
        <main className="flex-grow-1 p-5">

          {/* Show clock in/clock out dashboard */}
          {activePage === 'dashboard' && (
            <>
              <h3>Welcome, {username}</h3>
              <p className="lead">This is your time clock dashboard. Use the menu to manage your hours.</p>
              <Clock />
              <div className="mt-4">
                <ClockInStatus isClockedIn={isClockedIn} />
              </div>
              <div className="mt-4">
                <TimeEntryTable username={username} refreshTrigger={refreshTrigger} />
              </div>
            </>
          )}
          {/* show pay information dashboard */}
          {activePage === 'pay' && (
            <div style = {{flexGrow: 1}}>
              <ViewPay username={username} refreshTrigger={refreshTrigger} />
            </div>
          )}
        </main>
      </div>
    </div>
  );
}
