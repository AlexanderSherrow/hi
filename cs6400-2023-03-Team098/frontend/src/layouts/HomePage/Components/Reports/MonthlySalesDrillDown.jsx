import { useEffect, useState } from "react";
import { SpinnerLoading } from "../../../Utils/SpinnerLoading";
import '../Vehicle.css';
import { MonthlySalesRowColumn } from "./MonthlySalesRowColumn";
import { MonthlySalesDrillDownRow } from "./MonthlySalesDrillDownRow";
import { MonthlySalesDrillDownRowColumn } from "./MonthlySalesDrillDownRowColumn";

var constants = require('../../../../constants')

export function MonthlySalesDrillDown(props) {

  let [list, setList] = useState([]);
  const [isLoading, setIsLoading] = useState(false);

  useEffect(() => {
  
    let mainPath = "/report/monthlySalesReportDrillDown";
    const queryParameters = new URLSearchParams(window.location.search);

    const month = queryParameters.get("month");
    const year = queryParameters.get("year");

    let payload = {month: month, year: year};
    setIsLoading(true);
    fetch(constants.postURL + mainPath,
    {
      method:"POST",
      headers:{"Content-Type":"application/json"},
      body:JSON.stringify(payload)
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
      <MonthlySalesDrillDownRow key = {result[key].lastName + "," + result[key].firstName}
      firstName = {result[key].firstName}
      lastName = {result[key].lastName}
      vehiclesSold = {result[key].vehiclesSold}
      totalSales = {result[key].totalSales}/>);
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
      <MonthlySalesDrillDownRowColumn/>
    {list}
  </div>
    );

}