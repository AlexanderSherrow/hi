import { useEffect, useState } from "react";
import { SpinnerLoading } from "../../../Utils/SpinnerLoading";
import { Vehicle } from "../Vehicle";
import '../Vehicle.css';
import { PricePerConditonRow } from "./PricePerConditionRow";
import { PricePerConditonRowColumn } from "./PricePerConditionRowColumn";

var constants = require('../../../../constants')

export function PricePerConditon(props) {


  let [list, setList] = useState([]);
  const [isLoading, setIsLoading] = useState(false);

  useEffect(() => {
  
    let mainPath = "/report/pricePerCondition";
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
      <PricePerConditonRow key = {result[key].vin}
      chassisType = {key}
      excellent = {result[key].excellent}
      fair = {result[key].fair}
      good = {result[key].good}
      veryGood = {result[key].verygood}/>);
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
      <PricePerConditonRowColumn/>
    {list}
  </div>
    );

}