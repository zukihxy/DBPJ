/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var func = "";
var key = "";
$(document).ready(function () {
    replaceInfo();
    $("#right_div div").hide();

    $("#function_list a").each(function () {
        $(this).click(function () {
            $("#right_div div").hide();
            removeActive();
            $(this).attr("class", "list-group-item active");
            $("#add_delete_table").show();
            $("#search_form").show();
            $("#search_form div").show();
            $("#hello").hide();
            func = $(this).data("type");
            if (func == "teacher") 
                $("#department").hide();
            else 
                $("#department").show();
        });
    });
    
    $("ul.dropdown-menu a").each(function () {
        $(this).click(function () {
            key = $(this).data("type");
        });
    });

    $("#search_btn").click(function () {
        if (func == "employee") {
            
        } else if (func == "teacher") {
            
        } else if(func == "course") {
            
        } else if (func == "chief") {
            
        }
        $.post("../QueryChief", {id: $("#search_input").val()}, function (data) {
            var app = "<tr><th>id</th><th>password</th>";
            if (func == "update")
                app += "<th>operation</th>";
            app += "</tr>";
            for (var user in data) {
                app += "<tr><td><input type=\"text\" value=\"" + user.id + "\" /><td><input type=\"text\" value=\"" + user.password;
                if (func == "update")
                    app += "\" /><td><span class=\"glyphicon glyphicon-ok-sign table_icon\" title=\"update complete\"></span>";
                app += "\" /></td></tr>";
            }
            $("#table_empinfo").append(app);
        }, "json");
    });
});



