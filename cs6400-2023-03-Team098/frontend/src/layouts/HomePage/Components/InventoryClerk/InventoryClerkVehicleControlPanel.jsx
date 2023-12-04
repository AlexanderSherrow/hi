import { useState } from "react";
import InventoryClerkVehicleSearch from "../../Controller/InventoryClerkVehicleController";
import { SpinnerLoading } from "../../../Utils/SpinnerLoading";
import { VehicleColumnBar } from "../VehicleColumnBar";
import { useLocation, useNavigate } from 'react-router-dom'
import { InventoryClerkVehicleList } from "./InventoryClerkVehicleList";
import { InventoryClerkVehicleForSaleCount } from "./InventoryClerkVehicleForSaleCount";
import { InventoryClerkVehicleNotForSaleCount } from "./InventoryClerkVehicleNotForSaleCount";

import { AddNewCarButton } from "../AddNewCarButton";

export function InventoryClerkVehicleControlPanel() {
  const location = useLocation().pathname;
  const [refresh, setRefresh] = useState(false);
  const [payload, setPayload] = useState([])
  const navigate = useNavigate();

  function logout(){
    // 👇️ navigate to /
    navigate('/');
  };

  if(refresh == false)
  {
    return (
      <>
      <button onClick={logout}>LOG OUT</button>
      <InventoryClerkVehicleForSaleCount/>
      <InventoryClerkVehicleNotForSaleCount/>
      <AddNewCarButton/>
      <InventoryClerkVehicleSearch path method1Data={{setRefresh, setPayload}}/>
      Click on a row to go to the details page!
      <VehicleColumnBar/>
      <InventoryClerkVehicleList path ={location} payload={payload} setRefresh = {setRefresh} refresh={refresh}/>
      </>
    );
  }
  else
  {
    return (
      <>
        <SpinnerLoading/>
        <InventoryClerkVehicleSearch path method1Data={{setRefresh, setPayload}}/>
      </>
    );
  }
  
}
