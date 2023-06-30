import React, { useState, useEffect } from "react";
import {
    Chart as ChartJS,
    CategoryScale,
    LinearScale,
    PointElement,
    LineElement,
    Title,
    Tooltip,
    Legend
} from 'chart.js'

import { Line } from 'react-chartjs-2';
import { useSearchParams } from 'react-router-dom'

import axios from "axios"

ChartJS.register(
    CategoryScale,
    LinearScale,
    PointElement,
    LineElement,
    Title,
    Tooltip,
    Legend
);

export const options = {
    responsive: true,
    plugins: {
        legend: {
            position: 'top',
        },
        title: {
            display: true,
            text: 'ValueAUC',
        },
    },
};


function ValueAUC() {

    const [searchParams, setSearchParams] = useSearchParams();

    console.log("Params is " , searchParams.get("ProjectSlug"), searchParams.get("SprintName"));

    const [isBDCDataFetched, setIsBDCDataFetched] = useState(false);
    const [BDCData, setBDCData] = useState({dailyValuesList: []});

    const [BDCDates, setBDCDates] = useState([]);
    const [BVValues, setBVValues] = useState([]);
    const [SPValues, setSPValues] = useState([]);

    const [GraphData, setGraphData] = useState({});


    useEffect(() => {

        if (!isBDCDataFetched) {

            const baseUrl = "http://localhost:8084/getValueAUC?Project="+searchParams.get("ProjectSlug")+"&Sprint="+searchParams.get("SprintName");
            
            axios.get(baseUrl)
                .then((response) => {
                    let apidata = response.data;
                    setBDCData(apidata);

                    let tempDates = [];
                    let tempBVValues = [];
                    let tempSPValues = [];
                    apidata.dailyValuesList.map((item) => {
                        tempDates.push(item.date);
                        tempBVValues.push(item.bvDone);
                        tempSPValues.push(item.bvIdeal);
                    });

                    setBDCDates(tempDates);
                    setBVValues(tempBVValues);
                    setSPValues(tempSPValues);

                    const tempGraphData = {
                        labels: tempDates, //Labels
                        datasets: [  //Values to be plotted
                            {
                                label: 'bvDone',
                                data: tempBVValues,
                                borderColor: 'rgb(255, 99, 132)',
                                backgroundColor: 'rgba(255, 99, 132, 0.5)',
                            },
                            {
                                label: 'bvIdeal',
                                data: tempSPValues,
                                borderColor: 'rgb(53, 162, 235)',
                                backgroundColor: 'rgba(53, 162, 235, 0.5)',
                            },
                        ],
                    };

                    setGraphData(tempGraphData);
                    setIsBDCDataFetched(true);
                }
                )

        }

    }, [])

    if (!isBDCDataFetched) {
        return (
            <div> ValueAUC graph loading...</div>
        )
    }

    console.log("Graph data is ", GraphData);
    return (
        <div className="graphs">
            <Line options={options} data={GraphData} />
        </div>
    );


};

export default ValueAUC;