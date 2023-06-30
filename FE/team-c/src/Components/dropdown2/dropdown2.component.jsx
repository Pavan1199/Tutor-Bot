import React from 'react';
import './dropdown2.styles.scss';
import { useContext } from 'react';
import { UserContext } from '../../contexts/user.context';
const SprintDropDown = () => {

const {project} = useContext(UserContext);
return(
    <div className='Sprint-Names'>
    <label>
    <h1>Select Sprint</h1>
    <select>  
    {project.sprintnames.map((sprint) => (
      <option className="option" value={sprint}>{sprint}</option>
    ))}
  </select>
    </label>
    </div>

);
}
export default SprintDropDown;