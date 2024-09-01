import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import hill4Img from '../../../../Assets/adminpage1.jpg';

import './Imgslide.css';

const images = [
  { src: hill4Img, route: '/TOGOLoan' }
];

const ImageSlider = () => {
  const [currentIndex, setCurrentIndex] = useState(0);
  const navigate = useNavigate();

  const handleNext = () => {
    setCurrentIndex((prevIndex) => (prevIndex + 1) % images.length);
  };

  const handlePrev = () => {
    setCurrentIndex((prevIndex) => (prevIndex - 1 + images.length) % images.length);
  };

  const handleNavigate = () => {
    navigate('/PATHTOLOANDETAILS');
    

  };

  useEffect(() => {
    const handleScroll = () => {
      console.log('Scrolled');
    };

    window.addEventListener('scroll', handleScroll);
    return () => {
      window.removeEventListener('scroll', handleScroll);
    };
  }, []);

  useEffect(() => {
    const interval = setInterval(handleNext, 3000); // Change image every 3 seconds
    return () => clearInterval(interval); // Clear interval on component unmount
  }, []);

  return (
    <div className="slider">
      <button className="navigate-button prev" onClick={handlePrev}>
        Prev
      </button>
      <div className="slider-image-container">
        <img src={images[currentIndex].src} alt={`Slide ${currentIndex + 1}`} className="slider-image" />
      </div>
      <button className="navigate-button next" onClick={handleNext}>
        Next
      </button>
    </div>
  );
};

export default ImageSlider;
