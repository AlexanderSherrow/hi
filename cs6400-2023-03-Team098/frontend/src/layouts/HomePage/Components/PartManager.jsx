import React, { useEffect, useState } from "react";
import './Vehicle.css';
import { useNavigate } from 'react-router-dom';
var constants = require('../../../constants')



    export function PartManager(props) {

    const [background, setBackground] = useState('white');
    let [textColor, setTextColor] = useState('black');
    let [style, setStyle]  = useState({
      background: background,
      color: textColor
    });

return (      
  <ol class="horizontal-list">
    <li>{props.partNumber}</li>
    <li>{props.description}</li>
    <li>{props.vendorName}</li>
    <li>{props.purchaseOrderNumber}</li>
    <li>{props.totalPartsCost}</li>
    <li>{props.currentStatus}</li>
  </ol>
    );
    
  }

