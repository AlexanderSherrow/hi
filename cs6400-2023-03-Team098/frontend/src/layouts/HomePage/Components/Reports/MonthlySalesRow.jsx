import React, { useState } from "react";
import '../Vehicle.css';
import { useNavigate } from 'react-router-dom';

    export function MonthlySalesRow(props) {
    const [background, setBackground] = useState('white');
    let [textColor, setTextColor] = useState('black');
    let [style, setStyle]  = useState({
      background: background,
      color: textColor
    });
    const navigate = useNavigate();

    function navigateToDrillDown(){
      // 👇️ navigate to /
      navigate('/MonthlySalesDrillDown?month=' + props.month + '&year=' + props.year);
    };


    return (      
  <ol class="horizontal-list">
    <li>{props.year}</li>
    <li>{props.month}</li>
    <li>{props.vehiclesSold}</li>
    <li>${(Math.round(props.totalSaleIncome * 100) / 100).toFixed(2)}</li>
    <li>${(Math.round(props.totalNetIncome * 100) / 100).toFixed(2)}</li>
    <button onClick={navigateToDrillDown}>Click for drill down report</button>
  </ol>
    );
    
  }

