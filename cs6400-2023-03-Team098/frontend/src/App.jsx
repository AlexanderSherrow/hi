import { BrowserRouter, Routes, Route } from "react-router-dom";
import './App.css';
import { UserControlPanel } from './layouts/HomePage/Components/UserControlPanel';
import { PublicVehicleControlPanel } from './layouts/HomePage/Components/PublicVehicleControlPanel';
import { InventoryClerkVehicleControlPanel } from "./layouts/HomePage/Components/InventoryClerk/InventoryClerkVehicleControlPanel";
import { AddNewVehicleForm } from "./layouts/HomePage/Components/AddNewVehicleForm";
import { DetailsPage } from "./layouts/HomePage/Components/DetailsPage/DetailsPage";
import { DetailsPageSales } from "./layouts/HomePage/Components/DetailsPage/DetailsPageSales";
import { SalesForm } from "./layouts/HomePage/Components/SalesForm";
import { SalespeopleVehicleControlPanel } from "./layouts/HomePage/Components/Salespeople/SalespeopleVehicleControlPanel";
import { DetailsPageInventoryClerk } from "./layouts/HomePage/Components/DetailsPage/DetailsPageInventoryClerk";
import { AddPartForm } from "./layouts/HomePage/Components/DetailsPage/AddPartForm";
import { ManagerVehicleControlPanel } from "./layouts/HomePage/Components/Manager/ManagerVehicleControlPanel";
import { DetailsPageManager } from "./layouts/HomePage/Components/DetailsPage/DetailsPageManager";
import { PricePerConditon } from "./layouts/HomePage/Components/Reports/PricePerCondition";
import { AverageTimeInInventory } from "./layouts/HomePage/Components/Reports/AverageTimeInInventory";
import { PartStatistics } from "./layouts/HomePage/Components/Reports/PartStatistics";
import { SellerHistory } from "./layouts/HomePage/Components/Reports/SellerHistory";
import { MonthlySales } from "./layouts/HomePage/Components/Reports/MonthlySales";
import { Navigation } from "./layouts/HomePage/Navigation";
import { OwnerVehicleControlPanel } from "./layouts/HomePage/Components/Owner/OwnerVehicleControlPanel";
import { DetailsPageOwner } from "./layouts/HomePage/Components/DetailsPage/DetailsPageOwner";
import { MonthlySalesDrillDown } from "./layouts/HomePage/Components/Reports/MonthlySalesDrillDown";
function App() {

  return (
    <div>
    <BrowserRouter>
      <Routes>
      <Route index element={<PublicVehicleControlPanel />} />
      <Route path="User" element={<UserControlPanel/>} />
      <Route path="Navigation" element={<Navigation />} />
      <Route path="InventoryVehicle" element={<InventoryClerkVehicleControlPanel />} />
      <Route path="SalesVehicle" element={<SalespeopleVehicleControlPanel  />} />
      <Route path="ManagerVehicle" element={<ManagerVehicleControlPanel/>} />
      <Route path="OwnerVehicle" element={<OwnerVehicleControlPanel/>} />

      <Route path="AddNewVehicleForm" element={<AddNewVehicleForm/>} />
      <Route path="DetailsPage" element={<DetailsPage/>} />
      <Route path="SellVehicleForm" element={<SalesForm/>} />
      <Route path="DetailsPageSales" element={<DetailsPageSales/>} />
      <Route path="DetailsPageInventoryClerk" element={<DetailsPageInventoryClerk/>} />
      <Route path="DetailsPageManager" element={<DetailsPageManager/>} />
      <Route path="DetailsPageOwner" element={<DetailsPageOwner/>} />
      <Route path="AddPartForm" element={<AddPartForm/>} />
      <Route path="PricePerCondition" element={<PricePerConditon/>} />
      <Route path="AverageTimeInInventory" element={<AverageTimeInInventory/>} />
      <Route path="PartStatistics" element={<PartStatistics/>} />
      <Route path="SellerHistory" element={<SellerHistory/>} />
      <Route path="MonthlySales" element={<MonthlySales/>} />
      <Route path="MonthlySalesDrillDown" element={<MonthlySalesDrillDown/>} />



      </Routes>
    </BrowserRouter>
    </div>
  );
}

export default App;
