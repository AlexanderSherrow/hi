import { useState } from "react";
import PublicVehicleSearch from "../Controller/PublicVehicleController";
import { SpinnerLoading } from "../../Utils/SpinnerLoading";
import { VehicleColumnBar } from "./VehicleColumnBar";
import { useLocation, useNavigate } from 'react-router-dom'
import { PublicVehicleList } from "./PublicVehicleList";
import UserLogin from "../Controller/UserLogin";

export function PublicVehicleControlPanel() {
  const location = useLocation().pathname;
  const [refresh, setRefresh] = useState(false);
  const [payload, setPayload] = useState([])
  const [user, setUser] = useState([])
  const [username, setUsername] = useState("");
  const navigate = useNavigate();

console.log(user);  
  if(refresh)
  {
    return (
      <>
        <SpinnerLoading/>
        <PublicVehicleSearch method1Data={{setRefresh, setPayload}}/>
      </>
    );
  }

  if(user == "InventoryClerk")
  {
    console.log(user);
        navigate('/InventoryVehicle' + '?user=' + username);
  }

  if(user == "Salespeople")
  {
        navigate('/SalesVehicle' + '?user=' + username);
  }

  if(user == "Manager")
  {
        navigate('/ManagerVehicle' + '?user=' + username);
  }

  if(user == "Owner")
  {
        navigate('/OwnerVehicle' + '?user=' + username);
  }

  if(user == "FAILED")
  {
    return ( 
      <>
      LOGIN FAILED. TRY AGAIN.
      <UserLogin setUser = {setUser} setRefresh = {setRefresh} setUsername = {setUsername}/>
      <PublicVehicleSearch method1Data={{setRefresh, setPayload}}/>
      Click on a row to go to the details page!
      <VehicleColumnBar/>
      <PublicVehicleList path ={location} payload={payload} setRefresh = {setRefresh} refresh={refresh}/>
      </>
    );  }



    return ( 
      <>
      <UserLogin setUser = {setUser} setRefresh = {setRefresh} setUsername = {setUsername}/>
      <PublicVehicleSearch method1Data={{setRefresh, setPayload}}/>
      Click on a row to go to the details page!
      <VehicleColumnBar/>
      <PublicVehicleList path ={location} payload={payload} setRefresh = {setRefresh} refresh={refresh}/>
      </>
    );
  
}
