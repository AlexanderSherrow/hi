import { useEffect } from "react";
import { useState } from "react";
import { SpinnerLoading } from "../../../Utils/SpinnerLoading";
import { DetailsPageInventoryClerkColumnBar } from "./DetailsPageInventoryClerkColumnBar";
import { DetailsOwner } from "./DetailsOwner";
import { Part } from "../Part";
import { PartOrderColumnBar } from "./PartOrderColumnBar";
import { useNavigate } from "react-router-dom";
import { PartNoUpdate } from "../PartNoUpdate";
import { PartOrderNoUpdateColumnBar } from "./PartsOrderNoUpdateColumnBar";

export function DetailsPageOwner()
{
    var constants = require('../../../../constants')
    const queryParameters = new URLSearchParams(window.location.search);
    const vin = queryParameters.get("vin");
    let payload = {vin};

    let [descriptionList, setDescriptionList] = useState([]);
    let [partList, setPartList] = useState([]);
    let [partListNoUpdate, setPartListNoUpdate] = useState([]);
    let [hasBeenSold, setHasBeenSold] = useState([]);
    const [isLoading, setIsLoading] = useState(false);
    const navigate = useNavigate();


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
        <DetailsOwner key = {result[key].vin}
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
    partList = [];
    partListNoUpdate = [];
    result = JSON.stringify(result);
    result = JSON.parse(result);
    for(const key in result)
    {
      partList.push(
      <Part key = {result[key].partNumber}
      purchaseOrderNumber = {result[key].purchaseOrderNumber}
      vendorName = {result[key].vendorName}
      totalPartsCost = {result[key].totalPartsCost}
      partNumber = {result[key].partNumber}
      description = {result[key].description}
      currentStatus = {result[key].currentStatus}/>);

      partListNoUpdate.push(
        <PartNoUpdate key = {result[key].partNumber}
        purchaseOrderNumber = {result[key].purchaseOrderNumber}
        vendorName = {result[key].vendorName}
        totalPartsCost = {result[key].totalPartsCost}
        partNumber = {result[key].partNumber}
        description = {result[key].description}
        currentStatus = {result[key].currentStatus}/>);
    }
    setPartList(partList);
    setPartListNoUpdate(partListNoUpdate);

  });

  mainPath = "/vehicle/hasBeenSold";
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
  partList = [];
  result = JSON.stringify(result);
  result = JSON.parse(result);
  setHasBeenSold(result);
});

  setIsLoading(false);
  }, []);

  
  function navigateToAddPartForm(){
    const queryParameters = new URLSearchParams(window.location.search);
    let user = queryParameters.get("user");
    navigate('/AddPartForm?vin=' + vin + "&user=" + user);
  };

  if (isLoading) {
    return (
      <SpinnerLoading/>
    )
  }

  if(hasBeenSold)
  return (
    <>
    <div class="list-container">
    <DetailsPageInventoryClerkColumnBar/>
    {descriptionList}
  </div>
    <div class="list-container">
      Parts ordered for this car:
      <PartOrderNoUpdateColumnBar/>
    {partListNoUpdate}
    </div>
    </>
    );

    return (
    <>
    <div class="list-container">
    <DetailsPageInventoryClerkColumnBar/>
    {descriptionList}
  </div>
    <div class="list-container">
      Parts ordered for this car:
      <PartOrderColumnBar/>
    {partList}
    <button onClick={navigateToAddPartForm}> Click me to make a new part order!</button>
    </div>
    </>
    );


}