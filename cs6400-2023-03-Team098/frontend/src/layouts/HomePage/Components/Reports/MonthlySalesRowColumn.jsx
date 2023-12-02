import React, { useState } from "react";
import '../Vehicle.css';
import { useNavigate } from 'react-router-dom';

    export function MonthlySalesRowColumn(props) {
    const [background, setBackground] = useState('white');
    let [textColor, setTextColor] = useState('black');
    let [style, setStyle]  = useState({
      background: background,
      color: textColor
    });
    console.log(props);

    return (      
  <ol class="horizontal-list">
        <li>Year</li>
        <li>Month</li>
        <li>Vehicles Sold</li>
        <li>Sale Income</li>
        <li>Net Income</li>
        <li>Drill Down</li>
  </ol>
    );
    
  }

