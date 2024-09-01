import React, { useState, useContext } from 'react';
import './Styles/Style.css';
import { useNavigate } from 'react-router';
import { TextField, Button, FormControl, InputLabel, Select, MenuItem } from '@mui/material';
import axios from 'axios';
import { UserContext } from '../../UserContext/UserContext';

const SignUp = () => {
  const [username, setUsername] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [role, setRole] = useState('');
  const [address, setAddress] = useState('');
  const [phoneNumber, setPhoneNumber] = useState('');
  const { login } = useContext(UserContext);
  const navigate = useNavigate();

  const handleSubmit = async (event) => {


    event.preventDefault();

    if (!username) {
      alert('Please enter a valid username.');
      return;
    }

    if (!email) {
      alert('Please enter an email.');
      return;
    }

    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailPattern.test(email)) {
      alert('Please enter a valid email address.');
      return;
    }

    if (!password) {
      alert('Please enter a password.');
      return;
    }

    const passRegex = /^[a-zA-Z0-9._-]{8,}$/;
    if (!passRegex.test(password)) {
      alert('Please enter a valid password with at least 8 characters.');
      return;
    }

    if (password !== confirmPassword) {
      alert('Passwords do not match.');
      return;
    }

    if (!role) {
      alert('Please select a role.');
      return;
    }

    if (!address) {
      alert('Please enter an address.');
      return;
    }

    const phonePattern = /^[0-9]{10}$/;
    if (!phonePattern.test(phoneNumber)) {
      alert('Please enter a valid phone number with 10 digits.');
      return;
    }

    try {
      const userData = { "name":username, "email":email, "password":password, "role":role };
      console.log(userData);
      
      const response = await axios.post('http://localhost:5454/auth/api/users_list/signup', userData);

      if (response.status === 200) {
        const user = {
            "name":response.data.name,
            "role":response.data.role
        }

        login(user);

        alert('Successful Registration');
        navigate('/'); // Navigate to the login page
      }
    } catch (error) {
      alert('An error occurred. Please try again.');
      console.error('Registration error:', error);
    }
  };

  const handleLoginRedirect = () => {
    navigate('/'); // Navigate to the login page
  };

  return (
    <div className="signup-container">
      <div className="signup-box">
        <form onSubmit={handleSubmit} className="signup-form">
          <h1 className="signup-title">Sign Up</h1>
          <TextField
            type="text"
            label="Username"
            className="signup-input"
            placeholder="Enter Username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            fullWidth
            margin="normal"
          />
          <TextField
            type="email"
            label="Email"
            className="signup-input"
            placeholder="Enter Email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            fullWidth
            margin="normal"
          />
          <TextField
            type="password"
            label="Password"
            className="signup-input"
            placeholder="Enter Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            fullWidth
            margin="normal"
          />
          <TextField
            type="password"
            label="Confirm Password"
            className="signup-input"
            placeholder="Confirm Password"
            value={confirmPassword}
            onChange={(e) => setConfirmPassword(e.target.value)}
            fullWidth
            margin="normal"
          />
          <TextField
            type="text"
            label="Address"
            className="signup-input"
            placeholder="Enter Address"
            value={address}
            onChange={(e) => setAddress(e.target.value)}
            fullWidth
            margin="normal"
          />
          <TextField
            type="text"
            label="Phone Number"
            className="signup-input"
            placeholder="Enter Phone Number"
            value={phoneNumber}
            onChange={(e) => setPhoneNumber(e.target.value)}
            fullWidth
            margin="normal"
          />
          <FormControl fullWidth margin="normal">
            <InputLabel>Role</InputLabel>
            <Select
              value={role}
              onChange={(e) => setRole(e.target.value)}
              label="Role"
            >
              <MenuItem value="User">User</MenuItem>
              <MenuItem value="Admin">Admin</MenuItem>
            </Select>
          </FormControl>
          <Button type="submit" variant="contained" className="signup-submit-button">
            Submit
          </Button>
          <p className="signup-already-user">Already a user?</p>
          <Button variant="outlined" className="signup-login-button" onClick={handleLoginRedirect}>
            Login
          </Button>
        </form>
      </div>
    </div>
  );
};

export default SignUp;