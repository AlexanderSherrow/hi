import { useEffect, useState } from "react";
import { SpinnerLoading } from "../../../Utils/SpinnerLoading";
import { Vehicle } from "../Vehicle";
import '../Vehicle.css';
import { PricePerConditonRow } from "./PricePerConditionRow";
import { PricePerConditonRowColumn } from "./PricePerConditionRowColumn";
import { SellerHistoryRow } from "./SellerHistoryRow";
import { SellerHistoryRowColumn } from "./SellerHistoryRowColumn";

var constants = require('../../../../constants')

export function SellerHistory(props) {


  let [list, setList] = useState([]);
  const [isLoading, setIsLoading] = useState(false);

  useEffect(() => {
  
    let mainPath = "/report/sellerHistory";
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
      <SellerHistoryRow key = {result[key].name}
      name = {result[key].name}
      costOfParts = {result[key].costOfParts}
      purchasePrice = {result[key].purchasePrice}
      partsOrdered = {result[key].partsOrdered}
      totalSold = {result[key].totalSold}/>);
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
      <SellerHistoryRowColumn/>
    {list}
  </div>
    );

}