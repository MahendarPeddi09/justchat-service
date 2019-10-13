app.run(['$http','$rootScope','$location','check_session_service',function($http,$rootScope,$location,check_session_service) {
  $rootScope.Authorized = false;
  
  console.log("from run initilized");
  $rootScope.$on( "$routeChangeStart", function(event, next, current) {
    if ($rootScope.ownerId == undefined) {
      // no logged user, redirect to /login
      console.log("from run"+$rootScope.ownerId );
      if ( next.templateUrl === "/justchat/web/tempindex.html") {
      } else {
        $location.path("/login");
      }
    }
  });
 

}]);


app.service('current_menu_option',function(){
    var c_option = {}; 
    c_option.value = 0;
    c_option.updateVal = function (data) {
        c_option.value = data;
      };
      
    return c_option;
});
app.service('check_session_service',function($rootScope,$http,$location){
  console.log("from check session service");

  var session;
var session_info = $http.get('/justchat/user/session')
.then(function(response) {
    console.log("from http");
    if(response.data.user_name !=undefined && response.data.personId !=undefined){
       
        $rootScope.ownerId = response.data.personId;
        $rootScope.ownerName = response.data.user_name;
            $rootScope.ownerId = response.data.personId;
            $rootScope.ownerName = response.data.user_name;
            session = {ownerId : response.data.personId,ownerName:response.data.user_name}
          console.log("successful session");
       $location.url('/');
        
    }
    else{
        $location.url('/login');
        session = undefined;
        console.log("null session");
        
    }
    return session;
},function(err){
  session = undefined;
  console.log("error");
  return session;
 });
 return session_info;

});