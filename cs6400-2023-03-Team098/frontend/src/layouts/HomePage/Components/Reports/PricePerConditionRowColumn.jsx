import React, { useState } from "react";
import '../Vehicle.css';
import { useNavigate } from 'react-router-dom';

    export function PricePerConditonRowColumn(props) {
    const [background, setBackground] = useState('white');
    let [textColor, setTextColor] = useState('black');
    let [style, setStyle]  = useState({
      background: background,
      color: textColor
    });
    console.log(props);

    return (      
  <ol class="horizontal-list">
    <li>Vehicle Type</li>
    <li>Excellent</li>
    <li>Fair</li>
    <li>Good</li>
    <li>Very Good</li>

  </ol>
    );
    
  }

