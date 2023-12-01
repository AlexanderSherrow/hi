import React, { useState } from "react";
import './User.css';

    export function User(props) {
    const [background, setBackground] = useState('white');
    let [textColor, setTextColor] = useState('black');
    let [style, setStyle]  = useState({
      background: background,
      color: textColor
    });

    console.log(props);

    return (      
      <div
      > 
        <div class = 'left'
        style={style}
        >{props.username} </div>

        <div class = 'left'
        style={style}
        >{props.password} </div>

        <div class = 'left'
        style={style}
        >{props.firstName} </div>

        <div class = 'left'
        style={style}
        >{props.lastName} </div>
        
      </div>
    );
    
  }

