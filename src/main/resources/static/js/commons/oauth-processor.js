$(function() {
    
    function getQueryVariable(variable) {
      var query = window.location.href;
      var vars = query.split("&");
      for (var i=0;i<vars.length;i++) {
        var pair = vars[i].split("=");
        if (pair[0] == variable) {
          return pair[1];
        }
      } 
    }

    var access_token = getQueryVariable("access_token");
    var token_type = getQueryVariable("token_type");
    var expires_in = getQueryVariable("expires_in");    

    console.log(access_token);
    console.log(token_type);
    console.log(expires_in);
    
    //TODO : make a post request to send this data to server.
    // On server side store the token and ask user to go back to the application and use it.
    
    try {
        //console.log(window.opener.APP.conversation);
        window.opener.APP.conversation.handleOauthResult(access_token);
    }
    catch (err) {
        alert(err);
    }    
    window.close();
});

