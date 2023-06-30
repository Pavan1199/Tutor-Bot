import React from "react";
import { createContext, useState } from "react";

export const UserContext = createContext({
  data: null,
  flag: null,
  Project_Data: null,
  project: null,
  setProject: () => null,
  setProjectData: () => null,
  setData: () => null,
  setFlag: () => null,
});

export const UserProvider = ({ children }) => {
  const [data, setData] = useState({
    username: "",
    password: "",
  });
  const [flag, setFlag] = useState(0);
  const [Project_Data, setProjectData] = useState([]);
  const [project, setProject] = useState({
      projectname: '',
      sprintnames: []
  });
  const cred = { data, flag, Project_Data, project, setProject, setProjectData, setData, setFlag};
  return <UserContext.Provider value={cred}>{children}</UserContext.Provider>;
};
