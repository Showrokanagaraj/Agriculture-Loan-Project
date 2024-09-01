import React, { useState } from 'react';
import axios from 'axios';

const UserDetailsModal = ({ user, onClose, onUpdate }) => {
  const [username, setUsername] = useState(user.name);

  const handleUpdate = async () => {
    try {
      const response = await axios.put(`http://localhost:8484/auth/api/users_list/updateUser/${user.id}`, {
        name: username,
        email: user.email // Assuming you want to update email as well; otherwise, omit this line.
      });
      
      if (response.status === 200) {
        onUpdate(response.data); // Notify parent component about the update
        onClose(); // Close the modal
      } else {
        alert('Update failed');
      }
    } catch (error) {
      console.error('Error updating user details:', error);
      alert('Error updating user details');
    }
  };

  return (
    <div className="modal-overlay">
      <div className="modal-content">
        <button className="modal-close" onClick={onClose}>X</button>
        <h2>User Details</h2>
        <p><strong>Email:</strong> {user.email}</p>
        <div>
          <label><strong>Username:</strong></label>
          <input
            type="text"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
          />
        </div>
        <button onClick={handleUpdate}>Update</button>
      </div>
    </div>
  );
};

export default UserDetailsModal;
