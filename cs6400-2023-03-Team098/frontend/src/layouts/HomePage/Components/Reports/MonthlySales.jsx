import { useEffect, useState } from "react";
import { SpinnerLoading } from "../../../Utils/SpinnerLoading";
import '../Vehicle.css';
import { SellerHistoryRowColumn } from "./SellerHistoryRowColumn";
import { MonthlySalesRow } from "./MonthlySalesRow";
import { MonthlySalesRowColumn } from "./MonthlySalesRowColumn";

var constants = require('../../../../constants')

export function MonthlySales(props) {


  let [list, setList] = useState([]);
  const [isLoading, setIsLoading] = useState(false);

  useEffect(() => {
  
    let mainPath = "/report/monthlySalesReport";
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
      <MonthlySalesRow key = {result[key].month + result[key].year}
      month = {result[key].month}
      year = {result[key].year}
      totalSaleIncome = {result[key].totalSaleIncome}
      totalNetIncome = {result[key].totalNetIncome}
      vehiclesSold = {result[key].vehicles_sold}/>);
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
      <MonthlySalesRowColumn/>
    {list}
  </div>
    );

}