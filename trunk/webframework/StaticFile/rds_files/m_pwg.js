var pml={url:"/jpt/ajx",init:function(){var a="";0<zm("#photo_counter").length&&(a="&nguid="+zmConfig.viewerId);zm.browser.msie&&8>parseInt(zm.browser.version)&&(a+="&t="+(new Date).getTime());zm.get(pml.url+"//zme/initbox?vss=vs"+a,{dataType:"json"},pml.cml)},cml:function(a){pml.r=a;zm.addScript("http://stc.ugc.zdn.vn/ptr/zme/js/m_c."+a.v+".js")}};zm.ready(pml.init);