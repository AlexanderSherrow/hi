
import '../VehicleColumnBar.css'; // Tell webpack that Button.js uses these styles

export function DetailsPageSalesColumnBar()
{
    return(
        <ol class="horizontal-list">
        <li>VIN</li>
        <li>Vehicle type</li>
        <li>Model Year</li>
        <li>Model Name</li>
        <li>Manufacturer</li>
        <li>Fuel Type</li>
        <li>Color(s)</li>
        <li>Mileage</li>
        <li>Sales price</li>
        <li>Description</li>
        <li>Click to sell</li>
      </ol> 
         );
}