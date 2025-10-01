import React, { useState, useEffect } from 'react';
import '../styles/Login.css';
import { useNavigate } from 'react-router-dom';

const API_BASE = import.meta.env.VITE_API_BASE;

function Login() {
  const navigate = useNavigate();

  // Form data state
  const [formData, setFormData] = useState({
    username: '',
    password: '',
  });

  // Login status state
  const [status, setStatus] = useState(null);

  // Dark mode state
  const [darkMode, setDarkMode] = useState(false);

  // Load saved dark mode preference on mount
  useEffect(() => {
    const savedMode = localStorage.getItem('darkMode') === 'true';
    setDarkMode(savedMode);
  }, []);

  // Update body class when dark mode changes
  useEffect(() => {
    document.body.classList.toggle('dark-mode', darkMode);
  }, [darkMode]);

  // Toggle dark mode and save preference
  const toggleDarkMode = () => {
    const newMode = !darkMode;
    setDarkMode(newMode);
    localStorage.setItem('darkMode', newMode);
  };

  // Go to registration page
  const goToRegister = () => {
    navigate("/register");
  };

  // Handle input changes
  const handleInput = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  // Handle login form submission
  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await fetch(`${API_BASE}/api/auth/login`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(formData),
      });

      if (!response.ok) throw new Error('Login failed');

      const responseData = await response.json();

      localStorage.setItem("token", responseData.token);
      localStorage.setItem("user", JSON.stringify(responseData.user));
      setStatus('Login Successful');

      navigate(`/user/${responseData.user.username}`);

      setFormData({
        username: '',
        password: '',
      });

    } catch (err) {
      setStatus(`ERROR: ${err.message}`);
    }
  };

  return (
    <div className="register-container">
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
        <input
          name="password"
          type="password"
          placeholder="Password"
          value={formData.password}
          onChange={handleInput}
          required
          className="login-input"
        />

        <button type="submit" className="login-button">Login</button>
        <button type="button" className="secondary-button" onClick={goToRegister}>
          Register Account
        </button>
      </form>

      {status && <p className="login-status">{status}</p>}
    </div>
  );
}

export default Login;
