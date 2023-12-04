import React, { useEffect, useState } from "react";
import '../Vehicle.css';
import { useNavigate } from 'react-router-dom';

    export function DetailsSales(props) {
      const navigate = useNavigate();
      const queryParameters = new URLSearchParams(window.location.search);
      let user = queryParameters.get("user");
      let [showSale, setShowSale] = useState(true);
      var constants = require('../../../../constants')

      function navigateToSalesForm(){
        // 👇️ navigate to /
        navigate('/SellVehicleForm?vin=' + props.vin + "&user=" + user);
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

    if(showSale)
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
        <li>${(Math.round(props.salesPrice * 100) / 100).toFixed(2)}</li>
        <li>{props.description}</li>
        <button onClick={navigateToSalesForm}>Sell Vehicle</button>
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
    <li>${(Math.round(props.salesPrice * 100) / 100).toFixed(2)}</li>
    <li>{props.description}</li>
    <button onClick={navigateToSalesForm}>Sell Vehicle</button>
  </ol>
    );
    
  }

