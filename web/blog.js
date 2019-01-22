/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function () {
    var user = $.cookie("user");
    $("#nameUp").html(getQueryString("writer"));
    $("#nameDown").html(getQueryString("writer"));
    var writer = "";
    var bid = "";
    $.post("LoadBlog", {id: getQueryString("id"), direction: getQueryString("direction"), writer: getQueryString("writer"), user: user}, function (data) {
        if (data.type == "0") {
            $(".right .block").empty();
            $(".right .block").append("<h1>" + data.data + "</h1><h3>Click <a href=\"home1.html?writer="
                    + getQueryString("writer") + "\">here</a> to back to home page</h3>");
        } else {
            $("#add").empty();
            var label_html = "";
            for (var j = 0; j < data.label.length; j++) {
                label_html += "<h5 class=\"label_list\">" + data.label[j] + "</h5>";
            }
            writer = data.writer;
            var passage = "<div class = \"passage\"><h4>" + data.title + "<h5>(writer: " + data.writer
                    + ")</h5><span>" + data.time + "</span></h4>"
                    + "<pre>lable:" + label_html + "     cata:" + data.category + "</pre><p>"
                    + data.context + "</p><br/><div class = \"comment\"> readed(" + data.view + ") |<span id=\"like_" + data.id
                    + "\">like(" + data.like + ")</span>  </div>"
                    + "<br/>";
            bid = data.id;
            $("#add").append(passage);
            //add comment part
            $.post("comment", {id: bid}, function (data) {
                var comment = "";
                var user = $.cookie("user");
                for (i in  data) {
                    var obj = data[i];
                    comment = "<div><h5>" + obj.writer + "</h5><span>" + obj.time
                            + "</span><p>" + obj.comment + "</p>";
                    if (user == writer || user == obj.writer) {
                        comment += "<span class = \"delete gray\" id =\"comment_" + obj.id + "\">delete</span><br/>";
                    }
                    comment += "<br/></div>";
                    $("#addComment").append(comment);
                }
                $("span.delete.gray").click(function (event) {
                    if (confirm("Sure to delete?")) {
                        $.post("delete", {id: event.target.id}, function (data) {
                            alert(data);
                            window.location = "home1.html?writer=" + getQueryString("writer");
                        });
                    }
                });

            }, "json");
            //like function
            var user = $.cookie("user");
            $("div.passage span[id]:not(.delete)").click(function () {
                var target = $("div.passage span[id]:not(.delete)");
                $.post("like", {id: target.attr("id"), user: user}, function (data) {
                    $("div.passage span[id]:not(.delete)").html("like(" + data + ")");
                });
            });
        }
    }, "json");

    $.post("LoadUser", {user: getQueryString("writer")}, function (data) {
        $("#head").attr("src", data.img);
        var html = "<div class = \"myInfo\">nickname: " + data.nickname + "<br/>self introduction:<br/>" + data.introduction + "</div>";
        $("#head").after(html);
    }, "json");
    //put in button part
    $("#next").click(function () {
        window.location = "blog.html?writer=" + getQueryString("writer") + "&id=" + bid + "&direction=next";
    });
    $("#previous").click(function () {
        window.location = "blog.html?writer=" + getQueryString("writer") + "&id=" + bid + "&direction=previous";
    });
    $("#submit_comment").click(function () {
        $.post("write_comment",
                {
                    user: user,
                    blog_id: bid,
                    context: $("#context_com").val()
                },
        function (data) {
            if (data == "0") {
                alert("You have comment successfully~ Enjoy your browsing!");
                window.location = "home1.html?writer=" + getQueryString("writer");
            }
        }, "json");
    });
    //move to other's blog
    $("div.passage h5:not([id]):first()").click(function () {
        window.location = "home1.html?writer=" + getQueryString("writer");
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
}
);

function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null)
        return unescape(r[2]);
    return null;
}
