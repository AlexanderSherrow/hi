import React, { useState } from "react";
import '../Vehicle.css';
import { useNavigate } from 'react-router-dom';

    export function MonthlySalesDrillDownRowColumn(props) {
    const [background, setBackground] = useState('white');
    let [textColor, setTextColor] = useState('black');
    let [style, setStyle]  = useState({
      background: background,
      color: textColor
    });

    return (      
  <ol class="horizontal-list">
    <li>First Name</li>
    <li>Last Name</li>
    <li>Vehicles Sold</li>
    <li>Total Sales</li>
  </ol>
    );
    
  }

