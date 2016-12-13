/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var func = "";
$(document).ready(function () {
    replaceInfo();
    $("#right_div div").hide();
    if ($.cookie("new") == "1")
        $("#hello").text("There is something to be permitted!");

    $("#permit_list").click(function () {
        $("#right_div div").hide();
        removeActive();
        $(this).attr("class", "list-group-item active");
        $("#table_empinfo").empty();
        var app = "<tr><th>id</th><th>operation</th></tr>";
        $("#table_empinfo").append(app);
        $.post("../LoadUpdate", {}, function (data) {
            for (i in data.users) {
                var user = data.users[i];
                app = "<tr><td data-type=\"" + user.type + "\">" + user.id + "</td>";
                if (user.type == "add")
                    app += "<td><span class=\"glyphicon glyphicon-plus table_icon\" title=\"To be added\"></span></td></tr>";
                else
                    app += "<td><span class=\"glyphicon glyphicon-minus table_icon\" title=\"To be deleted\"></span></td></tr>";
                $("#table_empinfo").append(app);
            }
        }, "json");
        func = "permit";
        $("#add_delete_table").show();
        $("#submit_table").show();
        $("#hello").hide();
        $.cookie("new", null);
    });

    $("#delete_user_list").click(function () {
        $("#right_div div").hide();
        removeActive();
        $(this).attr("class", "list-group-item active");
        $("#table_empinfo").empty();
        var app = "<tr><th>id</th><th>password</th><th>operation</th></tr>";
        $("#table_empinfo").append(app);
        $.post("../QueryAdmin", {type: "all"}, function (data) {
            for (i in data.users) {
                var user = data.users[i];
                app = "<tr><td>" + user.id + "<td>" + user.password + "</td>"
                        + "<td><span class=\"glyphicon glyphicon-trash table_icon\" data-id=\"" + user.id + "\" title=\"delete this row\"></span></td></tr>";
                $("#table_empinfo").append(app);
            }
            $("span.glyphicon-trash").each(function () {
                $(this).click(function () {
                    var ele = $(this);
                    $.post("../Delete", {id: $(this).data("id"), type: "user"}, function (data) {
                        alert(data.message);
                        if (data.message == "Success!") {
                            ele.parent().parent().remove();
                        }
                    }, "json");
                });
            });
        }, "json");

        $("#add_delete_table").show();
        $("#hello").hide();
    });

    $("#update_user_list").click(function () {
        $("#right_div div").hide();
        $("#search_form").show();
        $("#search_form div").show();
        $("#hello").hide();
        removeActive();
        func = "update";
        $(this).attr("class", "list-group-item active");
    });

    $("#search_emp_list").click(function () {
        $("#right_div div").hide();
        $("#search_form").show();
        $("#search_form div").show();
        $("#hello").hide();
        removeActive();
        func = "search";
        $(this).attr("class", "list-group-item active");
    });

    $("#search_btn").click(function () {
        $("#add_delete_table").show();
        $("#table_empinfo").empty();
        $.post("../QueryAdmin", {id: $("#search_input").val(), type: "id"}, function (data) {
            if (data.result == "1") {
                var app = "<tr><th>id</th><th>password</th>";
                if (func == "update")
                    app += "<th>        </th>";
                app += "</tr>";
                app += "<tr><td>" + data.users.id + "</td>";
                if (func == "update")
                    app += "<td><input type=\"text\" value=\"" + data.users.password + "\" /></td><td><span class=\"glyphicon glyphicon-ok-sign table_icon\" title=\"update complete\"></span></td>";
                else
                    app += "<td>" + data.users.password + "</td>";
                app += "</tr>";
                $("#table_empinfo").append(app);
                if (func == "update") {
                    $("span.glyphicon-ok-sign").click(function () {
                        var id = $(this).parent().siblings().eq(0).text();
                        var pass = $(this).parent().siblings().eq(1).children().eq(0).val();
                        var array = "[{id:\""+id+"\", password:\""+ pass+"\"}]";
                        $.post("../UpdateUser", {users: array, function: "update"}, function (data) {
                            alert(data.message);
                            $("#table_empinfo").empty();
                        }, "json");
                    });
                }
            } else {
                alert(data.error);
            }
        }, "json");
    });

    $("#add_user_list").click(function () {
        $("#right_div div").hide();
        removeActive();
        $(this).attr("class", "list-group-item active");
        $("#table_empinfo").empty();
        $("#submit_table").show();
        func = "add";
        var app = "<tr id=\"tr_header\"><th>id</th><th>password</th><th>type</th><th>      </th></tr>";
        app += "<tr><td><input type=\"text\" /><td><input type=\"text\" /></td>"
                + "<td><input type=\"text\" /></td><td><span class=\"glyphicon glyphicon-plus table_icon\" title=\"add one more\"></span></td></tr>";
        $("#table_empinfo").append(app);
        $("#add_delete_table").show();
        $("#hello").hide();
        $("span.glyphicon-plus").click(function () {
            var id = $(this).parent().siblings().children().eq(0).val();
            var pass = $(this).parent().siblings().children().eq(1).val();
            var type = $(this).parent().siblings().children().eq(2).val();
            if (type != "teacher" && type != "ceo" && type != "employee" && type != "chief" && type != "admin") {
                alert("Type must be one of: teacher, ceo, employee, chief, admin!");
            } else {
                app = "<tr><td><input type=\"text\" value=\"" + id + "\"></td><td><input type=\"text\" value=\""
                        + pass + "\"></td><td><input type=\"text\" value=\"" + type + "\"></td><td></td></tr>";
                $(this).parent().parent().before(app);
            }
        });
    });

    $("#submit_icon").click(function () {
        var ele = $(this);
        if (func == "permit") {
            var array = "[";
            $("#table_empinfo td:even").each(function () {
                array += "{id:\"" + $(this).text() + "\", type: \"" + $(this).data("type") + "\"},";
            });
            array = array.substr(0, array.length - 1);
            array += "]";
            $.post("../PermitUpdate", {users: array}, function (data) {
                alert(data.message);
                if (data.message == "Succeed") {
                    $("#table_empinfo").empty();
                    ele.hide();
                }
            }, "json");
        } else if (func == "add") {
            var array = "[";
            $("#table_empinfo tr:not(:first)").each(function () {
                array += "{id:\"" + $(this).children().eq(0).children().eq(0).val()
                        + "\", password: \"" + $(this).children().eq(1).children().eq(0).val()
                        + "\", type:\"" + $(this).children().eq(2).children().eq(0).val() + "\"},";
            });
            array = array.substr(0, array.length - 1);
            array += "]";
            $.post("../UpdateUser", {users: array, function: "add"}, function (data) {
                alert(data.message);
                $("#table_empinfo").empty();
            }, "json");
        }

    });
});

