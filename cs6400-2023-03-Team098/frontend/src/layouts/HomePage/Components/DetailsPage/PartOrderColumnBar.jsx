
import '../VehicleColumnBar.css'; // Tell webpack that Button.js uses these styles

export function PartOrderColumnBar()
{
    return(
        <ol class="horizontal-list">
        <li>Part Number</li>
        <li>Description</li>
        <li>Vendor</li>
        <li>Purchase Order</li>
        <li>Cost</li>
        <li>Status</li>
        <li>Click me to update status</li>
      </ol> 
         );
}
