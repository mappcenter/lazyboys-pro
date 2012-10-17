<%-- 
    Document   : testFeeds
    Created on : Oct 3, 2012, 10:11:15 AM
    Author     : root
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="appinfo.MyAppInfo" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script type="text/javascript" src="http://static.me.zing.vn/feeddialog/js/zmfeeddialog-2.01.min.js" ></script>
    </head>
    <body>
        <h1>Hello World!</h1>
        <h2 style: color="red" > Feed Dialog Example Live</h2>
        <a href="javascript:;" onclick="getDialog3()">Click here get template 3</a><br/>
		<a href="javascript:;" onclick="getDialog4()">Click here get template 4</a><br/>
		<a href="javascript:;" onclick="getDialog7()">Click here get template 7</a><br/>
    </body>
</html>
<%=MyAppInfo.getInstance().getAppName()%>
        <script type="text/javascript">

                function getDialog3(){
                 zmf.ui(
                {
                    pub_key:"2dd6f7f390c7c2ac5d8c70f243cecdcb",
                    sign_key:"ee89b21d46ec1b2b44ca784b12000ecb",
                    action_key: "created by app",
                    action_id:1,
                    uid_to: 2103912,
                    object_id: "",
                    attach_name: "Ca sĩ Quang Hà chuẩn bị kiện nhân chứng Vietnam Airlines",
                    attach_href: "http://vnexpress.net/gl/kinh-doanh/2011/05/ca-si-quang-ha-chuan-bi-kien-nhan-chung-vietnam-airlines/",
                    attach_caption: "vnexpress.net",
                    attach_des: "Nhận được tin nhắn xin lỗi được cho là của bà Eileen Tan - nhân chứng trong vụ HLV Taekwondo, nhưng ca sĩ Quang Hà vẫn quyết đưa việc này ra tòa.",
                    media_type:1,
                    media_img:"http://vnexpress.net/Files/Subject/3b/a2/99/39/quang-ha-250.jpg.thumb150x0.ns.jpg",
                    media_src:"http://vnexpress.net/gl/kinh-doanh/2011/05/ca-si-quang-ha-chuan-bi-kien-nhan-chung-vietnam-airlines/",
                    actlink_text:"Link",
                    actlink_href:"http://me.zing.vn/apps/link",
                    tpl_id:3,
                    suggestion: ["suggestion1", "suggestion2", "suggestion3"]
                });
                }

                //Dialog 4
                function getDialog4(){
                        zmf.ui(
                {
                                pub_key:"2dd6f7f390c7c2ac5d8c70f243cecdcb",
                                sign_key:"ee89b21d46ec1b2b44ca784b12000ecb",
                                action_id:1,
                                uid_to: 5037964,
                                object_id: "113|234",
                                attach_name: 'Nhà đầu tư bán tháo, giá vàng thế giới tụt dốc - VnExpress',
                                attach_href: "blank",
                                attach_caption: "vnexpress.net",
                                attach_des: "Giá vàng thế giới giảm gần 40 USD xuống dưới 1.480 USD mỗi ounce trong một phiên giao dịch hoảng loạn, sau khi thị trường dầu lửa và chứng khoán tụt dốc với biên độ lớn nhất kể từ 2009.",
                                media_type:1,
                                media_img:"",
                                media_src:"",
                                actlink_text:"từ link",
                                actlink_href:"me.zing.vn",
                                tpl_id:4,
                                comment:"hello",
                                suggestion: ["suggestion1", "suggestion2", "suggestion3"]
                });
                }


                function getDialog7(){
                    zmf.ui(
                    {
                        pub_key:"159c4a92ba596ed3de61b0678acf52a7",
                        sign_key:"9416f6d8a08913189ca218bcf8ff179e",
                        action_id:1,
                        uid_to: 487573,
                        object_id: "113|234",
                        attach_name: "YouTube - Việt Nam Idol 2010 - Cô nàng tự tin nhất VNidol 2010",
                        attach_href: "blacnk",
                        attach_caption: "http://www.youtube.com",
                        attach_des: "Việt Nam Idol 2010 - Cô nàng tự tin nhất VNidol 2010 lıllı ((((|̲̅̅●̲̅̅|̲̅̅=̲̅̅|̲̅̅●̲̅̅|)))) ıllı",
                        media_type:2,
                        media_img:"http://stc.quick-upload.zdn.vn/file_uploads/gallery/zingshare/2011/05/06/f/3/5/2/f352833439d504e965dfe818624901bd.jpg",
                        media_src:"http://www.youtube.com/watch?v=_5UsK-mNgiE&playnext=1&list=PLF66F466F51CCFAB4",
                        actlink_text:"từ link",
                        actlink_href:"me.zing.vn",
                        tpl_id:7,
                        comment:"hello",
                        suggestion: ["suggestion1", "suggestion2", "suggestion3"]
                    });
            }
        </script>
    </body>
</html>
