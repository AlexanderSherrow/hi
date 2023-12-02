import React, { useState } from "react";
import '../Vehicle.css';



    export function AddNewPartRow(props) {

    return (      
  <ol class="horizontal-list">
    <li>{props.vin}</li>
  </ol>
    );
    
  }

