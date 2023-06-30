import React, { useState } from "react";
import { useContext } from "react";
import { UserContext } from "../../contexts/user.context";
import "./dropdown.styles.scss"
const ProjectDropDown = () => {
  const { Project_Data, setProject } = useContext(UserContext);
  const handleChange = (e) => {

  const {projectName, sprintName} = JSON.parse(e.target.value);
   setProject({
       projectname: projectName,
       sprintnames: sprintName
   });
  }

  return (
    <div className="drop-down">
      <label>
      <h1>Select Project: </h1>
        <select onClick={handleChange}>
      
          {Project_Data.map((projects) => (
            <option className="option" value={JSON.stringify(projects)}>{projects.projectName}</option>
          ))}
        </select>
      </label>
    </div>
  );
};

export default ProjectDropDown;
