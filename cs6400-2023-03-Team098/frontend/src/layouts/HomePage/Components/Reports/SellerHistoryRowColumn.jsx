import React, { useState } from "react";
import '../Vehicle.css';
import { useNavigate } from 'react-router-dom';

    export function SellerHistoryRowColumn(props) {
    const [background, setBackground] = useState('white');
    let [textColor, setTextColor] = useState('black');
    let [style, setStyle]  = useState({
      background: background,
      color: textColor
    });
    console.log(props);

    return (      
  <ol class="horizontal-list">
        <li>Name of Customer</li>
        <li>Total Number of Vehicles Sold to BuzzCars</li>
        <li>Average Purchase Price</li>
        <li>Average Parts ordered</li>
        <li>Average Cost of parts</li>
  </ol>
    );
    
  }

