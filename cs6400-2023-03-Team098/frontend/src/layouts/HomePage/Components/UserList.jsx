import { useEffect, useState } from "react";
import { SpinnerLoading } from "../../Utils/SpinnerLoading";
import { User } from "./User";

var constants = require('../../../constants')

export function UserList(props) {
  const [userList, setUserList] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  useEffect(() => {
    let mainPath = "/user/getAll"
    fetch(constants.postURL + mainPath,
    {
      method:"GET",
      headers:{"Content-Type":"application/json"},
    }
    )
  .then(res=>res.json())
  .then((result)=>{
    setIsLoading(true);
    result = JSON.stringify(result);
    result = JSON.parse(result);
    console.log(result);
    for(const key in result)
    {
      userList.push(
      <User key = {result[key].useName}
      username = {result[key].userName}
      password = {result[key].password}
      firstName = {result[key].firstName}
      lastName = {result[key].lastName}/>);
    }
    setUserList(userList);
    setIsLoading(false);
  }
)
}, [userList]);

  if (isLoading) {
    return (
      <SpinnerLoading/>
    )
  }
    return (
    <>
    {userList}
    </>
    );

}