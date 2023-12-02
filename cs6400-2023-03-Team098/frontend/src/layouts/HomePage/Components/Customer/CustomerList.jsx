import { useEffect, useState } from "react";
import { SpinnerLoading } from "../../../Utils/SpinnerLoading";
import { Vehicle } from "../Vehicle";
import '../Vehicle.css';
import { Customer } from "./Customer";
import { VehicleColumnBar } from "../VehicleColumnBar";
import { CustomerListColumnBar } from "./CustomerListColumnBar";
import AddNewCustomer from "../../Controller/AddNewCustomer";

var constants = require('../../../../constants')

export function CustomerList(props) {
  let [customerList, setCustomerList] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const [firstSearch, setFirstSearch] = useState(true);

  useEffect(() => {
    let mainPath = "/customer/findCustomer";
    setIsLoading(true);
    fetch(constants.postURL + mainPath,
    {
      method:"POST",
      headers:{"Content-Type":"application/json"},
      body:JSON.stringify(props.payload)
    }
    )
  .then(res=>res.json())
  .then((result)=>{
    customerList = [];
    result = JSON.stringify(result);
    result = JSON.parse(result);
    for(const key in result)
    {
      customerList.push(
      <Customer driverLicense = {result[key].driversLicenseNumber}
      businessTaxId = {result[key].businessTaxId}
      street = {result[key].street}
      city = {result[key].city}
      state = {result[key].state}
      postal = {result[key].postalCode}
      phone = {result[key].phoneNumber}
      email = {result[key].email}/>);
    }
    setFirstSearch(false);
    setCustomerList(customerList);
    if(customerList.length >= 1)
    {
    props.setCustomerPayload(customerList[0].props)
    }

  }
)
setIsLoading(false);
}, []);

if (firstSearch)
{
  return (
    <div>
    </div>
  )
}

  if (isLoading) {
    return (
      <SpinnerLoading/>
    )
  }

  if(customerList.length == 0)
  {
    return (
      <>
      <div class="list-container">
      <AddNewCustomer setCustomerPayload = {props.setCustomerPayload}></AddNewCustomer>
      </div>
    </>
    );
}
else
{
    return (
    <>
    <CustomerListColumnBar></CustomerListColumnBar>
    <div class="list-container">
    {customerList}
  </div>
  </>
    );
  }
}