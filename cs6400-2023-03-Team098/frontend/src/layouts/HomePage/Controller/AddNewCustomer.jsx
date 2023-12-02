import { useState } from "react";

var constants = require("../../../constants");

function AddNewCustomer(props) {
  let [confirmed, setConfirmed] = useState(false);
  const formHandler = (e) => {
    e.preventDefault();
    customer(
      e.target[0].value,
      e.target[1].value,
      e.target[2].value,
      e.target[3].value,
      e.target[4].value,
      e.target[5].value,
      e.target[6].value,
      e.target[7].value,
      e.target[8].value,
      e.target[9].value,
      e.target[10].value,
      e.target[11].value,
      e.target[12].value,
      e.target[13].value,
    );
  };

  const customer = (
    state,
    city,
    street,
    postal,
    phone,
    email,
    businessTaxId,
    businessName,
    businessContactTitle,
    businessFirstName,
    businessLastName,
    driverLicense,
    firstName,
    lastName
  ) => {
    let newCustomer = true;
    const newUser = {
      state,
      city,
      street,
      postal,
      phone,
      email,
      businessTaxId,
      businessName,
      businessContactTitle,
      businessFirstName,
      businessLastName,
      driverLicense,
      firstName,
      lastName,
      newCustomer
    };
    props.setCustomerPayload(newUser);
    setConfirmed(true);
  };

  if (confirmed)
    return (
      <div className="App">
      <form onSubmit={formHandler}>
      <div>Customer Details</div>
        <input type="text" name="state" placeholder="State" />
        <input type="text" name="city" placeholder="City" />
        <input type="text" name="street" placeholder="Street" />
        <input type="text" name="postal_code" placeholder="Postal Code" />
        <input type="text" name="phone_number" placeholder="Phone Number" />
        <input type="text" name="email" placeholder="email" />
        <div>Business Details (If inapplicable, please leave blank!)</div>
        <input type="text" name="businessTaxId" placeholder="Business Tax Id" />
        <input type="text" name="businessName" placeholder="Business Name" />
        <input type="text" name="businessContactTitle" placeholder="Contact Title" />
        <input type="text" name="businessFirstName" placeholder="First Name" />
        <input type="text" name="businessLastName" placeholder="Last Name" />
        <div>Individual Details (If inapplicable, please leave blank!)</div>
        <input
          type="text"
          name="driversLicenseNumber"
          placeholder="Drivers License Number"
        />
        <input type="text" name="firstName" placeholder="Individual First Name" />
        <input type="text" name="lastName" placeholder="Individual Last Name" />

        <br></br>
      </form>
      <hr />
    </div>
    );

  return (
    <div className="App">
      <form onSubmit={formHandler}>
      <div>Customer Details</div>
        <input type="text" name="state" placeholder="State" />
        <input type="text" name="city" placeholder="City" />
        <input type="text" name="street" placeholder="Street" />
        <input type="text" name="postal_code" placeholder="Postal Code" />
        <input type="text" name="phone_number" placeholder="Phone Number" />
        <input type="text" name="email" placeholder="email" />
        <div>Business Details (If inapplicable, please leave blank!)</div>
        <input type="text" name="businessTaxId" placeholder="Business Tax Id" />
        <input type="text" name="businessName" placeholder="Business Name" />
        <input type="text" name="businessContactTitle" placeholder="Contact Title" />
        <input type="text" name="businessFirstName" placeholder="First Name" />
        <input type="text" name="businessLastName" placeholder="Last Name" />
        <div>Individual Details (If inapplicable, please leave blank!)</div>
        <input
          type="text"
          name="driversLicenseNumber"
          placeholder="Drivers License Number"
        />
        <input type="text" name="firstName" placeholder="Individual First Name" />
        <input type="text" name="lastName" placeholder="Individual Last Name" />
        <br></br>
        <button type="submit">Confirm new Customer</button>
      </form>
      <hr />
    </div>
  );
}
export default AddNewCustomer;
