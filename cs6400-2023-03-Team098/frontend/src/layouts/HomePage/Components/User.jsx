import React, { useState } from "react";
import './User.css';

    export function User(props) {
    const [background, setBackground] = useState('white');
    let [textColor, setTextColor] = useState('black');
    let [style, setStyle]  = useState({
      background: background,
      color: textColor
    });

    return (
      <ol class="horizontal-list-user">
      <li>{props.username}</li>
      <li>{props.password}</li>
      <li>{props.firstName}</li>
      <li>{props.lastName}</li>
    </ol>
    );
    
  }

