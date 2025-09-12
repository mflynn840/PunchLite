import {useState} from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import {useParams} from "react-router-dom";

//import hooks (handlers)
import {useClockInStatus} from "../hooks/useClockInStatus";
import {useClockPunch} from "../hooks/useClockPunch";
import {useLogout} from "../hooks/useLogout";

//import components (views)
import Sidebar from "../components/Sidebar";
import ClockScreen from "../components/ClockScreen";
import ViewPay from "./ViewPay";
import ManageEmployees from "./ManageEmployees";
import ManageManagers from "./ManageManagers";


//import the backends ip address
const API_BASE = import.meta.env.VITE_API_BASE;


export default function Dashboard() {

  //get username from the url
  const {username} = useParams();

  //triggered when user clocks in or out
  const [refreshTrigger, setRefreshTrigger] = useState(0);

  //listening value for clock in status
  const isClockedIn = useClockInStatus(username, refreshTrigger);

  //manage a changing sub-view
  const [activePage, setActivePage] = useState('punch');

  const storedUser = JSON.parse(localStorage.getItem("user"));
  //import hooks
  const {handleClockIn, handleClockOut} = useClockPunch(username, setRefreshTrigger, setActivePage);
  const logout = useLogout();


  return (
    <div className="d-flex flex-column vh-100">

      {/* Top Navigation Bar */}
      <header className="bg-secondary text-white px-4 py-2">
        <h5 className="mb-0">User Dashboard</h5>
      </header>

      {/* Main content area */}
      <div className="d-flex flex-grow-1">

        {/* Sidebar */}
        <Sidebar
          onClockIn={handleClockIn}
          onClockOut={handleClockOut}
          onViewPay={() => setActivePage("pay")}
          onLogout={logout}
          onManageEmployees={() => setActivePage("manage-employees")}
          onManageManagers={() => setActivePage("manage-managers")}
          role = {storedUser?.role}
        />

        {/* Page Content */}
        <main className="flex-grow-1 p-5">

          {/* Show clock in/clock out dashboard */}
          {activePage === 'punch' && (
            <ClockScreen
              username = {username}
              isClockedIn={isClockedIn}
              refreshTrigger={refreshTrigger}
            />
          )}

          {/* show pay information dashboard */}
          {activePage === 'pay' && (
            <ViewPay
              username={username}
              refreshTrigger={refreshTrigger} 
            />
          )}

          {/* show manage employees dashboard */}
          {activePage === 'manage-employees' && (
            <ManageEmployees
              username={username}
              refreshTrigger={refreshTrigger}
            />
          )}
          
          {/* Show manage managers dashboard*/}
          {activePage === 'manage-managers' && (
            <ManageManagers
              username={username}
              refreshTrigger={refreshTrigger}
            />
          )}
        </main>
      </div>
    </div>
  );
}
