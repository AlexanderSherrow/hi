import { useEffect, useState } from "react";
import { SpinnerLoading } from "../../../Utils/SpinnerLoading";
import { Vehicle } from "../Vehicle";
import '../Vehicle.css';
import { PricePerConditonRow } from "./PricePerConditionRow";
import { PricePerConditonRowColumn } from "./PricePerConditionRowColumn";
import { AverageTimeInInventoryRow } from "./AverageTimeInInventoryRow";
import { AverageTimeInInventoryRowColumn } from "./AverageTimeInInventoryRowColumn";

var constants = require('../../../../constants')

export function AverageTimeInInventory(props) {


  let [list, setList] = useState([]);
  const [isLoading, setIsLoading] = useState(false);

  useEffect(() => {
  
    let mainPath = "/report/daysInInventory";
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
      <AverageTimeInInventoryRow key = {key}
      value = {key}
      days = {result[key]}/>);
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
      <AverageTimeInInventoryRowColumn/>
    {list}
  </div>
    );

}