import React, { useEffect, useState } from "react";
import './Vehicle.css';



    export function PartNoUpdate(props) {


return (      
  <ol class="horizontal-list">
    <li>{props.partNumber}</li>
    <li>{props.description}</li>
    <li>{props.vendorName}</li>
    <li>{props.purchaseOrderNumber}</li>
    <li>${(Math.round(props.totalPartsCost * 100) / 100).toFixed(2)}</li>
    <li>{props.currentStatus}</li>
  </ol>
    );
    
  }

