var constants = require('../../../constants')

function UserLogin(props) {
    const formHandler = (e) => {
        e.preventDefault();
        payload(e.target[0].value, e.target[1].value);
        props.setRefresh(true);
        };

        const payload = (username, password) => {
            const user={username, password};
            fetch(constants.postURL + "/user/login",{
              method:"POST",
              headers:{"Content-Type":"application/json"},
              body:JSON.stringify(user)
          }).then(res=>res.json())
          .then((result)=>{
            props.setUser(result.type);
            props.setUsername(username);
            props.setRefresh(false);
          });
    };

      return(
        <div className="App">
            <form onSubmit={formHandler}>
                <input type="text" name="username" placeholder='user name'/>
                <input type="text" name="password" placeholder='password'/>
                <button type="submit">Login</button>
            </form>
            <hr/>
        </div>
      );

}
export default UserLogin;