import { useEffect } from "react";

var constants = require("../../../constants");

function InventoryClerkVehicleSearch(props) {
  let { setRefresh, setPayload } = props.method1Data;
  const formHandler = (e) => {
    setRefresh(true);
    e.preventDefault();
    vehiclePayload(
      e.target[0].value,
      e.target[1].value,
      e.target[2].value,
      e.target[3].value,
      e.target[4].value,
      e.target[5].value,
      e.target[6].value
    );
  };

  async function vehiclePayload(
    chassisType,
    manufacturer,
    modelYear,
    fuelType,
    keyword,
    color,
    vin
  ) {
    const vehicle = {
      chassisType,
      manufacturer,
      modelYear,
      fuelType,
      keyword,
      color,
      vin,
    };
    await timeout(1000); //for 1 sec delay
    setRefresh(false);
    setPayload(vehicle);
  }

  useEffect(() => {
    let mainPath = "/manufacturer/getAll";
    fetch(constants.postURL + mainPath, {
      method: "GET",
      headers: { "Content-Type": "application/json" },
    })
      .then((res) => res.json())
      .then((result) => {
        let select = document.getElementById("manufacturer");
        var opt = document.createElement("option");
        opt.value = "";
        opt.innerHTML = "Manufacturer";
        select.appendChild(opt);
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
        var opt = document.createElement("option");
        opt.value = "";
        opt.innerHTML = "Color";
        select.appendChild(opt);
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
        var opt = document.createElement("option");
        opt.value = "";
        opt.innerHTML = "Fuel Type";
        select.appendChild(opt);
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
        var opt = document.createElement("option");
        opt.value = "";
        opt.innerHTML = "Vehicle Type";
        select.appendChild(opt);
        for (var i = 0; i < result.length; i++) {
          let name = result[i];
          var opt = document.createElement("option");
          opt.value = name;
          opt.innerHTML = name;
          select.appendChild(opt);
        }
      });
  }, []);

  function timeout(delay) {
    return new Promise((res) => setTimeout(res, delay));
  }

  return (
    <div className="App">
      <form onSubmit={formHandler}>
        <select name="chassisType" id="chassisType" />
        <select
          name="manufacturer"
          id="manufacturer"
          placeholder="Manufacturer"
        />
        <input
          type="number"
          name="modelYear"
          min="1000"
          max={new Date().getFullYear() + 1}
          placeholder="Year"
        />
        <select name="fuelType" id="fuelType" />
        <input type="text" name="keyword" placeholder="keyword" />
        <select name="color" id="color" />
        <input type="text" name="vin" placeholder="VIN" />
        <button type="submit">Search</button>
      </form>
      <hr />
    </div>
  );
}
export default InventoryClerkVehicleSearch;
