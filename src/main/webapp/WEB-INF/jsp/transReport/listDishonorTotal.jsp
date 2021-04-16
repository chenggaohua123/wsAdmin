<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="funcs" uri="funcs"%> 
<%
	String path = request.getContextPath();
%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>交易统计</title>
</head>
<script type="text/javascript">
$(function($) {
	$.simpleCanleder = function(box, options){
		var _canlederBox = "#SimpleCanleder_Year_Month";
		var _title_ul_li = ".title li";
		box = $(box);
		var box_height = parseFloat( box.height());
		var box_width = parseFloat( box.width());
		var boxOffset = box.offset();

		var canlederBox = null;
		box.click(function(){
			canlederBox = $(_canlederBox);
			if($(canlederBox).size() > 0){
				$(canlederBox).show();
			}else{
				_buildCanlederBox();
				$("body").append(canlederBox);

                $(document).click(function(e){
                    var pointX = e.pageX;
                    var pointY = e.pageY;
                    var $box  = canlederBox.data("box");

                    var isCanlederBox = $(e.target).parents(_canlederBox);

                    if(canlederBox.is(":visible") && $box && e.target != $box[0] && isCanlederBox.size() <= 0){
                        var offset = canlederBox.offset();
                        var top  = offset.top - 4;
                        var left  = offset.left - 4;
                        var height = top + parseFloat(canlederBox.outerHeight()) +  4;
                        var width = left + parseFloat(canlederBox.outerWidth()) + 4;
                        if(pointX > left && pointY > top &&
                                pointX < width && pointY < height){

                        }else{
                            canlederBox.hide();
                        }
                    }
                });
			}

			
			canlederBox.css({"top" : boxOffset.top + box_height + 6, "left": boxOffset.left});
			canlederBox.data("box", box); 

			_init();
		
		}); 


		

		function _init(){
			var now = new Date();
			var year = now.getFullYear();
			var month = now.getMonth() + 1;
			if(box.val()){
				year = box.val().split("-")[0] * 1;
				month = box.val().split("-")[1] * 1;
			}

			canlederBox.find(_title_ul_li).eq(1).find("div.inner").html(_getSelect(year));
			canlederBox.find(".body li").each(function(){
				if($(this).text() == month){
					$(this).addClass("cur");
				}else{
					$(this).removeClass("cur");
				};
			});
		}

		function _buildCanlederBox(){
			canlederBox = $("<div/>");
			canlederBox.attr("id", "SimpleCanleder_Year_Month"); 
			
			_buildTitle(canlederBox);
			_buildBody(canlederBox);
			canlederBox.append($("<div/>").addClass("clear"));
			_buildBottom(canlederBox);
			
		};
		
		 
		function _buildTitle(canlederBox){
			var $title =  $("<div/>").addClass("title").append("<ul/>").appendTo(canlederBox);
			var $title_ul = $title.find("ul");
			for(var i = 0; i < 3; i++){
				var $li = $("<li/>").append( $("<div/>").addClass("inner") );
				
				$li.hover(function(){
					$(this).addClass("over");	
				}, function(){
					$(this).removeClass("over");
				});

				$title_ul.append($li);
			}
			var $title_ul_li = $title_ul.find("li");

			$title_ul_li.eq(0).click(function(){
				var year = $select.val();	//$select 在_getSelect()有定义
				canlederBox.find(_title_ul_li).eq(1).find("div.inner").html(_getSelect(--year));
			}).find("div.inner").text(" < ");

			$title_ul_li.eq(1).addClass("middle").click(function(){
				
			})
			.find("div.inner").addClass("paddingTop").html(_getSelect());

			$title_ul_li.eq(2).click(function(){
				var year = $select.val();	//$select 在_getSelect()有定义
				canlederBox.find(_title_ul_li).eq(1).find("div.inner").html(_getSelect(++year));
			}).find("div.inner").text(" > ");
		};

		function _buildBody(canlederBox){
			var $body =  $("<div/>").addClass("body").append("<ul/>").appendTo(canlederBox);
			var $body_ul = $body.find("ul");
			for(var i = 0; i < 12; i++){
				var $inner = $("<div/>").addClass("inner").text(i+1);
				var $li = $("<li/>").append($inner).click(function(){
					var year = canlederBox.find(_title_ul_li).eq(1).find("select").val();
					var month = $(this).find("div.inner").text() * 1;
					month = month < 10 ? "0" + month : month;
					canlederBox.data("box").val(year + "-" + month);
					canlederBox.hide();
				});
				
				$li.hover(function(){
					$(this).addClass("over");	
				}, function(){
					$(this).removeClass("over");
				});

				$body_ul.append($li);
			}
		};

		function _buildBottom(canlederBox){
			var $button_clear = $("<button/>").addClass("clear").click(function(){
				canlederBox.data("box").val("");
				canlederBox.hide();
			}).text("清空");
			var $bottom = $("<div/>").addClass("bottom").append($button_clear);
			canlederBox.append($bottom);
			
		};
		
		var $select = null;
		function _getSelect(year){
			if(!year){
				year = new Date().getFullYear();
			}
			
			$select = $("<select/>");
			for(var i = 10; i >=0; i--){
				$select.append($("<option/>").text(year - i ));
			}
			for(var i = 1; i <= 10; i++){
				$select.append($("<option/>").text(year + i ));
			}
			$select.find("option").each(function(){
				if($(this).text() == year){
					$(this).attr("selected", "selected");
				}
			});
			return $select;
		};

		 

	};

    $.fn.extend({
        simpleCanleder: function(options) {
            options = $.extend({},options);
            this.each(function() {
				new $.simpleCanleder(this, options);
			});
			return this;
        }
    });
});

$(".yearMonth").simpleCanleder();  
         
</script>
<style type="text/css">

#SimpleCanleder_Year_Month *{margin:0px;padding:0px;}
#SimpleCanleder_Year_Month .clear{clear: both;}
#SimpleCanleder_Year_Month {width:140px; font-size:14px;line-height:14px; border:1px solid lavender; padding:10px; padding-top:5px;padding-right:5px; position:absolute; background-color:white;}
#SimpleCanleder_Year_Month .title{height:35px;}
#SimpleCanleder_Year_Month .title li.middle{width:65px; position:relative; overflow:visible;}
#SimpleCanleder_Year_Month .title li.middle select{margin-top:-3px} 

#SimpleCanleder_Year_Month .title li.middle .year_list{width:100px; position:absolute; background-color:lightgrey; left:-20px}
#SimpleCanleder_Year_Month .title li.middle .year_list li {background-color:transparent; text-align:center; width:45px; height: 20px;}
#SimpleCanleder_Year_Month .title li.middle .year_list ul{clear: both;}

#SimpleCanleder_Year_Month .title li, #SimpleCanleder_Year_Month .body li{background-color:lavender;list-style:none; width:30px; height:30px; overflow:hidden; margin-top:5px;margin-right:5px; float:left; display:inline; cursor:pointer}
#SimpleCanleder_Year_Month .title li.cur, #SimpleCanleder_Year_Month .body li.cur{background-color:tomato;}
#SimpleCanleder_Year_Month .title li.over, #SimpleCanleder_Year_Month .body li.over{background-color:lightblue;}

#SimpleCanleder_Year_Month .title li .inner, #SimpleCanleder_Year_Month .body li .inner{padding-top:8px; text-align:center;}

#SimpleCanleder_Year_Month .bottom{ height: 16px; padding-top: 5px; text-align: center }
#SimpleCanleder_Year_Month .bottom button.clear{   background-color: gainsboro; border: 1px solid; font-size: 12px;}

</style>

<body>
<form id="pagerForm" method="post" action="<%=path %>/transReport/goListDishonorTotal">
    <input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader" >
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/transReport/goListDishonorTotal" method="post">
	<div class="searchBar" >
		<ul class="searchContent">
		   <li>
		 		<label>商户号：</label>
				<input type="text" name="merNo" value="${param.merNo}"/>
		 	</li>
			<li  class="dateRange">
			  <label>查询月份</label>
			  <input type="text" name="yearMonth" readonly="readonly" class="yearMonth" value="${param.yearMonth}"/>
			</li>
		</ul>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">检索</button></div></div></li>
			</ul>
		</div>
	</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
	</div>
	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab" layoutH="114" style="text-align: center;">
		<thead>
			<tr>
			   	<th>商户号</th>
				<th>总交易笔数</th>
				<th>总拒付笔数</th>
				<th>总拒付率</th>
				<th>月交易笔数</th>
				<th>月拒付笔数</th>
				<th>月拒付率</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.data}" var="total">
				<tr>
				   	<td>${total.merNo }</td>
				    <td>${total.totalNum }</td>
				    <td>${total.dishonoeNum }</td>
				    <td>${total.dishonoeColumn }</td>
				    <td>${total.totalNumApril }</td>
				    <td>${total.dishonoeNumApril }</td>
				    <td>${total.dishonoeColumnApril }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
	<div class="panelBar" style="height:30px">
		<div class="pages">
			<span>显示</span>
			<select name="numPerPage" class="combox"  onchange="navTabPageBreak({numPerPage:this.value})">
				<option value="20" ${param.numPerPage==20?'selected':'' }>20</option>
				<option value="50" ${param.numPerPage==50?'selected':'' }>50</option>
				<option value="100" ${param.numPerPage==100?'selected':'' } >100</option>
				<option value="200" ${param.numPerPage==200?'selected':'' }>200</option>
			</select>
			<span>条，共${page.total }条</span>
		</div>
		<div class="pagination" targetType="navTab" totalCount="${page.total }" numPerPage="${page.numPerPage }" pageNumShown="10" currentPage="${page.nowPage }"></div>
	</div>
</div>
</body>
</html>