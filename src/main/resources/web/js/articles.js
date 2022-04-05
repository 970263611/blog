const url = window.location.href
const split = '?id='
let id = null
if (url.indexOf(split) != -1) {
    id = url.split(split)[1]
}
let commentSize = 0

function index() {
    $("#page_1").click()
}

function articles(data) {
    $("#textList").empty();
    let html = "";
    for (let a = 0; a < data.length; a++) {
        const head = "<div style='position: absolute; right: 36px; top: 10%;'>";
        const foot = "</div>";
        html +=
            "<div class='article'>" +
            "<div class='article_inside' style='position: relative'>";
        if (data[a].top == '1') {
            html += "<img src='../images/top.png' style='transform: rotate(90deg);width: 50px;position: absolute;top: 0;right: 0;'>";
        }
        html += "<h3 class='blogtitle' onclick='toArticle(\"" + data[a].id + "\")'>";
        html += "<a>" + data[a].title + "</a>";
        html +=
            "</h3>" +
            "<div class='content'>" +
            "<span>" +
            data[a].content +
            "</span>" +
            "</div>" +
            "<div class='article_img_div'>" +
            "<span class='type'>" + data[a].type + "</span>" +
            "<span class='time'>" + data[a].createTime + "</span>" +
            "<span class='view'>" + data[a].see + "</span>";
        html += "</div>" +
            head + foot +
            "<div class='readall'>" +
            "<span onclick='toArticle(\"" + data[a].id + "\")'><a style='color: green;'>阅读全文</a></span>" +
            "</div>" +
            "</div>" +
            "</div>";
    }
    $('#textList').append(html);
}

function toArticle(id) {
    window.location.href = '/article?id=' + id
}

function getArticle() {
    $.ajax({
        type: "post",
        url: "/getArticle",
        data: {"id": id},
        dataType: "json",
        cache: false,
        success: function (res) {
            if (res.success) {
                const data = res.data
                let html = "<p id='title' style='text-align: center;margin: 10px 0px 10px 0px;font-size: xx-large;'>" + data.title + "</p>"
                html += data.content
                $("#article").html(html)
            }
        }
    })
}

function getComment() {
    const url = window.location.href
    const split = '?id='
    if (url.indexOf(split) != -1) {
        const id = url.split(split)[1]
        $.ajax({
            type: "post",
            url: "/getComment",
            data: {"id": id},
            dataType: "json",
            cache: false,
            success: function (res) {
                if (res.success) {
                    const data = res.data
                    commentSize = data.length
                    $(".num").html('共<span>' + commentSize + '</span>条')
                    let html = "";
                    for (let comment of data) {
                        let date = comment.date
                        if (date.length == 14) {
                            date = '20' + date
                        }
                        html += '<li><a target="_blank">' + comment.message + '</a><label><span>' + date + '</span></label></li>'
                    }
                    $(".comment-ul").html(html)
                }
            }
        })
    }
}

function pl() {
    const comment = $("textarea").val().trim()
    if (comment != null && comment != undefined && comment != "") {
        $('.alert').html('提交评论成功').addClass('alert-success').show().delay(2500).fadeOut()
        const date = dateFormat('YY-mm-dd HH:MM', new Date())
        $.ajax({
            type: "post",
            url: "/setComment",
            data: {"id": id, "date": date, "comment": comment},
            dataType: "json",
            cache: false,
            success: function (res) {
            }
        })
        $("textarea").val('')
        document.getElementById("textarea").rows = 1
        $("#pl").hide()
        commentSize++
        $(".num").html('共<span>' + commentSize + '</span>条')
        $(".comment-ul").prepend('<li><a target="_blank">' + comment + '</a><label><span>' + date + '</span></label></li>')
    } else {
        $('.alert').html('请填写评论内容').addClass('alert-warning').show().delay(2500).fadeOut()
    }
}

function dateFormat(fmt, date) {
    const opt = {
        "Y+": date.getFullYear().toString(),        // 年
        "m+": (date.getMonth() + 1).toString(),     // 月
        "d+": date.getDate().toString(),            // 日
        "H+": date.getHours().toString(),           // 时
        "M+": date.getMinutes().toString(),         // 分
        "S+": date.getSeconds().toString()          // 秒
        // 有其他格式化字符需求可以继续添加，必须转化成字符串
    };
    for (let k in opt) {
        const ret = new RegExp("(" + k + ")").exec(fmt)
        if (ret) {
            fmt = fmt.replace(ret[1], (ret[1].length == 1) ? (opt[k]) : (opt[k].padStart(ret[1].length, "0")))
        }
    }
    return fmt
}