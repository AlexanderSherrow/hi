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
    <li>{props.excellent}</li>
    <li>{props.fair}</li>
    <li>{props.good}</li>
    <li>{props.veryGood}</li>

  </ol>
    );
    
  }

