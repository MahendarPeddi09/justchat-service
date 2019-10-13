
// Login script
function signIn() {
  var xhttp = new XMLHttpRequest();
  var uName=document.getElementById("login_name").value;
  var uPswd=document.getElementById("login_pswd").value;
  xhttp.onreadystatechange = function() { 
    if (this.readyState == 4 && this.status == 200 ) {
        if(this.responseText == 'success'){
        location.replace("/justchat/web/home.html");
        }
        else{
            alert(this.responseText);
        }
        
    }
    else{

    }
  };
  xhttp.open("POST", "/justchat/login", true);
  xhttp.setRequestHeader("Content-type", "application/json");
  xhttp.send(JSON.stringify({"userName":uName, "userPassword":uPswd}));

}



//Registration script
function signUp() {

    var uName=document.getElementById("iregister_name");
    var uEmail=document.getElementById("iregister_email");
    var uPhone=document.getElementById("iregister_phone");
    var uPswd=document.getElementById("iregister_pswd");
    var confirmPswd=document.getElementById("iconfirm_pswd");

    if((uName.value !='' || uName.value !=null) && (uEmail.value !='' || uEmail.value !=null) && (uPswd.value !='' || uPswd.value !=null)){
        if(uPswd.value == confirmPswd.value){
        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            //alert(this.response.status); 
        if (this.readyState == 4 && this.status == 200) {
            if(this.responseText == 'success'){
                location.replace("/justchat/web/home.html");
            }
            else{
                alert(this.responseText);
            }
        }
        else{
            //alert(this.response); 
        }
        };
        xhttp.open("POST", "/justchat/register", true);
        xhttp.setRequestHeader("Content-type", "application/json");
        xhttp.send(JSON.stringify({"userName":uName.value, "userEmail":uEmail.value, "userPhone":uPhone.value, "userPassword":uPswd.value}));
        uPswd.style.border="1px solid #cdc9ce";
        confirmPswd.style.border="1px solid #cdc9ce";
        }
        else{
            uPswd.style.border="1px solid red";
            confirmPswd.style.border="1px solid red";
        }
        
    }
    else{
        if(uName.value == null){
                uName.style.border="1px solid red";
            }
            if(uEmail.value == null){
                uEmail.style.border="1px solid red";
            }
    }
}