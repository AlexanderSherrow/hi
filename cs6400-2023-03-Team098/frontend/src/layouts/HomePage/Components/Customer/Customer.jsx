import React, { useState } from "react";
import '../User.css';

    export function Customer(props) {
    return (
      <ol class="horizontal-list-user">
      <li>{props.driverLicense}</li>
      <li>{props.businessTaxId}</li>
      <li>{props.street}</li>
      <li>{props.city}</li>
      <li>{props.state}</li>
      <li>{props.postal}</li>
      <li>{props.phone}</li>
      <li>{props.email}</li>
    </ol>
    );
    
  }

