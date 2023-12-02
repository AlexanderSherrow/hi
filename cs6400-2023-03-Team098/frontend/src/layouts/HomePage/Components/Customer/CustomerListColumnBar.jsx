
import '../VehicleColumnBar.css'; // Tell webpack that Button.js uses these styles

export function CustomerListColumnBar()
{
    return(
        <ol class="horizontal-list">
        <li>Driver's License</li>
        <li>Business Tax Id</li>
        <li>Street</li>
        <li>City</li>
        <li>State</li>
        <li>Postal Code</li>
        <li>Phone</li>
        <li>Email</li>
      </ol> 
         );
}