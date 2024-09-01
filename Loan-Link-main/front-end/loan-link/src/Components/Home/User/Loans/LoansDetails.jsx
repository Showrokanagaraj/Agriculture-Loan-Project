import React, { useState, useEffect, useContext } from 'react';
import axios from 'axios';
import './LoansDetails.css';
import { useNavigate } from 'react-router-dom';
import { UserContext } from '../../../UserContext/UserContext';

const LoansDetails = () => {
  const navigate = useNavigate();
  const { user } = useContext(UserContext);
  const [loanDetails, setLoanDetails] = useState([]);
  const [selectedLoan, setSelectedLoan] = useState(null);
  const [userDetails, setUserDetails] = useState({
    username: user.name || '',
    email: user.email || '',
    aadharNumber: '',
    panNumber: '',
    bankDetails: '',
    loanId: '',
    loanName: '',
    loanAmount: '',
    interestRate: '',
    loanTenure: '',
    status: 'pending', // Add status field
  });

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setUserDetails({
      ...userDetails,
      [name]: value,
    });
  };

  const handleApplyClick = (loan) => {
    setSelectedLoan(loan);
    setUserDetails({
      ...userDetails,
      loanId: loan.id,
      loanName: loan.loanName,
      loanAmount: loan.loanAmount,
      interestRate: loan.interestRate,
      loanTenure: loan.loanTenure,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const loanApplicationData = {
      username: userDetails.username,
      email: userDetails.email,
      aadharNumber: userDetails.aadharNumber,
      panNumber: userDetails.panNumber,
      bankDetails: userDetails.bankDetails,
      loanId: userDetails.loanId,
      loanName: userDetails.loanName,
      loanAmount: userDetails.loanAmount,
      interestRate: userDetails.interestRate,
      loanTenure: userDetails.loanTenure,
      status: userDetails.status, // Include status in submission
    };

    try {
      const response = await axios.post('http://localhost:5454/auth/api/users_list/save_loan_data', loanApplicationData);
      if (response.status === 200) {
        alert('Form submitted successfully!');
        navigate('/Home');
      } else {
        alert('There was a problem submitting the form. Please try again.');
      }
    } catch (error) {
      console.error('Error submitting form:', error);
      alert('An error occurred while submitting the form. Please try again.');
    }
  };

  useEffect(() => {
    const fetchLoanDetails = async () => {
      try {
        const response = await axios.get('http://localhost:5454/auth/api/users_list');
        setLoanDetails(response.data);
      } catch (error) {
        console.error('Error fetching loan details:', error);
      }
    };

    fetchLoanDetails();
  }, []);

  return (
    <div className="loans-details-page">
      <div className="leftsideofpageofloandetails">
        <h2>Available Loans</h2>
        <ul>
          {loanDetails.map((loan) => (
            <li key={loan.id} className="loan-item">
              <h3>{loan.loanName}</h3>
              <p><strong>Amount:</strong> ${loan.loanAmount}</p>
              <p><strong>Interest Rate:</strong> {loan.interestRate}%</p>
              <p><strong>Tenure:</strong> {loan.loanTenure} months</p>
              <button 
                className="apply-button" 
                onClick={() => handleApplyClick(loan)}
              >
                Apply
              </button>
            </li>
          ))}
        </ul>
      </div>
      <div className="rightsideofpageofloandetails">
        {selectedLoan && (
          <div className="loan-application-form">
            <h2>Apply for {selectedLoan.loanName}</h2>
            <form onSubmit={handleSubmit}>
              <div className="form-group">
                <label>Username</label>
                <input 
                  type="text" 
                  name="username" 
                  value={userDetails.username} 
                  readOnly 
                />
              </div>
              <div className="form-group">
                <label>Email</label>
                <input 
                  type="email" 
                  name="email" 
                  value={userDetails.email} 
                  readOnly 
                />
              </div>
              <div className="form-group">
                <label>Aadhaar Number</label>
                <input 
                  type="text" 
                  name="aadharNumber" 
                  value={userDetails.aadharNumber} 
                  onChange={handleInputChange} 
                />
              </div>
              <div className="form-group">
                <label>PAN Number</label>
                <input 
                  type="text" 
                  name="panNumber" 
                  value={userDetails.panNumber} 
                  onChange={handleInputChange} 
                />
              </div>
              <div className="form-group">
                <label>Bank Details</label>
                <input 
                  type="text" 
                  name="bankDetails" 
                  value={userDetails.bankDetails} 
                  onChange={handleInputChange} 
                />
              </div>
              <button 
                type="button" 
                className="payment-button"
              >
                Make Payment
              </button>
              <button 
                type="submit" 
                className="submit-button"
              >
                Submit
              </button>
            </form>
          </div>
        )}
      </div>
      <button className="back-button" onClick={() => navigate('/Home')}>Back</button>
    </div>
  );
};

export default LoansDetails;
