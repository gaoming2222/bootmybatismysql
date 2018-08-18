function InitExcelFile(service) {
 
    $("#excelFile").fileinput({
        uploadUrl: "/FileUpload/upload",//上传的地址
        uploadAsync: true,              //异步上传
        language: "zh",                 //设置语言
        showCaption: true,              //是否显示标题
        showUpload: true,               //是否显示上传按钮
        showRemove: true,               //是否显示移除按钮
        showPreview : true,             //是否显示预览按钮
        browseClass: "btn btn-primary", //按钮样式
        uploadLabel: "上传",           //设置上传按钮的汉字
        enctype: 'multipart/form-data',
        dropZoneEnabled: true,         //是否显示拖拽区域
        allowedFileExtensions: ["xls", "xlsx"], //接收的文件后缀
        maxFileCount: 1,                        //最大上传文件数限制
        previewFileIcon: '<i class="glyphicon glyphicon-file"></i>',
        allowedPreviewTypes: null,
        previewFileIconSettings: {
            'xls': '<i class="glyphicon glyphicon-file"></i>',
            'xlsx': '<i class="glyphicon glyphicon-file"></i>'
        },
        uploadExtraData: {
            service: service
        }
    }).on("fileuploaded", function(event, data) {
        var response=data.response;
        var html;
        if (response.code =='501') {
            html = "<div style='float:left;'><p style='text-align:left'>内容：  " + response.desp + "<a  href='/../../exportFile/"+ response.exportFnm + "'target='_blank'>详情下载。</a></p></div>"
        }else{
            html = "<div style='float:left;'><p style='text-align:left'>内容：  " + response.desp + "></p></div>"
        }
        $(html).appendTo($('#response'));
    }).on('fileerror', function(event, data, msg) {  //一个文件上传失败
        alert('文件上传失败！'+msg);
    });
}