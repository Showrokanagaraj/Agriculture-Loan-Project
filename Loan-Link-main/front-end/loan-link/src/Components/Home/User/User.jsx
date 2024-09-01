import React, { useContext, useState } from 'react';
import './styles/User.css';
import { FaBell, FaHome, FaUsers, FaFileAlt, FaNewspaper, FaPhoneAlt, FaSignOutAlt, FaFacebook, FaInstagram, FaTwitter, FaLinkedin } from 'react-icons/fa';
import ImageSlider from './ImageSlider/ImageSlider';
import { useNavigate } from 'react-router-dom';
import { UserContext } from '../../UserContext/UserContext';

const User = () => {
  const [menuOpen, setMenuOpen] = useState(false);
  const [activeLink, setActiveLink] = useState('home');
  const [showModal, setShowModal] = useState(false); // State to control modal visibility
  const navigate = useNavigate();
  const { user, logout } = useContext(UserContext);

  const firstLetter = user?.name ? user.name.charAt(0).toUpperCase() : 'U';

  const toggleMenu = () => {
    setMenuOpen(prevMenuOpen => !prevMenuOpen);
  };

  const handleLinkClick = (link) => {
    if (window.innerWidth <= 768) {
      setMenuOpen(false);
    }
    setActiveLink(link);
  };

  const handleProfileClick = () => {
    // Toggle modal visibility
    setShowModal(true);
  };

  const handleCloseModal = () => {
    setShowModal(false);
  };

  const GOTOLOANDETAILS = () => {
    navigate('/PATHTOLOANDETAILS'); // Replace with actual path
  };

  const GOTOCTFF = () => {
    navigate('/PATHTOCFT'); // Replace with actual path
  };

  const GOTOMYLOANPAGE = () => {
    navigate('/PATHTOMYLOANS'); // Replace with actual path
  };
  const GOTOCUSTOMERCARE=()=>{
    navigate('/PATHTOCUSTOMERCARE')
  }

  return (
    <div className='UserHomePageContainer'>
      <div className='UserHomePageNavbar'>
        <div className='profile' onClick={handleProfileClick}>
          <span>{firstLetter}</span> {/* Display the first letter of the username */}
        </div>
        <div className='menu-button' onClick={toggleMenu}>
          <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="#ffffff" width="24px" height="24px">
            <path d="M0 0h24v24H0z" fill="none"/>
            <path d="M4 6h16v2H4zm0 5h16v2H4zm0 5h16v2H4z"/>
          </svg>
        </div>
        <nav className={menuOpen ? 'open' : ''}>
          <ul>
            <li className={activeLink === 'home' ? 'active' : ''}>
              <a href="#home" onClick={() => handleLinkClick('home')}><FaHome size={24} /> Home</a>
            </li>
            <li className={activeLink === 'community-forum' ? 'active' : ''}>
              <a onClick={GOTOCTFF}><FaUsers size={24} /> Community Talks</a>
            </li>
            <li className={activeLink === 'loans' ? 'active' : ''}>
              <a onClick={GOTOLOANDETAILS} className='LOANPOINTER'><FaFileAlt size={24} /> Loans</a>
            </li>
            <li className={activeLink === 'news-now' ? 'active' : ''}>
              <a href="#news-now" onClick={GOTOMYLOANPAGE}><FaNewspaper size={24} /> My Loans</a>
            </li>
            <li className='notifications'>
              <a href="#notifications" onClick={() => handleLinkClick('notifications')}><FaBell size={24} /></a>
            </li>
            <li className='logout'>
              <a href="/PATHTOlogoutfromUser" onClick={(e) => { logout(); }}><FaSignOutAlt size={24} /> Logout</a>
            </li>
          </ul>
        </nav>
      </div>
      <div className='UserHomePageBody'>
        <div className={`sidebar ${menuOpen ? 'hidden' : ''}`}>
          <ul>
            <li><a href="https://facebook.com" target="_blank" rel="noopener noreferrer"><FaFacebook size={30} /></a></li>
            <li><a href="https://instagram.com" target="_blank" rel="noopener noreferrer"><FaInstagram size={30} /></a></li>
            <li><a href="https://twitter.com" target="_blank" rel="noopener noreferrer"><FaTwitter size={30} /></a></li>
            <li><a href="https://linkedin.com" target="_blank" rel="noopener noreferrer"><FaLinkedin size={30} /></a></li>
          </ul>
        </div>
        <div className='main-content'>
          <div className='img11'>
            
          </div>
          <ImageSlider />
          
        </div>
      </div>
      <div className='UserHomePageFooter'>
        <p>&copy; 2024 Agro Loan Company. All rights reserved.</p>
      </div>

      {/* Modal for displaying user details */}
      {showModal && (
        <div className='user-details-modal'>
          <div className='modal-content'>
            <span className='close-button' onClick={handleCloseModal}>&times;</span>
            <h2>User Details</h2>
            <p><strong>Name:</strong> {user?.name}</p>
            <p><strong>Email:</strong> {user?.email}</p>
            <p><strong>Role:</strong> {user?.role}</p>
            {/* Add more user details as needed */}
          </div>
        </div>
      )}
    </div>
  );
}

export default User;
