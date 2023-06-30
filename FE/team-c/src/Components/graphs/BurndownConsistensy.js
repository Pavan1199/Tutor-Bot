import React, { useState, useEffect } from "react";
import {Line } from "react-chartjs-2"
const BDConsistensy =() =>{
  const [user, setUsers] = useState([])
  const [chart,setChart] = useState([])
  var baseUrl ="https://localhost:8081/getBDConsistency?Project=dbiegan-sbs-bieganski&Sprint=Sprint 3"
  const fetchdata =()=>{
    fetch("https://localhost:8081/getBDConsistency?Project=dbiegan-sbs-bieganski&Sprint=Sprint 3")
    .then(response => {
      return response.json()
    })
    .then(data => {
      setUsers(data)
    })
  }
  useEffect(() => {
    fetchdata()

    const fetchBDdata = async() => {
      await fetch("https://localhost:8081/getBDConsistency?Project=dbiegan-sbs-bieganski&Sprint=Sprint 3", {
        method: 'GET',
        headers: {
          'Content-Type' : 'application/json',
          'Access-Control-Allow-Origin':'*'
        }
      }).then((response) => {
        response.json().then((json) => {
          console.log(json)
        })
      }).catch(error =>{
        console.log(error);

      })
    };
     fetchBDdata()
  },[])
  var data ={
    labels: ["date1","Date2"],
    dataets: [{
      label:'Done Story points',
      data:[1,2]
    }

    ]
  }
  return (
    <div>
          console.log(fetchBDdata())
    </div>
  )
}
export default BDConsistensy; 