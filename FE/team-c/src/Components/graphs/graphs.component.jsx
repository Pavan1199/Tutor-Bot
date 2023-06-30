import React from "react";
import './graphs.styles.scss';
import { useContext } from "react";
import { Line } from "react-chartjs-2";
import { MenuItemContext } from "../../contexts/menuitem.context";
import { Chart, registerables } from 'chart.js';
Chart.register(...registerables);
const GraphItem = () => {

const {graphdisplay,bdgraphdiplay} = useContext(MenuItemContext);
const state = {
    labels: ["March 19", "March 20", "March 21", "March 22", "March 23", "March 24", "March 25"],
    datasets: [
      {
        label: "Actual Workdone",
        backgroundColor: "rgba(75,192,192,1)",
        borderColor: "rgba(0,0,0,1)",
        borderWidth: 2,
        data: graphdisplay.data1,
        tension:0.4,
        fill:1
      },
      {
        label: "Ideal Workdone",
        backgroundColor: "rgba(23,125,244,1)",
        borderColor: "rgba(0,0,0,1)",
        borderWidth: 2,
        data: graphdisplay.data2,
        tension:0.4,
        fill:1
      },
      {
        label: "Story Points done",
        backgroundColor: "rgba(30,125,244,1)",
        borderColor: "rgba(0,0,0,1)",
        borderWidth: 2,
        data: graphdisplay.spDone,
        tension:0.4,
        fill:1
      },
      {
        label: "Business value Points done",
        backgroundColor: "rgba(60,192,192,1)",
        borderColor: "rgba(0,0,0,1)",
        borderWidth: 2,
        data: graphdisplay.bvDone,
        tension:0.4,
        fill:1
      }
    ]
  }
return(
<div className="Graph_display">
<Line
          data={state}
          options={{
            title:{
              display:true,
              text:'Taiga Metrics',
              fontSize:20
            },
            legend:{
              display:true,
              position:'right'
            }
          }}
        />
</div>
)
}
export default GraphItem ;
