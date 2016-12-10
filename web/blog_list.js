/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function () {
    var user = $.cookie("user");
    $("#nameUp").html(getQueryString("writer"));
    $("#nameDown").html(getQueryString("writer"));
    $.post("LoadCate", {user: getQueryString("writer")}, function (data) {
        for (i in data) {
            var elm = "<li class =\"otherCata\" id=\"" + i + "\">" + data[i].category;
            if ($.cookie("user") == getQueryString("writer")) {
                elm += "<span class = \"gray cate\" id=\"edit_" + data[i].category
                        + "\">edit</span><span class = \"gray del\" id=\"delete_" + data[i].category
                        + "\">delete</span>";
            }
            elm += "<span class=\"badge\">" + data[i].count + "</span></li>";
            var old = data[i].category;
            $("#category").append(elm);
            $("#delete_" + old).click(function (event) {
                if (confirm("Sure to delete?")) {
                    $.post("deletecate", {id: event.target.id, writer: getQueryString("writer")},
                    function (data) {
                        alert(data);
                        window.location = "blog_list.html?writer=" + getQueryString("writer");
                    });
                }
                event.stopPropagation();
            });
            $("#edit_" + old).click(function (event) {
                $.post("deletecate", {id: event.target.id, writer: getQueryString("writer"),
                    cate: prompt("Rename the category into:")},
                function (data) {
                    alert(data);
                    window.location = "blog_list.html?writer=" + getQueryString("writer");
                });
                event.stopPropagation();
            });

            $("#" + i).click(function (event) {
                $("#add").empty();
                $.post("category", {user: getQueryString("writer"), cate: data[event.target.id].category}, function (data) {
                    $("#add").empty();
                    for (j in data) {
                        var obj = data[j];
                        var id = obj.id;
                        var html = "<div class = \"blog_title\"><strong class = \"blog_title\">" + obj.title + " ("
                                + obj.time + ")</strong> ";
                        var fadein = "<div name=\"edit_" + obj.id + "\">title:<br/><input type = \"text\" id = \"title_fade\" /><br/>"
                                + "<textarea rows = \"10\" id = \"context_fade\"></textarea> <br/>"
                                + "category:<select name = \"category\" id=\"category_fade\"></select> <span id=\"newCate\">create a new catagory</span>"
                                + "label:<input type = \"text\" id =\"label_fade\"/><br/><input type=\"button\" id=\"submitupdate_"
                                + id + "\" value=\"submit\" /></div>";

                        if ($.cookie("user") == getQueryString("writer")) {
                            html += "<span class = \"gray edit\" id=\"edit_" + obj.id
                                    + "\">edit</span><span class = \"gray delete\" id=\"delete_" + obj.id
                                    + "\">delete</span></div>";
                        } else {
                            html += "</div>"
                        }
                        $("#add").append(html);

                        if ($.cookie("user") == getQueryString("writer")) {
                            $("#add").append(fadein);
                            $("#submitupdate_" + id).click(function (event) {
                                $.post("edit", {
                                    title: $("#title_fade").val(),
                                    context: $("#context_fade").val(),
                                    category: $("#category_fade").val(),
                                    label: $("#label_fade").val(),
                                    writer: getQueryString("writer"),
                                    id: event.target.id
                                }, function (data) {
                                    if (data == "0") {
                                        alert("You have edit a blog successfully!");
                                        window.location = "blog_list.html?writer=" + getQueryString("writer");
                                    } else {
                                        alert(data);
                                        $("#label_fade").val("");
                                    }
                                });
                            });
                        }
                        //add click to delete
                        $("span.delete").click(function (event) {
                            if (confirm("Sure to delete?")) {
                                $.post("delete", {id: event.target.id}, function (data) {
                                    alert(data);
                                    window.location = "blog_list.html?writer=" + getQueryString("writer");
                                });
                            }
                        });
                        //add click to edit
                        $("#edit_" + id).click(function (event) {
                            $.post("origin", {user: getQueryString("writer"), blog_id: event.target.id}, function (data) {
                                $("div[name=" + event.target.id + "] #category_fade").empty();
                                $("div[name=" + event.target.id + "] #title_fade").val(data.title);
                                $("div[name=" + event.target.id + "] #context_fade").val(data.context);
                                $("div[name=" + event.target.id + "] #label_fade").val(data.label);
                                var cate = data.category;
                                for (j = 0; j < cate.length; j++) {
                                    var elm = "";
                                    elm = $("<option></option>").text(cate[j]);
                                  
                                    $("div[name=" + event.target.id + "] #category_fade").append(elm);
                                }
                                elm = "<option selected=\"selected\">" + data.selected + "</option>";
                                $("div[name=" + event.target.id + "] #category_fade").append(elm);
                            }, "json");
                            $("div[name=" + event.target.id + "]").fadeToggle();
                        });

                        $("#edit_" + id).click();
                    }
                }, "json");

                $("#category").children().removeClass();
                $("#category").children().addClass("otherCata");
                $("#" + event.target.id).removeClass("otherCata");
                $("#" + event.target.id).addClass("active");
            });
            $("#totalNum").html(data[i].total);
            $("#cate").css("height", i * 35 + 140);
        }

    }, "json");
    $.post("LoadUser", {user: getQueryString("writer")}, function (data) {
        $("#head").attr("src", data.img);
        var html = "<div class = \"myInfo\">nickname: " + data.nickname + "<br/>self introduction:<br/>" + data.introduction + "</div>";
        $("#head").after(html);
    }, "json");
    all();

    //click on navigation and all
    $("#all_blogs").click(all);
    if (user !== getQueryString("writer")) {
        $("#write").hide();
    }
    $("#write").click(function () {
        window.location = "edit.html";
    });
    $("#me_link").click(function () {
        if ($.cookie("user") == getQueryString("writer")) {
            window.location = "edit_info.html";
        } else {
            window.location = "writer_info.html?writer=" + getQueryString("writer");
        }
    });
    $("#home_link").click(function () {
        window.location = "home1.html?writer=" + getQueryString("writer");
    });
    $("#contet_link").click(function () {
        window.location = "blog_list.html?writer=" + getQueryString("writer");
    });

});

function all() {
    $.post("blog_list", {user: getQueryString("writer")}, function (data) {
        for (j in data) {
            var obj = data[j];
            var id = obj.id;
            var html = "<div class = \"blog_title\"><strong class = \"blog_title\">" + obj.title + " ("
                    + obj.time + ")</strong> ";
            var fadein = "<div name=\"edit_" + obj.id + "\">title:<br/><input type = \"text\" id = \"title_fade\" /><br/>"
                    + "<textarea rows = \"10\" id = \"context_fade\"></textarea> <br/>"
                    + "category:<select name = \"category\" id=\"category_fade\"></select> <span id=\"newCate\">create a new catagory</span>"
                    + "<br/>label:<input type = \"text\" id =\"label_fade\"/><br/><input type=\"button\" id=\"submitupdate_"
                    + id + "\" value=\"submit\" /></div>";

            if ($.cookie("user") == getQueryString("writer")) {
                html += "<span class = \"gray edit\" id=\"edit_" + obj.id
                        + "\">edit</span><span class = \"gray delete\" id=\"delete_" + obj.id
                        + "\">delete</span></div>";
            } else {
                html += "</div>"
            }
            $("#add").append(html);

            //if the visitor is owner, he may edit these infomation
            if ($.cookie("user") == getQueryString("writer")) {
                $("#add").append(fadein);
                
                $("#submitupdate_" + id).click(function (event) {
                    $.post("edit", {
                        title: $("#title_fade").val(),
                        context: $("#context_fade").val(),
                        category: $("#category_fade").val(),
                        label: $("#label_fade").val(),
                        writer: getQueryString("writer"),
                        id: event.target.id
                    }, function (data) {
                        if (data == "0") {
                            alert("You have edit a blog successfully!");
                            window.location = "home1.html?writer=" + getQueryString("writer");
                        } else {
                            alert(data);
                            $("#label_fade").val("");
                        }
                    });
                });
            }
            //add click to delete
            $("span.delete").click(function (event) {
                if (confirm("Sure to delete?")) {
                    $.post("delete", {id: event.target.id}, function (data) {
                        alert(data);
                        window.location = "blog_list.html?writer=" + getQueryString("writer");
                    });
                }
            });
            //add click to edit
            $("#edit_" + id).click(function (event) {
                $.post("origin", {user: getQueryString("writer"), blog_id: event.target.id}, function (data) {
                    $("div[name=" + event.target.id + "] #category_fade").empty();
                    $("div[name=" + event.target.id + "] #title_fade").val(data.title);
                    $("div[name=" + event.target.id + "] #context_fade").val(data.context);
                    $("div[name=" + event.target.id + "] #label_fade").val(data.label);
                    var cate = data.category;
                    for (j = 0; j < cate.length; j++) {
                        var elm = "";
                        elm = $("<option></option>").text(cate[j]);
                        
                        $("div[name=" + event.target.id + "] #category_fade").append(elm);
                    }
                    elm = "<option selected=\"selected\">" + data.selected + "</option>";
                    $("div[name=" + event.target.id + "] #category_fade").append(elm);
                }, "json");
                $("div[name=" + event.target.id + "]").fadeToggle();
            });

            $("#edit_" + id).click();
        }
    }, "json");
}

function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null)
        return unescape(r[2]);
    return null;
}

