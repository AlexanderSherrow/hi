
import '../VehicleColumnBar.css'; // Tell webpack that Button.js uses these styles

export function DetailsPageBoughtColumns()
{
    return(
        <ol class="horizontal-list">
        <li>Street</li>
        <li>City</li>
        <li>State</li>
        <li>Postal Code</li>
        <li>Phone</li>
        <li>Email</li>
        <li>Business Name</li>
        <li>Business Contact Title</li>
        <li>Business First Name</li>
        <li>Business Last Name</li>
        <li>Individual First Name</li>
        <li>Individual Last Name</li>
        <li>Sales Person First Name</li>
        <li>Sales Person Last Name</li>
        <li>Sold Date</li>
      </ol> 
         );
}