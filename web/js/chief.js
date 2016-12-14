/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var func = "";
$(document).ready(function () {
    replaceInfo();
    dropDown();
    $("#right_div div").hide();

    $("#make_plan_list").click(function () {
        $("#right_div div").hide();
        removeActive();
        $(this).attr("class", "list-group-item active");
        $("#table_empinfo").empty();
        func = "make";
        var app = "<tr id=\"tr_header\"><th>course id</th><th>course name</th><th>total time</th>"
                + "<th>teacher name</th></tr>";
        app += "</tr>";
        $("#table_empinfo").append(app);
        $.post("../QueryChief", {type: "course"}, function (data) {
            if (data.result == "1") {
                for (i in data.course) {
                    var c = data.course[i];
                    app = "<tr><td>" + c.course_id + "</td><td>" + c.course_name + "</td><td>" + c.total_time + "</td><td>" + c.teacher_name + "</td>";
                    app += "<td><div class=\"radio\"><label><input type=\"radio\" name=\"optionsRadios\" value=\"mandatory\">mandatory"
                            + "</label></div><div class=\"radio\"><label><input type=\"radio\" name=\"optionsRadios\" value=\"elective\">elective</label>"
                            + "</div><div class=\"radio\"><label><input type=\"radio\" name=\"optionsRadios\" value=\"un\">not chosen"
                            + "</label></div></td></tr>";
                    $("#table_empinfo").append(app);
                }
            } else {
                alert(data.error);
            }
        }, "json");
        $("#add_delete_table").show();
        $("#submit_table").show();
        $("#hello").hide();
        $.cookie("new", null);
    });

    $("#delete_info_list").click(function () {
        $("#right_div div").hide();
        removeActive();
        $(this).attr("class", "list-group-item active");
        $("#table_empinfo").empty();
        var app = "<tr id=\"tr_header\"><th>id</th><th>name</th><th>sex</th>"
                + "<th>salary</th><th>addition</th><th>work_addr</th><th>work_age</th></tr>";
        $("#table_empinfo").append(app);
        $.post("../QueryChief", {key: "all", type: "employee"}, function (data) {
            for (i in data.users) {
                var user = data.users[i];
                app = "<tr><td>" + user.person_id + "</td><td>" + user.name + "</td><td>" + user.sex + "</td>"
                        + "<td>" + user.salary + "</td><td>" + user.addition + "</td><td>" + user.work_addr + "</td><td>" + user.work_age + "</td>"
                        + "<td><span class=\"glyphicon glyphicon-trash table_icon\" data-id=\"" + user.person_id + "\" title=\"delete this row\"></span></td></tr>";
                $("#table_empinfo").append(app);
            }
            $("span.glyphicon-trash").each(function () {
                $(this).click(function () {
                    var ele = $(this);
                    $.post("../Delete", {id: $(this).data("id"), type: "employee"}, function (data) {
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

    $("#update_info_list").click(function () {
        $("#right_div div").hide();
        $("#search_form").show();
        $("#search_form div").show();
        $("#hello").hide();
        removeActive();
        func = "update";
        $(this).attr("class", "list-group-item active");
    });

    $("#check_plan_list").click(function () {
        $("#right_div div").hide();
        $("#search_form").show();
        $("#search_form div").show();
        $("#hello").hide();
        removeActive();
        func = "check";
        $(this).attr("class", "list-group-item active");
    });

    $("#search_btn").click(function () {
        $("#add_delete_table").show();
        $("#table_empinfo").empty();
        if (func == "update") {
            $.post("../QueryChief", {id: $("#search_input").val(), key: $("button.dropdown-toggle").text(), type: "employee"},
            function (data) {
                if (data.result == "1") {
                    var app = "";
                    app = "<tr id=\"tr_header\"><th>id</th>"
                            + "<th>salary</th><th>addition</th><th>work_addr</th><th>work_age</th><th></th></tr>";
                    $("#table_empinfo").append(app);
                    for (i in data.users) {
                        var user = data.users[i];
                        app = "<tr><td>" + user.person_id + "</td>";
                        app += "<td><input style=\"width:100px\" type=\"text\" value=\"" + user.salary
                                + "\" /></td><td><input style=\"width:100px\" type=\"text\" value=\"" + user.addition
                                + "\" /></td><td><input style=\"width:100px\" type=\"text\" value=\"" + user.work_addr
                                + "\" /></td><td><input style=\"width:100px\" type=\"text\" value=\"" + user.work_age
                                + "\" /></td><td><span class=\"glyphicon glyphicon-ok-sign table_icon\" title=\"update complete\"></span></td>";
                        app += "</tr>";
                        $("#table_empinfo").append(app);
                    }


                    $("span.glyphicon-ok-sign").click(function () {
                        var id = $(this).parent().siblings().eq(0).text();
                        var salary = $(this).parent().siblings().eq(1).children().eq(0).val();
                        var addition = $(this).parent().siblings().eq(2).children().eq(0).val();
                        var work_addr = $(this).parent().siblings().eq(3).children().eq(0).val();
                        var work_age = $(this).parent().siblings().eq(4).children().eq(0).val();
                        var array = "[{id:\"" + id + "\", salary:\"" + salary + "\", addition:\"" + addition
                                + "\", work_addr:\"" + work_addr + "\", work_age:\"" + work_age + "\"}]";
                        alert(array);
                        $.post("../UpdateEmployee", {users: array}, function (data) {
                            alert(data.message);
                            $("#table_empinfo").empty();
                        }, "json");
                    });
                } else {
                    alert(data.error);
                }
            }, "json");
        } else if (func == "check") {
            app = "<tr id=\"tr_header\"><th>course id</th><th>course name</th><th>total time</th>"
                    + "<th>teacher name</th></tr>";
            app += "</tr>";
            $("#table_empinfo").append(app);
            $.post("../QueryChief", {id: $("#search_input").val(), key: $("button.dropdown-toggle").text(), type: "choose"},
            function (data) {
                if (data.result == "1") {
                    for (i in data.users) {
                        var c = data.users[i];
                        app = "<tr id=\"tr_header\"><th>course id</th><th>course name</th><th>total time</th>" + "<th>teacher name</th></tr>";
                        app += "<tr><td>" + c.id + "</td><td>" + c.name + "</td><td>" + c.total_time + "</td><td>" + c.teacher + "</td></tr>";
                        $("#table_empinfo").append(app);
                    }
                } else {
                    alert(data.error);
                }
            });
        }

    });

    $("#add_info_list").click(function () {
        $("#right_div div").hide();
        removeActive();
        $(this).attr("class", "list-group-item active");
        $("#table_empinfo").empty();
        $("#submit_table").show();
        func = "add";
        var app = "<tr id=\"tr_header\"><th>id</th><th>name</th><th>sex</th>"
                + "<th>salary</th><th>addition</th><th>work_addr</th><th>work_age</th><th></th></tr>";
        app += "<tr><td><input style=\"width:80px\" type=\"text\" /><td><input style=\"width:80px\" type=\"text\" /></td><td><input style=\"width:80px\" type=\"text\" /></td>"
                + "<td><input style=\"width:80px\" type=\"text\" /></td><td><input style=\"width:80px\" type=\"text\" /></td><td><input style=\"width:80px\" type=\"text\" />"
                + "</td><td><input style=\"width:80px\" type=\"text\" /></td>"
                + "<td><span class=\"glyphicon glyphicon-plus table_icon\" title=\"add one more\"></span></td></tr>";
        $("#table_empinfo").append(app);
        $("#add_delete_table").show();
        $("#hello").hide();
        $("span.glyphicon-plus").click(function () {
            var id = $(this).parent().siblings().children().eq(0).val();
            var name = $(this).parent().siblings().children().eq(1).val();
            var sex = $(this).parent().siblings().children().eq(2).val();
            var salary = $(this).parent().siblings().children().eq(3).val();
            var addition = $(this).parent().siblings().children().eq(4).val();
            var work_addr = $(this).parent().siblings().children().eq(5).val();
            var work_age = $(this).parent().siblings().children().eq(6).val();
            if (sex != "female" && sex != "male" && sex != "other") {
                alert("Type must be one of: male, female, other!");
            } else {
                app = "<tr><td><input style=\"width:80px\" type=\"text\" value=\""
                        + id + "\"><td><input style=\"width:80px\" type=\"text\" value=\""
                        + name + "\"></td><td><input style=\"width:80px\" type=\"text\"  value=\""
                        + sex + "\"></td><td><input style=\"width:80px\" type=\"text\" value=\""
                        + salary + "\"></td><td><input style=\"width:80px\" type=\"text\" value=\""
                        + addition + "\"></td><td><input style=\"width:80px\" type=\"text\" value=\""
                        + work_addr + "\"></td><td><input style=\"width:80px\" type=\"text\" value=\""
                        + work_age + "\"></td><td></td></tr>";
                $(this).parent().parent().before(app);
            }
        });
    });

    $("#submit_icon").click(function () {
        var ele = $(this);
        if (func == "make") {
            var array = "[";
            var num = 0;
            var err = 0;
            $("#table_empinfo tr:not(:first)").each(function () {
                var check = $(this).children("td:last").children().children().children(":checked").val();
                if (check != "un" && check != "mandatory" && check != "elective") {
                    alert("Must choose one from mandatory, elective or not chosen!");
                    err = 1;
                } else if (check != "un") {
                    array += "{id:\"" + $(this).children("td:first").text() + "\", mandatory: \"" + check + "\"},";
                    num++;
                }
            });
            if (err == 0) {
                if (num != 0)
                    array = array.substr(0, array.length - 1);
                array += "]";
                $.post("../MakePlan", {courses: array}, function (data) {
                    alert(data.message);
                    if (data.message == "succeed!") {
                        $("#table_empinfo").empty();
                        ele.hide();
                    }
                }, "json");
            }
        } else if (func == "add") {
            var array = "[";
            $("#table_empinfo tr:not(:first)").each(function () {
                array += "{id:\"" + $(this).children().eq(0).children().eq(0).val()
                        + "\", name: \"" + $(this).children().eq(1).children().eq(0).val()
                        + "\", sex: \"" + $(this).children().eq(2).children().eq(0).val()
                        + "\", salary: \"" + $(this).children().eq(3).children().eq(0).val()
                        + "\", addition: \"" + $(this).children().eq(4).children().eq(0).val()
                        + "\", work_addr: \"" + $(this).children().eq(5).children().eq(0).val()
                        + "\", work_age:\"" + $(this).children().eq(6).children().eq(0).val() + "\"},";
            });
            array = array.substr(0, array.length - 1);
            array += "]";
            alert(array);
            $.post("../AddEmployee", {users: array}, function (data) {
                alert(data.message);
                $("#table_empinfo").empty();
            }, "json");
        }

    });
});

