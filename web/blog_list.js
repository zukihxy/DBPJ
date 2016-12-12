$(document).ready(function() {
    $("#submit").click(function() {
    $.session.set("id", $("#test").val());
    $.ajax({
        type:"post",
        url:"edit_info",
        dataType: "json",
        data:{},
        success:function(returnData){
            var state=returnData.id;//取得对象属性
            alert(state);
        }        
    });
    });
});
