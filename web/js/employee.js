/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var func = "";
$(document).ready(function () {
    replaceInfo();
    changePass();
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
        $.post("../QueryEmployee", {type: "plan"}, function (data) {
            if (data.result == "0") {
                alert(data.message);
            } else {
                for (i in data.courses) {
                    var c = data.courses[i];
                    app = "<tr><td>" + c.course_id + "</td><td>" + c.course_name + "</td><td>" + c.total_time + "</td>"
                            + "<td>" + c.teacher_name + "</td><td>"
                    if (c.mandatory == "false") {
                        if (c.choose == "false")
                            app += "<td><span class=\"glyphicon glyphicon-plus-sign table_icon\" data-type=\"add\" title=\"choose this course\"></span></td></tr>";
                        else if (c.choose == "true")
                            app += "<td><span class=\"glyphicon glyphicon-minus-sign table_icon\" data-type=\"delete\" title=\"drop this course\"></span></td></tr>";

                    }
                    $("#table_empinfo").append(app);
                }
                $("span.glyphicon-plus-sign,span.glyphicon-minus-sign").each(function () {
                    $(this).click(function () {
                        if ($(this).attr("class") == "glyphicon glyphicon-plus-sign table_icon") {
                            $(this).data("type", "delete");
                            $(this).attr("class", "glyphicon glyphicon-minus-sign table_icon");
                            $(this).attr("title", "drop this course");
                        } else {
                            $(this).data("type", "add");
                            $(this).attr("class", "glyphicon glyphicon-plus-sign table_icon");
                            $(this).attr("title", "choose this course");
                        }
                    });
                });
            }
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
        var app = "<tr><th>course id</th><th>course name</th><th>score</th></tr>";
        $("#table_empinfo").append(app);
        $.post("../QueryEmployee", {type: "score"}, function (data) {
            for (i in data.courses) {
                var c = data.courses[i];
                app = "<tr><td>" + c.course_id + "</td><td>" + c.course_name + "</td><td>" + c.score + "</td><td></tr>";
                $("#table_empinfo").append(app);
            }
        }, "json");
        $("#add_delete_table").show();
        $("#hello").hide();
    });

    $("#apply_list").click(function () {
        $("#right_div div").hide();
        removeActive();
        $("#payment").show();
        $(this).attr("class", "list-group-item active");
        $("#table_empinfo").empty();
        var app = "<tr><th>course id</th><th>score</th></tr>";
        $("#table_empinfo").append(app);
        $.post("../QueryEmployee", {type: "score"}, function (data) {
            for (i in data.courses) {
                var c = data.users[i];
                if (c.need_retest == "true") {
                    app = "<tr><td>" + c.course_id + "</td><td>" + c.score + "</td><td>"
                            + "<td><span class=\"glyphicon glyphicon-repeat table_icon\" data-id=\"" + c.course_id
                            + "\" title=\"apply retest\"></span></td></tr>";
                    $("#table_empinfo").append(app);
                }
            }
            $("span.glyphicon-repeat").each(function () {
                $(this).attr("class", "");
                $(this).attr("title", "");
                $(this).text("Applied");
                $.post("../ApplyRetest", {course_id: $(this).data("id")}, function (data) {
                    alert(data.message);
                    $("span.glyphicon-usd").text(data.pay);
                }, "json");
            });
        }, "json");
        $("#add_delete_table").show();
        $("#hello").hide();
    });

    $("#submit_icon").click(function () {
        if (func == "choose") {
            var array = "[";
            $("#table_empinfo tr:not(:first)").each(function () {
                var type = $(this).children("td:last").children("span").eq(0).data("type");
                if (typeof (type) != "undefined")
                    array += "{course_id:\"" + $(this).children("td:first").text() + "\", choose: \"" + type + "\"},";
            });
            array = array.substr(0, array.length - 1);
            array += "]";
            $.post("../ChooseCourse", {courses: array}, function (data) {
                alert(data.message);
                if (data.message == "succeed!") {
                    $("#table_empinfo").empty();
                    $("#submit_table").hide();
                }
            }, "json");
        }
    });
});

