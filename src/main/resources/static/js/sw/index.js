var dataSet = [
    [ "Tiger Nixon", "System Architect", "Edinburgh", "5421", "2011/04/25", "$320,800" ],
    [ "Garrett Winters", "Accountant", "Tokyo", "8422", "2011/07/25", "$170,750" ],
    [ "Ashton Cox", "Junior Technical Author", "San Francisco", "1562", "2009/01/12", "$86,000" ],
    [ "Cedric Kelly", "Senior Javascript Developer", "Edinburgh", "6224", "2012/03/29", "$433,060" ],
    [ "Airi Satou", "Accountant", "Tokyo", "5407", "2008/11/28", "$162,700" ],
    [ "Brielle Williamson", "Integration Specialist", "New York", "4804", "2012/12/02", "$372,000" ],
    [ "Herrod Chandler", "Sales Assistant", "San Francisco", "9608", "2012/08/06", "$137,500" ],
    [ "Rhona Davidson", "Integration Specialist", "Tokyo", "6200", "2010/10/14", "$327,900" ],
    [ "Colleen Hurst", "Javascript Developer", "San Francisco", "2360", "2009/09/15", "$205,500" ],
    [ "Sonya Frost", "Software Engineer", "Edinburgh", "1667", "2008/12/13", "$103,600" ],
    [ "Jena Gaines", "Office Manager", "London", "3814", "2008/12/19", "$90,560" ],
    [ "Quinn Flynn", "Support Lead", "Edinburgh", "9497", "2013/03/03", "$342,000" ],
    [ "Charde Marshall", "Regional Director", "San Francisco", "6741", "2008/10/16", "$470,600" ],
    [ "Haley Kennedy", "Senior Marketing Designer", "London", "3597", "2012/12/18", "$313,500" ],
    [ "Tatyana Fitzpatrick", "Regional Director", "London", "1965", "2010/03/17", "$385,750" ],
    [ "Michael Silva", "Marketing Designer", "London", "1581", "2012/11/27", "$198,500" ],
    [ "Paul Byrd", "Chief Financial Officer (CFO)", "New York", "3059", "2010/06/09", "$725,000" ],
    [ "Gloria Little", "Systems Administrator", "New York", "1721", "2009/04/10", "$237,500" ],
    [ "Bradley Greer", "Software Engineer", "London", "2558", "2012/10/13", "$132,000" ],
    [ "Dai Rios", "Personnel Lead", "Edinburgh", "2290", "2012/09/26", "$217,500" ],
    [ "Jenette Caldwell", "Development Lead", "New York", "1937", "2011/09/03", "$345,000" ],
    [ "Yuri Berry", "Chief Marketing Officer (CMO)", "New York", "6154", "2009/06/25", "$675,000" ],
    [ "Caesar Vance", "Pre-Sales Support", "New York", "8330", "2011/12/12", "$106,450" ],
    [ "Doris Wilder", "Sales Assistant", "Sidney", "3023", "2010/09/20", "$85,600" ],
    [ "Angelica Ramos", "Chief Executive Officer (CEO)", "London", "5797", "2009/10/09", "$1,200,000" ],
    [ "Gavin Joyce", "Developer", "Edinburgh", "8822", "2010/12/22", "$92,575" ],
    [ "Jennifer Chang", "Regional Director", "Singapore", "9239", "2010/11/14", "$357,650" ],
    [ "Brenden Wagner", "Software Engineer", "San Francisco", "1314", "2011/06/07", "$206,850" ],
    [ "Fiona Green", "Chief Operating Officer (COO)", "San Francisco", "2947", "2010/03/11", "$850,000" ],
    [ "Shou Itou", "Regional Marketing", "Tokyo", "8899", "2011/08/14", "$163,000" ],
    [ "Michelle House", "Integration Specialist", "Sidney", "2769", "2011/06/02", "$95,400" ],
    [ "Suki Burks", "Developer", "London", "6832", "2009/10/22", "$114,500" ],
    [ "Prescott Bartlett", "Technical Author", "London", "3606", "2011/05/07", "$145,000" ],
    [ "Gavin Cortez", "Team Leader", "San Francisco", "2860", "2008/10/26", "$235,500" ],
    [ "Martena Mccray", "Post-Sales support", "Edinburgh", "8240", "2011/03/09", "$324,050" ],
    [ "Unity Butler", "Marketing Designer", "San Francisco", "5384", "2009/12/09", "$85,675" ]
];

var dataSet1 = [
            {"StationId":"0001",},
            {"StationId":"0002"},
            {"StationId":"0003"}
            ];
 




$(document).ready( function () {
        var tables=$('#table_id').DataTable({
            "oLanguage": { // 表格的语言设置
                "sProcessing": "正在获取数据，请稍后...",
                
                "sLengthMenu": "显示 _MENU_ 条",
                "sZeroRecords": "没有您要搜索的内容",
                "sInfo": " 总记录数为 _TOTAL_ 条",
                "sInfoEmpty": "记录数为0",
                "sInfoFiltered": "(全部记录数 _MAX_ 条)",
                "sInfoPostFix": "",
                "sSearch": "搜索",
                "sUrl": "",
                "oPaginate": {
                    "sFirst": "第一页",
                    "sPrevious": "上一页",
                    "sNext": "下一页",
                    "sLast": "最后一页"
                }
            },
            "select": true,
            "bProcessing" : true, // DataTables载入数据时，是否显示‘进度’提示
            // "serverSide": true,
            "autoWidth":false,
            // "sScrollY" : 350, //DataTables的高
            "sPaginationType" : "full_numbers", // 详细分页组，可以支持直接跳转到某页
            "iDisplayStart": 0,// 刷新插件后显示的第几页（如果一页为10条数据，填10就显示第二页）
//            "ajax":{
//                "url":"url地址"// 输入url地址
//            },
			data: dataSet1,
            columns: [
           				 { "title": "站号","data": "StationId"},
            			 { title: "测量时间" },
            			 { title: "水温" },
            		     { title: "气温" },
                         { title: "报送类型" },
                         { title: "传输信道" },
                         { title: "录入时间" }
        		],
//            'bStateSave': true,// 配置好这个,刷新页面会让页面停留在之前的页码
//            "columnDefs": [{
//                "targets": 6,// 编辑
//                "data": null,// 下面这行，添加了编辑按钮和，删除按钮
//                "defaultContent": "<button id='editrow' class='btn btn-primary' type='button' style='margin-right:10px;'>编辑</button><button id='delrow' class='btn btn-primary' type='button'>删除</button>"
//            }],
//            "createdRow": function( row, data, index ) {
//                // 每加载完一行的回调函数
//　　　　　　　　　　$('td', row).eq(5).css('font-weight',"bold").css("color","red");// 获取到具体行具体格的元素
//
//            }
        });
      //设置行可以选择
        $('#table_id').on('click', 'tr', function () {
            if ($(this).hasClass('selected') ) {
               $(this).removeClass('selected');
            } else {
            	tables.$('tr.selected').removeClass('selected');
               $(this).addClass('selected');
            }
        });
        
        tables.$("tr:even").css("background-color", "#BAFFF8");
        tables.$("tr:odd").css("background-color", "#FAF9F8");
        
//        $("tr:even ").css("background-color", "#BAFFF8");
//        $("tr:odd ").css("background-color", "#FAF9F8");
        
//        //设置可以多行选中
//        $('#table_id').on('click', 'tr', function () {
//            $(this).toggleClass('selected');
//        });
        
//        $('#table_id tbody').on( 'click', 'button#delrow', function () {
//            var data = tables.row( $(this).parents('tr') ).data();
//            // tables.ajax.reload();重新获取数据
//            // tables.draw(false);重新刷新页面
//            if(confirm("是否确认删除这条信息")){
//                $.ajax({
//                    url:'http://10.10.1.183:8080/crm/enterprise/'+data.id,
//                    type:'delete',
//                    timeout:"3000",
//                    cache:"false",
//                    success:function(str){
//                        if(str.data){
//                            tables.row().remove();// 删除这行的数据
//                            window.location.reload();// 重新刷新页面，还有一种方式：tables.draw(false);(这是不刷新，重新初始化插件，但是做删除时候，老有问题)
//                        }
//                    },
//                    error:function(err){
//                        alert("获取数据失败");
//                    }
//                });
//            }
//
//        });
//        $('#table_id tbody i').css({"fontStyle":"normal"});
//        $('#table_id tbody').on( 'click', 'button#editrow', function () {
//            // 获取数据
//            var data = tables.row( $(this).parents('tr') ).data();
//            // 清空内容
//            $('.pop_clear_text input').val('');
//            // 弹出层展示
//            $('.pop').show();
//            // 填充内容
//            $('.pop_id').val(data.id);
//            $('.pop_name').val(data.name);
//            $('.pop_industry').val(data.industry);
//            $('.pop_source').val(data.source);
//            $('.pop_location').val(data.location);
//            $('.pop_serviceStatus').val(data.serviceStatus);
//        });
//        // 弹出层的功能
//        $('.pop_cancel,.pop_close').on("click",function(){
//            $('.pop').hide();
//        });
//        $('.pop_confirm').on('click',function(){
//            var n=parseInt($('.pop_id').val());
//            console.log(typeof(n));
//            $.ajax({
//                url:'http://10.10.1.183:8080/crm/enterprise',
//                type:'PUT',
//                contentType: "application/json",
//                timeout : "3000",
//                cache:false,
//                data: JSON.stringify({
//                    "id":n,
//                    "name":$('.pop_name').val(),
//                    "industry":$('.pop_industry').val(),
//                    "location":$('.pop_location').val(),
//                    "source":$('.pop_source').val(),
//                    "serviceStatus":$('.pop_serviceStatus').val()
//                }),
//                dataType: "json",
//                success:function(str){
//                    // 弹窗关闭
//                    $('.pop').hide();
//                    window.location.reload();
//                },
//                error:function(err){
//                    alert("数据刷新失败,请重新刷新");
//                }
//            });
//        });
//        // 添加事件
//        $('.table_list_add').on("click",function(){
//            // 先清空
//            $('.table_list_name').val('');
//            $('.table_list_industry').val('');
//            $('.table_list_source').val('');
//            $('.table_list_location').val('');
//            $('.table_list_serviceStatus').val('');
//            // 展示
//            $('.table_list').show();
//        });
//        // 增加弹窗的功能
//        $('.table_list_close,.table_list_cancel').on("click",function(){
//            $('.table_list').hide();
//        });
//        $('.table_list_confirm').on("click",function(){
//            $.ajax({
//                url:'http://10.10.1.183:8080/crm/enterprise',
//                type:'POST',
//                contentType: "application/json",
//                timeout : "3000",
//                cache:false,
//                data: JSON.stringify({
//                    "name":$('.table_list_name').val(),
//                    "industry":$('.table_list_industry').val(),
//                    "location":$('.table_list_location').val(),
//                    "source":$('.table_list_source').val(),
//                    "serviceStatus":$('.table_list_serviceStatus').val()
//                }),
//                dataType: "json",
//                success:function(str){
//                    // 弹窗关闭
//                    $('.table_list').hide();
//                    window.location.reload();
//                    $('#table_id_last').click();
//                },
//                error:function(err){
//                    alert("数据刷新失败,请重新刷新");
//                }
//            });
//        });
//        // 控制这个表格大小
//        $('#table_id_wrapper').css({"width":"1400px","margin":"20px auto"});
    } );
	
	
	
	