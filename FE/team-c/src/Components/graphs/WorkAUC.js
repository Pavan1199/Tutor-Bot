import React,{Component} from'react'
import WorkAucData from "../Components/sampleData/WorkAUC.json";
function WorkAUC(){


    console.log(WAUCDate)
    return(
        <div>
            <h1> date: {WAUCDate.dailyValuesList[0].date}</h1>
        </div>
    )
}

export default WorkAUC;