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
            text: 'Total Work done',
        },
    },
};


function TotalWork() {

    const [searchParams, setSearchParams] = useSearchParams();

    console.log("Params is " , searchParams.get("ProjectSlug"), searchParams.get("SprintName"));

    const [isWorkDone, setIsWorkDone] = useState(false);
    const [BDCData, setBDCData] = useState({dailyValuesList: []});

    const [BDCDates, setBDCDates] = useState([]);
    const [BVValues, setBVValues] = useState([]);
    const [SPValues, setSPValues] = useState([]);

    const [GraphData, setGraphData] = useState({});


    useEffect(() => {

        if (!isWorkDone) {


            const baseUrl = "http://localhost:8085/getWorkBD?Project="+searchParams.get("ProjectSlug")+"&Sprint="+searchParams.get("SprintName");

            axios.get(baseUrl)
                .then((response) => {
                    let apidata = response.data;
                    setBDCData(apidata);

                    console.log("API data is ", apidata)


                    //Update BDCData
                    let tempDates = [];
                    let tempidealvalue = [];
                    let tempactualvalue = [];
                    apidata.daily_values.map((item) => {
                        tempDates.push(item.date);
                        tempidealvalue.push(item.ideal_value);
                        tempactualvalue.push(item.actual_value);

                        //console.log(tempDates, tempBVValues, tempSPValues);
                    });

                    setBDCDates(tempDates);
                    setBVValues(tempidealvalue);
                    setSPValues(tempactualvalue);

                    const tempGraphData = {
                        labels: tempDates, 
                        datasets: [  
                            {
                                label: 'Ideal',
                                data: tempidealvalue,
                                borderColor: 'rgb(255, 99, 132)',
                                backgroundColor: 'rgba(255, 99, 132, 0.5)',
                            },
                            {
                                label: 'Actual',
                                data: tempactualvalue,
                                borderColor: 'rgb(53, 162, 235)',
                                backgroundColor: 'rgba(53, 162, 235, 0.5)',
                            },
                        ],
                    };

                    setGraphData(tempGraphData);
                    //console.log(tempGraphData);

                    setIsWorkDone(true);
                }
                )

        }

    }, [])

    if (!isWorkDone) {
        return (
            <div>  graph loading...</div>
        )
    }

    console.log("Graph data is ", GraphData);
    return (
        <div className = "graphs">
            <Line options={options} data={GraphData} />;
        </div>
    );


};

export default TotalWork;