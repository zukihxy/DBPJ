/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var func = "";
$(document).ready(function () {
    replaceInfo();
    dropDown();
    changePass();
    $("#right_div div").hide();
    $("#hello").show();

    $("#function_list a:not(:last)").each(function () {
        $(this).click(function () {
            $("#right_div div").hide();
            $("#search_form").show();
            $("#search_form div").show();
            $("#hello").hide();
            removeActive();
            func = $(this).data("type");
            $(this).attr("class", "list-group-item active");
        })
    });

    $("#search_btn").click(function () {
        $("#add_delete_table").show();
        $("#table_empinfo").empty();
        $.post("../QueryCEO", {id: $("#search_input").val(), key: $("button#s_btn").text(), type: func},
        function (data) {
            if (data.result == "0") {
                alert(data.error);
            } else {
                var app;
                if (func == "teacher") {
                    app = "<tr><th>id</th><th>name</th><th>email</th><th>phone</th><th>sex</th></tr>";
                    $("#table_empinfo").append(app);
                } else if (func == "course") {
                    app = "<tr><th>id</th><th>name</th><th>total time</th>";
                    if (data.score == "true") {
                        app += "<th>employee id</th><th>score</th></tr>"
                    } else {
                        app += "</tr>";
                    }
                    $("#table_empinfo").append(app);
                } else if (func == "chief") {
                    app = "<tr><th>id</th><th>name</th><th>sex</th><th>phone</th><th>department</th><th>email</th><th>work_addr</th></tr>";
                    $("#table_empinfo").append(app);
                } else if (func == "employee") {
                    app = "<tr><th>id</th><th>name</th><th>sex</th><th>salary</th><th>addition</th><th>work_addr</th><th>department</th><th>work_age</th></tr>";
                    $("#table_empinfo").append(app);
                }
                for (i in data.users) {
                    var item = data.users[i];
                    if (func == "teacher") {
                        app = "<tr><th>" + item.person_id + "</th><th>" + item.name + "</th><th>" + item.email
                                + "</th><th>" + item.phone + "</th><th>" + item.sex + "</th></tr>";
                        $("#table_empinfo").append(app);
                    } else if (func == "course") {
                        app = "<tr><th>" + item.course_id + "</th><th>" + item.name + "</th><th>" + item.total_time + "</th>";
                        if (data.score == "true") {
                            app += "<th>"+item.employee_id+"</th><th>"+item.score+"</th>";
                        }
                        app+= "</tr>";
                        $("#table_empinfo").append(app);
                    } else if (func == "chief") {
                        app = "<tr><th>" + item.person_id + "</th><th>" + item.name + "</th><th>" + item.sex
                                + "</th><th>" + item.phone + "</th><th>" + item.department + "</th><th>"
                                + item.email + "</th><th>" + item.work_addr + "</th></tr>";
                        $("#table_empinfo").append(app);
                    } else if (func == "employee") {
                        app = "<tr><th>" + item.person_id + "</th><th>" + item.name + "</th><th>" + item.sex
                                + "</th><th>" + item.salary + "</th><th>" + item.addition + "</th><th>" + item.work_addr
                                + "</th><th>" + item.department + "</th><th>" + item.work_age + "</th></tr>";
                        $("#table_empinfo").append(app);
                    }
                }
            }
        }, "json");
    });
});

