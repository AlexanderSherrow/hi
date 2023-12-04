import React, { useState } from "react";
import './Vehicle.css';
import { useNavigate } from 'react-router-dom';

    export function Vehicle(props) {
      const navigate = useNavigate();
      const disableNav = props.disableNav;
    const [background, setBackground] = useState('white');
    let [textColor, setTextColor] = useState('black');
    let [style, setStyle]  = useState({
      background: background,
      color: textColor
    });

    function navigateToDetailsPage(){
      // 👇️ navigate to /
      if(!disableNav)
      navigate('/DetailsPage?vin=' + props.vin);
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

