var binSummary = new Array();
var binSet = new Set();
function gradientColor(startColor, endColor, step) {
    startRGB = this.colorRgb(startColor);
    startR = startRGB[0];
    startG = startRGB[1];
    startB = startRGB[2];

    endRGB = this.colorRgb(endColor);
    endR = endRGB[0];
    endG = endRGB[1];
    endB = endRGB[2];

    sR = (endR - startR) / step;
    sG = (endG - startG) / step;
    sB = (endB - startB) / step;

    var colorArr = [];
    for (var i = 0; i < step; i++) {
        var hex = this.colorHex('rgb('+ parseInt((sR * i + startR))+ ',' + parseInt((sG * i + startG))+ ',' + parseInt((sB * i + startB)) + ')');
        colorArr.push(hex);
    }
    return colorArr;
}
gradientColor.prototype.colorRgb = function (sColor) {
    var reg = /^#([0-9a-fA-f]{3}|[0-9a-fA-f]{6})$/;
    var sColor = sColor.toLowerCase();
    if (sColor && reg.test(sColor)) {
        if (sColor.length === 4) {
            var sColorNew = "#";
            for (var i = 1; i < 4; i += 1) {
                sColorNew += sColor.slice(i, i + 1).concat(sColor.slice(i, i + 1));
            }
            sColor = sColorNew;
        }
        var sColorChange = [];
        for (var i = 1; i < 7; i += 2) {
            sColorChange.push(parseInt("0x" + sColor.slice(i, i + 2)));
        }
        return sColorChange;
    } else {
        return sColor;
    }
};
gradientColor.prototype.colorHex = function (rgb) {
    var _this = rgb;
    var reg = /^#([0-9a-fA-f]{3}|[0-9a-fA-f]{6})$/;
    if (/^(rgb|RGB)/.test(_this)) {
        var aColor = _this.replace(/(?:(|)|rgb|RGB)*!/g, "").split(",");
        var strHex = "#";
        for (var i = 0; i < aColor.length; i++) {
            var hex = Number(aColor[i]).toString(16);
            hex = hex < 10 ? 0 + '' + hex : hex;// 保证每个rgb的值为2位
            if (hex === "0") {
                hex += hex;
            }
            strHex += hex;
        }
        if (strHex.length !== 7) {
            strHex = _this;
        }
        return strHex;
    } else if (reg.test(_this)) {
        var aNum = _this.replace(/#/, "").split("");
        if (aNum.length === 6) {
            return _this;
        } else if (aNum.length === 3) {
            var numHex = "#";
            for (var i = 0; i < aNum.length; i += 1) {
                numHex += (aNum[i] + aNum[i]);
            }
            return numHex;
        }
    } else {
        return _this;
    }
}
var compare = function (prop) {
    return function (obj1, obj2) {
        var val1 = obj1[prop];
        var val2 = obj2[prop];if (val1 < val2) {
            return -1;
        } else if (val1 > val2) {
            return 1;
        } else {
            return 0;
        }
    }
}
var value=new Set();
var gradient = new gradientColor('#ADFF2F', '#FF0000',26);
var url=window.location.search;
$.ajax({
    type:"get",
    url:"/DataParseSystem/getWaferInfor/getSuperPosition"+url,
    async:false,
    dataType: 'json',
    success:function(data){
        var transferData=data.sort(compare("v"));
        $.each(transferData, function(i,item) {
            var dieInforObj={
                x:item.x,
                y:item.y,
                v:item.v,
            };
            if(!binSet.has(item.v)) {
                binSet.add(item.v);
                var binInfo = new Object();
                binInfo.animation = false;
                binInfo.turboThreshold =0;
                binInfo.name = "Num:"+item.v;
                var marker = new Object();
                marker.symbol = 'square';
                for(var k=0;k<26;k++){
                    if(k==item.v){
                        binInfo.color=gradient[k];
                    }
                }
                binInfo.marker = marker;
                binInfo.data = new Array();
                binInfo.data.push(dieInforObj);
                binSummary.push(binInfo);
            } else {
                for(var i = 0; i < binSummary.length; i++) {
                    if(binSummary[i].name === "Num:"+item.v) {
                        binSummary[i].data.push(dieInforObj);
                    }
                }
            }
        });
    }
});
var chart = Highcharts.chart('containers', {
    chart: {
        renderTo: 'containers',
        type: 'scatter',
        plotBorderWidth: 1,
        zoomType: 'xy'
    },
    legend: {
        layout: 'vertical',
        align: 'left',
        verticalAlign: 'top',
        x: 70,
        y: 70,
        floating: true,
        backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF',
        borderWidth: 1
    },
    title: {
        text: 'Rawdata生成Mapping图'
    },
    subtitle: {
        text: '数据来源:/192.168.10.212/RawData'
    },
    xAxis: {
        title: {
            enabled: true,
            text: 'x',
        },
        startOnTick: true,
        endOnTick: true,
        showLastLabel: true
    },
    yAxis: {
        title: {
            text: 'y'
        }
    },
    tooltip: {
        useHTML: true,
        headerFormat: '<table>',
        pointFormat: '<tr><th colspan="2"><h3>{point.v}</h3></th></tr>' +
            '<tr><th>X:</th><td>{point.x}</td></tr>' +
            '<tr><th>Y:</th><td>{point.y}</td></tr>' ,
        footerFormat: '</table>',
        followPointer: true
    },
    series: binSummary
});