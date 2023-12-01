import { BrowserRouter, Routes, Route } from "react-router-dom";
import './App.css';
import { HomePage } from './layouts/HomePage/HomePage';
import { ControlPanel } from './layouts/HomePage/Components/ControlPanel';

function App() {
  return (
    <div>
    <BrowserRouter>
      <Routes>
      <Route index element={<HomePage />} />
      <Route path="Main" element={<ControlPanel/>} />
      </Routes>
    </BrowserRouter>
    </div>
  );
}

export default App;
