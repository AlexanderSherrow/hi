
import '../VehicleColumnBar.css'; // Tell webpack that Button.js uses these styles

export function PartOrderNoUpdateColumnBar()
{
    return(
        <ol class="horizontal-list">
        <li>Part Number</li>
        <li>Description</li>
        <li>Vendor</li>
        <li>Purchase Order</li>
        <li>Cost</li>
        <li>Status</li>
      </ol> 
         );
}
