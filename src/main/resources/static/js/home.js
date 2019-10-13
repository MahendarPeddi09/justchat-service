


app.service('window_size_service',function(){
    
    this.getWindowHeight = function(){
         return window.innerHeight;
     }
     this.getWindowwidth = function(){
         return window.innerWidth;
     }
     console.log("Hi from window_size_service");
 });

app.controller('indexController',['$scope','$http','$rootScope','window_size_service','$location',function($scope,$http,$rootScope,wSize,$location,check_session_service){
    console.log("Hi from indexController");
        //$rootScope.notAuthorized = true;
        //alert(wSize.getWindowHeight()+" - "+wSize.getWindowwidth());
       // alert($location + " - "+ $scope);
        var loc = $location;
        $scope.windowH = wSize.getWindowHeight();
        $scope.windowW = wSize.getWindowwidth();
        $scope.test = true;
        $scope.getOwnerInfo = function () {
            
            $http.get('/justchat/user/session')
            .then(function(response) {
                //alert($rootScope.notAuthorized +""+$rootScope.authorized);
                if(response.data.user_name !=undefined && response.data.personId !=undefined){
                    //$rootScope.$broadcast('on_session_active',{name: response.data.user_name,id : response.data.personId,auth_status : true});
                    $scope.$parent.Authenticated = true;
                    $rootScope.ownerId = response.data.personId;
                    $rootScope.ownerName = response.data.user_name;
                    while(!($rootScope.ownerId == response.data.personId && $rootScope.ownerName == response.data.user_name)){
                        $rootScope.ownerId = response.data.personId;
                        $rootScope.ownerName = response.data.user_name;
                        
                    }
                    $location.url('/');
                    
                }
                else{
                    $location.url('/login');
                   
                    //$rootScope.$broadcast('on_session_active',{name: '',id : '',auth_status : false});
                    
                    
                }
            },function(err){
                //$rootScope.$broadcast('on_session_active',{name: '',id : '',auth_status : false});
           
             });
            
    };
   

    $scope.regPage = function(){
    $scope.defaultLogin = true;
    };
    $scope.loginPage = function(){
        $scope.defaultLogin = false;
    };
    $scope.signIn = function(){
        var url = "/justchat/login";
        var config = {headers : {'Content-Type': 'application/json'}};
        var data = {"userName":$scope.ilogin_name, "userPassword":$scope.ilogin_pswd};
        $http.post(url,data,config)
        .then(function(response){
            if(response.data = "success"){
                $scope.getOwnerInfo();
                

            }
        },function(response){
        }); 
    };
    $scope.signUp = function(){
        if(($scope.iregister_name!='' || $scope.iregister_name !=null) && ($scope.iregister_emaile !='' || $scope.iregister_email !=null) && ($scope.iregister_pswd !='' || $scope.iregister_pswd !=null)){
        var url = "/justchat/register";
        var config = {headers : {'Content-Type': 'application/json'}};
        var data = {"userName":$scope.iregister_name, "userEmail":$scope.iregister_email,"userPhone":$scope.iregister_phone,"userPassword":$scope.iregister_pswd};
        $http.post(url,data,config)
        .then(function(response){
            if(response.data = "success"){
                $scope.getOwnerInfo();
                
            }
            
        },function(response){
            
        }); 
        }
        else{
            if($scope.iregister == null){
                uName.style.border="1px solid red";
            }
            if(uEmail.value == null){
                uEmail.style.border="1px solid red";
            }
        }
    };
    
        
    
}]);






app.controller('chatMainController',['$scope','$http','$rootScope',function($scope,$http,$rootScope,userInfoData){
    
    $scope.session_live = ($rootScope.ownerId != undefined);
    console.log($rootScope.ownerId);

    $scope.current_selected = 0;
    $scope.chats = '';
    var orginalContactsList;
    var originalChatsList;
    console.log("from chatController"+$rootScope.ownerName);
    $scope.select_option=function(val){
        
        $scope.current_selected = val;
        console.log($scope.current_selected);
        
    };
    
    getAllChatsContacts = function(){
           console.log($rootScope.ownerId);
        //}
        var contactsUrl = "/justchat/friends/"+$rootScope.ownerId;
        //if ($scope.ContactList == null){
            $http.get(contactsUrl)
            .then(function(response){
                //response.body.sort(GetSortOrder('user_name'));
            $scope.contactsList = response.data;
            orginalContactsList = response.data;
            var latestMsgData = '';
            var chatsUrl = "/justchat/friends/active/"+$rootScope.ownerId;
            
                $http.get(chatsUrl).then(function(response){
                    
                    latestMsgData = response.data;
                    temp = [];
                    
                    //$scope.chatsList = response.data;
                    angular.forEach(latestMsgData,function(val,key){
                        angular.forEach(orginalContactsList,function(va,key){
                            if(val.to_id == va.personId || val.from_id == va.personId ){
                                //if(va.user_name !=null || va.user_name !=''){
                                    val['user_name'] = va.user_name;
                                    val['personId'] = va.personId;
                            }                            
                        });
                         if(val.user_name == '' || val.user_name == null || val.personId =='' || val.personId == null){
                                //if(va.user_name ==null || va.user_name ==''){
                                    var ext = val.from_id;
                                    if(val.from_id == $scope.ownerId) ext = val.to_id;
                                var newuserUrl = "/justchat/friends/newuser/"+ext;
                                    $http.get(newuserUrl)
                                        .then(function(response){
                                            //var newuserdata = JSON.parse(response.data);
                                            val['user_name'] = response.data.user_name;
                                            val['personId'] = val.from_id;
                                            //alert("jj");
                                    });
                                //} 
                         }
                        temp.push(val);
                    });
                    $scope.chatsList = temp;
                    originalChatsList = temp;
                    //$scope.$apply();
                    //alert($scope.chatsList[0].personId);
                    $scope.callAtTimeout = function() {
                            //console.log("$scope.callAtTimeout - Timeout occurred");
                            $rootScope.preload = false;
                        }
                    // $timeout(function(){$scope.callAtTimeout()}, 3000);
                    
                });
        });
    };
    if($rootScope.ownerId !=undefined) getAllChatsContacts();

    $scope.searchChats = function(){
        alert($scope.chatSearchKey);
    };

    $scope.searchContacts = function(key){
        alert($scope.contactsKey);
        //$scope.ContactList = null;
        //if(key.keyCode == 13){
            if($scope.contactsKey == null){
                $scope.contactsList = orginalContactsList;
                
            }
        else{
            
            $scope.tempList = [];
            $scope.contactsList = orginalContactsList;
            var list = $scope.contactsList;
            //alert($scope.sKey);
            
            angular.forEach(list, function (item, key) { 
                
                if((angular.lowercase(item.user_name)).startsWith(angular.lowercase($scope.contactsKey))){
                    $scope.tempList.push(item); 
                }
                
            });
            $scope.contactsList = $scope.tempList;
            }
            //}
    };

    $scope.Launch_chat = function(x){
        
        $rootScope.$broadcast('launch_chat',{name: x.user_name,id : x.personId});
        
        console.log("new chat window"+x.user_name+x.personId);
    };
    $rootScope.$on('new_msg_received_event',function(event,data){
           
        //angular.forEach(orginalContactList,function(va,key){
                        // if(data.m.to_id == va.personId){
                        //     data.m['user_name'] = va.user_name;
                        //     data.m['personId'] = va.personId;
                        //     //alert(va.user_name + " " +val.user_name);
                            var inactiveContactsList = false;
                            var inorginalContactList = false;
                            var te = $scope.chatsList;
                            
                            angular.forEach(te,function(vv,key){
                                
                                if(data.m.from_id== vv.personId || data.m.to_id == vv.personId){
                                    inactiveContactsList = true;
                                    vv.from_id = data.m.from_id;
                                    vv.msg_id = data.m.msg_id;
                                    vv.msg_status = data.m.msg_status;
                                    vv.msg_text = data.m.msg_text;
                                    vv.timestamp = data.m.timestamp;
                                    //alert(vv.msg_text);
                                   
                                    
                                }
                            });
                            if(!inactiveContactsList){
                                angular.forEach(orginalContactsList,function(va,key){
                                    if(data.m.to_id == va.personId || data.m.from_id == va.personId){
                                        data.m['user_name'] = va.user_name;
                                        data.m['personId'] = va.personId;
                                    //alert(va.user_name + " " +val.user_name);
                                        //inactiveContactsList = true;
                                        inorginalContactList = true;
                                        te.push(data.m);
                                    }
                                });
                                
                            }
                            // if(!inactiveContactsList && !inorginalContactList){
                            //     var newuserUrl = "/justchat/friends/newuser/"+data.m.from_id;
                            //     $http.get(newuserUrl)
                            //         .then(function(response){
                            //             //var newuserdata = JSON.parse(response.data);
                            //             data.m['user_name'] = response.data.user_name;
                            //             data.m['personId'] = data.m.from_id;
                            //             alert("jj");
                            //     });
                            //     te.push(data.m);
                            // }
                            $scope.chatsList = te;
                            alert("----"+JSON.stringify(te));
                            $scope.$apply();
                           
                           //console.log($scope.activeContactsList);
                            
                        //}
        //});

    });

}]);



// 
// 
// 
// 
app.controller('chatController',['$scope','$rootScope','$http','current_menu_option',function($scope,$rootScope,$http,current_menu_option){
    
    $scope.fromId = $rootScope.ownerId;
    $scope.chatwindowName = '';
    $scope.chatwindowId = '';
    $scope.open_info = false;
    $scope.chat_clicked = false;
    $scope.emojis = [
    {"emoji": "👩‍👩‍👧‍👦", "name": "family_mothers_children", "shortname": "", "unicode": "", "html": "&#128512;", "category": "p", "order": ""},
    {"emoji": "👩‍👩‍👦‍👦", "name": "family_mothers_two_boys", "shortname": "", "unicode": "", "html": "&#128105;", "category": "p", "order": ""},
    {"emoji": "👩‍👩‍👧‍👧", "name": "family_mothers_two_girls", "shortname": "", "unicode": "", "html": "&#128105;&zwj;&#128105;&zwj;&#128103;&zwj;&#128103;", "category": "p", "order": ""},
    {"emoji": "👩‍👩‍👧‍👦", "name": "family_mothers_children", "shortname": "", "unicode": "", "html": "&#128105;&zwj;&#128105;&zwj;&#128103;&zwj;&#128102;", "category": "p", "order": ""},
    {"emoji": "👩‍👩‍👦‍👦", "name": "family_mothers_two_boys", "shortname": "", "unicode": "", "html": "&#128105;&zwj;&#128105;&zwj;&#128102;&zwj;&#128102;", "category": "p", "order": ""},
    {"emoji": "👨‍👩‍👧‍👧", "name": "family_two_girls", "shortname": "", "unicode": "", "html": "&#128104;&zwj;&#128105;&zwj;&#128103;&zwj;&#128103;", "category": "p", "order": ""},
    {"emoji": "👨‍👩‍👧‍👦", "name": "family_children", "shortname": "", "unicode": "", "html": "&#128104;&zwj;&#128105;&zwj;&#128103;&zwj;&#128102;", "category": "p", "order": ""},
    {"emoji": "👨‍👩‍👦‍👦", "name": "family_two_boys", "shortname": "", "unicode": "", "html": "&#128104;&zwj;&#128105;&zwj;&#128102;&zwj;&#128102;", "category": "p", "order": ""},
    {"emoji": "👨‍👨‍👧‍👧", "name": "family_fathers_two_girls", "shortname": "", "unicode": "", "html": "&#128104;&zwj;&#128104;&zwj;&#128103;&zwj;&#128103;", "category": "p", "order": ""},
    {"emoji": "👨‍👨‍👧‍👦", "name": "family_fathers_children", "shortname": "", "unicode": "", "html": "&#128104;&zwj;&#128104;&zwj;&#128103;&zwj;&#128102;", "category": "p", "order": ""},
    {"emoji": "👨‍👨‍👦‍👦", "name": "family_fathers_two_boys", "shortname": "", "unicode": "", "html": "&#128104;&zwj;&#128104;&zwj;&#128102;&zwj;&#128102;", "category": "p", "order": ""},
    {"emoji": "👩‍👩‍👧", "name": "family_mothers_one_girl", "shortname": "", "unicode": "", "html": "&#128105;&zwj;&#128105;&zwj;&#128103;", "category": "p", "order": ""},
    {"emoji": "👩‍👩‍👦", "name": "family_mothers_one_boy", "shortname": "", "unicode": "", "html": "&#128105;&zwj;&#128105;&zwj;&#128102;", "category": "p", "order": ""},
    {"emoji": "👩‍👧‍👧", "name": "single_mother_two_girls", "shortname": "", "unicode": "", "html": "&#128105;&zwj;&#128103;&zwj;&#128103;", "category": "p", "order": ""},
    {"emoji": "👩‍👧‍👦", "name": "single_mother_two_children", "shortname": "", "unicode": "", "html": "&#128105;&zwj;&#128103;&zwj;&#128102;", "category": "p", "order": ""},
    {"emoji": "👩‍👦‍👦", "name": "single_mother_two_boys", "shortname": "", "unicode": "", "html": "&#128105;&zwj;&#128102;&zwj;&#128102;", "category": "p", "order": ""}
    ,
    {"emoji": "👨‍👩‍👧‍👧", "name": "family_two_girls", "shortname": "", "unicode": "", "html": "&#128104;&zwj;&#128105;&zwj;&#128103;&zwj;&#128103;", "category": "p", "order": ""},
    {"emoji": "👨‍👩‍👧‍👦", "name": "family_children", "shortname": "", "unicode": "", "html": "&#128104;&zwj;&#128105;&zwj;&#128103;&zwj;&#128102;", "category": "p", "order": ""},
    {"emoji": "👨‍👩‍👦‍👦", "name": "family_two_boys", "shortname": "", "unicode": "", "html": "&#128104;&zwj;&#128105;&zwj;&#128102;&zwj;&#128102;", "category": "p", "order": ""},
    {"emoji": "👨‍👨‍👧‍👧", "name": "family_fathers_two_girls", "shortname": "", "unicode": "", "html": "&#128104;&zwj;&#128104;&zwj;&#128103;&zwj;&#128103;", "category": "p", "order": ""},
    {"emoji": "👨‍👨‍👧‍👦", "name": "family_fathers_children", "shortname": "", "unicode": "", "html": "&#128104;&zwj;&#128104;&zwj;&#128103;&zwj;&#128102;", "category": "p", "order": ""},
    {"emoji": "👨‍👨‍👦‍👦", "name": "family_fathers_two_boys", "shortname": "", "unicode": "", "html": "&#128104;&zwj;&#128104;&zwj;&#128102;&zwj;&#128102;", "category": "p", "order": ""},
    {"emoji": "👩‍👩‍👧", "name": "family_mothers_one_girl", "shortname": "", "unicode": "", "html": "&#128105;&zwj;&#128105;&zwj;&#128103;", "category": "p", "order": ""},
    {"emoji": "👩‍👩‍👦", "name": "family_mothers_one_boy", "shortname": "", "unicode": "", "html": "&#128105;&zwj;&#128105;&zwj;&#128102;", "category": "p", "order": ""},
    {"emoji": "👩‍👧‍👧", "name": "single_mother_two_girls", "shortname": "", "unicode": "", "html": "&#128105;&zwj;&#128103;&zwj;&#128103;", "category": "p", "order": ""},
    {"emoji": "👩‍👧‍👦", "name": "single_mother_two_children", "shortname": "", "unicode": "", "html": "&#128105;&zwj;&#128103;&zwj;&#128102;", "category": "p", "order": ""},
    {"emoji": "👩‍👦‍👦", "name": "single_mother_two_boys", "shortname": "", "unicode": "", "html": "&#128105;&zwj;&#128102;&zwj;&#128102;", "category": "p", "order": ""}
    ,
    {"emoji": "👨‍👩‍👧‍👧", "name": "family_two_girls", "shortname": "", "unicode": "", "html": "&#128104;&zwj;&#128105;&zwj;&#128103;&zwj;&#128103;", "category": "p", "order": ""},
    {"emoji": "👨‍👩‍👧‍👦", "name": "family_children", "shortname": "", "unicode": "", "html": "&#128104;&zwj;&#128105;&zwj;&#128103;&zwj;&#128102;", "category": "p", "order": ""},
    {"emoji": "👨‍👩‍👦‍👦", "name": "family_two_boys", "shortname": "", "unicode": "", "html": "&#128104;&zwj;&#128105;&zwj;&#128102;&zwj;&#128102;", "category": "p", "order": ""},
    {"emoji": "👨‍👨‍👧‍👧", "name": "family_fathers_two_girls", "shortname": "", "unicode": "", "html": "&#128104;&zwj;&#128104;&zwj;&#128103;&zwj;&#128103;", "category": "p", "order": ""},
    {"emoji": "👨‍👨‍👧‍👦", "name": "family_fathers_children", "shortname": "", "unicode": "", "html": "&#128104;&zwj;&#128104;&zwj;&#128103;&zwj;&#128102;", "category": "p", "order": ""},
    {"emoji": "👨‍👨‍👦‍👦", "name": "family_fathers_two_boys", "shortname": "", "unicode": "", "html": "&#128104;&zwj;&#128104;&zwj;&#128102;&zwj;&#128102;", "category": "p", "order": ""},
    {"emoji": "👩‍👩‍👧", "name": "family_mothers_one_girl", "shortname": "", "unicode": "", "html": "&#128105;&zwj;&#128105;&zwj;&#128103;", "category": "p", "order": ""},
    {"emoji": "👩‍👩‍👦", "name": "family_mothers_one_boy", "shortname": "", "unicode": "", "html": "&#128105;&zwj;&#128105;&zwj;&#128102;", "category": "p", "order": ""},
    {"emoji": "👩‍👧‍👧", "name": "single_mother_two_girls", "shortname": "", "unicode": "", "html": "&#128105;&zwj;&#128103;&zwj;&#128103;", "category": "p", "order": ""},
    {"emoji": "👩‍👧‍👦", "name": "single_mother_two_children", "shortname": "", "unicode": "", "html": "&#128105;&zwj;&#128103;&zwj;&#128102;", "category": "p", "order": ""},
    {"emoji": "👩‍👦‍👦", "name": "single_mother_two_boys", "shortname": "", "unicode": "", "html": "&#128105;&zwj;&#128102;&zwj;&#128102;", "category": "p", "order": ""}]
    
   //socket
    var ws = null;
    var socket = null;
    $scope.connect = function() {
        //alert($scope.chatt.chat_name);
        if(socket ==null){
        socket = new WebSocket('ws://192.168.1.40:9119/justchat/chat');
        }
        ws = Stomp.over(socket);
        
        ws.connect({}, function(frame) {
            ws.subscribe("/user/queue/reply", function(message) {
                var msg =  JSON.parse(message.body);
                
                alert("happy");
                $rootScope.$broadcast("new_msg_received_event",{m : msg});
                var tt = $scope.ActiveConversation;
                //alert(msg.from_id +"-"+$rootScope.ownerId+"-"+msg.to_id+"-"+$scope.chatwindowId+"-"+msg.msg_text);
                if((msg.from_id ==$scope.ownerId && msg.to_id ==$scope.chatwindowId) ||(msg.to_id ==$scope.ownerId && msg.from_id == $scope.chatwindowId)){
                   
                        tt.push(msg);
                        $scope.ActiveConversation = tt;
                        $scope.$digest();
                }
                
            });
            
        }, function(error) {
            //alert("STOMP error " + error);
    });
    };
    if($rootScope.ownerId !=undefined) $scope.connect();
    $scope.disconnect = function() {
        if (ws != null) {
        ws.disconnect();
        }
    console.log("Disconnected");
    };
   //
   $scope.sendMsg = function(){
        if($scope.msgContent !=undefined && $scope.msgContent !=null && $scope.msgContent !=''){
            ws.send("/app/senduser/"+$scope.chatwindowId,{},JSON.stringify({"msg_id" :0,"from_id" : ""+$scope.ownerId,"to_id":$scope.chatwindowId,"msg_text":$scope.msgContent,"msg_status":0}));
            $scope.msgContent = '';
        
        }
    };
   $scope.send_msg = function(event){
        if(event.keyCode == 13 ){
            $scope.sendMsg();
        }
    };

    $rootScope.$on('launch_chat',function(event,data){
        //$scope.chatwindowName = '';
        //$scope.chatwindowId = '';
        $scope.chat_clicked = true;
        if($scope.chatwindowName == data.name && $scope.chatwindowId == data.id){
            
        }
        else{
            
            //alert($scope.chatwindowName+"c-v-v-"+ data.name);
            $scope.chatwindowName = data.name;
            $scope.chatwindowId = data.id;
            //debugger;
            $scope.chat_area_active = true;
            $scope.ActiveConversation = [];
            $scope.userInfoOverlayVisible = false;
            var url1 = '/justchat/chats/'+$scope.ownerId+'/'+$scope.chatwindowId;
            $http.get(url1).then(function(response){
                $scope.ActiveConversation = response.data;
                //$scope.$apply();
                //var cs = document.getElementById('conversation_area');
                //cs.scrollTo = cs.scrollHeight;
                //alert(cs.scrollTop + " - "+cs.scrollHeight);
        });
        //
        }
                
    });
    
    // $scope.$watch(current_menu_option.value,function(){
    //    // alert("jhvg"+$scope.current_selected);
    // });

    $scope.open_close_userInfo=function(){
        if($scope.open_info) $scope.open_info = false;
        else $scope.open_info = true;
    }
    
}]);