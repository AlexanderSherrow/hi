import { useEffect } from "react";
import { useState } from "react";
import { SpinnerLoading } from "../../../Utils/SpinnerLoading";
import { DetailsInventoryClerk } from "./DetailsInventoryClerk";
import { DetailsPageInventoryClerkColumnBar } from "./DetailsPageInventoryClerkColumnBar";
import { PartManager } from "../PartManager";
import { PartOrderManagerColumnBar } from "./PartOrderManagerColumnBar";
export function DetailsPageManager()
{
    var constants = require('../../../../constants')
    const queryParameters = new URLSearchParams(window.location.search);
    const vin = queryParameters.get("vin");
    let payload = {vin};

    let [descriptionList, setDescriptionList] = useState([]);
    let [partList, setPartList] = useState([]);
    const [isLoading, setIsLoading] = useState(false);

    useEffect(() => {
      let mainPath = "/vehicle/getDetailsInventoryClerk";
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
        <DetailsInventoryClerk key = {result[key].vin}
        vin = {result[key].vin}
        modelName = {result[key].modelName}
        modelYear = {result[key].modelYear}
        mileage = {result[key].mileage}
        manufacturer = {result[key].manufacturer}
        chassisType = {result[key].chassisType}
        fuelType = {result[key].fuelType}
        color = {result[key].color}
        salesPrice = {result[key].salesPrice}
        costOfParts = {result[key].costOfParts}
        description = {result[key].description}
        />);
      }
      setDescriptionList(descriptionList);
    }
    );

    mainPath = "/partsOrder/getPartsByVin";
    fetch(constants.postURL + mainPath,
    {
      method:"POST",
      headers:{"Content-Type":"application/json"},
      body:JSON.stringify(payload)
    }
    )
  .then(res=>res.json())
  .then((result)=>{
    partList = [];
    result = JSON.stringify(result);
    result = JSON.parse(result);
    for(const key in result)
    {
      partList.push(
      <PartManager key = {result[key].partNumber}
      purchaseOrderNumber = {result[key].purchaseOrderNumber}
      vendorName = {result[key].vendorName}
      totalPartsCost = {result[key].totalPartsCost}
      partNumber = {result[key].partNumber}
      description = {result[key].description}
      currentStatus = {result[key].currentStatus}/>);
    }
    setPartList(partList);
  });

  setIsLoading(false);
  }, []);

  if (isLoading) {
    return (
      <SpinnerLoading/>
    )
  }

    return (
    <>
    <div class="list-container">
    <DetailsPageInventoryClerkColumnBar/>
    {descriptionList}
  </div>
    <div class="list-container">
      Parts ordered for this car:
      <PartOrderManagerColumnBar/>
    {partList}
    </div>
    </>
    );
}