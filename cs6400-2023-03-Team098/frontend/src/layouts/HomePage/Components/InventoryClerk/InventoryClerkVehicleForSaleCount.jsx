import { useEffect, useState } from "react";
import { SpinnerLoading } from "../../../Utils/SpinnerLoading";
import '../Vehicle.css';

var constants = require('../../../../constants')

export function InventoryClerkVehicleForSaleCount(props) {
  let [vehicleForSaleCount, setVehicleForSaleCount] = useState(0);
  const [isLoading, setIsLoading] = useState(false);

  useEffect(() => {
    let mainPath = "/vehicle/numberOfVehiclesForSale";
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
    setVehicleForSaleCount(result);
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
    Number of vehicles available for purchase: {vehicleForSaleCount}
  </div>
    );

}