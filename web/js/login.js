/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function() {
    $("#log_in").click(function() {
        var id = $("#user_id").val();
        var pass = $("#password").val();
        if (id == "" | pass == "") {
            alert("Please fill all the blanks.");
            window.location="login.html";
        }
        $.post("../Login", {id: id, password: pass}, function (data) {
            if (data.success == "0") {
                alert(data.message);
                window.location="login.html";
            } else {
                $.cookie("id",null);
                $.cookie("type",null);
                $.cookie("id",id,{path:"/"});
                $.cookie("type",data.type,{path:"/"});
                if (data.type == "admin")
                    $.cookie("new",data.new,{path:"/"});
                window.location = data.type+".html";
            }
        },"json");
    });
});

