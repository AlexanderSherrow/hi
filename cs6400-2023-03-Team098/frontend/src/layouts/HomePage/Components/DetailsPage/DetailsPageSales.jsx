import { useEffect } from "react";
import { useState } from "react";
import { SpinnerLoading } from "../../../Utils/SpinnerLoading";
import { DetailsSales } from "./DetailsSales";
import { DetailsPageSalesColumnBar } from "./DetailsPageSalesColumnBar";
export function DetailsPageSales()
{
    var constants = require('../../../../constants')
    const queryParameters = new URLSearchParams(window.location.search);
    const vin = queryParameters.get("vin");
    let payload = {vin};

    let [descriptionList, setDescriptionList] = useState([]);
    const [isLoading, setIsLoading] = useState(false);
  
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
    descriptionList = [];
      result = JSON.stringify(result);
      result = JSON.parse(result);
      for(const key in result)
      {
        descriptionList.push(
        <DetailsSales key = {result[key].vin}
        vin = {result[key].vin}
        modelName = {result[key].modelName}
        modelYear = {result[key].modelYear}
        mileage = {result[key].mileage}
        manufacturer = {result[key].manufacturer}
        chassisType = {result[key].chassisType}
        fuelType = {result[key].fuelType}
        color = {result[key].color}
        salesPrice = {result[key].salesPrice}
        description = {result[key].description}
        />);
      }
      setDescriptionList(descriptionList);
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
            <DetailsPageSalesColumnBar/>

    {descriptionList}
  </div>
    );
}