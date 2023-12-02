import React, { useEffect, useState } from "react";
import './Vehicle.css';



    export function PartNoUpdate(props) {


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

