var constants = require("../../../constants");

function CustomerSearch(props) {
  let { setRefresh, setPayload } = props.method1Data;
  const formHandler = (e) => {
    setRefresh(true);
    e.preventDefault();
    payload(e.target[0].value, e.target[1].value);
  };

  async function payload(driversLicense, businessTaxID) {
    const payload = { driversLicense, businessTaxID };
    await timeout(1000); //for 1 sec delay
    setRefresh(false);
    setPayload(payload);
  }

  function timeout(delay) {
    return new Promise((res) => setTimeout(res, delay));
  }

  return (
    <div className="App">
      <form onSubmit={formHandler}>
        Please search for an existing Customer! If the search fails, enter the
        details for a new Customer!
        <br />
        If Drivers License Number or Business Tax ID is unapplicable, please
        enter "N/A"
        <br></br>
        <input
          type="text"
          name="driversLicense"
          placeholder="Driver License Number"
        />
        <input type="text" name="businessTaxID" placeholder="Business Tax ID" />
        <br />
        <button type="submit">Search</button>
      </form>
      <hr />
    </div>
  );
}
export default CustomerSearch;
