
import './App.css';
import Loginpage from './Pages/LoginPage/loginPage.component';
import { Route, Routes } from 'react-router-dom';
import Loggedin from './Pages/Loggedin/loggedin.component';
function App() {
  return (
    <div className="App">
      <Routes>
    <Route exact path='/' element={<Loginpage/>}/>
    <Route path='/logger' element={<Loggedin/>}/>
   
    </Routes>
    </div>
  );
}

export default App;
