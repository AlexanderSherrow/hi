import { AddNewPart } from "./AddNewPart";

export function AddPartForm(props)
{
    const queryParameters = new URLSearchParams(window.location.search);
    const vin = queryParameters.get("vin");
    return(
        <div>
            Add Part Form:
            <AddNewPart/>
        </div>
    );
}