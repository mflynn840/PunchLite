
//import react and the useState hook
import React, { useState } from 'react';
import '../styles/Register.css'
import { useNavigate } from 'react-router-dom'; 
const API_BASE = import.meta.env.VITE_API_BASE;

// Create a component that displays user registration form
function Register() {
    //initilize an empty form and store the values in the json format
    const [formData, setFormData] = useState({
        username: '',
        email: '',
        password: '',
        role: '',
    });

    const navigate = useNavigate();

    const goToLogin = (e) => {
      navigate("/login");
    };
    // Create a state to hold the success or error message from backend
    const [status, setStatus] = useState(null); 


    // Handler for user modifying input fields
    const handleInput = (e) => {
        const { name, value} = e.target; //obtain name and value from JSON element

        // Update the form state
        setFormData((prev) => ({
            ...prev, //keep unchanged fields the same
            [name]: value, //update the changed field
        }));
    };

    //Handle form submit button press
    const handleSubmit = async (e) => {
        e.preventDefault(); //prevent page reloading

        //try sending a POST request to the backend
        try {
            const res = await fetch(`${API_BASE}/api/auth/register`, {
                method: 'POST',  //HTTP send method
                headers: {'Content-Type': 'application/json'}, //Tell backend we are sending a json
                body: JSON.stringify(formData), //convert form data to a json and use as body
            });

            //If an error occurs when registering
            if (!res.ok) throw new Error('Registration failed');

            setStatus('User registration succesful');


            // Clear the form after a succesful registration
            setFormData({
                username: '',
                email: '',
                password: '',
                role: '',
            });
        //if a JS error occurs print it
        }catch (err){
            setStatus(`ERROR: ${err.message}`);

        }
    };


    // Render the form on the page
    return (
      <div className="register-container" style={{ maxWidth: '400px', margin: '2rem auto' }}>
        <h2 className="register-title">Register</h2>

        <form onSubmit={handleSubmit} className="register-form">

          <input
            name="username"
            placeholder="Username"
            value={formData.username}
            onChange={handleInput}
            required
            className="register-input"
          />
          <br />

          <input
            name="email"
            type="email"
            placeholder="Email"
            value={formData.email}
            onChange={handleInput}
            required
            className="register-input"
          />
          <br />

          <input
            name="password"
            type="password"
            placeholder="Password"
            value={formData.password}
            onChange={handleInput}
            required
            className="register-input"
          />
          <br />

          <select
            name="role"
            placeholder="Role"
            value={formData.role}
            onChange={handleInput}
            className="register-input"
          >
            <option value="" disabled> Select role </option>
            <option value="EMPLOYEE">Employee</option>
            <option value="MANAGER">Manager</option>
            <option value="ADMIN">Admin</option>

          </select>
          <br />

          <button type="submit" className="register-button">Register</button>
          <button type="button" className="register-button" onClick={goToLogin}>Login</button>
        </form>

        {status && <p className="register-status">{status}</p>}
      </div>
    );

}

export default Register;