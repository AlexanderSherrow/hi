import { useState } from "react";
import { SpinnerLoading } from "../../../Utils/SpinnerLoading";
import { VehicleColumnBar } from "../VehicleColumnBar";
import { useLocation, useNavigate } from "react-router-dom";
import { OwnerVehicleList } from "./OwnerVehicleList";

import { InventoryClerkVehicleForSaleCount } from "../InventoryClerk/InventoryClerkVehicleForSaleCount";
import { InventoryClerkVehicleNotForSaleCount } from "../InventoryClerk/InventoryClerkVehicleNotForSaleCount";
import OwnerVehicleSearch from "../../Controller/OwnerVehicleController";
import { AddNewCarButton } from "../AddNewCarButton";

export function OwnerVehicleControlPanel() {
  const location = useLocation().pathname;
  const [refresh, setRefresh] = useState(false);
  const [payload, setPayload] = useState([]);
  var constants = require("../../../../constants");
  const navigate = useNavigate();

  function logout() {
    navigate("/");
  }

  if (refresh == false) {
    return (
      <>
        <button onClick={logout}>LOG OUT</button>
        <InventoryClerkVehicleForSaleCount />
        <InventoryClerkVehicleNotForSaleCount />
        <a href={constants.hostURL + "/PricePerCondition"}>
          Price per Condition Report
        </a>
        <br />
        <a href={constants.hostURL + "/AverageTimeInInventory"}>
          Average time in Inventory by Vehicle Type
        </a>
        <br />
        <a href={constants.hostURL + "/PartStatistics"}>Parts Statistics</a>
        <br />
        <a href={constants.hostURL + "/SellerHistory"}>Seller History</a>
        <br></br>
        <a href={constants.hostURL + "/MonthlySales"}>Monthly Sales</a>

        <OwnerVehicleSearch path method1Data={{ setRefresh, setPayload }} />
        <AddNewCarButton/>
        Click on a row to go to the details page!
        <VehicleColumnBar />
        <OwnerVehicleList
          path={location}
          payload={payload}
          setRefresh={setRefresh}
          refresh={refresh}
        />
      </>
    );
  } else {
    return (
      <>
        <SpinnerLoading />
        <OwnerVehicleSearch path method1Data={{ setRefresh, setPayload }} />
      </>
    );
  }
}
