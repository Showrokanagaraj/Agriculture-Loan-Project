import React, { useState, useContext, useEffect } from 'react';
import axios from 'axios';
import { UserContext } from '../../../UserContext/UserContext';
// import './InquiryForm.css';

const InquiryForm = () => {
  const { user, isLoggedIn } = useContext(UserContext);
  const [message, setMessage] = useState('');
  const [reply, setReply] = useState('');
  const [error, setError] = useState(null);

  // State for form fields
  const [name, setName] = useState('');
  const [email, setEmail] = useState('');

  // Function to fetch the reply based on user email
  const fetchReply = async () => {
    try {
      const response = await axios.get(`http://localhost:5454/auth/api/users_list/reply/${user.email}`);
      if (response.data) {
        setReply(response.data.reply || 'No reply available.');
      } else {
        setReply('No reply available.');
      }
    } catch (error) {
      setError('Error fetching reply.');
    }
  };

  // Fetch user data and initial inquiry reply when the component mounts
  useEffect(() => {
    if (user && user.email) {
      setName(user.name || '');
      setEmail(user.email || '');
      fetchReply();
    }
  }, [user]);

  // Handle form submission
  const handleSubmit = async (event) => {
    event.preventDefault();

    if (!isLoggedIn) {
      alert('You must be logged in to submit an inquiry.');
      return;
    }

    const inquiryData = {
      name,
      email,
      message,
    };

    try {
      await axios.post('http://localhost:5454/auth/api/users_list/inquiry', inquiryData);

      // Refetch the reply after submission
      fetchReply();

      alert('Inquiry submitted successfully!');
      setMessage('');
    } catch (error) {
      setError('Error submitting inquiry.');
    }
  };

  return (
    <div className="inquiryform-container">
      <h2 className="inquiryform-title">Submit Your Inquiry</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label className="inquiryform-label">Name:</label>
          <input type="text" value={name} readOnly className="inquiryform-input" />
        </div>
        <div>
          <label className="inquiryform-label">Email:</label>
          <input type="email" value={email} readOnly className="inquiryform-input" />
        </div>
        <div>
          <label className="inquiryform-label">Message:</label>
          <textarea
            value={message}
            onChange={(e) => setMessage(e.target.value)}
            required
            className="inquiryform-textarea"
          ></textarea>
        </div>
        <button type="submit" className="inquiryform-button">Submit</button>
      </form>
      {reply && (
        <div className="inquiryform-reply">
          <h3 className="inquiryform-reply-title">Reply:</h3>
          <p className="inquiryform-reply-content">{reply}</p>
        </div>
      )}
      {error && <p className="inquiryform-error">{error}</p>}
    </div>
  );
};

export default InquiryForm;
