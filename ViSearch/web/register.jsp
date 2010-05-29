<%-- 
    Document   : register
    Created on : May 29, 2010, 8:44:56 PM
    Author     : tuandom
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title>ViSearch - Wikipedia</title>
        <link href="style.css"rel="stylesheet" type="text/css" />
        <script language="javascript">
            function setText()
            {
                var keysearch = document.getElementById('txtSearch').value;
                if(keysearch=="")
                    document.getElementById('txtSearch').focus();
            }
            function CheckInput()
            {
                var keysearch = document.getElementById('txtSearch').value;
                if(keysearch == "")
                    return;
                else
                {
                    var url = "SearchWikiController?type=0&sp=1&KeySearch=";
                    //url += keysearch.value;
                    url += encodeURIComponent(keysearch);
                    //alert(url);
                    window.location = url;
                }
            }
        </script>
    </head>

    <body onLoad="setText();">
       
        
        
        <div id="wrap_left" align="center">
            <div id="wrap_right">
                <table id="wrap" width="974" border="0" cellpadding="0" cellspacing="0">

                    <tr><td height="20" colspan="2" align="center" valign="middle"></td></tr>
                    <tr>
                        <td height="130" colspan="2" valign="top">
                            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                <tr>
                                    <td width="974" valign="top">
                                         <% String strQuery ="";%>
                                        <!-- banner here !-->
                                        <%@ include file="template/banner_Wiki.jsp"%>
                                        <!-- end banner      !-->
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr><td height="20" colspan="2" align="center" valign="bottom"><div align="center" class="nav"></div></td></tr>
                 
             <tr>
      <td colspan="2" height="33" valign="top">
      <!-- Start form register -->
      <div id="frmRegister">
      <h2 class="blockhead"> Ghi danh với ViSearch</h2>
      <h3 class="subblockhead"> Thông tin bắt buộc</h3>

      <table style="font-size: 13px" border="0" cellpadding="0" cellspacing="0">
 		  <tr><td>Ký danh: </td></tr>
          <tr><td><input type="text" size="60" class="textForm" onfocus="this.className='textForm_Hover';" onblur="this.className='textForm';" /></td>

          </tr>

          <tr><td height="15"></td></tr>

           <tr><td>Mật mã: </td></tr>
          <tr>
            <td><input type="password" class="textForm" onfocus="this.className='textForm_Hover';" onblur="this.className='textForm';"/></td>
          </tr>
           <tr><td>Nhắc lại mật mã: </td></tr>
          <tr><td><input type="password" class="textForm" onfocus="this.className='textForm_Hover';" onblur="this.className='textForm';"/></td></tr>

 		<tr><td height="15"></td></tr>

		 <tr><td>Địa chỉ Email: </td></tr>
          <tr><td><input type="text" size="40" class="textForm" onfocus="this.className='textForm_Hover';" onblur="this.className='textForm';"/></td></tr>
           <tr><td>Nhắc lại Địa chỉ Email: </td></tr>
          <tr><td><input type="text" size="40" class="textForm" onfocus="this.className='textForm_Hover';" onblur="this.className='textForm';"/></td></tr>

           <tr><td height="15"></td></tr>
           <tr><td>Giới tính: </td></tr>
          <tr><td><input type="checkbox" checked="checked" class="textForm"/>Nam
          			<input type="checkbox" class="textForm"/>Nữ
          </td></tr>
           <tr><td height="15"></td></tr>
           <tr><td>Ngày sinh: </td></tr>
           <tr><td>
           <input type="text" class="textForm" size="10px" onfocus="this.className='textForm_Hover';" onblur="this.className='textForm';"/>
           <select>
           <option value="1">Tháng 1</option>
           <option value="2">Tháng 2</option>
           <option value="3">Tháng 3</option>
           <option value="4">Tháng 4</option>
           <option value="5">Tháng 5</option>
           <option value="6">Tháng 6</option>
           <option value="7">Tháng 7</option>
           <option value="8">Tháng 8</option>
           <option value="9">Tháng 9</option>
           <option value="10">Tháng 10</option>
           <option value="11">Tháng 11</option>
           <option value="12">Tháng 12</option>

           </select>
           <input type="text" class="textForm" size="10px" onfocus="this.className='textForm_Hover';" onblur="this.className='textForm';"/>
           </td></tr>
          <tr><td height="15"></td></tr>

   	</table>
   	 <h2 class="blockhead">Quy định</h2>
      <h3 class="subblockhead">Để tiếp tục đăng kí, bạn phải đồng ý với những quy định sau:</h3>
      <div class="page" style="border:thin inset; padding:6px; height:175px; overflow:auto">
    <!-- regular forum rules -->
    <p><strong>Forum Rules</strong></p>
    <p>Registration to this forum is free! We do insist that you abide by the rules and policies detailed
      below. If you agree to the terms, please check the 'I agree' checkbox and press the 'Register' button below.
      If you would like to cancel the registration, click <a href="index.php">here</a> to return to the forums index.</p>
    <p>Registration to this forum is free! We do insist that you abide by the rules and policies detailed
      below. If you agree to the terms, please check the 'I agree' checkbox and press the 'Register' button below.
      If you would like to cancel the registration, click <a href="index.php">here</a> to return to the forums index.</p>
    <p>Registration to this forum is free! We do insist that you abide by the rules and policies detailed
      below. If you agree to the terms, please check the 'I agree' checkbox and press the 'Register' button below.
      If you would like to cancel the registration, click <a href="index.php">here</a> to return to the forums index.</p>
    <font size=1>
      <p><b>Đăng ký vào DDTH.com hoàn toàn miễn phí nhưng bạn phải tuân theo các quy định của diễn đàn!</b> <br />
          <br />
        a. Mục đích của các quy định này là để giữ cho nội dung của Diễn dàn Tin học được tốt hơn, hữu ích hơn cho những người muốn học hỏi, trao đổi về CNTT. <br />
        <br />
        b. Tất cả các thành viên của DDTH đều phải tuân thủ những quy định sau. Sân chơi nào cũng có luật chơi riêng của nó và nếu bạn không thích luật chơi thì có quyền không tham gia. <br />
        <br />
        c. Những thành viên nào vi phạm quy định sẽ được Administrator hoặc các Moderators nhắc nhở lần đầu tiên bằng tin nhắn cá nhân hoặc nhắc nhở ngay trong bài viết vi phạm. Nếu thành viên đó không sửa lại cho phù hợp hoặc không có lý do chính đáng thì bài viết sẽ được biên tập lại (nếu nội dung hay), hoặc sẽ bị xóa, users đó sẽ bị loại khỏi diễn đàn. Trong trường hợp cần thiết, Administrator hoặc Moderators có quyền biên tập mà không cần phải thông báo trước. <br />
        <br />
        d. Các quy định này sẽ được bổ sung, thay đổi cho phù hợp với sự phát triển của diễn đàn. Khi quy định được thay đổi thì Administrator sẽ gửi e-mail đến tất cả mọi người để thông báo. <br />
        <br />
        <b>Và sau đây là những quy định mà các bạn cần phải nhớ kỹ khi tham gia DDTH.com :</b> <br />
        <br />
        1. Diễn đàn không chấp nhận bất cứ thông tin nào đi ngược lại với thuần phong mỹ tục và truyền thống văn hoá của nước Việt Nam, không chấp nhận bất cứ chủ đề thảo luận hoặc có ý gây hiểu lầm về chính trị, vi phạm luật pháp của nhà nước CHXHCN Việt Nam. Mọi bài viết có nội dung hoặc chứa liên kết đến các trang web có nội dung vi phạm những điều trên đều sẽ được xóa hoặc biên tập mà không cần thông báo trước. <br />
        <br />
        2. Thành viên không được phép spam bằng những hình thức sau : Gửi nhiều bài viết cùng nội dung hoặc có nội dung tương tự nhau trong một hoặc nhiều chuyên mục (gửi 3 bài trở sẽ bị xóa); Gửi nhiều bài viết không có ý nghĩa hoặc không phù hợp với chủ đề đang tham gia. <br />
        <br />
        3. Thành viên sẽ phải chịu trách nhiệm về nội dung do mình gửi lên. Ban điều hành DDTH.com sẽ không chịu trách nhiệm về bất kỳ nội dung nào do thành viên gửi lên. Mọi khiếu nại về nội dung bài viết xin gửi về cho Ban Điều Hành, chúng tôi sẽ giải quyết tùy theo trường hợp. <br />
        <br />
        4. Không được gửi các bài viết <b>KHONG CO DAU TIENG VIET</b> hoặc <b>TOÀN BẰNG CHỮ HOA</b>. Không được gửi các bài viết không liên quan đến chuyên mục hoặc chủ đề đang tham gia. Không được gửi các bài viết không có tiêu đề rõ ràng hoặc không giống ý với nội dung bên trong. <br />
        <br />
        5. Những thành viên cố tình vi phạm hoặc vi phạm nhiều lần quy định của DDTH.com sẽ bị loại ra khỏi diễn đàn.</p>
      </font>
    <!-- regular forum rules -->
  </div>
      <br />
                        <input type="checkbox"  />Tôi đã đọc và đồng ý với những quy định của ViSearch
                                    <br />            <br />
                          <div align="center">
                        <input type="button" value="Ghi danh hoàn tất" />
                        <input type="button" value="Hủy bỏ tất cả" />
                        </div>

    </div>
<!-- End form register -->

      </td>

    </tr>



    <tr height="30"><td></td><td width="743"></td>
    </tr>
                    
                    <tr><td height="20" colspan="2" align="center" valign="bottom">

                            <div align="center" class="nav"></div></td></tr>
                    <tr>
                        <td width="200"></td>
                        <td colspan="2" valign="top">
                            <%@include file="template/footer.jsp"%>
                        </td>
                    </tr>
                </table>
            </div>
        </div>

    </body>
</html>


