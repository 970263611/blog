const PAGE_SIZE = 5
let first = true
$(function () {
    $.ajax({
        type: "post",
        url: "/pages",
        dataType: "json",
        async: false,
        success: function (res) {
            if (res.success) {
                const data = res.data
                pages(data)
                index()
                first = false
            }
        }
    })
})

function pages(data) {
    $("#page").empty()
    let html = "<li id='previousParent'><a onclick='toPage(1,this," + data + ")'>首页</a></li>" +
        "<li class='disabled' id='previous'>" +
        "<a id='previousChild'>" +
        "<span>&lt;</span>" +
        "</a>" +
        "</li>";
    for (let a = 0; a < Math.ceil(data / PAGE_SIZE); a++) {
        if (a == 0) {
            html += "<li class='active'>" +
                "<a id='page_" + (a + 1) +
                "'onclick='toPage(" + (a + 1) + ",this," + data + ")'>" + (a + 1) +
                "<span class='sr-only'>(current)</span>" +
                "</a>" +
                "</li>";
        } else {
            html += "<li>" +
                "<a id='page_" + (a + 1) +
                "'onclick='toPage(" + (a + 1) + ",this," + data + ")'>" + (a + 1) +
                "<span class='sr-only'>(current)</span>" +
                "</a>" +
                "</li>";
        }
    }
    html += "<li id='lastPage'>" +
        "<a id='nextPage' onclick='toPage(2,this," + data + ")'>" +
        "<span>&gt;</span>" +
        "</a>" +
        "</li>" +
        "<li id='lastPageParent'><a onclick='toPage(" + Math.ceil(data / PAGE_SIZE) + ",this," + data + ")'>尾页</a></li>";
    $("#page").append(html)
    $("#previous").addClass("disabled");
    $("#previousParent").addClass("disabled");
    if (data <= PAGE_SIZE) {
        $("#lastPage").addClass("disabled");
        $("#lastPageParent").addClass("disabled");
    }
}

function toPage(page, obj, lastPage) {
    const className = $(obj).parent()[0].className;
    if (className.indexOf('disabled') != -1) {
        return;
    }
    if (!first) {
        const flag = $('#scrollTop').offset().top;
        $('html,body').animate({scrollTop: flag}, 500);
    }
    //分页页码控制开始
    $("#page li").each(function () {
        $(this).removeClass("active");
    });
    $(obj).parent().addClass("active");
    if (page != 1) {
        $("#previous").removeClass("disabled");
        $("#previousParent").removeClass("disabled");
    } else {
        $("#previous").addClass("disabled");
        $("#previousParent").addClass("disabled");
    }
    if (Math.ceil(lastPage / PAGE_SIZE) == page) {
        $("#lastPage").addClass("disabled");
        $("#lastPageParent").addClass("disabled");
    } else {
        $("#lastPage").removeClass("disabled");
        $("#lastPageParent").removeClass("disabled");
    }
    $("#previous").removeClass("active");
    $("#lastPage").removeClass("active");
    $("#previousParent").removeClass("active");
    $("#lastPageParent").removeClass("active");
    $("#page_" + page).parent().addClass('active');
    //分页页码控制结束
    let search = $('#search').val()
    if (search == null || search == undefined || search == '') {
        search = null
    }
    $.ajax({
        type: "post",
        url: "/toPage",
        data: {"page": page, "search": search},
        dataType: "json",
        cache: false,
        success: function (res) {
            if (res.success) {
                const data = res.data
                articles(data)
                if (page - 1 > 0) {
                    $("#previousChild").attr("onclick", "toPage(" + (page - 1) + ",this," + lastPage + ")")
                }
                if (page < Math.ceil(lastPage / PAGE_SIZE)) {
                    $("#nextPage").attr("onclick", "toPage(" + (page + 1) + ",this," + lastPage + ")")
                }
            }
        }
    });
}