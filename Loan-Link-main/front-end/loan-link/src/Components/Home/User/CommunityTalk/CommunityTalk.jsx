import React, { useState, useEffect, useContext } from 'react';
import axios from 'axios';
import { UserContext } from '../../../UserContext/UserContext';
import './Community.css'; // Assume you have some basic styling in this CSS file
import { useNavigate } from 'react-router-dom'; // Import useNavigate for routing

const Comment = () => {
  const { user } = useContext(UserContext);
  const [message, setMessage] = useState('');
  const [comments, setComments] = useState([]);
  const navigate = useNavigate(); // Hook for navigation

  useEffect(() => {
    fetchComments();
  }, []);

  const fetchComments = async () => {
    try {
      const response = await axios.get('http://localhost:5454/auth/api/users_list/getcomments');
      setComments(response.data);
    } catch (error) {
      console.error('Error fetching comments:', error);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!message.trim()) return;

    const newComment = {
      username: user.name,
      role: user.role,
      message,
      timestamp: new Date().toISOString()
    };

    try {
      await axios.post('http://localhost:5454/auth/api/users_list/postcomments', newComment);
      setComments([...comments, newComment]);
      setMessage('');
    } catch (error) {
      console.error('Error posting comment:', error);
    }
  };

  const handleBack = () => {
    navigate('/Home'); // Redirect to home or desired route
  };

  return (
    <div className="comment-section">
      <h2>Community Forum</h2>

      <form className="comment-form" onSubmit={handleSubmit}>
        <textarea
          className="comment-input"
          value={message}
          onChange={(e) => setMessage(e.target.value)}
          placeholder="Leave a comment..."
        ></textarea>
        <button type="submit" className="comment-submit">Post Comment</button>
      </form>

      <div className="comments-list">
        {comments.map((comment, index) => (
          <div key={index} className="comment-item">
            <div className="comment-header">
              <strong>{comment.username}</strong>
              <span className={`role-badge ${comment.role === 'admin' ? 'admin' : 'user'}`}>
                {comment.role}
              </span>
              <span className="timestamp">{new Date(comment.timestamp).toLocaleString()}</span>
            </div>
            <p className="comment-message">{comment.message}</p>
          </div>
        ))}
      </div>

      <button className="back-button" onClick={handleBack}>Back</button>
    </div>
  );
};

export default Comment;
