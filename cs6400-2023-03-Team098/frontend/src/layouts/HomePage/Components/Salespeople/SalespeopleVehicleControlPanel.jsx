import { useState } from "react";
import SalespoepleVehicleSearch from "../../Controller/SalespeopleVehicleController";
import { SpinnerLoading } from "../../../Utils/SpinnerLoading";
import { VehicleColumnBar } from "../VehicleColumnBar";
import { useLocation, useNavigate } from 'react-router-dom'
import { SalespeopleVehicleList } from "./SalespeopleVehicleList";

export function SalespeopleVehicleControlPanel() {
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
      <SalespoepleVehicleSearch path method1Data={{setRefresh, setPayload}}/>
      Click on a row to go to the details page!
      <VehicleColumnBar/>
      <SalespeopleVehicleList path ={location} payload={payload} setRefresh = {setRefresh} refresh={refresh}/>
      </>
    );
  }
  else
  {
    return (
      <>
        <SpinnerLoading/>
        <SalespoepleVehicleSearch path method1Data={{setRefresh, setPayload}}/>
      </>
    );
  }
  
}
