import React, { useState } from "react";
import '../Vehicle.css';
import { useNavigate } from 'react-router-dom';

    export function PartStatisticsRow(props) {
    const [background, setBackground] = useState('white');
    let [textColor, setTextColor] = useState('black');
    let [style, setStyle]  = useState({
      background: background,
      color: textColor
    });

    return (      
  <ol class="horizontal-list">
    <li>{props.vendorName}</li>
    <li>{props.quantity}</li>
    <li>${(Math.round(props.cost * 100) / 100).toFixed(2)}</li>
    </ol>
    );
    
  }

