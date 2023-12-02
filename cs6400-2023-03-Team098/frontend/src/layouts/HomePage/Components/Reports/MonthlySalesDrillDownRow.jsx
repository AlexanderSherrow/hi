import React, { useState } from "react";
import '../Vehicle.css';
import { useNavigate } from 'react-router-dom';

    export function MonthlySalesDrillDownRow(props) {
    const [background, setBackground] = useState('white');
    let [textColor, setTextColor] = useState('black');
    let [style, setStyle]  = useState({
      background: background,
      color: textColor
    });

    return (      
  <ol class="horizontal-list">
    <li>{props.firstName}</li>
    <li>{props.lastName}</li>
    <li>{props.vehiclesSold}</li>
    <li>{props.totalSales}</li>
  </ol>
    );
    
  }

