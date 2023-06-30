import React, {useState, useEffect} from "react";
import Graph from "react-graph-vis";
import axios from "axios";
import { useSearchParams } from 'react-router-dom'
function SBcoupling() {
    const [isDataFetched, setIsDataFetched] = useState(false);
    const [GraphData, setGraphData] = useState({});
    const [searchParams, setSearchParams] = useSearchParams();
    useEffect(() => {
        const baseUrl = "http://localhost:8089/getSBC?Project="+searchParams.get("ProjectSlug")+"&Sprint="+searchParams.get("SprintName");
            axios.get(baseUrl)
                .then((response) => {
                    let apidata = response.data;
                    console.log('apidata', apidata);
                    //const obj = apidata[0].dependencyMap;
                    const map = {};
                    let graphs = {
                        edges: [],
                        nodes: []
                    };
                    // graphs.nodes = apidata.nodes
                    // for(let key in obj) {
                    //     const arr = obj[key];
                    //     for (let i = 0; i < arr.length; i++) {
                    //         const edge = { from: map[key], to: map[arr[i]]};
                    //         graphs.edges.push(edge);
                    //     }
                    // }
                    // graphs.edges = apidata.edges
                    console.log(apidata);
                    setGraphData(apidata);
                    setIsDataFetched(true);
                }
                )
    }, []);
    if (!isDataFetched) {
        return (
            <div> SBCoupling graph loading...</div>
        )
    }
    const options = {
        layout: {
          hierarchical: true
        },
        edges: {
          color: "#000000"
        },
        height: "500px"
      };
      const events = {
        select: function(event) {
          var { nodes, edges } = event;
        }
      };
      console.log(GraphData);
    return (
        <div>
            <h4>SB coupling graph</h4>
            <Graph
                graph={GraphData}
                options={options}
                events={events}
                getNetwork={network => {
                    //  if you want access to vis.js network api you can set the state in a parent component using this property
                }}
            />
        </div>
    );
};
export default SBcoupling;