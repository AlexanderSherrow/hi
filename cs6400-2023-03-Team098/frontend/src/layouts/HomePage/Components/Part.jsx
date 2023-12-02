import React, { useEffect, useState } from "react";
import './Vehicle.css';
import { useNavigate } from 'react-router-dom';
var constants = require('../../../constants')



    export function Part(props) {
      const navigate = useNavigate();

    const [background, setBackground] = useState('white');
    let [textColor, setTextColor] = useState('black');
    let [style, setStyle]  = useState({
      background: background,
      color: textColor
    });
    let id = "currentStatus" + props.purchaseOrderNumber + "," + props.partNumber
    let buttonId = "buttn:currentStatus" + props.purchaseOrderNumber + "," + props.partNumber


    useEffect(() => {
      let select = document.getElementById(id);
      let options = ['ordered', 'received', 'installed'];
      const isStatus = (element) => element == props.currentStatus;
      let index  = options.findIndex(isStatus);
  for (var i = index; i<options.length; i++){
      let name = options[i];
      var opt = document.createElement('option');
      opt.value = name;
      opt.innerHTML = name;
      select.appendChild(opt);
  }

  }, []);

  async function buttonClickHandler()
  {
    let select = document.getElementById(id);
    let options = ['ordered', 'received', 'installed'];
    const isStatus = (element) => element == document.getElementById(id).value;
    let index  = options.findIndex(isStatus);
   for(let i = select.options.length - 1; i >= 0; i--)
    select.remove(i);

    for (var i = index; i<options.length; i++){
        let name = options[i];
        var opt = document.createElement('option');
        opt.value = name;
        opt.innerHTML = name;
        select.appendChild(opt);
    }

      let partNumber = props.partNumber;
      let purchaseOrderNumber = props.purchaseOrderNumber
      let status = document.getElementById(id).value;

      if (status == "installed")
      document.getElementById(buttonId).remove();
      const payload={partNumber, purchaseOrderNumber, status};
      fetch(constants.postURL + "/partsOrder/updatePartStatus",{
        method:"POST",
        headers:{"Content-Type":"application/json"},
        body:JSON.stringify(payload)
    }).then(()=>{
      console.log("New user added");
    });


  }

if(props.currentStatus == "installed")
return (      
  <ol class="horizontal-list">
    <li>{props.partNumber}</li>
    <li>{props.description}</li>
    <li>{props.vendorName}</li>
    <li>{props.purchaseOrderNumber}</li>
    <li>{props.totalPartsCost}</li>
    <select name="currentStatus" id={id} placeholder='currentStatus'/>
  </ol>
    );

    return (      
  <ol class="horizontal-list">
    <li>{props.partNumber}</li>
    <li>{props.description}</li>
    <li>{props.vendorName}</li>
    <li>{props.purchaseOrderNumber}</li>
    <li>{props.totalPartsCost}</li>
    <select name="currentStatus" id={id} placeholder='currentStatus'/>
    <button type="submit" id={buttonId} onClick={buttonClickHandler}>Update Part</button>
  </ol>
    );
    
  }

