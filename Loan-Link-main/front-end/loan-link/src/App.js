
import { Route, Routes } from 'react-router-dom';
import './App.css';
import User from './Components/Home/User/User';
import ImageSlider from './Components/Home/User/ImageSlider/ImageSlider';
import Checking from './Components/Checking/Checking';
import SignUp from './Components/Authentication/SignUp/SignUp';
import SignIn from './Components/Authentication/SignIn/SignIn';
import { UserContext } from './Components/UserContext/UserContext';
import { useContext } from 'react';

import LoansDetails from './Components/Home/User/Loans/LoansDetails';
import Admin from './Components/Home/Admin/Admin';
import CommunityForum from './Components/Home/User/CommunityTalk/CommunityTalk';
import UserDetails from './Components/Home/Admin/UserDetails/UserDetails';

import UserAppliedLoans from './Components/Home/User/LoanStatus/UserAppliedLoans';
import AdminLoanApplication from './Components/Home/Admin/LoanStatus/AdminLoanApplication';
import CustomerCare from './Components/Home/User/CustomerCare/CustomerCare';
import AdminCustomer from './Components/Home/Admin/CustomerCare.jsx'


function App() {
  const {user} = useContext(UserContext);
  return (
    <div>
   <Routes>
   <Route path="/"  element={<SignIn/>} />
   <Route path="/PATHTOSIGNUP"  element={<SignUp/>} />
   <Route path="/PATHTOlogoutfromAdmin"  element={user?.role==='Admin'?<Admin/>:<SignIn/>} />
   <Route path="/PATHTOlogoutfromUser"  element={user?.role==='User'?<User/>:<SignIn/>} />
   {/* <Route path="/"  element={user?.role==='User'?<User/>:<SignIn/>} /> */}
   <Route path="/PATHTOSLIDEBAR"  element={<ImageSlider/>} />
   <Route path="/Checking"  element={<Checking/>} />
   <Route path="/Home"  element={user?.role==='User'?<User/>:<Admin/>} />
   <Route path="/PATHTOLOANDETAILS"  element={user?.role==='User'?<LoansDetails/>:<AdminLoanApplication/>} />
   <Route path="/PATHTOCFT"  element={user?<CommunityForum/>:<SignIn/>} />
   <Route path="/PATHTOCUSTOMERCARE"  element={<CustomerCare/>} />
   <Route path="/PATHTOCUSTOMERCAREADMIN"  element={<AdminCustomer/>} />
   
   <Route path="/PATHTOSIGNIN"  element={<User/>} />
   <Route path="/PATHTOADMINUSERDETAILS"  element={<UserDetails/>} />
   <Route path="/PATHTOMYLOANS"  element={<UserAppliedLoans/>} />

   <Route path="/userhome"  element={<User/>} />

   <Route path="/adminhome"  element={<Admin/>} />


   <Route path="/userhome"  element={<User/>} />
   {/* <Route path="/"  element={<User/>} /> */}
   
   </Routes>
    </div>
  );
}

export default App;
