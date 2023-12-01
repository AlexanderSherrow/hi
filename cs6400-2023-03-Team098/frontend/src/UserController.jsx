var constants = require('./constants')

function UserUpload(props) {
    const formHandler = (e) => {
        e.preventDefault();
        uploadUser(e.target[0].value, e.target[1].value, e.target[2].value, e.target[3].value, e.target[4].value);
        props.setRefresh(true);
        };

    const uploadUser = (userName, password, firstName, lastName, type) => {
              const user={userName, password, firstName, lastName, type};
              fetch(constants.postURL + "/user/save",{
                method:"POST",
                headers:{"Content-Type":"application/json"},
                body:JSON.stringify(user)
            }).then(()=>{
              console.log("New user added");
              props.setRefresh(false);
            })
      };

      return(
        <div className="App">
            <form onSubmit={formHandler}>
                <input type="text" name="username" defaultValue='user name'/>
                <input type="text" name="password" defaultValue='password'/>
                <input type="text" name="firstNAme" defaultValue='First Name'/>
                <input type="text" name="lastName" defaultValue='Last Name'/>
                <button type="submit">Upload</button>
            </form>
            <hr/>
        </div>
      );

}
export default UserUpload;