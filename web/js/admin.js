/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var func = "";
$(document).ready(function () {
    replaceInfo();
    $("#right_div div").hide();

    $("#permit_list").click(function () {
        $("#right_div div").hide();
        removeActive();
        $(this).attr("class", "list-group-item active");
        $("#table_empinfo").empty();
        var app = "<tr><th>id</th><th>password</th><th>operation</th></tr>";
        $.post("../LoadNewUser", {}, function (data) {
            for (var user in data) {
                app += "<tr><td><input type=\"text\" data-type=\""+user.type+"\"value=\"" + user.id + "\" /><td><input type=\"text\" value=\"" + user.password;
                if (user.type == "add")
                    app += "\" /><td><span class=\"glyphicon glyphicon-plus table_icon\" title=\"To be added\"></span></td></tr>";
                else
                    app += "\" /><td><span class=\"glyphicon glyphicon-remove table_icon\" title=\"To be deleted\"></span></td></tr>";
            }
        }, "json");
        $("#table_empinfo").append(app);
        $("#add_delete_table").show();
        $("#submit_table").show();
        $("#hello").hide();
    });

    $("#delete_user_list").click(function () {
        $("#right_div div").hide();
        removeActive();
        $(this).attr("class", "list-group-item active");
        $("#table_empinfo").empty();
        var app = "<tr><th>id</th><th>password</th><th>operation</th></tr>";
        $.post("../LoadUsers", {}, function (data) {
            for (var user in data)
                app += "<tr><td><input type=\"text\" value=\"" + user.id + "\" /><td><input type=\"text\" value=\"" + user.password + "\" /></td></tr>"
                        + "\" /><td><span class=\"glyphicon glyphicon-trash table_icon\" data-id=\""+user.id+"\"title=\"delete this row\"></span></td></tr>";
        }, "json");
        $("#table_empinfo").append(app);
        $("#add_delete_table").show();
        $("#hello").hide();
        $("span.glyphicon-trash").each(function (){
            $(this).click(function () {
                $.post("../Delete", {id: $(this).data("id"), type: "user"}, function (data) {
                    alert(data);
                }, "json");
            });
        });
    });

    $("#update_user_list").click(function () {
        $("#right_div div").hide();
        $("#search_form").show();
        $("#search_form div").show();
        $("#hello").hide();
        removeActive();
        $(this).attr("class", "list-group-item active");
    });

    $("#search_emp_list").click(function () {
        $("#right_div div").hide();
        $("#search_form").show();
        $("#search_form div").show();
        $("#hello").hide();
        removeActive();
        $(this).attr("class", "list-group-item active");
    });

    $("#search_btn").click(function () {
        $.post("../QueryAdmin", {id: $("#search_input").val()}, function (data) {
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

        if (func == "update") {
            $("span.glyphicon-ok-sign").each(function () {
                var id = $(this).parent().siblings().children("input").eq(0).val();
                var pass = $(this).parent().siblings().children("input").eq(1).val();
                $.post("../UpdateUser", {id:id,password:pass}, function (data) {
                    alert(data);
                }, "json");
            });
        }
    });

    $("#submit_icon").click(function () {
        var array = "[";
        $("#table_empinfo td:even").each(function () {
            array += "{id:\"" + $(this).val() + "\", type: \""+$(this).data("type")+"\"},";
        });
        array = array.substr(0, array.length - 1);
        array += "]";
        $.post("../AdmitNewUser", {users: array}, function (data) {
            alert(data.message);
        }, "json");
    });
});

