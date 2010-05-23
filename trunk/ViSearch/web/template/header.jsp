<%-- 
    Document   : header
    Created on : May 22, 2010, 4:09:09 PM
    Author     : tuandom, vinhpham
--%>


<head>
 <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Trang ch? - ViSearch</title>
<link href="style.css"rel="stylesheet" type="text/css" />
<script language="javascript">
function setText()
{
	document.getElementById('txtSearch').focus();
}
function CheckInput()
            {
                var keysearch = document.getElementById('txtSearch').value;
                if(keysearch == "")
                    return;
                else
                {
                    var url = "RaoVatController?type=0&KeySearch=";
                    //url += keysearch.value;
                    url += encodeURIComponent(keysearch);
                    //alert(url);
                    window.location = url;
                }
            }
</script>

</head>

    <body onload="setText();">

<div id="wrap_left" align="center">
<div id="wrap_right">
  <table id="wrap" width="974" border="0" cellpadding="0" cellspacing="0">

	<tr><td height="20" colspan="2" align="center" valign="middle"></td></tr>
    <tr>
      <td height="130" colspan="2" valign="top">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td width="974" valign="top">
