import React from "react";
import { useState, createContext } from "react";
import Bddata from "../Components/sampleData/burndownconsistensy.json"
import valuaucdata from "../Components/sampleData/valueaucdata.json"
import WorkAucData from "../Components/sampleData/WorkAUC.json";


export const MenuItemContext = createContext({

menu_items : null,
graphdisplay: null,
setgraph: ()=>null,
setMenu_items : ()=>null,

});

let bdLabel = [];
Bddata.dailyValuesList.map((item) => {
    bdLabel.push(item.date);
    return null;
})

let spDone = [];
Bddata.dailyValuesList.map((item) => {
    spDone.push(item.spDone);
    return null;
})
let bvDone = [];
Bddata.dailyValuesList.map((item) => {
    bvDone.push(item.bvDone);
    return null;
})
let temp = [];
valuaucdata.dailyValuesList.map((item) => {
    temp.push(item.bvDone);
    return null;
})

let temp1 = [];
WorkAucData.dailyValuesList.map((item) => {
    temp1.push(item.spDone);
    return null;
})
let temp2 = [];
WorkAucData.dailyValuesList.map((item) => {
    temp2.push(item.spIdeal);
    return null;
})




// console.log(temp);

//     fetch("https://example.com").then((data) => {
//     console.log(data);
// })

export const MenuItemProvider = ({children}) => {

const [menu_items, setMenu_items] = useState([
    {name: 'BurnDown Consistency', label:bdLabel, spDone:spDone , bvDone:bvDone},
    {name: 'Work AUC', data1:temp1, data2: temp2}, 
    {name:'Value AUC', data1:temp, data2:[2,4,8,3,7,5,1]}, 
    {name:'Partial Work Burndown', data:[10,3,8]},
    {name:'Total Work Burndown', data:[8,6,1]}, 
    {name:'Delivery On Time',data:[6,18,9]}, 
    {name:'Value Burndown',data:[6,19,9]}, 
    {name:'PB Coupling', data:[1,7,1]}, 
    {name:'SB Coupling',data:[6,45,9]}, 
    {name:'Task Coupling',data:[6,0,9]}]);

const [graphdisplay, setgraph] = useState({name:'BurnDown Consistency', data:spDone , data1:bvDone});

const value = {menu_items, graphdisplay, setgraph, setMenu_items};

return <MenuItemContext.Provider value={value}>{children}</MenuItemContext.Provider>

}
