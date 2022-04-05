$(".cloud ul a").click(function () {
    $.ajax({
        type: "post",
        url: "/tag",
        data: {'tag': this.innerText},
        async: true,
        success: function (res) {
            $('.alert').html('感谢评价').addClass('alert-success').show().delay(2500).fadeOut()
        }
    })
})
$("#leaveMessage").click(function () {
    const mes = $('#message').val()
    if (mes == null || mes == '') {
        alert('请填写留言信息')
        return false
    }
    $.ajax({
        type: "post",
        url: "/leaveMessage",
        data: {'leaveMessage': mes},
        async: true,
        success: function (res) {
            $('.alert').html('感谢留言').addClass('alert-success').show().delay(2500).fadeOut()
            $('#message').val('')
        }
    })
})

$("#searchBtn").click(function () {
    const flag = $('#scrollTop').offset().top;
    $('html,body').animate({scrollTop: flag}, 500);
    let mes = $('#search').val()
    if (mes == null || mes == undefined) {
        mes = ''
    }
    $.ajax({
        type: "post",
        url: "/search",
        data: {'search': mes},
        async: true,
        cache: false,
        success: function (res) {
            if (res.success) {
                const data = res.data
                pages(data[1])
                articles(data[0])
            }
        }
    })
})