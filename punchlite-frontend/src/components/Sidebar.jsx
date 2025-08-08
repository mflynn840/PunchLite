import logoImg from '../assets/logo.png';

/**
 * This hook handles the sidebar view
 * -handle clockIns using the useClockPunch hook
 * 
 * @Params handlers for the four buttons
 */
export default function Sidebar({onClockIn, onClockOut, onViewPay, onLogout, onManageEmployees, role}){
    return(<aside className="bg-primary text-white p-3" style={{ width: "250px" }}>
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
            <button className="btn btn-outline-light" onClick={onClockIn}>Clock In</button>
            <button className="btn btn-outline-light" onClick={onClockOut}>Clock Out</button>
            <button className="btn btn-outline-light" onClick={onViewPay}>View Pay</button>

            {/* Only render the manage-employees button for managers */}
            {role.includes("MANAGER") && (
              <button className="btn btn-outline-light" onClick={onManageEmployees}> Manage Employees</button>
            )}

            <button className="btn btn-outline-light" onClick={onLogout}>Logout</button>
          </div>
        </aside>
    );

}