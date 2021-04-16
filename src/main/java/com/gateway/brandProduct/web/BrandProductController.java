package com.gateway.brandProduct.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gateway.brandProduct.model.BrandProductInfo;
import com.gateway.brandProduct.service.BrandProductService;
import com.gateway.common.adapter.web.BaseController;
import com.gateway.common.adapter.web.BaseDataListener;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.merchantmgr.model.MerchantRelCurrencyInfo;

@Controller
@RequestMapping(value = "/brandProductMgr")
public class BrandProductController extends BaseController {
	@Resource
	BrandProductService brandProductService;
	
	/**
	 *跳转修改品牌与产品操作 
	 */
	@RequestMapping(value="/goUpdatebrandProductInfo")
	public String goUpdatebrandProductInfo(String id){
		BrandProductInfo brandProductInfo=brandProductService.queryBrandProductById(id);
		getRequest().setAttribute("brandProductInfo", brandProductInfo);
		return "brandProductmgr/updatebrandProductInfo";
	}
	/**
	 * 
	 *修改品牌与产品操作
	 */
	
	@RequestMapping(value="/updatebrandProduct")
	public ModelAndView updatebrandProduct(BrandProductInfo brandProductInfo){
		brandProductInfo.setCreateBy(getLogAccount().getRealName());
		if(brandProductService.queryBrandProductDup(brandProductInfo)>0){
			return ajaxDoneError("有重复的信息");
		}
		int i = brandProductService.updatebrandProduct(brandProductInfo);
		if(i>0){
			return ajaxDoneSuccess("修改成功");
		}else{
			return ajaxDoneSuccess("修改失败");
		}
	}
	/**
	 * 
	 *品牌与产品查询 
	 */
	@RequestMapping(value="/getBrandProductList")
	public String getBrandProductList(){
		PageInfo<BrandProductInfo> page = brandProductService.getBrandProductList(getCriteria());
		getRequest().setAttribute("page", page);
		return "brandProductmgr/brandProductList";
	}
	
	/**
	 * 跳转添加产品与品牌信息页面
	 * @return
	 */
	@RequestMapping(value="/goBrandProductInfo")
	public String goBrandProductInfo(){
		return "brandProductmgr/goAddbrandProductInfo";
	}
	
	/**
	 * 添加品牌信息
	 * @param bankInfo
	 * @return
	 */
	@RequestMapping(value="/addBrandProductInfo")
	public ModelAndView addBrandProductInfo(BrandProductInfo brandProductInfo){
		brandProductInfo.setCreateBy(getLogAccount().getRealName());
		if(brandProductService.queryBrandProductDup(brandProductInfo)>0){
			return ajaxDoneError("重复信息添加");
		}
		int i = brandProductService.addBrandProductInfo(brandProductInfo);
		if(i>0){
			return ajaxDoneSuccess("添加成功");
		}else{
			return ajaxDoneSuccess("添加失败");
		}
	}
	
	@RequestMapping(value="/deletebrandProductInfo")
	public ModelAndView deletebrandProductInfo(String id){
		int i = brandProductService.deletebrandProductInfo(id);
		if(i>0){
			return ajaxDoneSuccess("删除成功");
		}else{
			return ajaxDoneSuccess("删除失败");
		}
	}
	
	/**
	 * 导出产品品牌信息 
	 * @throws IOException   
	 * @throws WriteException 
	 * @throws RowsExceededException 
	 */
	@RequestMapping(value="/exportbrandProductInfo")
	public void exportbrandProductInfo(HttpServletResponse resp) throws IOException, RowsExceededException, WriteException{
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-disposition","attachment;filename="+ "merchantList.xls" ); 
		List<BrandProductInfo> list = brandProductService.ExportBrandProductList(getCriteria());
		WritableWorkbook book = Workbook.createWorkbook(resp.getOutputStream());
		WritableSheet sheet = book.createSheet("绑定通道列表", 0);
		String[] headerName = { "类型", "名称","创建人","创建时间"};
		// 写入标题
		for (int index = 0; index < headerName.length; index++) {
			Label label = new Label(index, 0, headerName[index]);
			sheet.addCell(label);
		}
		for (int row = 1; row <= list.size(); row++) {
			int col = 0;
			BrandProductInfo info = list.get(row-1);
			sheet.addCell( new Label(col++, row, "1".equals(info.getType())?"产品":"品牌"));
			sheet.addCell( new Label(col++, row, info.getBpname()));
			sheet.addCell( new Label(col++, row, info.getCreatedate()));
			sheet.addCell( new Label(col++, row, info.getCreateBy()));
		}
		book.write();
		book.close();
	}
}
