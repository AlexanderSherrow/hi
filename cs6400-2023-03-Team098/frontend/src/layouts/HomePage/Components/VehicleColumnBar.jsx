
import './VehicleColumnBar.css'; // Tell webpack that Button.js uses these styles

export function VehicleColumnBar()
{
    return(
        <ol class="horizontal-list">
        <li>VIN</li>
        <li>Vehicle type</li>
        <li>Model Year</li>
        <li>Manufacturer</li>
        <li>Model</li>
        <li>Fuel Type</li>
        <li>Color(s)</li>
        <li>Mileage</li>
        <li>Sales price</li>
      </ol> 
         );
}