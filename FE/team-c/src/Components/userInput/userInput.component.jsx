import React from "react";
import "./userInput.styles.scss";

const Inp = ({ placeholder, handleChange, ...otherProps }) => (
  <div className="val_form">
    <input
      className="val_input"
      placeholder={placeholder}
      onChange={handleChange}
      {...otherProps}
    />
  </div>
);
export default Inp;
