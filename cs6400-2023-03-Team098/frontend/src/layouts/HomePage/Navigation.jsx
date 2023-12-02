
var constants = require('../../constants')

export const Navigation = () => {
  return (
    <>
    <a href= {constants.hostURL + '/User'}>User</a>
    <br></br>
    <a href= {constants.hostURL}>Public Vehicle View</a>
    <br></br>
    <a href= {constants.hostURL + '/InventoryVehicle'}>Inventory Clerk Vehicle View</a>
    <br/>
    <a href= {constants.hostURL + '/SalesVehicle'}>Sales people Vehicle View</a>
    <br/>
    <a href= {constants.hostURL + '/ManagerVehicle'}>Manager Vehicle View</a>
    <br/>
    <a href= {constants.hostURL + '/OwnerVehicle'}>Owner Vehicle View</a>
    </>
  );
};