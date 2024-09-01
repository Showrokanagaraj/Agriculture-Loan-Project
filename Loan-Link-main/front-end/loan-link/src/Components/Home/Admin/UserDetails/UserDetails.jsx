import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './Styles/Style.css';
import { useNavigate } from 'react-router-dom';

const UserDetails = () => {
  const [users, setUsers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [editingUser, setEditingUser] = useState(null);
  const [newUserDetails, setNewUserDetails] = useState({ name: '', role: '' });
  const navigate = useNavigate(); // For navigation

  useEffect(() => {
    axios.get('http://localhost:5454/auth/api/users_list/getAllUsers')
      .then((response) => {
        setUsers(response.data);
        setLoading(false);
      })
      .catch((error) => {
        console.error('Error fetching user data:', error);
        setError('Error fetching user details');
        setLoading(false);
      });
  }, []);

  const handleDelete = (id) => {
    axios.delete(`http://localhost:5454/auth/api/users_list/deleteUser/${id}`)
      .then(() => {
        setUsers(users.filter(user => user.id !== id));
      })
      .catch((error) => {
        console.error('Error deleting user:', error);
        setError('Error deleting user');
      });
  };

  const handleEdit = (user) => {
    setEditingUser(user);
    setNewUserDetails({ name: user.name, role: user.role });
  };

  const handleUpdate = () => {
    if (editingUser) {
      axios.put(`http://localhost:5454/auth/api/users_list/updateUser/${editingUser.id}`, newUserDetails)
        .then(() => {
          setUsers(users.map(user => user.id === editingUser.id ? { ...user, ...newUserDetails } : user));
          setEditingUser(null);
        })
        .catch((error) => {
          console.error('Error updating user:', error);
          setError('Error updating user');
        });
    }
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setNewUserDetails(prevDetails => ({ ...prevDetails, [name]: value }));
  };

  const handleBackFromUserDetails = () => {
    navigate(-1); // Navigate to the previous page
  };

  if (loading) {
    return <div className="userdetails-loading">Loading...</div>;
  }

  if (error) {
    return <div className="userdetails-error">{error}</div>;
  }

  return (
    <div className="userdetails-container">
      <h2>All User Details</h2>
      {users.length === 0 ? (
        <p>No users found.</p>
      ) : (
        <table className="userdetails-table">
          <thead>
            <tr>
              <th>Name</th>
              <th>Email</th>
              <th>Role</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {users.map((user) => (
              <tr key={user.id}>
                <td>{user.name}</td>
                <td>{user.email}</td>
                <td>{user.role}</td>
                <td>
                  <button className="userdetails-button" onClick={() => handleEdit(user)}>Edit</button>
                  <button className="userdetails-button" onClick={() => handleDelete(user.id)}>Delete</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
      
      {editingUser && (
        <div className="userdetails-edit-form">
          <h3>Edit User</h3>
          <label>
            Name:
            <input
              type="text"
              name="name"
              value={newUserDetails.name}
              onChange={handleChange}
              className="userdetails-input"
            />
          </label>
          <br />
          <label>
            Role:
            <input
              type="text"
              name="role"
              value={newUserDetails.role}
              onChange={handleChange}
              className="userdetails-input"
            />
          </label>
          <br />
          <button className="userdetails-button" onClick={handleUpdate}>Update</button>
          <button className="userdetails-button" onClick={() => setEditingUser(null)}>Cancel</button>
        </div>
      )}
      
      <button className="userdetails-back-button" onClick={handleBackFromUserDetails}>Back</button>
    </div>
  );
};

export default UserDetails;
