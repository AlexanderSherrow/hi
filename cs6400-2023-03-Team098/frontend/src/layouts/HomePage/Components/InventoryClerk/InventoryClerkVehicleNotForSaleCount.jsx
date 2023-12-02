import { useEffect, useState } from "react";
import { SpinnerLoading } from "../../../Utils/SpinnerLoading";
import '../Vehicle.css';

var constants = require('../../../../constants')

export function InventoryClerkVehicleNotForSaleCount(props) {
  let [vehicleNotForSaleCount, setVehicleNotForSaleCount] = useState(0);
  const [isLoading, setIsLoading] = useState(false);

  useEffect(() => {
    let mainPath = "/vehicle/numberOfVehiclesNotForSale";
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
    result = JSON.stringify(result);
    result = JSON.parse(result);
    setVehicleNotForSaleCount(result);
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
    Number of vehicles currently with parts pending: {vehicleNotForSaleCount}
  </div>
    );

}