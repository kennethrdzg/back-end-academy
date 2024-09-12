import React from 'react';
import {
  BrowserRouter as Router, Routes, Route
} from 'react-router-dom';

import './App.css';
import Auth from './pages/Auth/Auth';
import Home from './pages/Home/Home';
import Dashboard from './pages/Dashboard/Dashboard';

function App() {
  return (
    <Router class='App'>
      <Routes>
        <Route path='/register' element={<Auth/>}></Route>
        <Route path='/login' element={<Auth/>}></Route>
        <Route path='/' element={<PrivateRoute/>}></Route>
      </Routes>
    </Router>
  );
}

const PrivateRoute = () => {
  const session = false;

  const finalComponent = session ? <Dashboard/> : <Home/>
  return finalComponent;
}

export default App;
