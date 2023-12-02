import { useEffect } from "react";
var constants = require("../../../constants");

export function SellVehicle(props) {
  const formHandler = (e) => {
    e.preventDefault();
    sellVehicleUpload(e.target[0].value, e.target[1].value);
  };

  const sellVehicleUpload = (sellDate) => {
    const queryParameters = new URLSearchParams(window.location.search);
    let salesPeople =  queryParameters.get("user");
    let salePayload = { sellDate, salesPeople };
    let customerPayload = props.customerPayload;
    let vehiclePayload = props.vehiclePayload;
    let payload = { ...vehiclePayload, ...customerPayload, ...salePayload };
    fetch(constants.postURL + "/vehicle/sellVehicle", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(payload),
    }).then(() => {
      let setIsSold = props.setIsSold;
      setIsSold(true);
      console.log("Sale complete");
    });
  };

  return (
    <div className="App">
      <form onSubmit={formHandler}>
        <input type="text" name="sellDate" placeholder="Sales Date" />
        <button type="submit">Finalize Sale</button>
      </form>
      <hr />
    </div>
  );
}
