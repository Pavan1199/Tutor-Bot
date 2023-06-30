import React from "react";
import "./header.styles.scss";
import { useContext } from "react";
import { UserContext } from "../../contexts/user.context";
import { Navigate } from "react-router-dom";
const Header = () => {
  const { flag, setFlag, setData } = useContext(UserContext);
  return (
    <div className="header-class">
      <span
        className="sign-out"
        onClick={() => {
          setFlag(0);
          setData({ username: "", password: "" });
        }}
      >
        SIGN OUT
      </span>
      {flag === 0 ? <Navigate to="/" replace="true" /> : null}
    </div>
  );
};
export default Header;
