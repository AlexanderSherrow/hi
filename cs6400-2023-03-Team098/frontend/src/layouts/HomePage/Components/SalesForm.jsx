import { CustomerSearchControlPanel } from "./Customer/CustomerSearchControlPanel";
import { useEffect, useState } from "react";
import { SpinnerLoading } from "../../Utils/SpinnerLoading";
import { Vehicle } from "./Vehicle";
import './Vehicle.css';
import { VehicleColumnBar } from "./VehicleColumnBar";
import { SellVehicle } from "../Controller/SellVehicleController";

var constants = require('../../../constants')

export function SalesForm()
{
    const [customerPayload, setCustomerPayload] = useState([]);
    const [vehiclePayload, setVehiclePayload] = useState([]);
    const queryParameters = new URLSearchParams(window.location.search);
    const vin = queryParameters.get("vin");
    let payload = {vin};
    let [vehicleList, setVehicleList] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
    let [isSold, setIsSold] = useState(false);

  useEffect(() => {
    let mainPath = "/vehicle/getDetails";
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
    vehicleList = [];
    result = JSON.stringify(result);
    result = JSON.parse(result);
    for(const key in result)
    {
      vehicleList.push(
      <Vehicle key = {result[key].vin}
      vin = {result[key].vin}
      modelName = {result[key].modelName}
      modelYear = {result[key].modelYear}
      description = {result[key].description}
      mileage = {result[key].mileage}
      manufacturer = {result[key].manufacturer}
      chassisType = {result[key].chassisType}
      fuelType = {result[key].fuelType}
      color = {result[key].color}
      salesPrice = {result[key].salesPrice}
      disableNav = {true}/>);
    }
    setVehicleList(vehicleList);
    setVehiclePayload(vehicleList[0].props)
  }
)
setIsLoading(false);
}, []);

  if (isLoading) {
    return (
      <SpinnerLoading/>
    )
  }

  if(isSold)
  return (
    <div>
        <CustomerSearchControlPanel setCustomerPayload = {setCustomerPayload}></CustomerSearchControlPanel>
        <div>
          SALE COMPLETE!
        </div>
    </div>
);

    return (
        <div>
            <CustomerSearchControlPanel setCustomerPayload = {setCustomerPayload}></CustomerSearchControlPanel>
            <div>
            Car to be sold below:
            <VehicleColumnBar/>
            {vehicleList}
            <SellVehicle vehiclePayload = {vehiclePayload} customerPayload = {customerPayload} setIsSold = {setIsSold}/>
            </div>
        </div>
    );
}