import React, { useState } from "react";
import '../Vehicle.css';
import { useNavigate } from 'react-router-dom';

    export function InventoryClerkVehicle(props) {
      const navigate = useNavigate();
    const [background, setBackground] = useState('white');
    let [textColor, setTextColor] = useState('black');
    let [style, setStyle]  = useState({
      background: background,
      color: textColor
    });

    function navigateToDetailsPage(){
      navigate('/DetailsPageInventoryClerk?vin=' + props.vin);
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
    <li>{props.salesPrice}</li>
  </ol>
    );
    
  }

