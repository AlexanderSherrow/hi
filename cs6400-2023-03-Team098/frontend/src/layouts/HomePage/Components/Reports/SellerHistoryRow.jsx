import React, { useState } from "react";
import '../Vehicle.css';
import { useNavigate } from 'react-router-dom';

    export function SellerHistoryRow(props) {
    const [background, setBackground] = useState('white');
    let [textColor, setTextColor] = useState('black');
    let [style, setStyle]  = useState({
      background: background,
      color: textColor
    });

    if(props.costOfParts >= 500)
    return (      
        <ol class="horizontal-list-red">
              <li>{props.name}</li>
              <li>{props.totalSold}</li>
              <li>{props.purchasePrice}</li>
              <li>{props.partsOrdered}</li>
              <li>{props.costOfParts}</li>
        </ol>
          );


    return (      
  <ol class="horizontal-list">
        <li>{props.name}</li>
        <li>{props.totalSold}</li>
        <li>{props.purchasePrice}</li>
        <li>{props.partsOrdered}</li>
        <li>{props.costOfParts}</li>
  </ol>
    );
    
  }

