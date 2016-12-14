/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var func = "";
var course_id = "";
$(document).ready(function () {
    replaceInfo();
    removeActive();
    changePass();
    $("#right_div div").hide();
    $("#submit_table").hide();

    $("#offer_list").click(function () {
        $("#right_div div").hide();
        removeActive();
        $(this).attr("class", "list-group-item active");
        $("#table_empinfo").empty();
        $("#submit_table").show();
        func = "offer";
        var app = "<tr><th>course id</th><th>course name</th><th>total time</th><th></th></tr>";
        app += "<tr><td><input type=\"text\" /><td><input type=\"text\" /></td>"
                + "<td><input type=\"text\" /></td><td><span class=\"glyphicon glyphicon-plus table_icon\" title=\"add one more\"></span></td></tr>";
        $("#table_empinfo").append(app);
        $("#add_delete_table").show();
        $("#hello").hide();
        $("span.glyphicon-plus").click(function () {
            var id = $(this).parent().siblings().children().eq(0).val();
            var name = $(this).parent().siblings().children().eq(1).val();
            var time = $(this).parent().siblings().children().eq(2).val();
            app = "<tr><td><input type=\"text\" value=\"" + id + "\"></td><td><input type=\"text\" value=\""
                    + name + "\"></td><td><input type=\"text\" value=\"" + time + "\"></td><td></td></tr>";
            $(this).parent().parent().before(app);
        });
    });

    $("#delete_list").click(function () {
        $("#right_div div").hide();
        removeActive();
        $(this).attr("class", "list-group-item active");
        $("#table_empinfo").empty();
        var app = "<tr><th>course id</th><th>course name</th><th></th></tr>";
        $("#table_empinfo").append(app);
        $.post("../QueryTeacher", {type: "delete"}, function (data) {
            for (i in data.courses) {
                var user = data.courses[i];
                app = "<tr><td>" + user.course_id + "<td>" + user.course_name + "</td>"
                        + "<td><span class=\"glyphicon glyphicon-trash table_icon\" data-id=\"" + user.course_id + "\" title=\"delete this row\"></span></td></tr>";
                $("#table_empinfo").append(app);
            }
            $("span.glyphicon-trash").each(function () {
                $(this).click(function () {
                    var ele = $(this);
                    $.post("../Delete", {id: $(this).data("id"), type: "course"}, function (data) {
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

    $("#update_list,#upload_list").click(function () {
        $("#right_div div").hide();
        removeActive();
        $("#hello").hide();
        $(this).attr("class", "list-group-item active");
        $("#submit_table").show();
        $("#search_form").show();
        $("#search_form div").show();
        $("#table_empinfo").empty();
        func = $(this).data("func");
        $("#add_delete_table").show();
    });

    $("#approve_list").click(function () {
        $("#right_div div").hide();
        removeActive();
        $(this).attr("class", "list-group-item active");
        $("#table_empinfo").empty();
        var app = "<tr><th>course id</th><th>employee id</th><th></th></tr>";
        $("#table_empinfo").append(app);
        $.post("../QueryTeacher", {type: "retest"}, function (data) {
            for (i in data.users) {
                var user = data.users[i];
                app = "<tr><td>" + user.course_id + "<td>" + user.employee_id + "</td>"
                        + "<td><span class=\"glyphicon glyphicon-ok-sign table_icon\" data-eid=\"" + user.employee_id + "\" data-cid=\"" + user.course_id + "\" title=\"approve retest\"></span></td></tr>";
                $("#table_empinfo").append(app);
            }
            $("span.glyphicon-ok-sign").each(function () {
                $(this).click(function () {
                    var ele = $(this);
                    $.post("../Approve", {course_id: $(this).data("cid"), employee_id: $(this).data("eid")}, function (data) {
                        alert(data.message);
                        if (data.message == "succeed") {
                            ele.parent().parent().remove();
                        }
                    }, "json");
                });
            });
        }, "json");

        $("#add_delete_table").show();
        $("#hello").hide();
    });

    $("#submit_icon").click(function () {
        if (func == "upload" || func == "update") {
            var array = "[";
            var num = 0;
            $("#table_empinfo tr:not(:first)").each(function () {
                var id = $(this).children().eq(0).text();
                var eid = $(this).children().eq(1).text();
                var check = $(this).children("td:last").children().children().children(":checked").val();
                    num++;
                    array += "{cid:\"" + id + "\",eid:\"" + eid + "\", score:\"" + check + "\"},";
            });
            if (num != 0)
                array = array.substr(0, array.length - 1);
            array += "]";
            $.post("../UpdateScore", {score: array, course_id: course_id}, function (data) {
                alert(data.message);
                $("#table_empinfo").empty();
            }, "json");
        } else if (func == "offer") {
            var array = "[";
            var num = 0;
            $("#table_empinfo tr:not(:first)").each(function () {
                var id = $(this).children().eq(0).children().eq(0).val();
                var name = $(this).children().eq(1).children().eq(0).val();
                var time = $(this).children().eq(2).children().eq(0).val();
                if (id != "" && name != "" && time != "") {
                    num++;
                    array += "{course_id:\"" + id + "\", course_name: \"" + name + "\", total_time:\"" + time + "\"},";
                } else {
                    alert("Warning: There are empty blanks!\n\r Only the lines without empty blanks will be added.");
                }
            });
            if (num != 0)
                array = array.substr(0, array.length - 1);
            array += "]";
            $.post("../Offer", {courses: array}, function (data) {
                alert(data.message);
                $("#table_empinfo").empty();
            }, "json");
        }

    });

    $("#search_btn").click(function () {
        $("#add_delete_table").show();
        $("#table_empinfo").empty();
        course_id = $("#search_cid").val();
        var eid = $("#search_eid").val();
        $.post("../QueryTeacher", {cid: course_id, eid: eid, type: func}, function (data) {
            if (data.result == "1") {
                var app = "<tr><th>course id</th><th>employee id</th><th>score</th></tr>";
                $("#table_empinfo").append(app);
                for (i in data.users) {
                    var item = data.users[i];
                    app = "<tr><td>" + item.cid + "</td><td>" + item.eid + "</td>";
                    app += "<td><div class=\"radio\"><label><input type=\"radio\" name=\"optionsRadios" + i + "\" value=\"true\">pass";
                    if (func == "update")
                        app += "</label></div><div class=\"radio\"><label><input type=\"radio\" name=\"optionsRadios" + i + "\" value=\"false\" checked>fail";
                    else if (func == "upload")
                        app += "</label></div><div class=\"radio\"><label><input type=\"radio\" name=\"optionsRadios" + i + "\" value=\"false\">fail";                        
                    app += "</label></div></td></tr>";
                    $("#table_empinfo").append(app);
                }
            } else {
                alert(data.message);
            }
        }, "json");
    });
});

