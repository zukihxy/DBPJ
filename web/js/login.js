/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function() {
    $("#log_in").click(function() {
        var name = $("#username").val();
        var pass = $("#password").val();
        if (name == "" | pass == "") {
            alert("Please fill all the blanks.");
            window.location="login.html";
        }
        $.post("../login", {username: name, password: pass}, function (data) {
            if (data != "0") {
                alert(data);
                window.location="login.html";
            } else {
                $.cookie("username",null);
                $.cookie("username",name,{path:"/"});
                $.cookie("password",pass,{path:"/"});
            }
        });
    });
});

