import { useEffect } from "react";
import { useState } from "react";
import { SpinnerLoading } from "../../../Utils/SpinnerLoading";
import { Details } from "./Details";
import { DetailsPageColumnBar } from "./DetailsPageColumnBar";
export function DetailsPage()
{
    var constants = require('../../../../constants')
    const queryParameters = new URLSearchParams(window.location.search);
    const vin = queryParameters.get("vin");
    let payload = {vin};

    let [descriptionList, setDescriptionList] = useState([]);
    const [isLoading, setIsLoading] = useState(false);
    const [firstSearch, setFirstSearch] = useState(false);
  
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
        <Details key = {result[key].vin}
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
      setFirstSearch(true);
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
            <DetailsPageColumnBar/>

    {descriptionList}
  </div>
    );
}