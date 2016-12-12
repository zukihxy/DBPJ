   
   $("#loginbtn").click(function () {
        var all=true;
        var user = $("#user").val();
        var passwd = $("#passwd").val();

        $(".invalid").html("");
        if (user === "") {
            all=false;
            $("#uncompleteName").html("Please enter username first!");
        }
        if (passwd === "") {
            all=false;
            $("#uncompletePassword").html("Please enter password first!");
        }

        if (all){
            validate();
        }
        
    });
    $("#regbtn").click(function () {
        window.location="register.html";
    });


var code ; //在全局定义验证码   
//产生验证码  
window.onload = function createCode(){  
     code = "";   
     var codeLength = 4;//验证码的长度  
     var checkCode = document.getElementById("code");   
     var random = new Array(0,1,2,3,4,5,6,7,8,9,'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R',  
     'S','T','U','V','W','X','Y','Z');//随机数  
     for(var i = 0; i < codeLength; i++) {//循环操作  
        var index = Math.floor(Math.random()*36);//取得随机数的索引（0~35）  
        code += random[index];//根据索引取得随机数加到code上  
    }  
    checkCode.value = code;
}  
//校验验证码  
function validate(){ 
    var user = $("#user").val();
   var passwd = $("#passwd").val();
    var inputCode = document.getElementById("input").value.toUpperCase(); //取得输入的验证码并转化为大写        
    if(inputCode.length <= 0) { //若输入的验证码长度为0  
        $("#uncheck").html("Please enter check code！"); 
    }         
    else if(inputCode != code ) { //若输入的验证码与产生的验证码不一致时  
        $("#uncheck").html("Wrong check code！@_@");   
        createCode();//刷新验证码  
        document.getElementById("input").value = ""; 
    }         
    else { //输入正确时  
       $.post("login", $("#form").serialize(), function (data) {
            if (data != "0") {
                alert(data);
                window.location="login.html";
            } else {
                $.cookie("user",null);
                $.cookie("user",user,{path:"/"});
                $.cookie("passwd",passwd,{path:"/"});
                window.location="home1.html?writer="+user;
            }
        });
    }             
}  




