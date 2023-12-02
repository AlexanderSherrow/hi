import { useState } from "react";
import UserPost from "../Controller/UserController";
import { SpinnerLoading } from "../../Utils/SpinnerLoading";
import { App, UserList } from "./UserList";
import { UsersColumnBar } from "./UsersColumnBar";
import { useLocation } from 'react-router-dom'

export function UserControlPanel() {
  const location = useLocation().pathname;
  const [refresh, setRefresh] = useState(false);
  if(refresh == false)
  {
    return (
      <>
      <UsersColumnBar/>
      <UserList path = {location}/>
      <UserPost setRefresh = {setRefresh}/>
      </>
    );
  }
  else
  {
    return (
      <>
        <SpinnerLoading/>
        <UserPost setRefresh = {setRefresh}/>
      </>
    );
  }
  
}
