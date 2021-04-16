<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body><div>
    <div>
      <ul>
        <li>
          <input name="orgName" value="" type="text" />
        </li>
        <li>
          <label>部门编号:</label>
          <input name="orgNum" value="" type="text" />
        </li>
        <li>
          <label>部门经理:</label>
          <input name="leader" value="" type="text" />
        </li>
        <li>
          <label>上级部门:</label>
          <input name="parentOrg.orgName" value="" type="text" />
        </li>
      </ul>
      <div>
        <ul>
          <li>
            <div>
              <div>
                <button type="submit">查询</button>
              </div>
            </div>
          </li>
          <li>
            <div>
              <div>
                <button type="button" multlookup="orgId" warn="请选择部门">选择带回</button>
              </div>
            </div>
          </li>
        </ul>
      </div>
    </div>
</div>
<div>
  <div>
    <div>
      <div>
        <table>
          <thead>
            <tr>
              <th><div title="">
                <input name="checkbox" type="checkbox" group="orgId" />
              </div></th>
              <th orderfield="orgName"><div title="部门名称">部门名称</div></th>
              <th orderfield="orgNum"><div title="部门编号">部门编号</div></th>
              <th orderfield="leader"><div title="部门经理">部门经理</div></th>
              <th orderfield="creator"><div title="创建人">创建人</div></th>
            </tr>
          </thead>
        </table>
      </div>
    </div>
    <div layouth="118">
      <div>
        <table>
          <tbody>
            <tr>
              <td><div>
                <input type="checkbox" name="orgId" value="{id:'1', orgName:'技术部', orgNum:'1001'}" />
              </div></td>
              <td><div>技术部</div></td>
              <td><div>1001</div></td>
              <td><div>leader</div></td>
              <td><div>administrator</div></td>
            </tr>
            <tr>
              <td><div>
                <input type="checkbox" name="orgId" value="{id:'2', orgName:'人事部', orgNum:'1002'}" />
              </div></td>
              <td><div>人事部</div></td>
              <td><div>1002</div></td>
              <td><div>test</div></td>
              <td><div>administrator</div></td>
            </tr>
            <tr>
              <td><div>
                <input type="checkbox" name="orgId" value="{id:'3', orgName:'销售部', orgNum:'1003'}" />
              </div></td>
              <td><div>人事部</div></td>
              <td><div>1002</div></td>
              <td><div>test</div></td>
              <td><div>administrator</div></td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</div>
</body>
</html>