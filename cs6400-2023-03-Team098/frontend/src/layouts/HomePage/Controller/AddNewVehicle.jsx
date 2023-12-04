import { useEffect, useState } from "react";
var constants = require("../../../constants");

function AddNewVehicle(props) {
  let [confirmed, setConfirmed] = useState(false);
  let [vin, setVin] = useState(0);
  const formHandler = (e) => {
    e.preventDefault();
    vehicleUpload(
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
      e.target[12].value
    );
  };

  const vehicleUpload = (
    modelName,
    modelYear,
    description,
    vin,
    mileage,
    manufacturer,
    fuelType,
    chassisType,
    color,
    condition,
    purchasePrice,
    purchaseDate,
  ) => {
    const queryParameters = new URLSearchParams(window.location.search);
    const inventoryClerk = queryParameters.get("user");
    const vehicle = {
      modelName,
      modelYear,
      description,
      vin,
      mileage,
      manufacturer,
      fuelType,
      chassisType,
      color,
      condition,
      purchasePrice,
      purchaseDate,
      inventoryClerk,
    };
    let customerPayload = props.customerPayload;
    let payload = { ...vehicle, ...customerPayload };
    fetch(constants.postURL + "/vehicle/save", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(payload),
    }).then(() => {
      console.log("New Vehicle added");
      setConfirmed(true);
      setVin(vin);
    });
  };

  useEffect(() => {
    let mainPath = "/manufacturer/getAll";
    fetch(constants.postURL + mainPath, {
      method: "GET",
      headers: { "Content-Type": "application/json" },
    })
      .then((res) => res.json())
      .then((result) => {
        let select = document.getElementById("manufacturer");

        for (var i = 0; i < result.length; i++) {
          let name = result[i];
          var opt = document.createElement("option");
          opt.value = name;
          opt.innerHTML = name;
          select.appendChild(opt);
        }
      });

    mainPath = "/color/getAll";
    fetch(constants.postURL + mainPath, {
      method: "GET",
      headers: { "Content-Type": "application/json" },
    })
      .then((res) => res.json())
      .then((result) => {
        let select = document.getElementById("color");

        for (var i = 0; i < result.length; i++) {
          let name = result[i];
          var opt = document.createElement("option");
          opt.value = name;
          opt.innerHTML = name;
          select.appendChild(opt);
        }
      });

    mainPath = "/fuelType/getAll";
    fetch(constants.postURL + mainPath, {
      method: "GET",
      headers: { "Content-Type": "application/json" },
    })
      .then((res) => res.json())
      .then((result) => {
        let select = document.getElementById("fuelType");

        for (var i = 0; i < result.length; i++) {
          let name = result[i];
          var opt = document.createElement("option");
          opt.value = name;
          opt.innerHTML = name;
          select.appendChild(opt);
        }
      });

    mainPath = "/chassisType/getAll";
    fetch(constants.postURL + mainPath, {
      method: "GET",
      headers: { "Content-Type": "application/json" },
    })
      .then((res) => res.json())
      .then((result) => {
        let select = document.getElementById("chassisType");

        for (var i = 0; i < result.length; i++) {
          let name = result[i];
          var opt = document.createElement("option");
          opt.value = name;
          opt.innerHTML = name;
          select.appendChild(opt);
        }
      });

    mainPath = "/condition/getAll";
    fetch(constants.postURL + mainPath, {
      method: "GET",
      headers: { "Content-Type": "application/json" },
    })
      .then((res) => res.json())
      .then((result) => {
        let select = document.getElementById("condition");

        for (var i = 0; i < result.length; i++) {
          let name = result[i];
          var opt = document.createElement("option");
          opt.value = name;
          opt.innerHTML = name;
          select.appendChild(opt);
        }
      });
  }, []);
  if (!confirmed)
    return (
      <div className="App">
        <form onSubmit={formHandler}>
          <input type="text" name="modelName" placeholder="Model Name" />
          <input
            type="number"
            name="modelYear"
            min="1000"
            max={new Date().getFullYear() + 1}
            placeholder="Year"
          />
          <input type="text" name="description" placeholder="Description" />
          <input type="text" name="vin" placeholder="VIN" />
          <input type="number" name="mileage" placeholder="Mileage" />
          <select
            name="manufacturer"
            id="manufacturer"
            placeholder="Manufacturer"
          />
          <select name="fuelType" id="fuelType" />
          <select name="chassisType" id="chassisType" />
          <select name="color" id="color" />
          <select name="condition" id="condition" />
          <input type="number" name="purchasePrice" placeholder="Purchase Price" />
          <input type="text" name="purchaseDate" placeholder="Date (YYYY-MM-DD)" />
          <button type="submit">Add new Vehicle</button>
        </form>
        <hr />
      </div>
    );

  return (
    <div className="App">
      <a href={constants.hostURL + "/DetailsPageInventoryClerk?vin=" + vin}>
        Click me to see your new car details!
      </a>
    </div>
  );
}
export default AddNewVehicle;
