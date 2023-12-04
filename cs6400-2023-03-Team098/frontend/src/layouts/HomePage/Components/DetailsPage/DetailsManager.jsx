import React, { useState } from "react";
import '../Vehicle.css';
import { DetailsPageSoldColumns } from "./DetailsPageSoldColumns";
import { DetailsPageBoughtColumns } from "./DetailsPageBoughtColumns";



    export function DetailsManager(props) {

    return (<>
  <ol class="horizontal-list">
    <li>{props.vin}</li>
    <li>{props.chassis_type}</li>
    <li>{props.model_year}</li>
    <li>{props.model_name}</li>
    <li>{props.manufacturer}</li>
    <li>{props.fuel_type}</li>
    <li>{props.color}</li>
    <li>{props.mileage}</li>
    <li>${(Math.round(props.originalPurchasePrice * 100) / 100).toFixed(2)}</li>
    <li>${(Math.round(props.costOfParts * 100) / 100).toFixed(2)}</li>
    <li>{props.description}</li>
  </ol>

Bought by BuzzCars Details:
<div class="list-container">
<DetailsPageSoldColumns/>
<ol class="horizontal-list">
<li>{props.boughtStreet}</li>
<li>{props.boughtCity}</li>
<li>{props.boughtState}</li>
<li>{props.boughtPostal}</li>
<li>{props.boughtPhone}</li>
<li>{props.boughtEmail}</li>
<li>{props.boughtBusinessName}</li>
<li>{props.boughtContactTitle}</li>
<li>{props.boughtBusinessFirstName}</li>
<li>{props.boughtBusinessLastName}</li>
<li>{props.boughtIndividualFirstName}</li>
<li>{props.boughtIndividualLastName}</li>
<li>{props.inventoryClerkFirstName}</li>
<li>{props.inventoryClerkUserLastName}</li>
<li>{props.originalPurchaseDate}</li>
</ol>
</div>

Sold to Customer Details:
<DetailsPageBoughtColumns/>
<ol class="horizontal-list">
<li>{props.soldStreet}</li>
<li>{props.soldCity}</li>
<li>{props.soldState}</li>
<li>{props.soldPostal}</li>
<li>{props.soldPhone}</li>
<li>{props.soldEmail}</li>
<li>{props.soldBusinessName}</li>
<li>{props.soldContactTitle}</li>
<li>{props.soldBusinessFirstName}</li>
<li>{props.soldBusinessLastName}</li>
<li>{props.soldIndividualFirstName}</li>
<li>{props.soldIndividualLastName}</li>
<li>{props.salespeopleFirstName}</li>
<li>{props.salespeopleLastName}</li>
<li>{props.CustomerPurchaseDate}</li>
</ol>
</>
    );
    
  }

