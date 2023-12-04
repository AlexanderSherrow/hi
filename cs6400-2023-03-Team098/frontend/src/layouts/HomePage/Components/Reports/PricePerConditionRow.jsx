import React, { useState } from "react";
import '../Vehicle.css';
import { useNavigate } from 'react-router-dom';

    export function PricePerConditonRow(props) {
    const [background, setBackground] = useState('white');
    let [textColor, setTextColor] = useState('black');
    let [style, setStyle]  = useState({
      background: background,
      color: textColor
    });
    console.log(props);

    return (      
  <ol class="horizontal-list">
    <li>{props.chassisType}</li>
    <li>${(Math.round(props.excellent * 100) / 100).toFixed(2)}</li>
    <li>${(Math.round(props.fair * 100) / 100).toFixed(2)}</li>
    <li>${(Math.round(props.good * 100) / 100).toFixed(2)}</li>
    <li>${(Math.round(props.veryGood * 100) / 100).toFixed(2)}</li>
  </ol>
    );
    
  }

