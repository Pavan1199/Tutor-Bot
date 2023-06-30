import React, { useContext } from "react";
import "./sidebar.styles.scss";
import { useNavigate, createSearchParams } from "react-router-dom";
import { MenuItemContext } from "../../contexts/menuitem.context";
function Side_menu(props) {
  const {project, sprint} = useContext(MenuItemContext);
  console.log(project);
  console.log(sprint);
  let navigate = useNavigate();
  console.log("Side menu props", props);

  return (
    <div className="menu-component">
    <h1>Choose a metric to see the graph..</h1>
    <br></br><br></br>
    <button className="button-component btn" onClick={() => {
        return window.open(`/BDConsistency?ProjectSlug=${props.ProjectSlug}&SprintName=${props.SprintName}`);
      }}
    >BDConsistency</button>
    
    <button className="button-component btn" onClick={() => {
        return window.open(`/WorkAUC?ProjectSlug=${props.ProjectSlug}&SprintName=${props.SprintName}`);
      }}
    >WorkAUC</button>
    
    <button className="button-component btn" onClick={() => {
        return window.open(`/ValueAUC?ProjectSlug=${props.ProjectSlug}&SprintName=${props.SprintName}`);
      }}
    >ValueAUC</button>

<button className="button-component btn" onClick={() => {
        return window.open(`/PartialWork?ProjectSlug=${props.ProjectSlug}&SprintName=${props.SprintName}`);
      }}
    >PartialWork</button>

    <button className="button-component btn" onClick={() => {
      return window.open(`/TotalWork?ProjectSlug=${props.ProjectSlug}&SprintName=${props.SprintName}`);
    }}
    >TotalWork</button>

    <button className="button-component btn" onClick={() => {
      return window.open(`/DeliveryTime?ProjectSlug=${props.ProjectSlug}&SprintName=${props.SprintName}`);
    }}
    >DeliveryTime</button>

    <br />

    <button className="button-component btn" onClick={() => {
        return window.open(`/ValueBurndown?ProjectSlug=${props.ProjectSlug}&SprintName=${props.SprintName}`);
      }}
    >ValueBurndown</button>

    <button className="button-component btn" onClick={() => {
        return window.open(`/PBcoupling?ProjectSlug=${props.ProjectSlug}&SprintName=${props.SprintName}`);
      }}
    >PBcoupling</button>
    

    <button className="button-component btn" onClick={() => {
      return window.open(`/SBCoupling?ProjectSlug=${props.ProjectSlug}&SprintName=${props.SprintName}`);
    }}
  >SBCoupling</button>

    <button className="button-component btn" onClick={() => {
      const newWindow  = window.open("/Taskcoupling")
      newWindow.project = project;
      newWindow.sprint = sprint;
    }}
    >Taskcoupling</button>
    </div>
  );
};
export default Side_menu;
