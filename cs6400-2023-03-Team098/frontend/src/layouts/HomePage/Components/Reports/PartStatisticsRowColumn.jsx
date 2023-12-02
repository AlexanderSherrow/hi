import React, { useState } from "react";
import '../Vehicle.css';
import { useNavigate } from 'react-router-dom';

    export function PartStatisticsRowColumn(props) {
    const [background, setBackground] = useState('white');
    let [textColor, setTextColor] = useState('black');
    let [style, setStyle]  = useState({
      background: background,
      color: textColor
    });
    console.log(props);

    return (      
  <ol class="horizontal-list">
    <li>Vendor Name</li>
    <li>number of parts supplied
</li>
<li>Total dollar amount
spent on parts.
</li>
  </ol>
    );
    
  }

