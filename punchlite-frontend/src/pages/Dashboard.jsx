import React from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import {useParams} from "react-router-dom";

export default function Dashboard() {
  const {username} = useParams();

  return (
    <div className="d-flex flex-column vh-100">
      {/* Top Navigation Bar */}
      <header className="bg-secondary text-white px-4 py-2">
        <h5 className="mb-0">Time Clock - Page Specific Actions</h5>
      </header>

      {/* Main content area */}
      <div className="d-flex flex-grow-1">
        {/* Sidebar */}
        <aside className="bg-primary text-white p-3" style={{ width: "250px" }}>
          {/* Logo */}
          <div className="bg-dark text-center py-3 mb-4">
            <h4 className="mb-0">LOGO</h4>
          </div>

          {/* Navigation Buttons */}
          <div className="d-grid gap-2">
            <button className="btn btn-outline-light">Clock In</button>
            <button className="btn btn-outline-light">Clock Out</button>
            <button className="btn btn-outline-light">My Time Entries</button>
            <button className="btn btn-outline-light">Reports</button>
            <button className="btn btn-outline-light">Settings</button>
          </div>
        </aside>

        {/* Page Content */}
        <main className="flex-grow-1 p-5">
          <h3>Welcome, {username}</h3>
          <p className="lead">This is your time clock dashboard. Use the menu to manage your hours.</p>
        </main>
      </div>
    </div>
  );
}
