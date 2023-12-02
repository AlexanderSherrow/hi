import { useEffect, useState } from "react";
import { SpinnerLoading } from "../../../Utils/SpinnerLoading";
import {
  InventoryClerkVehicle,
  SalespeopleVehicle,
} from "./SalespeopleVehicle";
import "../Vehicle.css";

var constants = require("../../../../constants");

export function SalespeopleVehicleList(props) {
  let [vehicleList, setVehicleList] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const [firstSearch, setFirstSearch] = useState(false);

  useEffect(() => {
    let mainPath = "/vehicle/salesVehicleSearch";
    if (props.payload.length == 0) return;

    setIsLoading(true);
    fetch(constants.postURL + mainPath, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(props.payload),
    })
      .then((res) => res.json())
      .then((result) => {
        vehicleList = [];
        result = JSON.stringify(result);
        result = JSON.parse(result);
        for (const key in result) {
          vehicleList.push(
            <SalespeopleVehicle
              key={result[key].vin}
              vin={result[key].vin}
              modelName={result[key].modelName}
              modelYear={result[key].modelYear}
              description={result[key].description}
              mileage={result[key].mileage}
              manufacturer={result[key].manufacturer}
              chassisType={result[key].chassisType}
              fuelType={result[key].fuelType}
              color={result[key].color}
              salesPrice={result[key].salesPrice}
            />
          );
        }
        setFirstSearch(true);
        setVehicleList(vehicleList);
      });
    setIsLoading(false);
  }, []);

  if (isLoading) {
    return <SpinnerLoading />;
  }
  if (!firstSearch) return <div>Please search a vehicle.</div>;

  if (vehicleList.length == 0)
    return <div>Sorry, it looks like we don’t have that in stock! </div>;
  return <div class="list-container">{vehicleList}</div>;
}
