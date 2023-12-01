
import './UsersColumnBar.css'; // Tell webpack that Button.js uses these styles

export function UsersColumnBar()
{
    return(
<div>
  <div class = "left">User Name</div>
  <div class = "left">Pass word</div>
  <div class = "left">First Name</div>
  <div class = "left">Last Name</div>
</div>
    );
}