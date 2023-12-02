import React, { useState, useEffect } from "react";
import '../Vehicle.css';
import { useNavigate } from "react-router-dom";



    export function DetailsOwner(props) {
      const [showSale, setShowSale] = useState(false);
      var constants = require('../../../../constants')


      const navigate = useNavigate();

      function navigateToSalesForm(){
        // 👇️ navigate to /
        const queryParameters = new URLSearchParams(window.location.search);
        let user = queryParameters.get("user");
        navigate('/SellVehicleForm?vin=' + props.vin + '&user=' + user);
      };

      useEffect(() => {
        let mainPath = "/vehicle/notForSale";
        let vin = props.vin;
        let payload = {vin};

        fetch(constants.postURL + mainPath,
        {
          method:"POST",
          headers:{"Content-Type":"application/json"},
          body:JSON.stringify(payload)
        }
        )
      .then(res=>res.json())
      .then((result)=>{
        result = JSON.stringify(result);
        result = JSON.parse(result);
        console.log(result);
        if(result)
        setShowSale(false);
        else
        setShowSale(true);

      }
      );
  
    }, []);

    if(!showSale)
    return (      
      <ol class="horizontal-list">
        <li>{props.vin}</li>
        <li>{props.chassisType}</li>
        <li>{props.modelYear}</li>
        <li>{props.modelName}</li>
        <li>{props.manufacturer}</li>
        <li>{props.fuelType}</li>
        <li>{props.color}</li>
        <li>{props.mileage}</li>
        <li>{props.salesPrice}</li>
        <li>{props.costOfParts}</li>
        <li>{props.description}</li>
      </ol>
        );

    return (      
  <ol class="horizontal-list">
    <li>{props.vin}</li>
    <li>{props.chassisType}</li>
    <li>{props.modelYear}</li>
    <li>{props.modelName}</li>
    <li>{props.manufacturer}</li>
    <li>{props.fuelType}</li>
    <li>{props.color}</li>
    <li>{props.mileage}</li>
    <li>{props.salesPrice}</li>
    <li>{props.costOfParts}</li>
    <li>{props.description}</li>
    <button onClick={navigateToSalesForm}>Sell Vehicle</button>
  </ol>
    );
    
  }

