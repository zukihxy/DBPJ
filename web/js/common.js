/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function replaceInfo() {
    var id = $.cookie("id");
    $("#info_id").text(id);
}
function logout() {
    $.cookie("id", null);
    $.cookie("type", null);
}
function removeActive() {
    $("#function_list a").each(function () {
        if ($(this).attr("class") == "list-group-item active")
            $(this).attr("class", "list-group-item");
    });
}

function dropDown() {
    $("button.dropdown-toggle").click(function () {
        if ($("div.input-group-btn").attr("class") == "input-group-btn")
            $("div.input-group-btn").attr("class", "input-group-btn open");
        else
            $("div.input-group-btn").attr("class", "input-group-btn");
    });
    $("div.input-group-btn ul.dropdown-menu[role=menu] li").click(function () {
        $("button.dropdown-toggle").html($(this).children("a").eq(0).text() + "<span class=\"caret\"></span>");
        $("div.input-group-btn").attr("class", "input-group-btn");
    });
}

function changePass() {
    $("#passwd_list").click(function () {
        removeActive();
        $("#add_delete_table").empty();
        $("div.inner").hide();
        $("#hello").hide();
        $(this).attr("class", "list-group-item active");
        $("#passwd_form").show();
        $("#passwd_form div").show();
        $("#passwd_btn").click(function () {
            var newpass = $("#passwd_input").val();
            $.post("../ChangePassword", {password: newpass}, function (data) {
                alert(data.message);
            }, "json");
        });
    });
}
