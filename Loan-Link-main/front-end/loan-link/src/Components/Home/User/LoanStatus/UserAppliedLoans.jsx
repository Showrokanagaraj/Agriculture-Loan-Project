import React, { useContext, useEffect, useState } from 'react';
import axios from 'axios';
import { UserContext } from '../../../UserContext/UserContext';
import { useNavigate } from 'react-router-dom';
import './UserAppliedLoans.css'; // Import the CSS file

const LoanApplicationsByEmail = () => {
    const { user } = useContext(UserContext);
    const [loanApplications, setLoanApplications] = useState([]);
    const [error, setError] = useState('');
    const navigate = useNavigate();

    useEffect(() => {
        if (user && user.email) {
            fetchLoanApplications(user.email);
        }
    }, [user]);

    const fetchLoanApplications = async (email) => {
        try {
            const response = await axios.get(`http://localhost:5454/auth/api/users_list/getemail`, {
                params: { email }
            });
            setLoanApplications(response.data);
            setError('');
        } catch (error) {
            setError('Failed to fetch loan applications. Please try again.');
            setLoanApplications([]);
        }
    };

    const handleBackClick = () => {
        navigate('/home');
    };

    return (
        <div className="loanapplication-container">
            <h2 className="loanapplication-heading">Your Loan Applications</h2>
            
            {error && <p className="loanapplication-error">{error}</p>}
            
            {loanApplications.length > 0 ? (
                <table className="loanapplication-table">
                    <thead>
                        <tr>
                            <th>Loan ID</th>
                            <th>Loan Name</th>
                            <th>Loan Amount</th>
                            <th>Interest Rate</th>
                            <th>Loan Tenure</th>
                            <th>Application Status</th>
                        </tr>
                    </thead>
                    <tbody>
                        {loanApplications.map((loan) => (
                            <tr key={loan.id}>
                                <td>{loan.id}</td>
                                <td>{loan.loanName}</td>
                                <td>{loan.loanAmount}</td>
                                <td>{loan.interestRate}</td>
                                <td>{loan.loanTenure}</td>
                                <td>{loan.status}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            ) : (
                <p>No loan applications found for your email.</p>
            )}

            <button className="loanapplication-back-button" onClick={handleBackClick}>Back</button>
        </div>
    );
};

export default LoanApplicationsByEmail;
