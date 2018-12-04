$(document).ready(function () {
    $.ajax({
        type: "get",
        url: "/DataParseSystem/getSource/getSourceList",
        async: false,
        dataType: 'json',
        success: function (data) {
            var head = '<div class="con pad-normal" id="fp-products"><div class="rw pad-b-slight">';
            $.each(data, function (i, item) {
                head = head + '<div class="col-1-of-2 pad-tb-slight"><div class="fp-product-icon"><img id="productImg"src="' + item.image + '"/></div><div class="fp-product">' +
                    '<div class="fp-product"><h2>' + item.name + '</h2><p>' + item.description + '<br /> <a href="' + item.url + '"><i class="fa fa-download"></i>&nbsp;立即下载</a></p>' +
                    '</div></div></div>'
            });
            var final = head + "</div></div>";
            $("#softWareDownload").append(final);
        }
    });
})