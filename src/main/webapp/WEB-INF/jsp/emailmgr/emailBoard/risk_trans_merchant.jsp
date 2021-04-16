<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>风控待处理</title>
<style type="text/css">
body{
	background:#f5f5f5;
	font: 14px "Arial",sans-serif;
}
#main{
	width:90%;
	margin:60px auto;
	padding:20px;
	border:1px solid #f2f1f1;
	border-radius: 5px;
	background:#fff;
	}
#transactions{
	font: 14px "Arial",sans-serif;
	line-height:25px;
    margin: 20px 10px 10px 0;  
}
table #transactions {
    clear: both;
    margin-bottom: 10px;
    text-align: left;
}
table {
    margin-bottom: 10px;
    border: 1px solid #DDD;
	width: 95%;
	border-collapse: collapse;
    border-spacing: 0;
}
td{
	border: 1px solid #ddd;
	padding:10px;
	}

</style>
</head>

<body>
   <div id="main">
    <h2>Dear ${merNo}---${terNo} Merchant:</h2>
    <p id="transactions">You have high risk pending transactions , processing limits within 24 hours .exceeded will be treated as failure , please check it timely .Our company suggest you after contacted with the customer first and conformed the status , then consider it should be submit to the bank or not .</p><br/>
    
    <table id="dataTable1" >
		<tbody>
        <tr>
		    <td colspan="2"><h4>Order information:</h4></td>
		</tr>
		<tr>
		    <td width="20%">Order No.:</td>
		    <td>${orderNo}</td>
		</tr>
		<tr>
	    	<td>Transaction No.:</td>
	    	<td>${tradeNo}</td>
		</tr>
		<tr>
		    <td>Transaction Time:</td>
		    <td>${transDate}</td>
		</tr>
		<tr>
		    <td>Amount:</td>
		    <td>${merBusCurrency} ${merTransAmount}</td>
		</tr>
		<tr>
	    	<td>blocking reason：</td>
		    <td>${doReason}</td>
		</tr>
		
		</tbody>
        </table>
        
      <h4>Note:</h4> 
      <ul>
          <li>If you want to know more information of the transaction, please login the system platform and check.</li><br/>
          <li>E-mail:<a href="#">service@fhtpay.hk</a></li>
      </ul>
      <p>Thank you!</p>
      
       <h4>Notice:Please don't reply this e-mail directly as it was sent automatically!</h4> 
    </div>
    
</body>
</html>
