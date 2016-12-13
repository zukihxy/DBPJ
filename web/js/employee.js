/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var func = "";
$(document).ready(function () {
    replaceInfo();
    $("#right_div div").hide();

    $("#choose_list").click(function () {
        $("#right_div div").hide();
        removeActive();
        $(this).attr("class", "list-group-item active");
        $("#submit_table").show();
        $("#table_empinfo").empty();
        var app = "<tr id=\"tr_header\"><th>course id</th><th>course name</th><th>total time</th>"
                + "<th>teacher name</th></tr>";
        $("#table_empinfo").append(app);
        $.post("../QueryPlan", {}, function (data) {
            for (i in data.courses) {
                var c = data.users[i];
                app = "<tr><td>" + c.course_id + "</td><td>" + c.course_name + "</td><td>" + c.total_time + "</td>"
                        + "<td>" + c.teacher_name + "</td><td>"
                        + "<td><span class=\"glyphicon glyphicon-plus-sign table_icon\" data-type=\"add\" title=\"choose this course\"></span></td></tr>";
                $("#table_empinfo").append(app);
            }
            $("span.glyphicon-plus-sign").each(function () {
                $(this).click(function () {
                    $(this).data("type", "delete");
                    $(this).attr("class", "glyphicon glyphicon-minus-sign table_icon");
                    $(this).attr("title", "drop this course");
                });
            });
        }, "json");
        func = "choose";
        $("#add_delete_table").show();
        $("#hello").hide();
    });

    $("#query_list").click(function () {
        $("#right_div div").hide();
        removeActive();
        $(this).attr("class", "list-group-item active");
        $("#table_empinfo").empty();
        var app = "<tr><th>course id</th><th>score</th></tr>";
        $("#table_empinfo").append(app);
        $.post("../QueryScore", {}, function (data) {
            for (i in data.courses) {
                var c = data.users[i];
                app = "<tr><td>" + c.course_id + "</td><td>" + c.score + "</td><td></tr>";
                $("#table_empinfo").append(app);
            }
        }, "json");
        $("#add_delete_table").show();
        $("#hello").hide();
    });

    $("#apply_list").click(function () {
        $("#right_div div").hide();
        removeActive();
        $(this).attr("class", "list-group-item active");
        $("#table_empinfo").empty();
        var app = "<tr><th>course id</th><th>score</th></tr>";
        $("#table_empinfo").append(app);
        $.post("../QueryScore", {}, function (data) {
            for (i in data.courses) {
                var c = data.users[i];
                app = "<tr><td>" + c.course_id + "</td><td>" + c.score + "</td><td>"
                        +"<td><span class=\"glyphicon glyphicon-repeat table_icon\" data-id=\""+c.course_id
                        +"\" title=\"apply retest\"></span></td></tr>";
                $("#table_empinfo").append(app);
            }
            $("span.glyphicon-repeat").each(function() {
                $(this).attr("class", "");
                $(this).attr("title", "");
                $(this).text("Applied");
                $.post("../Retest", {id: $(this).data("id")}, function(data) {
                    alert(data.message);
                }, "json");
            });
        }, "json");
        $("#add_delete_table").show();
        $("#hello").hide();
    });

    $("#submit_icon").click(function () {
        var ele = $(this);
        if (func == "choose") {
            var array = "[";
            var num = 0;
            $("#table_empinfo tr:not(:first)").each(function () {
                var type = $(this).children("td:last").children("span").eq(0).data("type");
                if (type == "delete") {
                    array += "{id:\"" + $(this).children("td:first").text() + "\"},";
                }
            });
            if (num != 0)
                array = array.substr(0, array.length - 1);
            array += "]";
            $.post("../Choose", {courses: array}, function (data) {
                alert(data.message);
                if (data.message == "succeed!") {
                    $("#table_empinfo").empty();
                    ele.hide();
                }
            }, "json");
        }
    });
});

