import React, { useState } from "react";
import '../Vehicle.css';
import { useNavigate } from 'react-router-dom';

    export function ManagerVehicle(props) {
      const navigate = useNavigate();
      const queryParameters = new URLSearchParams(window.location.search);
      let user = queryParameters.get("user");
    function navigateToDetailsPage(){
      navigate('/DetailsPageManager?vin=' + props.vin + "&user=" + user);
    };


    return (      
  <ol class="horizontal-list" onClick={navigateToDetailsPage}>
    <li>{props.vin}</li>
    <li>{props.chassisType}</li>
    <li>{props.modelYear}</li>
    <li>{props.manufacturer}</li>
    <li>{props.modelName}</li>
    <li>{props.fuelType}</li>
    <li>{props.color}</li>
    <li>{props.mileage}</li>
    <li>${(Math.round(props.salesPrice * 100) / 100).toFixed(2)}</li>

  </ol>
    );
    
  }

