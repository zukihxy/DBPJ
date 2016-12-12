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
