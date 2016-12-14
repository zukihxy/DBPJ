/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var func = "";
//$.post("../Offer", {}, function(data){}, "json");
$(document).ready(function () {
    replaceInfo();
    $("#right_div div").hide();
    $("#hello").hide();

    $("#offer_list").click(function () {
        $("#right_div div").hide();
        removeActive();
        $(this).attr("class", "list-group-item active");
        $("#submit_table").show();
        $("#table_empinfo").empty();
        func = "offer";
        $.post("../Offer", {}, function(data){
            
        }, "json");
        $("#add_delete_table").show();
    });
});

