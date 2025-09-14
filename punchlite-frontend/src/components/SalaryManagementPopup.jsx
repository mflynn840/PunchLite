import React, { useState } from 'react';

const API_BASE = import.meta.env.VITE_API_BASE;

function SalaryManagementPopup({ isOpen, onClose, employee, managerUsername, onSuccess }) {
    const [hourlyRate, setHourlyRate] = useState(employee?.hourlyRate || 0);
    const [isLoading, setIsLoading] = useState(false);
    const [error, setError] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();
        setIsLoading(true);
        setError('');

        try {
            const token = localStorage.getItem("token");
            const response = await fetch(`${API_BASE}/api/users/${employee.id}/hourly-rate`, {
                method: 'PUT',
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    newHourlyRate: parseFloat(hourlyRate),
                    employeeUsername: employee.username,
                    managerUsername: managerUsername
                })
            });

            if (!response.ok) {
                const errorData = await response.text();
                throw new Error(errorData || 'Failed to update hourly rate');
            }

            onSuccess();
            onClose();
        } catch (err) {
            setError(err.message);
        } finally {
            setIsLoading(false);
        }
    };

    const handleClose = () => {
        setError('');
        setHourlyRate(employee?.hourlyRate || 0);
        onClose();
    };

    if (!isOpen) return null;

    return (
        <div style={{
            position: 'fixed',
            top: 0,
            left: 0,
            right: 0,
            bottom: 0,
            backgroundColor: 'rgba(0, 0, 0, 0.5)',
            display: 'flex',
            justifyContent: 'center',
            alignItems: 'center',
            zIndex: 1000
        }}>
            <div style={{
                backgroundColor: 'white',
                padding: '30px',
                borderRadius: '8px',
                boxShadow: '0 4px 6px rgba(0, 0, 0, 0.1)',
                minWidth: '400px',
                maxWidth: '500px',
                fontFamily: "'Times New Roman', serif"
            }}>
                <h2 style={{ marginTop: 0, marginBottom: '20px', color: '#333' }}>
                    Manage Salary - {employee?.username}
                </h2>
                
                <form onSubmit={handleSubmit}>
                    <div style={{ marginBottom: '20px' }}>
                        <label style={{ 
                            display: 'block', 
                            marginBottom: '8px', 
                            fontWeight: 'bold',
                            color: '#555'
                        }}>
                            Hourly Rate ($):
                        </label>
                        <input
                            type="number"
                            step="0.01"
                            min="0"
                            value={hourlyRate}
                            onChange={(e) => setHourlyRate(e.target.value)}
                            style={{
                                width: '100%',
                                padding: '10px',
                                border: '1px solid #ddd',
                                borderRadius: '4px',
                                fontSize: '16px',
                                boxSizing: 'border-box'
                            }}
                            required
                        />
                    </div>

                    {error && (
                        <div style={{
                            color: 'red',
                            marginBottom: '15px',
                            padding: '10px',
                            backgroundColor: '#ffe6e6',
                            border: '1px solid #ffcccc',
                            borderRadius: '4px'
                        }}>
                            {error}
                        </div>
                    )}

                    <div style={{ 
                        display: 'flex', 
                        gap: '10px', 
                        justifyContent: 'flex-end',
                        marginTop: '20px'
                    }}>
                        <button
                            type="button"
                            onClick={handleClose}
                            style={{
                                padding: '10px 20px',
                                border: '1px solid #ddd',
                                borderRadius: '4px',
                                backgroundColor: 'white',
                                cursor: 'pointer',
                                fontSize: '14px'
                            }}
                        >
                            Cancel
                        </button>
                        <button
                            type="submit"
                            disabled={isLoading}
                            style={{
                                padding: '10px 20px',
                                border: 'none',
                                borderRadius: '4px',
                                backgroundColor: isLoading ? '#ccc' : '#007bff',
                                color: 'white',
                                cursor: isLoading ? 'not-allowed' : 'pointer',
                                fontSize: '14px'
                            }}
                        >
                            {isLoading ? 'Updating...' : 'Update Salary'}
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
}

export default SalaryManagementPopup;
