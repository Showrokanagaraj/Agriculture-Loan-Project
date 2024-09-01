import React, { useEffect, useState } from 'react';
import axios from 'axios';

const Checking = () => {
  const [users, setUsers] = useState([]);
  
  // Function to fetch user data
  const fetchUsers = async () => {
    try {
      const response = await axios.get('http://localhost:5454/admin/getAllUsers');
      if (response.status === 200) {
        setUsers(response.data.usersList);
      } else {
        alert("Failed to fetch users");
      }
    } catch (error) {
      console.error("Error fetching users:", error);
      alert("Error fetching users");
    }
  };

  // Fetch the data when the component mounts
  useEffect(() => {
    fetchUsers();
  }, []);

  return (
    <div>
      <h2>User List</h2>
      <ul>
        {users.length > 0 ? (
          users.map((user) => (
            <li key={user.id}>
              {user.name} - {user.email} - {user.city} - {user.role}
            </li>
          ))
        ) : (
          <p>No users found.</p>
        )}
      </ul>
    </div>
  );
};

export default Checking;
