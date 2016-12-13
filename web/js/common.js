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

//function dropDown(chosen) {
//    $("button.btn.btn-default.dropdown-toggle").click(function () {
//        if ($(chosen).attr("class") == "input-group-btn")
//            $(chosen).attr("class", "input-group-btn open");
//        else
//            $(chosen).attr("class", "input-group-btn");
//    });
//    $(chosen+" ul.dropdown-menu[role=menu] li").click(function () {
//        $("button.btn.btn-default.dropdown-toggle").text($(this).children("a").eq(0).text());
//    });
//}
