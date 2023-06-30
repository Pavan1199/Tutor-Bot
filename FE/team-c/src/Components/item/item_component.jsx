import React from "react";
import './item.styles.scss';
import { useContext } from "react";
import { MenuItemContext } from "../../contexts/menuitem.context";
const Item = ({menu_it}) =>{
  const {setgraph} = useContext(MenuItemContext);
return(
<div className="Item_display" onClick={()=>setgraph(menu_it)}>
{
    menu_it.name
}
</div>
)
}
export default Item;
