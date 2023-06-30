import Inp from "../userInput/userInput.component";
import "./login.styles.scss";
import { Navigate } from "react-router-dom";
import { useContext, useEffect } from "react";
import { UserContext } from "../../contexts/user.context";
const Login = () => {   

const {data, setData, flag, setFlag, setProjectData} = useContext(UserContext);
  useEffect(()=>{
    fetch("http://localhost:8080/taiga/projects").then(response => response.json()).then(data=>setProjectData(data));
  },[]);
  const Handlechange = (e) => {
    const { value, name } = e.target;
    setData({
      ...data,
      [name]: [value],
    });
  };

  const HandleSubmit = async () => {
    let datas = {
      username: data.username[0],
      password: data.password[0],
    };
    fetch("http://localhost:8080/taiga/auth-input", {
      //   mode: "no-cors",
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(datas),
    })
      .then((response) => response.json())
      .then((data) => {
        data === "OK"
          ? setFlag(1)
          : console.log("Auth Failed: " + data)

          console.log(data)
      }
      );
      
  };

  return (
    <div className="val_form">
      <Inp
        type="text"
        name="username"
        placeholder="Username"
        handleChange={Handlechange}
      />
      <Inp
        type="password"
        name="password"
        placeholder="Password"
        handleChange={Handlechange}
      />
      <button className="val_button" onClick={HandleSubmit}>
        Log in
      </button>
      {flag === 1 ? <Navigate to="/logger" replace="true" /> : null}
    </div>
  );
};
export default Login;
