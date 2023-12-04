import React, { useState } from "react";
import '../Vehicle.css';



    export function Details(props) {

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
  </ol>
    );
    
  }

