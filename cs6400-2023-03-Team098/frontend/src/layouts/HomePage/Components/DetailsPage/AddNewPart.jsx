import React, { useEffect, useState } from "react";

export function AddNewPart() {
  const [vendor, setVendor] = useState("Bioholding");
  const [quantity, setQuantity] = useState("");
  const [partNumber, setPartNumber] = useState("");
  const [cost, setCost] = useState(0);
  const [description, setDescription] = useState("");
  const [partsList, setPartsList] = useState([]);
  const [vin, setVin] = useState("");
  const [partOrderNumber, setPartOrderNumber] = useState(0);
  const [orderPlaced, setOrderPlaced] = useState(false);
  const [count, setCount] = useState(1);
  var constants = require("../../../../constants");

  useEffect(() => {
    let mainPath = "/partsOrder/getPartsOrderNumber";
    const queryParameters = new URLSearchParams(window.location.search);
    let payload = { vin: queryParameters.get("vin") };

    fetch(constants.postURL + mainPath, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(payload),
    })
      .then((res) => res.json())
      .then((result) => {
        result = JSON.stringify(result);
        result = JSON.parse(result);
        console.log(result);
        let zeroFilled = ("000" + (result.count + 1)).substr(-3);
        let queryVin = queryParameters.get("vin");
        setVin(queryVin);
        setPartOrderNumber(queryVin + "-" + zeroFilled);
      });

    mainPath = "/vendor/getAllNames";
    fetch(constants.postURL + mainPath, {
      method: "GET",
      headers: { "Content-Type": "application/json" },
    })
      .then((res) => res.json())
      .then((result) => {
        let select = document.getElementById("vendor");

        for (var i = 0; i < result.length; i++) {
          let name = result[i].name;
          var opt = document.createElement("option");
          opt.value = name;
          opt.innerHTML = name;
          select.appendChild(opt);
        }
      });
  }, []);

  function handlePlaceOrder() {
    let length = partsList.length;
    let payload = {...partsList, numberOfParts: {length}, vin: {vin}, partOrderNumber: {partOrderNumber}};
    console.log(JSON.stringify(payload));
    fetch(constants.postURL + "/partsOrder/createNewPartsOrder",{
      method:"POST",
      headers:{"Content-Type":"application/json"},
      body:JSON.stringify(payload)
  }).then(()=>{
    setOrderPlaced(true);
  })
  }

  function handleAddPart() {
    const newPart = {
      id: "part"+count,
      vendor: vendor,
      quantity: quantity,
      partNumber: partNumber,
      cost: cost,
      description: description
    };
    setCount(count + 1);
    setPartsList([...partsList, newPart]);
    setVendor("Bioholding");
    setQuantity(0);
    setPartNumber("");
    setCost(0);
  }

  function handleVendorChange(event) {
    setVendor(event.target.value);
  }

  function handleQuantityChange(event) {
    setQuantity(event.target.value);
  }

  function handlePartNumberChange(event) {
    setPartNumber(event.target.value);
  }

  function handleCostChange(event) {
    setCost(event.target.value);
  }

  function handleDescriptionChange(event) {
    setDescription(event.target.value);
  }

  if(orderPlaced)
  return(
    <div>Order Placed!</div>
    );

  return (
    <div>
      Part Order Number: {partOrderNumber}
      <br></br>
      <select
        value={vendor}
        onChange={handleVendorChange}
        placeholder="Vendor"
        id="vendor"
      />
      <input
        type="number"
        value={quantity}
        onChange={handleQuantityChange}
        placeholder="Quantity"
      />
      <input
        type="text"
        value={partNumber}
        onChange={handlePartNumberChange}
        placeholder="Part Number"
      />
      <input
        type="number"
        value={cost}
        onChange={handleCostChange}
        placeholder="Cost"
      />
        <input
        type="text"
        value={description}
        onChange={handleDescriptionChange}
        placeholder="Description"
      />
      <button onClick={handleAddPart}>ADD</button>
      <br></br>
      Parts being ordered:
      <ul>
        {partsList.map((part) => (
          <li key={part.id}>
            Part:
            <br/>
            Vendor: {part.vendor}
            <br/>
            Quantity: {part.quantity}
            <br/>
            Part Number: {part.partNumber}
            <br/>
            Cost: {part.cost}
            <br/>
            Description: {part.cost}
          </li>
        ))}
      </ul>
      <br></br>
      <button onClick={handlePlaceOrder}>Place Order</button>
    </div>
  );
}
