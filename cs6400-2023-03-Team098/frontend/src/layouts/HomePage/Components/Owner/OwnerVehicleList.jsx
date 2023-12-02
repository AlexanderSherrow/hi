import { useEffect, useState } from "react";
import { SpinnerLoading } from "../../../Utils/SpinnerLoading";
import { OwnerVehicle } from "./OwnerVehicle";
import '../Vehicle.css';

var constants = require('../../../../constants')

export function OwnerVehicleList(props) {
  let [vehicleList, setVehicleList] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const [firstSearch, setFirstSearch] = useState(false);

  useEffect(() => {
    if(props.payload.soldStatus == "Sold")
    {
    let mainPath = "/vehicle/soldVehicleSearch";
    if(props.payload.length == 0)
    return;
  
    setIsLoading(true);
    fetch(constants.postURL + mainPath,
    {
      method:"POST",
      headers:{"Content-Type":"application/json"},
      body:JSON.stringify(props.payload)
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
      <OwnerVehicle key = {result[key].vin}
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
      soldStatus = {props.payload.soldStatus}
      />);
    }
    setFirstSearch(true);
    setVehicleList(vehicleList);
    console.log(vehicleList.length);

  }
)
    }
    else if(props.payload.soldStatus == "Unsold")
    {
    let mainPath = "/vehicle/unsoldVehicleSearch";
    if(props.payload.length == 0)
    return;
  
    setIsLoading(true);
    fetch(constants.postURL + mainPath,
    {
      method:"POST",
      headers:{"Content-Type":"application/json"},
      body:JSON.stringify(props.payload)
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
      <OwnerVehicle key = {result[key].vin}
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
      soldStatus = {props.payload.soldStatus}
      />);
    }
    setFirstSearch(true);
    setVehicleList(vehicleList);
    console.log(vehicleList.length);

  }
)
    }
    else if(props.payload.soldStatus == "All")
    {
    let mainPath = "/vehicle/allVehicles";
    if(props.payload.length == 0)
    return;
  
    setIsLoading(true);
    fetch(constants.postURL + mainPath,
    {
      method:"POST",
      headers:{"Content-Type":"application/json"},
      body:JSON.stringify(props.payload)
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
      <OwnerVehicle key = {result[key].vin}
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
      soldStatus = {props.payload.soldStatus}
      />);
    }
    setFirstSearch(true);
    setVehicleList(vehicleList);
    console.log(vehicleList.length);
  }
)
    }

setIsLoading(false);
}, []);

  if (isLoading) {
    return (
      <SpinnerLoading/>
    )
  }
  if(!firstSearch)
  return (
    <div>
      Please search a vehicle.
    </div>
    );
    
  if(vehicleList.length == 0)
  return (
    <div>
Sorry, it looks like we
don’t have that in stock!  </div>
    );
    return (
    <div class="list-container">
    {vehicleList}
  </div>
    );

}