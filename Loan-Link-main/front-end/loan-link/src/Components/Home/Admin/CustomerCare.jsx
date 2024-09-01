import React, { useState, useEffect } from 'react';
import axios from 'axios';
// import './InquiryList.css';

const InquiryList = () => {
  const [inquiries, setInquiries] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [replies, setReplies] = useState({}); // Track replies for each inquiry
  const [replyError, setReplyError] = useState(null);
  const [sending, setSending] = useState(false); // Track the sending state

  useEffect(() => {
    const fetchInquiries = async () => {
      try {
        const response = await axios.get('http://localhost:5454/auth/api/users_list/inquirylist');
        setInquiries(response.data);
      } catch (error) {
        setError('Error fetching inquiries.');
      } finally {
        setLoading(false);
      }
    };

    fetchInquiries();
  }, []);

  const handleReplyChange = (id, value) => {
    setReplies((prevReplies) => ({ ...prevReplies, [id]: value }));
  };

  const handleReplySubmit = async (id) => {
    setSending(true);
    try {
      await axios.post(`http://localhost:5454/auth/api/users_list/inquiries/${id}/reply`, {
        reply: replies[id],
      });
      alert('Reply sent successfully!');
      setReplies((prevReplies) => ({ ...prevReplies, [id]: '' }));
    } catch (error) {
      setReplyError('Error sending reply.');
    } finally {
      setSending(false);
    }
  };

  if (loading) return <p>Loading inquiries...</p>;
  if (error) return <p>{error}</p>;

  return (
    <div className="inquirylist-container">
      <h2 className="inquirylist-title">Inquiries List</h2>
      <table className="inquirylist-table">
        <thead>
          <tr>
            <th>Name</th>
            <th>Email</th>
            <th>Message</th>
            <th>Reply</th>
          </tr>
        </thead>
        <tbody>
          {inquiries.map((inquiry) => (
            <tr key={inquiry.id}>
              <td>{inquiry.name}</td>
              <td>{inquiry.email}</td>
              <td>{inquiry.message}</td>
              <td>
                <textarea
                  value={replies[inquiry.id] || ''}
                  onChange={(e) => handleReplyChange(inquiry.id, e.target.value)}
                  className="inquirylist-textarea"
                />
                <button
                  onClick={() => handleReplySubmit(inquiry.id)}
                  disabled={sending} // Disable button while sending
                  className="inquirylist-button"
                >
                  {sending ? 'Sending...' : 'Send Reply'}
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      {replyError && <p className="inquirylist-error">{replyError}</p>}
    </div>
  );
};

export default InquiryList;
