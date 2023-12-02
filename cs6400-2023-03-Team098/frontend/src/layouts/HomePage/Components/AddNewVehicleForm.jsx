import AddNewVehicle from "../Controller/AddNewVehicle";
import { CustomerSearchControlPanel } from "./Customer/CustomerSearchControlPanel";
import { useState } from "react";

export function AddNewVehicleForm()
{
    const [customerPayload, setCustomerPayload] = useState([]);
    return (
        <div>
            <CustomerSearchControlPanel setCustomerPayload = {setCustomerPayload}></CustomerSearchControlPanel>
            <div>
            Please fill out the following details:
            <AddNewVehicle customerPayload = {customerPayload}></AddNewVehicle>
            </div>
        </div>
    );
}