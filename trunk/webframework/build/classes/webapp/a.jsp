<%-- 
    Document   : newjsf
    Created on : Oct 10, 2012, 10:25:35 AM
    Author     : root
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>



<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">


<html>
    <head>
        <title>Nghĩ Nhanh</title>
        <link href="http://localhost:81/rds_files/a_data/style.css" media="screen" rel="stylesheet" type="text/css">
        <link href="http://localhost:81/rds_files/a_data/boxy.css" media="screen" rel="stylesheet" type="text/css">
        <link href="http://localhost:81/rds_files/a_data/light_box.css" media="screen" rel="stylesheet" type="text/css">

        <!--  <script type="text/javascript" src="http://localhost:81/rds_files/a_data/jquery_003.js"></script>
          <script type="text/javascript" src="http://localhost:81/rds_files/a_data/zm.js"></script>
          <script type="text/javascript" src="http://localhost:81/rds_files/a_data/jquery_002.js"></script>
          <script type="text/javascript" src="http://localhost:81/rds_files/a_data/scripts.js"></script>
          <script type="text/javascript" src="http://localhost:81/rds_files/a_data/jquery.js"></script>
          <script type="text/javascript" src="http://localhost:81/rds_files/a_data/zmCore-1.js"></script>
          <script type="text/javascript" src="http://localhost:81/rds_files/a_data/zmwg-3.js"></script>
          
        -->
        <script type="text/javascript" src="http://code.jquery.com/jquery-1.8.2.js"></script>
        <script type="text/javascript" src="http://json-sans-eval.googlecode.com/svn/trunk/src/json_sans_eval.js"></script>
        <script type="text/javascript" src="http://fresher2012.dev/cachingfolder/listtags.js"></script>



    </head>
    <body>
        <div class="wrapper">
            <div style="width:820px;">
                <div style="height:30px; text-align:left;">
                    <a href="/index"><img alt="logo" style="border:0px; padding:0px; margin:0px;" src="http://localhost:81/rds_files/a_data/status_shuffle_header.png" align="absmiddle"></a>
                    <span style=" color:#4b6394; font-size:11px">“ Hãy chọn 1 câu ngẫu nhiên mà bạn thích để đăng lên tường nhà bạn.” </span>
                    <div class="add_faq"><a href="http://me.zing.vn/apps/hoidap?params=category/detail/id/509" target="_blank"><img src="http://localhost:81/rds_files/a_data/but_hoidap.gif" alt="Hỏi đáp"></a></div>
                </div>
                <div class="menu">
                    <div class="tl"></div>
                    <div class="tr"></div>
                    <div class="menu_fo_left_select"><a href="/index">Bạn nghĩ gì ?</a></div>
                    <div class="menu_fo"><a href="/favorites">Ưa thích</a></div>
                </div>                    <div class="shuffle_area">
                    <div>
                        <div class="t">

                            <div class="l"><img alt="" src="http://localhost:81/rds_files/a_data/space.gif" align="absmiddle" border="0"></div>
                            <div class="r"><img alt="" src="http://localhost:81/rds_files/a_data/space.gif" align="absmiddle" border="0"></div>
                        </div>
                        <div class="m">
                            <div style="display:block">
                                <div class="vote">
                                    <div id="voting-buttons">
                                        <div class="vote_t"><a id="btn-like" href="#"></a></div>
                                        <div class="vote_f"><a id="btn-dislike" href="#"></a></div>

                                    </div>
                                    <div style="display: none;" id="voting-feedback">
                                        <div class="voting-ok">

                                        </div>
                                    </div>
                                </div>
                                <div class="content">
                                    <p id="current-tag">Đang hiển thị những status về : <b></b> <a href="#">[x]</a></p>

                                    <div class="t"></div>
                                    <div class="c">
                                        <div style="padding:0 10px;">
                                            <span class="username"><span rel_f="1" rel="ZMED_25174256?id=1&amp;l=0&amp;vi=0" id="rds_uname_25174256"><span href="#" title="Lê Trung Chánh" k="d.i.25174256" l="0" c="" n="" cnt="" vi="0" id="wgdrds_uname_25174256" m="0">Lê Trung Chánh</span></span> ...</span>
                                            <span style="-moz-user-select: none;" id="status">I
                                                don't care what people think, I know the truth that's all that matters.
                                                Anyone who knows me has supported me, those who didn't are not worth 
                                                worrying about.</span>
                                            <script type="text/javascript">	
                                                zwg.addItem("rds_uname_" + 25174256  ,"ZMED_" + 25174256 + "?id=1&l=0&vi=0");
                                                zwg.fillWg();                                                
                                            </script>
                                        </div>

                                    </div>
                                    <div class="b" id="status-tags"><b>Từ khóa :</b> <span><a href="#" rel="0">life</a> </span></div>

                                </div>
                            </div>
                            <div style="clear:both;"></div>
                            <div style="margin-top:10px;">
                                <div class="button_shuffle"><a id="btn-shuffle" href="#"></a></div>
                                <div class="button_back_dis"><a id="btn-back" href="#"></a></div>
                                <div class="button_save"><a id="btn-fav" href="#"></a></div>
                                <div class="button_dang"><a id="btn-set" href="#"></a></div>

                                <div class="button_friend">
                                </div>
                            </div>
                            <div style="clear:both;"></div>
                        </div>
                        <div class="b">
                            <div class="l"><img alt="" src="http://localhost:81/rds_files/a_data/space.gif" align="absmiddle" border="0"></div>
                            <div class="r"><img alt="" src="http://localhost:81/rds_files/a_data/space.gif" align="absmiddle" border="0"></div>
                        </div>

                    </div>
                </div>
                <div style="padding-top:10px; width:820px;">
                    <div class="toptag">
                        <div class="l"><img alt="" src="http://localhost:81/rds_files/a_data/space.gif" align="absmiddle" border="0"></div>
                        <div class="r"><img alt="" src="http://localhost:81/rds_files/a_data/space.gif" align="absmiddle" border="0"></div>
                        <h2>Từ khóa nổi bật</h2>
                        <div class="xemtatca"><a href="http://rds2.apps.zing.vn/api/tag">Xem tất cả</a></div>
                    </div>
                    <div id="tags" class="toptag_con">
                        <a class="wc2" href="#" rel="49">Tiền</a> <a class="wc0" href="#" rel="14">break up</a> <a class="wc12" href="#" rel="3">châm biếm</a> <a class="wc11" href="#" rel="48">châm ngôn sống</a> <a class="wc13" href="#" rel="9">cuộc sống</a> <a class="wc0" href="#" rel="16">failure</a></div>
                </div>


                <div class="popular">
                    <h3>★ Nổi bật ★</h3><br>
                    <div class="popular-data">
                        <table border="0" cellpadding="0" cellspacing="0" width="100%">
                            <tbody><tr>
                                    <td class="title" width="84%">Nội dung</td>
                                    <td class="title" width="16%">Quản lý</td>
                                </tr>                                                        
                                <tr>
                                    <td>
                                        Hãy giữ mãi nụ cười trên môi nhé em!
                                        Em có biết là, em rất dễ thương khi em cười không?                     
                                    </td>
                                    <td><a onclick="return showPopup(1170, 'Hãy giữ mãi nụ cười trên môi nhé em! Em có biết là, em rất dễ thương khi em cười không?');" href="#">Đăng lên tường</a> | <a class="fav-status" rel="1170" href="#">Lưu</a></td>
                                </tr>
                                <tr>

                                    <td>
                                        Xin chúc mọi người, Sức khoẻ dồi 
                                        dào, Ngày một thêm cao, Không còn xanh xao, ốm đau bệnh tật.            
                                    </td>
                                    <td><a onclick="return showPopup(1162, 'Xin chúc mọi người, Sức khoẻ dồi dào, Ngày một thêm cao, Không còn xanh xao, ốm đau bệnh tật.');" href="#">Đăng lên tường</a> | <a class="fav-status" rel="1162" href="#">Lưu</a></td>
                                </tr>

                            </tbody></table>
                    </div>
                    <br>
                </div>

                <div id="friendlist-box">
                    <div class="contai_popup">
                        <div class="contain_pad">
                            <div style="padding: 10px 10px 0px;">
                                <table class="wlb_height" border="0" cellpadding="0" cellspacing="0">
                                    <tbody><tr>
                                            <td>
                                                <div class="cntBlog pd_lightbox">
                                                    <div class="blog_tag">
                                                        <div class="box_search">
                                                            <p class="titleSearch"><strong>Tìm kiếm:</strong></p>
                                                            <div class="search">
                                                                <input class="icon_search" value=" " type="button">
                                                                <input name="friend_kw" class="input" type="text">
                                                            </div>

                                                        </div>
                                                        <div class="info_tag">
                                                            <!-- <ul></ul>-->
                                                            <div class="fTag">
                                                                <div class="fTag_l">
                                                                    <a href="#" id="friend_update"><img src="http://localhost:81/rds_files/a_data/blank.gif" alt="" height="20" width="103"></a>            </div>
                                                                <div class="fTag_r"><a id="friend_pre" class="btnLeft" href="#"><img src="http://localhost:81/rds_files/a_data/blank.gif" alt=""></a><a class="btnRight" id="friend_next" href="#"><img style="margin-right:0" src="http://localhost:81/rds_files/a_data/blank.gif" alt=""></a></div>
                                                            </div>

                                                            <br class="clr">
                                                        </div>
                                                    </div>
                                                </div>
                                            </td>
                                        </tr>
                                    </tbody></table>
                            </div>
                            <div class="clear">

                            </div>
                        </div>
                        <div align="center"></div>
                    </div>
                    <div class="clear"></div>
                    <div class="bottom_popup">
                        <table align="right" cellpadding="3" cellspacing="0">
                            <tbody>
                                <tr>

                                    <td>
                                        <div class="buttunblue" id="dv_action_add" style="display: block;">
                                            <a href="#" onclick="return postStatus(true);">Đăng tải</a>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="buttungray">
                                            <a href="#" onclick="return Boxy.get(this).hide();">Đóng</a>

                                        </div>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>

        <div id="popup-box">
            <div class="contai_popup">
                <div class="contain_pad">
                    <div style="padding: 10px;">
                        <div style="float: left;">
                            <img alt="Lê Trung Chánh" title="Lê Trung Chánh" src="rds_files/chanhlt190290_50_31.jpg" align="absmiddle" border="0" width="50">
                        </div>
                        <div style="float: left; margin-left: 10px;">
                            <div style="height: 20px; font-size: 12px;"><b>Bạn đang nghĩ gì?</b></div>

                            <div>
                                <textarea rows="5" cols="" id="status-text" style="width: 500px; border: 1px solid rgb(189, 199, 222); overflow: hidden;"></textarea>
                                <input id="status-id" type="hidden">
                            </div>
                        </div>
                    </div>
                    <div class="clear">
                    </div>

                </div>
                <div align="center"></div>
            </div>
            <div class="clear"></div>
            <div class="bottom_popup">
                <table align="right" cellpadding="3" cellspacing="0">
                    <tbody>
                        <tr>
                            <td>

                                <div class="buttunblue" id="dv_action_add" style="display: block;">
                                    <a href="#" onclick="return postStatus();">Đăng tải</a>
                                </div>
                            </td>
                            <td>
                                <div class="buttungray">
                                    <a href="#" onclick="return closePopup();">Đóng</a>
                                </div>

                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>

        <script type="text/javascript">
            //if (typeof window.parent != "undefined" && typeof window.frameElement != "undefined") {
            zmApps.resizeFrame('rds', $(document).height());
            //}
        </script><iframe src="http://localhost:81/rds_files/a_data/rs_proxy-1.html" style="height: 0px; width: 0px; top: -999px; visibility: hidden;"></iframe>

        <script type="text/javascript">
            var gaJsHost = (("https:" == document.location.protocol) ? "https://ssl." : "http://www.");
            document.write(unescape("%3Cscript src='" + gaJsHost + "google-analytics.com/ga.js' type='text/javascript'%3E%3C/script%3E"));
        </script><script src="rds_files/ga.js" type="text/javascript"></script>


        <script type="text/javascript">
            var secondTracker = _gat._getTracker("UA-4718926-24");
            secondTracker._initData();
            secondTracker._trackPageview();
        </script>


    </body></html>
