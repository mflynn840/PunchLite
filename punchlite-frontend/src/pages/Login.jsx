
//import react and the useState hook
import React, { useState } from 'react';

import '../styles/Login.css'

//use react router to switch between pages
import { useNavigate } from 'react-router-dom'; 

const API_BASE = import.meta.env.VITE_API_BASE;

// Create a component that displays user registration form
function Login() {

    const navigate = useNavigate();

    //handler for switching to the register page
    const goToRegister = () => {
      navigate("/register");
    };


    //initilize an empty form and store the values in the json format
    const [formData, setFormData] = useState({
        username: '',
        password: '',
    });


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
            const response = await fetch(`${API_BASE}/api/auth/login`, {
                method: 'POST',  //HTTP send method
                headers: {'Content-Type': 'application/json'}, //Tell backend we are sending a json
                body: JSON.stringify(formData), //convert form data to a json and use as body
            });

            //If an error occurs when registering
            if (!response.ok) throw new Error('Login failed');

            const responseDat = await response.json();
            //store the returned JWT token to get access to the dashboard
            localStorage.setItem("token", responseDat.token);
            localStorage.setItem("user", JSON.stringify(responseDat.user))
            setStatus('Login Succesful');

            //Go to the users dashboard
            navigate(`/user/${responseDat.user.username}`)

            // Clear the form after a succesful registration
            setFormData({
                username: '',
                password: '',
            });

            
        //if a JS error occurs print it
        }catch (err){
            setStatus(`ERROR: ${err.message}`);

        }
    };


    // Render the form on the page
    return (
      <div className="register-container" style={{ maxWidth: '400px', margin: '2rem auto' }}>
        <h2 className="login-title">Login</h2>

        <form onSubmit={handleSubmit} className="login-form">

          <input
            name="username"
            placeholder="Username"
            value={formData.username}
            onChange={handleInput}
            required
            className="login-input"
          />
          <br />


          <input
            name="password"
            type="password"
            placeholder="Password"
            value={formData.password}
            onChange={handleInput}
            required
            className="login-input"
          />
          <br />

          <button type="submit" className="login-button">Login</button>
          <button type="button" className="login-button" onClick={goToRegister}>Register Account</button>
        </form>

        {status && <p className="login-status">{status}</p>}
      </div>
    );

}

export default Login;