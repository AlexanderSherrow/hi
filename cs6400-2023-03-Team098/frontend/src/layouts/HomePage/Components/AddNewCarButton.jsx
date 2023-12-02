var constants = require('../../../constants')

export function AddNewCarButton()
{
  const queryParameters = new URLSearchParams(window.location.search);
  const user = queryParameters.get("user");
    return (
      <div>
        <a href= {constants.hostURL + '/AddNewVehicleForm?user=' + user }>Add New Vehicle Form</a>
      </div>  
    );
}