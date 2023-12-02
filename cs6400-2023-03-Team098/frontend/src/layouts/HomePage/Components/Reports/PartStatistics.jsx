import { useEffect, useState } from "react";
import { SpinnerLoading } from "../../../Utils/SpinnerLoading";
import { Vehicle } from "../Vehicle";
import '../Vehicle.css';
import { AverageTimeInInventoryRowColumn } from "./AverageTimeInInventoryRowColumn";
import { PartStatisticsRow } from "./PartStatisticsRow";
import { PartStatisticsRowColumn } from "./PartStatisticsRowColumn";

var constants = require('../../../../constants')

export function PartStatistics(props) {


  let [list, setList] = useState([]);
  const [isLoading, setIsLoading] = useState(false);

  useEffect(() => {
  
    let mainPath = "/report/partsStatistics";
    setIsLoading(true);
    fetch(constants.postURL + mainPath,
    {
      method:"GET",
      headers:{"Content-Type":"application/json"},
      body:JSON.stringify(props.payload)
    }
    )
  .then(res=>res.json())
  .then((result)=>{
    list = [];
    result = JSON.stringify(result);
    result = JSON.parse(result);
    console.log(result);
    for(const key in result)
    {
        list.push(
      <PartStatisticsRow key = {key}
      vendorName = {key}
      cost = {result[key].cost}
      quantity = {result[key].quantity}/>);

    }
    setList(list);
  }
)
setIsLoading(false);
}, []);

  if (isLoading) {
    return (
      <SpinnerLoading/>
    )
  }

    return (
    <div class="list-container">
      <PartStatisticsRowColumn/>
    {list}
  </div>
    );

}