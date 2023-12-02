import { useState } from "react";
import CustomerSearch from "../../Controller/CustomerController";
import { SpinnerLoading } from "../../../Utils/SpinnerLoading";
import { useLocation } from 'react-router-dom'
import { CustomerList } from "./CustomerList";

export function CustomerSearchControlPanel(props) {
  const location = useLocation().pathname;
  const [refresh, setRefresh] = useState(false);
  const [payload, setPayload] = useState([])
  const [agreement, setAgreement] = useState(false);
  const handleChange = (event) => {
    setAgreement(!agreement);
  }
    if(refresh == false)
    {
      return ( 
        <>
        <CustomerSearch method1Data={{setRefresh, setPayload}}/>
        <CustomerList path ={location} payload={payload} setRefresh = {setRefresh} refresh={refresh} setCustomerPayload = {props.setCustomerPayload}/>
        </>
      );
    }
    else
    {
      return (
        <>
          <CustomerSearch method1Data={{setRefresh, setPayload}}/>
          <SpinnerLoading/>
        </>
      );
    }
}
