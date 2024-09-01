import React, { useState, useEffect } from 'react';
import axios from 'axios';

const AdminLoanApplication = () => {
    const [loanApplications, setLoanApplications] = useState([]);
    const [error, setError] = useState('');

    useEffect(() => {
        fetchAllLoanApplications();
    }, []);

    const fetchAllLoanApplications = async () => {
        try {
            const response = await axios.get('http://localhost:5454/auth/api/users_list/get_all_loans');
            setLoanApplications(response.data);
        } catch (error) {
            setError('Failed to fetch loan applications.');
        }
    };

    const handleStatusChange = async (loanId, status) => {
        try {
            await axios.put('http://localhost:5454/auth/api/users_list/update_loan_status', {
                id: loanId,
                status: status
            });
            setLoanApplications(prevLoans =>
                prevLoans.map(loan =>
                    loan.id === loanId ? { ...loan, status: status } : loan
                )
            );
        } catch (error) {
            console.error("Failed to update loan status:", error);
            setError('Failed to update loan status.');
        }
    };
    

    return (
        <div className="admin-loan-application">
            <h2>Admin Loan Applications</h2>
            {error && <p className="error">{error}</p>}
            {loanApplications.length > 0 ? (
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Aadhar Number</th>
                            <th>Bank Details</th>
                            <th>Email</th>
                            <th>Interest Rate</th>
                            <th>Loan Amount</th>
                            <th>Loan ID</th>
                            <th>Loan Name</th>
                            <th>Loan Tenure</th>
                            <th>PAN Number</th>
                            <th>Status</th>
                            <th>Username</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        {loanApplications.map((loan) => (
                            <tr key={loan.id}>
                                <td>{loan.id}</td>
                                <td>{loan.aadharNumber}</td>
                                <td>{loan.bankDetails}</td>
                                <td>{loan.email}</td>
                                <td>{loan.interestRate}</td>
                                <td>{loan.loanAmount}</td>
                                <td>{loan.loanId}</td>
                                <td>{loan.loanName}</td>
                                <td>{loan.loanTenure}</td>
                                <td>{loan.panNumber}</td>
                                <td>{loan.status}</td>
                                <td>{loan.username}</td>
                                <td>
                                    <select
                                        value={loan.status}
                                        onChange={(e) => handleStatusChange(loan.id, e.target.value)}
                                    >
                                        <option value="pending">Pending</option>
                                        <option value="approved">Approved</option>
                                        <option value="rejected">Rejected</option>
                                        <option value="review">Review</option>
                                    </select>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            ) : (
                <p>No loan applications found.</p>
            )}
        </div>
    );
};

export default AdminLoanApplication;
