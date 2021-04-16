<%@page import="org.springframework.web.context.request.RequestAttributes"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="funcs" uri="funcs"%> 
<%
	String path = request.getContextPath();
%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
.panelBar {
    background-repeat: repeat-x;
    border-style: solid;
    border-width: 1px 0;
    display: block;
    height: 25px;
    overflow: hidden;
}

.panelBar {
    background-color: #efefef;
    border-color: #b8d0d6;
}
.panelBar, .toolBar li.hover, .toolBar li.hover a, .toolBar li.hover span, .toolBar span, .pagination, .pagination li.hover, .pagination li.hover a, .pagination li span, .pagination li.disabled span span, .panelBar .line, .pagination li.jumpto, .pagination li.jumpto .goto {
    background: rgba(0, 0, 0, 0) url("./images/grid/grid.png") no-repeat scroll 0 0;
}
html, body, div, span, applet, object, iframe, h1, h2, h3, h4, h5, h6, p, blockquote, pre, a, abbr, acronym, address, big, cite, code, del, dfn, em, font, img, ins, kbd, q, s, samp, small, strike, strong, sub, sup, tt, var, dl, dt, dd, ol, ul, li, fieldset, form, label, legend, table, caption, tbody, tfoot, thead, tr, th, td {
    font-family: Arial,sans-serif;
    font-size: 12px;
    line-height: 100%;
    margin: 0;
    padding: 0;
}
#layout {
    text-align: left;
}
body {
    color: black;
    text-align: center;
}

element.style {
    height: 380.083px;
    overflow: auto;
}

.tabsHeader li.main a span span, #taskbar li .taskbutton span {
    background: rgba(0, 0, 0, 0) url("./images/icon.png") no-repeat scroll 0 0;
}
.panel, .panel .panelHeader, .panel .panelHeaderContent, .panel .panelHeaderContent h1, .panel .panelFooter, .panel .panelFooterContent {
    background: rgba(0, 0, 0, 0) url("./images/panel/panel.png") no-repeat scroll 0 0;
}
.panel .expandable, .panel .collapsable {
    background: rgba(0, 0, 0, 0) url("./images/panel/panel_icon.png") no-repeat scroll 0 0;
}
.panel .panelHeaderContent h1 {
    color: #183152;
}
.panel .panelContent {
    background: #eef4f5 none repeat scroll 0 0;
    border-color: #b8d0d6;
}
.panel .grid {
    border-color: #b8d0d6;
}
.tabs, .tabsHeader, .tabsHeaderContent, .tabs .tabsHeader ul, .tabs .tabsHeader li, .tabs .tabsHeader li a, .tabs .tabsHeader li span, .tabs .tabsFooter, .tabs .tabsFooterContent {
    background: rgba(0, 0, 0, 0) url("./images/tabs/tabspanel.png") no-repeat scroll 0 0;
}
.tabs .tabsHeader li a {
    color: #03408b;
}
.tabs .tabsHeader li span {
    color: #183152;
}
.tabs .tabsContent {
    background: #eef4f5 none repeat scroll 0 0;
    border-color: #b8d0d6;
}
.tabsPage .tabsPageHeader, .tabsPage .tabsPageHeader li, .tabsPage .tabsPageHeader li a, .tabsPage .tabsPageHeader li span {
    background: rgba(0, 0, 0, 0) url("./images/tabs/tabspage.png") no-repeat scroll 0 0;
}
.tabsPage .tabsPageHeader {
    background-color: #e9f0f2;
}
.tabsPage .tabsPageHeader {
    border-color: #b8d0d6;
}
.tabsPage .tabsPageHeader li a {
    color: #183152;
}
.tabsPage .tabsPageHeader li .close, .tabsPage .tabsPageHeader li.hover .close, .tabsPage .tabsPageHeader li.selected .close {
    background: rgba(0, 0, 0, 0) url("./images/tabs/tabspage_icon.png") no-repeat scroll 0 0;
}
.tabsPage .tabsLeft, .tabsPage .tabsRight, .tabsPage .tabsMore {
    background: rgba(0, 0, 0, 0) url("./images/tabs/tabscontrol.png") no-repeat scroll 0 0;
}
.tabsPage .tabsMoreList {
    background: #fff none repeat scroll 0 0;
    border-color: #b8d0d6;
}
.tabsPage .tabsPageHeader .home_icon {
    background: rgba(0, 0, 0, 0) url("./images/icon.png") no-repeat scroll 0 0;
}
.tabsPage .tabsPageContent {
    background: #fff none repeat scroll 0 0;
    border-color: #b8d0d6;
}
.alert .alertFooter, .alert .alertFooter_r, .alert .alertFooter_c {
    background: rgba(0, 0, 0, 0) url("./images/alert/alertpanel.png") no-repeat scroll 0 0;
}
.alert .alertContent {
    background: #eef4f5 none repeat scroll 0 0;
    border-color: #b8d0d6;
}
.alert .warn .alertInner {
    background: #fefacf none repeat scroll 0 0;
    border-color: #e83e09;
}
.alert .error .alertInner {
    background: #fefacf none repeat scroll 0 0;
    border-color: #e50000;
}
.alert .correct .alertInner, .alert .info .alertInner {
    background: #fefacf none repeat scroll 0 0;
    border-color: #b8d0d6;
}
.alert .confirm .alertInner {
    background: #fefacf none repeat scroll 0 0;
    border-color: #b8d0d6;
}
.alert h1 {
    background: rgba(0, 0, 0, 0) url("./images/alert/alertpanel_icon.png") no-repeat scroll 0 0;
    border-color: #ccc;
}
.dialog .dialogHeader, .dialog .dialogHeader_r, .dialog .dialogHeader_c, .dialog .dialogFooter, .dialog .dialogFooter_r, .dialog .dialogFooter_c {
    background: rgba(0, 0, 0, 0) url("./images/dialog/dialogpanel.png") no-repeat scroll 0 0;
}
.dialog .dialogHeader h1, .dialog .dialogHeader .close, .dialog .dialogHeader .maximize, .dialog .dialogHeader .restore, .dialog .dialogHeader .minimize, .resizable_f_r {
    background: rgba(0, 0, 0, 0) url("./images/dialog/dialogpanel_icon.png") no-repeat scroll 0 0;
}
.dialog .dialogHeader h1 {
    color: #183152;
}
.dialog .dialogContent {
    background: #eef4f5 none repeat scroll 0 0;
    border-color: #b8d0d6;
}
.resizable {
    background: #c3d7dc none repeat scroll 0 0;
    border-color: #081629;
}
.shadow .shadow_h_l {
    background: rgba(0, 0, 0, 0) url("./images/shadow/shadow_h_l.png") no-repeat scroll 0 0;
}
.shadow .shadow_h_r {
    background: rgba(0, 0, 0, 0) url("./images/shadow/shadow_h_r.png") no-repeat scroll 0 0;
}
.shadow .shadow_h_c {
    background: rgba(0, 0, 0, 0) url("./images/shadow/shadow_h_c.png") repeat-x scroll 0 0;
}
.shadow .shadow_c_l {
    background: rgba(0, 0, 0, 0) url("./images/shadow/shadow_c_l.png") repeat-y scroll 0 0;
}
.shadow .shadow_c_r {
    background: rgba(0, 0, 0, 0) url("./images/shadow/shadow_c_r.png") repeat-y scroll 0 0;
}
.shadow .shadow_c_c {
    background: rgba(0, 0, 0, 0) url("./images/shadow/shadow_c_c.png") repeat scroll 0 0;
}
.shadow .shadow_f_l {
    background: rgba(0, 0, 0, 0) url("./images/shadow/shadow_f_l.png") no-repeat scroll 0 0;
}
.shadow .shadow_f_r {
    background: rgba(0, 0, 0, 0) url("./images/shadow/shadow_f_r.png") no-repeat scroll 0 0;
}
.shadow .shadow_f_c {
    background: rgba(0, 0, 0, 0) url("./images/shadow/shadow_f_c.png") repeat-x scroll 0 0;
}
.tree div div {
    background: rgba(0, 0, 0, 0) url("./images/tree/tree.png") no-repeat scroll 0 0;
}
.tree .folder_collapsable, .tree .folder_expandable, .tree .file {
    background: rgba(0, 0, 0, 0) url("./images/tree/folder.png") no-repeat scroll 0 0;
}
.tree .checked, .tree .unchecked, .tree .indeterminate {
    background: rgba(0, 0, 0, 0) url("./images/tree/check.png") no-repeat scroll 0 0;
}
.tree ul {
    background: #fff none repeat scroll 0 0;
}
.tree li a, .tree li span {
    color: #183152;
}
.tree .hover {
    background: #f5f5f5 none repeat scroll 0 0;
}
.tree .selected {
    background-color: #e8edf3;
}
.accordion .accordionHeader, .accordion .accordionHeader h2, .accordion .accordionHeader h2 span {
    background: rgba(0, 0, 0, 0) url("./images/accordion/accordion.png") repeat scroll 0 0;
    color: #183152;
}
.accordion {
    background: #fff none repeat scroll 0 0;
    border-color: #b8d0d6;
}
.accordion .accordionHeader {
    background-color: #eaf4ff;
}
.accordion .accordionContent {
    border-color: #b8d0d6;
}
.panelBar, .toolBar li.hover, .toolBar li.hover a, .toolBar li.hover span, .toolBar span, .pagination, .pagination li.hover, .pagination li.hover a, .pagination li span, .pagination li.disabled span span, .panelBar .line, .pagination li.jumpto, .pagination li.jumpto .goto {
    background: rgba(0, 0, 0, 0) url("./images/grid/grid.png") no-repeat scroll 0 0;
}
.panelBar {
    background-color: #efefef;
    border-color: #b8d0d6;
}
.grid .gridHeader {
    background: #eee none repeat scroll 0 0;
}
.grid {
    background: #fff none repeat scroll 0 0;
}
.grid table {
    border-color: #d0d0d0;
}
.grid .gridHeader, .grid .gridHeader th {
    background: #f0eff0 url("./images/grid/tableth.png") repeat-x scroll 0 0;
    border-color: #d0d0d0;
}
.grid table th div {
    border-left-color: #eee;
    border-right-color: #d0d0d0;
}
.grid table td {
    border-color: #ededed;
}
.grid .resizeMarker, .grid .resizeProxy {
    background: rgba(0, 0, 0, 0) url("./images/grid/resizeCol.png") repeat-y scroll 0 0;
}
.grid .gridHeader th.hover, .grid .gridHeader th.thSelected {
    border-color: #aaccf6;
}
.grid .gridTbody .gridRowBg {
    background: #f7f7f7 none repeat scroll 0 0;
}
.grid .gridTbody .gridRow {
    border-color: #ededed;
}
.grid .gridTbody .gridRow td.tdLast {
    border-color: #ededed;
}
.grid .gridTbody .hover {
    background: #f5f5f5 none repeat scroll 0 0;
    border-color: #dddddd;
}
.grid .gridTbody .hover .tdSelected {
    background: #f5f5f5 none repeat scroll 0 0;
}
.grid .gridTbody .selected {
    background: #7cc5e5 none repeat scroll 0 0;
    border-color: #b8d0d6;
}
.grid .gridTbody .selected .tdSelected {
    background: #e8edf3 none repeat scroll 0 0;
}
.grid .gridTbody .tdSelected {
    background: #f8f8f8 none repeat scroll 0 0;
}
.grid .error {
    background: #fb7e81 none repeat scroll 0 0;
}
.progressBar {
    background: #fff url("./images/progressBar/progressBar_m.gif") no-repeat scroll 10px 10px;
    border: 2px solid #86a5ad;
}
.textInput, input.focus, input.required, input.error, input.readonly, input.disabled, textarea.focus, textarea.required, textarea.error, textarea.readonly, textarea.disabled {
    background: rgba(0, 0, 0, 0) url("./images/form/input_bg.png") no-repeat scroll 0 0;
}
.textInput, .textArea {
    background-color: #fff;
    border-color: #a2bac0 #b8d0d6 #b8d0d6 #a2bac0;
}
input.required, textarea.required {
    background-color: #fff;
    border-color: #a2bac0 #b8d0d6 #b8d0d6 #a2bac0;
}
input.error, textarea.error {
    border-color: #f80c11 #fb7e81 #fb7e81 #f80c11;
}
input.focus, textarea.focus {
    background-color: #f8fafc;
    border-color: #64aabc #a9d7e3 #a9d7e3 #64aabc;
}
input.readonly, textarea.readonly {
    background-color: #f6f6f6;
    border-color: #9eabb3 #d5dbdf #d5dbdf #9eabb3;
}
input.disabled, textarea.disabled {
    background-color: #f6f6f6;
    border-color: #9eabb3 #d5dbdf #d5dbdf #9eabb3;
}
.inputButton, .inputDateButton {
    background: rgba(0, 0, 0, 0) url("./images/form/input_bt.png") no-repeat scroll 0 0;
}
.button, .button span, .buttonDisabled, .buttonDisabled span, .buttonActive, .buttonActive span, .button .buttonContent, .buttonHover, .buttonHover .buttonContent, .buttonActive .buttonContent, .buttonActiveHover, .buttonActiveHover .buttonContent, .buttonDisabled .buttonContent {
    background: rgba(0, 0, 0, 0) url("./images/button/button_s.png") no-repeat scroll 0 0;
}
.button span, .buttonDisabled span, .buttonActive span, .button .buttonContent, .buttonHover, .buttonHover .buttonContent, .buttonActive .buttonContent, .buttonDisabled .buttonContent, .button button, .buttonHover button, .buttonActive button, .buttonDisabled button {
    color: #183152;
}
.buttonDisabled span, .buttonDisabled:hover span, .buttonDisabled button {
    color: #999;
}
body, #splitBar {
    background: #e5edef none repeat scroll 0 0;
}
#splitBarProxy {
    background: #ccc none repeat scroll 0 0;
    border-color: #c0c0c0;
}
#header, #header .headerNav {
    background: rgba(0, 0, 0, 0) url("./images/header_bg.png") repeat-x scroll 0 0;
}
#header {
    background-color: #102c4a;
}
#header .logo {
    background: rgba(0, 0, 0, 0) url("./images/logo.png") no-repeat scroll 0 0;
}
#header .nav li {
    background: rgba(0, 0, 0, 0) url("./images/listLine.png") no-repeat scroll 0 0;
    float: left;
    line-height: 11px;
    margin-left: -1px;
    padding: 0 8px;
}
#header .nav li a {
    color: #b9ccda;
}
#header .themeList li div {
    background: rgba(0, 0, 0, 0) url("./images/themeButton.png") no-repeat scroll 0 0;
}
.toggleCollapse, .toggleCollapse div {
    background: rgba(0, 0, 0, 0) url("./images/layout/toggleSidebar.png") no-repeat scroll 0 0;
}
.toggleCollapse {
    background-color: #e7eff0;
    border-color: #b8d0d6;
    border-style: solid;
    border-width: 1px 1px 0;
}
.toggleCollapse h2 {
    color: #183152;
}
#sidebar_s .collapse {
    background: #eff5f6 none repeat scroll 0 0;
    border: 1px solid #b8d0d6;
}
#sidebar_s .collapse:hover {
    background: #f5f9fa none repeat scroll 0 0;
}
#taskbar, #taskbar li, #taskbar li .taskbutton {
    background: rgba(0, 0, 0, 0) url("./images/layout/taskbar.png") no-repeat scroll 0 0;
}
#taskbar .close, #taskbar .restore, #taskbar .minimize {
    background: rgba(0, 0, 0, 0) url("./images/layout/taskbar_icon.png") no-repeat scroll 0 0;
}
#taskbar li .taskbutton span {
    color: #fff;
}
#taskbar .taskbarLeft, #taskbar .taskbarRight {
    background: rgba(0, 0, 0, 0) url("./images/layout/taskbar_control.png") no-repeat scroll 0 0;
}
#navMenu, #navMenu li, #navMenu li.selected a, #navMenu li.selected span {
    background: rgba(0, 0, 0, 0) url("../default/images/menu/menu.png") no-repeat scroll 0 0;
}
#navMenu {
    background-color: #1871dd;
}
#navMenu li a, #navMenu li span {
    color: #fff;
}
#navMenu li.selected span {
    color: #000;
}
.sidebarContent {
    background: #fff none repeat scroll 0 0;
    border: 1px solid #86b4ec;
    display: block;
    height: 500px;
    overflow: auto;
}
.accountInfo {
    background: rgba(0, 0, 0, 0) url("./images/account_info_bg.png") repeat-x scroll 0 0;
    display: block;
    height: 60px;
    overflow: hidden;
    padding: 0 10px;
}
.accountInfo p {
    line-height: 19px;
    padding: 8px 0 0;
}
.accountInfo p span {
    font-size: 14px;
    font-weight: bold;
}
.accountInfo .right {
    float: right;
    padding-right: 10px;
    text-align: right;
}
.accountInfo .alertInfo {
    border-left: 1px solid #accdf4;
    float: right;
    height: 60px;
    padding-left: 10px;
    width: 300px;
}
.accountInfo .alertInfo h2 {
    line-height: 17px;
    padding: 8px 0;
}
.accountInfo .alertInfo a {
    line-height: 21px;
    padding: 6px 0 0;
}
.pageForm .inputInfo {
    color: #999;
}
.dialog .pageHeader, .dialog .pageContent {
    border-color: #b8d0d6;
}
.dialog .pageContent .pageFormContent {
    background: #fff none repeat scroll 0 0;
    border-color: #b8d0d6;
}
.page .pageHeader, .formBar {
    background: #ebf0f5 url("../default/images/pageHeader_bg.png") repeat-x scroll 0 0;
    border-color: #b8d0d6;
}
.page .searchBar label {
    color: #183152;
}
.formBar {
    border-color: #b8d0d6;
}
.divider {
    border-color: #b8d0d6;
}
.combox .select a {
    color: #183152;
}
.comboxop {
    border-color: #b8d0d6;
}
.combox, .combox div, .combox div a {
    background: rgba(0, 0, 0, 0) url("../default/images/search-bg.gif") no-repeat scroll 0 0;
}
html, body, div, span, applet, object, iframe, h1, h2, h3, h4, h5, h6, p, blockquote, pre, a, abbr, acronym, address, big, cite, code, del, dfn, em, font, img, ins, kbd, q, s, samp, small, strike, strong, sub, sup, tt, var, dl, dt, dd, ol, ul, li, fieldset, form, label, legend, table, caption, tbody, tfoot, thead, tr, th, td {
    font-family: Arial,sans-serif;
    font-size: 12px;
    line-height: 100%;
    margin: 0;
    padding: 0;
}
:focus {
    outline: 0 none;
}
body {
    color: black;
    height: 100%;
    text-align: center;
    width: 100%;
}
ul, ol {
    list-style: outside none none;
}
table {
    border-collapse: separate;
    border-spacing: 0;
}
caption, th, td {
    font-weight: normal;
}
blockquote::before, blockquote::after, q::before, q::after {
    content: "";
}
blockquote, q {
    quotes: "" "";
}
img {
    border: medium none;
}
a {
    color: #000;
    text-decoration: none;
}
a:hover {
    text-decoration: underline;
}
.panel {
    background-position: 0 100%;
    background-repeat: repeat-x;
    display: block;
}
.panel .panelHeader {
    background-position: 0 0;
    display: block;
    height: 28px;
    padding-left: 5px;
}
.panel .panelHeaderContent {
    background-position: 100% -50px;
    display: block;
    height: 28px;
    padding-right: 5px;
    position: relative;
}
.panel .panelHeaderContent h1 {
    background-position: 0 -100px;
    background-repeat: repeat-x;
    display: block;
    height: 28px;
    line-height: 28px;
    overflow: hidden;
    padding: 0 5px;
}
.panel .panelContent {
    border-style: solid;
    border-width: 0 1px;
    display: block;
    overflow: auto;
    padding: 5px 5px 1px;
}
.panel .panelFooter {
    background-position: 0 -150px;
    display: block;
    height: 5px;
    overflow: hidden;
    padding-left: 5px;
}
.panel .panelFooterContent {
    background-position: 100% -200px;
    display: block;
    height: 5px;
    overflow: hidden;
    padding-right: 5px;
}
.panel .collapsable, .panel .expandable {
    display: block;
    height: 21px;
    overflow: hidden;
    position: absolute;
    right: 4px;
    text-indent: -1000px;
    top: 4px;
    width: 20px;
}
.panel .collapsable:hover {
    background-position: 0 -50px;
}
.panel .expandable {
    background-position: 0 -100px;
}
.panel .expandable:hover {
    background-position: 0 -150px;
}
.tabs {
    background-position: 0 100%;
    background-repeat: repeat-x;
}
.tabs .tabsHeader {
    background-position: 0 0;
    display: block;
    height: 28px;
    overflow: hidden;
    padding-left: 5px;
}
.tabs .tabsHeaderContent {
    background-position: 100% -50px;
    display: block;
    height: 28px;
    overflow: hidden;
    padding-right: 5px;
}
.tabs .tabsHeader ul {
    background-position: 0 -100px;
    background-repeat: repeat-x;
    display: block;
    height: 28px;
}
.tabs .tabsHeader li {
    background-position: 0 -250px;
    background-repeat: repeat-x;
    cursor: pointer;
    display: block;
    float: left;
    height: 28px;
    margin-right: 2px;
}
.tabs .tabsHeader li a {
    background-position: 0 -150px;
    display: block;
    float: left;
    height: 28px;
    padding-left: 5px;
}
.tabs .tabsHeader li a:hover {
    text-decoration: none;
}
.tabs .tabsHeader li span {
    background-position: 100% -200px;
    cursor: pointer;
    display: block;
    float: left;
    height: 28px;
    line-height: 25px;
    overflow: hidden;
    padding: 2px 10px 0 5px;
}
.tabs .tabsHeader li.hover {
    background-position: 0 -400px;
    background-repeat: repeat-x;
}
.tabs .tabsHeader li.hover a {
    background-position: 0 -300px;
}
.tabs .tabsHeader li.hover span {
    background-position: 100% -350px;
}
.tabs .tabsHeader li.selected {
    background-position: 0 -550px;
}
.tabs .tabsHeader li.selected a {
    background-position: 0 -450px;
}
.tabs .tabsHeader li.selected span {
    background-position: 100% -500px;
    font-weight: bold;
}
.tabs .tabsContent {
    border-style: solid;
    border-width: 0 1px;
    display: block;
    overflow: auto;
    padding: 5px 5px 1px;
}
.tabs .tabsFooter {
    background-position: 0 -600px;
    display: block;
    height: 5px;
    overflow: hidden;
}
.tabs .tabsFooterContent {
    background-position: 100% -650px;
    display: block;
    height: 5px;
    overflow: hidden;
}
.tabsPage .tabsPageHeader {
    background-position: 0 -450px;
    background-repeat: repeat-x;
    border-style: solid;
    border-width: 1px 1px 0;
    display: block;
    height: 27px;
    position: relative;
}
.tabsPage .tabsPageHeaderContent {
    display: block;
    height: 27px;
    margin-right: 19px;
    overflow: hidden;
    position: relative;
}
.tabsPage .tabsPageHeaderMargin {
    margin: 0 34px 0 17px;
}
.tabsPage .tabsPageHeader ul {
    display: block;
    height: 26px;
    position: absolute;
    width: 10000px;
    z-index: 1;
}
.tabsPage .tabsPageHeader li {
    background-position: 0 -100px;
    background-repeat: repeat-x;
    cursor: pointer;
    display: block;
    float: left;
    height: 26px;
    margin-left: 2px;
    position: relative;
}
.tabsPage .tabsPageHeader li a {
    background-position: 0 0;
    display: block;
    float: left;
    height: 26px;
    line-height: 25px;
    overflow: hidden;
    padding-left: 5px;
}
.tabsPage .tabsPageHeader li a:hover {
    text-decoration: none;
}
.tabsPage .tabsPageHeader li span {
    background-position: 100% -50px;
    cursor: pointer;
    display: block;
    float: left;
    height: 24px;
    line-height: 21px;
    overflow: hidden;
    padding: 2px 20px 0 3px;
    width: 92px;
}
.tabsPage .tabsPageHeader li.hover {
    background-position: 0 -250px;
    background-repeat: repeat-x;
}
.tabsPage .tabsPageHeader li.hover a {
    background-position: 0 -150px;
}
.tabsPage .tabsPageHeader li.hover span {
    background-position: 100% -200px;
}
.tabsPage .tabsPageHeader li.selected {
    background-position: 0 -400px;
    background-repeat: repeat-x;
}
.tabsPage .tabsPageHeader li.selected a {
    background-position: 0 -300px;
    font-weight: bold;
}
.tabsPage .tabsPageHeader li.selected span {
    background-position: 100% -350px;
}
.tabsPage .tabsPageHeader li .close {
    display: block;
    height: 11px;
    overflow: hidden;
    padding: 0;
    position: absolute;
    right: 2px;
    text-indent: -1000px;
    top: 3px;
    width: 11px;
}
.tabsPage .tabsPageHeader li .close:hover {
    background-position: 0 -50px;
}
.tabsPage .tabsPageHeader li.main span {
    padding: 2px 8px 0 3px;
}
.tabsPage .tabsPageHeader li .home_icon, .tabsPage .tabsPageHeader li.main .home_icon {
    background-position: 0 3px;
    padding: 0 0 0 15px;
    width: auto;
}
.tabsPage .tabsMove {
    height: 25px;
    position: absolute;
    right: 0;
    top: 0;
    z-index: 2;
}
.tabsPage .tabsLeft, .tabsPage .tabsRight, .tabsPage .tabsMore {
    display: block;
    height: 23px;
    overflow: hidden;
    position: absolute;
    text-indent: -1000px;
    width: 17px;
    z-index: 2;
}
.tabsPage .tabsLeft {
    background-position: 0 0;
    cursor: pointer;
    left: 0;
    top: 1px;
}
.tabsPage .tabsLeftHover {
    background-position: 0 -50px;
}
.tabsPage .tabsLeftDisabled {
    background-position: 0 -100px;
    cursor: default;
    left: 0;
    top: 1px;
}
.tabsPage .tabsRight {
    background-position: 0 -150px;
    cursor: pointer;
    right: 17px;
    top: 1px;
}
.tabsPage .tabsRightHover {
    background-position: 0 -200px;
}
.tabsPage .tabsRightDisabled {
    background-position: 0 -250px;
    cursor: default;
    right: 17px;
    top: 1px;
}
.tabsPage .tabsMore {
    background-position: 0 -300px;
    cursor: pointer;
    right: 0;
    top: 1px;
}
.tabsPage .tabsMoreHover {
    background-position: 0 -350px;
}
.tabsPage .tabsMoreList {
    border-style: solid;
    border-width: 1px;
    display: none;
    max-height: 380px;
    overflow-x: hidden;
    overflow-y: auto;
    padding: 2px;
    position: absolute;
    right: 0;
    top: 24px;
    width: 170px;
    z-index: 3;
}
.tabsPage .tabsMoreList li {
    display: block;
    height: 23px;
    line-height: 21px;
    overflow: hidden;
}
.tabsPage .tabsMoreList li a {
    border: 1px solid #fff;
    display: block;
    height: 21px;
    line-height: 21px;
    padding: 0 10px;
    white-space: nowrap;
    width: 148px;
}
.tabsPage .tabsMoreList li a:hover {
    background: #f5f5f5 none repeat scroll 0 0;
    border-color: #ececec;
    text-decoration: none;
}
.tabsPage .tabsMoreList li.selected a {
    background: #e8edf3 none repeat scroll 0 0;
    border-color: #dfe5ed;
    font-weight: bold;
}
.tabsPage .tabsPageContent {
    border-style: solid;
    border-width: 0 1px 1px;
    display: block;
    overflow: hidden;
    position: relative;
}
.alert {
    display: block;
    left: 50%;
    margin-left: -150px;
    overflow: hidden;
    position: absolute;
    top: 0;
    width: 300px;
    z-index: 1011;
}
.alert .alertContent {
    border-style: solid;
    border-width: 0 1px;
    display: block;
    overflow: hidden;
    padding: 5px 5px 1px;
}
.alert .alertInner {
    border-style: solid;
    border-width: 1px;
    display: block;
    padding: 0 9px 9px;
    text-align: left;
}
.alert .alertInner .msg {
    line-height: 1.3em;
    margin: 10px;
    max-height: 200px;
    overflow: auto;
}
.alert h1 {
    border-style: solid;
    border-width: 0 0 1px;
    display: block;
    height: 30px;
    line-height: 30px;
    margin-bottom: 10px;
    overflow: hidden;
    padding: 0 0 0 25px;
}
.alert .error h1 {
    background-position: 2px -42px;
}
.alert .info h1, .alert .warn h1 {
    background-position: 2px 8px;
}
.alert .correct h1 {
    background-position: 2px -92px;
}
.alert .confirm h1 {
    background-position: 2px 8px;
}
.alert p {
    margin: 10px;
}
.alert .toolBar {
    display: block;
    height: 25px;
    overflow: hidden;
    padding-top: 5px;
    text-align: right;
}
.alert .toolBar ul {
    float: right;
}
.alert .toolBar li {
    float: left;
}
.alert .toolBar .button, .alert .toolBar .buttonActive {
    margin-left: 5px;
}
.alert .alertFooter_c {
    display: block;
    height: 5px;
    overflow: hidden;
}
.alert .alertFooter {
    background-position: 0 0;
    padding-left: 5px;
}
.alert .alertFooter_r {
    background-position: 100% -50px;
    padding-right: 5px;
}
.alert .alertFooter_c {
    background-position: 0 -100px;
    background-repeat: repeat-x;
    padding: 0;
}
.dialog {
    display: block;
    position: absolute;
    text-align: left;
    z-index: 42;
}
.dialog .dialogHeader, .dialog .dialogHeader_r, .dialog .dialogHeader_c {
    display: block;
    height: 28px;
    overflow: hidden;
}
.dialog .dialogHeader .close, .dialog .dialogHeader .maximize, .dialog .dialogHeader .restore, .dialog .dialogHeader .minimize {
    display: block;
    height: 19px;
    overflow: hidden;
    position: absolute;
    text-indent: -1000px;
    top: 5px;
    width: 19px;
}
.dialog .dialogHeader h1 {
    background-position: 0 -450px;
    display: block;
    height: 28px;
    line-height: 28px;
    overflow: hidden;
    padding: 0 5px 0 20px;
}
.dialog .dialogHeader {
    background-position: 0 0;
    cursor: move;
    padding-left: 5px;
    position: relative;
}
.dialog .dialogHeader_r {
    background-position: 100% -50px;
    padding-right: 5px;
}
.dialog .dialogHeader_c {
    background-position: 0 -100px;
    background-repeat: repeat-x;
    padding: 0;
}
.dialog .dialogHeader .close {
    background-position: 0 0;
    right: 4px;
}
.dialog .dialogHeader .close:hover {
    background-position: 0 -50px;
}
.dialog .dialogHeader .maximize {
    background-position: 0 -100px;
    right: 23px;
}
.dialog .dialogHeader .maximize:hover {
    background-position: 0 -150px;
}
.dialog .dialogHeader .restore {
    background-position: 0 -200px;
    display: none;
    right: 23px;
}
.dialog .dialogHeader .restore:hover {
    background-position: 0 -250px;
}
.dialog .dialogHeader .minimize {
    background-position: 0 -300px;
    right: 42px;
}
.dialog .dialogHeader .minimize:hover {
    background-position: 0 -350px;
}
.dialog .dialogContent {
    border-style: solid;
    border-width: 0 1px;
    display: block;
    overflow: hidden;
    padding: 5px 5px 1px;
}
.dialog .panelFooter_r, .dialog .dialogFooter_c {
    display: block;
    height: 5px;
    overflow: hidden;
}
.dialog .dialogFooter {
    background-position: 0 -150px;
    padding-left: 5px;
}
.dialog .dialogFooter_r {
    background-position: 100% -200px;
    padding-right: 5px;
}
.dialog .dialogFooter_c {
    background-position: 0 -250px;
    background-repeat: repeat-x;
    padding: 0;
}
.dialogProxy {
    opacity: 0.8;
}
.dialog .resizable_f_r {
    background-position: 0 -400px;
    height: 11px;
    width: 11px;
}
.resizable {
    border-style: dashed;
    border-width: 1px;
    display: none;
    left: 0;
    opacity: 0.5;
    overflow: hidden;
    position: absolute;
    top: 0;
    z-index: 100;
}
.resizable_h_l, .resizable_h_r, .resizable_h_c, .resizable_c_l, .resizable_c_r, .resizable_f_l, .resizable_f_r, .resizable_f_c {
    display: block;
    height: 6px;
    overflow: hidden;
    position: absolute;
    width: 6px;
}
.resizable_h_l {
    cursor: nw-resize;
    left: 0;
    top: 0;
    z-index: 2;
}
.resizable_h_r {
    cursor: ne-resize;
    right: 0;
    top: 0;
    z-index: 2;
}
.resizable_h_c {
    cursor: n-resize;
    left: 0;
    top: 0;
    width: 100%;
    z-index: 1;
}
.resizable_c_l {
    cursor: w-resize;
    left: 0;
    top: 0;
    z-index: 1;
}
.resizable_c_r {
    cursor: e-resize;
    right: 0;
    top: 0;
    z-index: 1;
}
.resizable_f_l {
    bottom: 0;
    cursor: sw-resize;
    left: 0;
    z-index: 2;
}
.resizable_f_r {
    bottom: 0;
    cursor: se-resize;
    right: 0;
    z-index: 2;
}
.resizable_f_c {
    bottom: 0;
    cursor: s-resize;
    left: 0;
    width: 100%;
    z-index: 1;
}
.shadow {
    display: none;
    overflow: hidden;
    position: absolute;
    z-index: 41;
}
.shadow .shadow_h, .shadow .shadow_h_l, .shadow .shadow_h_r, .shadow .shadow_h_c, .shadow .shadow_f, .shadow .shadow_f_l, .shadow .shadow_f_r, .shadow .shadow_f_c {
    display: block;
    height: 6px;
    overflow: hidden;
}
.shadow .shadow_h, .shadow .shadow_c, .shadow .shadow_f {
    position: relative;
}
.shadow .shadow_h_l, .shadow .shadow_c_l, .shadow .shadow_f_l {
    left: 0;
    position: absolute;
    top: 0;
    width: 6px;
}
.shadow .shadow_h_r, .shadow .shadow_c_r, .shadow .shadow_f_r {
    position: absolute;
    right: 0;
    top: 0;
    width: 6px;
}
.shadow .shadow_h_c, .shadow .shadow_c_c, .shadow .shadow_f_c {
    margin: 0 6px;
}
.shadow .shadow_c, .shadow .shadow_c_l, .shadow .shadow_c_r, .shadow .shadow_c_c {
    display: block;
    height: 100%;
    overflow: hidden;
}
.tree li {
    clear: both;
    cursor: pointer;
    display: block;
    line-height: 22px;
}
.tree div, .tree a, .tree span {
    display: inherit;
    height: 22px;
    line-height: 22px;
}
.tree div {
    display: block;
    overflow: hidden;
}
.tree div div {
    background-position: 0 -100px;
    border: medium none;
    display: block;
    float: left;
    height: 22px;
    overflow: hidden;
    width: 22px;
}
.tree a, .tree a:hover {
    text-decoration: none;
}
.tree .collapsable {
    background-position: 0 -300px;
}
.tree .first_collapsable {
    background-position: 0 -250px;
}
.tree .last_collapsable {
    background-position: 0 -350px;
}
.tree .expandable {
    background-position: 0 -100px;
}
.tree .first_expandable {
    background-position: 0 -50px;
}
.tree .last_expandable {
    background-position: 0 -150px;
}
.tree .end_expandable {
    background-position: 0 0;
}
.tree .end_collapsable {
    background-position: 0 -200px;
}
.tree .indent {
    background: rgba(0, 0, 0, 0) none repeat scroll 0 0;
}
.tree .line {
    background-position: 0 -400px;
}
.tree .node {
    background-position: 0 -450px;
}
.tree .last .node {
    background-position: 0 -500px;
}
.tree .folder_expandable {
    background-position: 0 0;
}
.tree .folder_collapsable {
    background-position: 0 -50px;
}
.tree .file {
    background-position: 0 -100px;
}
.tree .unchecked {
    background-position: 0 0;
}
.tree .hover .unchecked {
    background-position: 0 -50px;
}
.tree .checked {
    background-position: 0 -100px;
}
.tree .hover .checked {
    background-position: 0 -150px;
}
.tree .indeterminate {
    background-position: 0 -200px;
}
.tree .hover .indeterminate {
    background-position: 0 -250px;
}
.accordion {
    border-style: solid;
    border-width: 1px 1px 0;
    display: block;
}
.accordion .accordionHeader {
    background-repeat: repeat-x;
    cursor: pointer;
    display: block;
    overflow: hidden;
}
.accordion .accordionHeader h2 {
    display: block;
    height: 25px;
    line-height: 24px;
    overflow: hidden;
    padding: 0 25px 0 5px;
}
.accordion .accordionHeader h2 span {
    display: block;
    float: left;
    height: 25px;
    overflow: hidden;
    text-indent: -1000px;
    width: 20px;
}
.accordion .accordionContent {
    border-style: solid;
    border-width: 0 0 1px;
    display: block;
    overflow: auto;
}
.accordion .accordionHeader.hover {
    background-position: 0 -25px;
}
.accordion .accordionHeader h2 {
    background-position: 100% -50px;
    background-repeat: no-repeat;
}
.accordion .accordionHeader.hover h2 {
    background-position: 100% -75px;
}
.accordion .accordionHeader .collapsable {
    background-position: 100% -100px;
}
.accordion .accordionHeader.hover .collapsable {
    background-position: 100% -125px;
}
.accordion .accordionHeader h2 span {
    background-position: 0 -150px;
}
.panel .grid {
    border-style: solid;
    border-width: 0 1px;
}
.panel .panelBar {
    border-width: 1px;
}
.panelBar {
    background-repeat: repeat-x;
    border-style: solid;
    border-width: 1px 0;
    display: block;
    height: 25px;
    overflow: hidden;
}
.panelBar ul {
    padding: 1px;
}
.panelBar li {
    display: block;
    float: left;
    height: 23px;
    overflow: hidden;
    padding: 0 0 0 5px;
}
.panelBar li.hover {
    background-position: 0 -100px;
}
.panelBar li.hover a {
    background-position: 100% -150px;
}
.panelBar .toolBar li, .panelBar .toolBar li.hover {
    background-position: 0 -100px;
    padding: 0 0 0 5px;
}
.panelBar .toolBar a, .panelBar .toolBar li.hover a {
    background-position: 100% -150px;
    display: block;
    float: left;
    overflow: hidden;
    padding: 0 5px 0 0;
    text-decoration: none;
}
.panelBar .toolBar span, .panelBar .toolBar li.hover span {
    cursor: pointer;
    display: block;
    float: left;
    height: 23px;
    line-height: 23px;
    overflow: hidden;
    padding: 0 0 0 20px;
}
.panelBar .toolBar a.add span {
    background-position: 0 -696px;
}
.panelBar .toolBar a.delete span {
    background-position: 0 -746px;
}
.panelBar .toolBar a.edit span {
    background-position: 0 -796px;
}
.panelBar .toolBar a.icon span {
    background-position: 0 -846px;
}
.panelBar .toolBar li.line {
    background-position: 5px -200px;
    display: block;
    overflow: hidden;
    padding: 0;
    text-indent: -1000px;
    width: 12px;
}
.panelBar .pages {
    float: left;
    height: 21px;
    overflow: hidden;
    padding: 2px 5px;
}
.panelBar .pages span {
    float: left;
    line-height: 21px;
}
.panelBar .pages select {
    float: left;
    font-size: 12px;
    margin: 0 3px;
}
.pagination {
    background-position: 0 -199px;
    float: right;
    padding-left: 7px;
}
.pagination li, .pagination li.hover {
    background-position: 0 -100px;
    padding: 0 0 0 5px;
}
.pagination a, .pagination li.hover a, .pagination li span {
    background-position: 100% -150px;
    display: block;
    float: left;
    line-height: 23px;
    padding: 0 5px 0 0;
    text-decoration: none;
}
.pagination li.selected a {
    color: red;
    font-weight: bold;
}
.pagination span, .pagination li.hover span {
    cursor: pointer;
    display: block;
    float: left;
    height: 23px;
    line-height: 23px;
}
.pagination li .first span, .panelBar li .previous span {
    padding: 0 0 0 10px;
}
.pagination li .next span, .panelBar li .last span {
    padding: 0 10px 0 0;
}
.pagination li .first span {
    background-position: 0 -244px;
}
.pagination li .previous span {
    background-position: 0 -294px;
}
.pagination li .next span {
    background-position: 100% -344px;
}
.pagination li .last span {
    background-position: 100% -394px;
}
.pagination li .last {
    margin-right: 5px;
}
.pagination li.disabled {
    background: rgba(0, 0, 0, 0) none repeat scroll 0 0;
}
.pagination li.disabled span, .grid .pagination li.disabled a {
    background-position: 0 100px;
    cursor: default;
}
.pagination li.disabled span span {
    color: #666;
}
.pagination li.disabled .first span {
    background-position: 0 -444px;
}
.pagination li.disabled .previous span {
    background-position: 0 -494px;
}
.pagination li.disabled .next span {
    background-position: 100% -544px;
}
.pagination li.disabled .last span {
    background-position: 100% -594px;
}
.pagination li.disabled .last {
    margin-right: 5px;
}
.pagination li.jumpto {
    background-position: 0 -200px;
    padding: 2px 2px 0 7px;
}
.pagination li.jumpto .textInput {
    border-color: #acaeaf;
    float: left;
    padding: 1px;
    width: 30px;
}
.pagination li.jumpto .goto {
    background-position: 0 -650px;
    border: 0 none;
    cursor: pointer;
    display: block;
    float: left;
    height: 19px;
    overflow: hidden;
    text-indent: -1000px;
    width: 16px;
}
.grid {
    border-width: 0 1px;
    display: block;
    overflow: hidden;
    position: relative;
    width: 100%;
}
.grid .gridHeader {
    display: block;
    overflow: hidden;
    width: auto;
}
.grid .gridThead {
}
.grid .gridScroller {
    display: block;
    overflow: auto;
    position: relative;
}
.grid .gridTbody {
}
.grid table {
    border: 0 none;
    border-collapse: collapse;
    table-layout: fixed;
}
.grid .gridHeader th {
    border-style: solid;
    border-width: 0 1px 1px 0;
    cursor: default;
    line-height: 21px;
    padding: 0 3px;
    vertical-align: top;
    white-space: nowrap;
}
.grid .gridHeader th.hover, .grid .gridHeader th.thSelected {
    background-position: 0 -50px;
}
.grid .gridTbody td {
    border-bottom: 1px solid #ededed;
    border-right: 1px solid #ededed;
    line-height: 21px;
    overflow: hidden;
    padding: 0 3px;
    vertical-align: middle;
}
.grid .gridTbody td div {
    display: block;
    height: 21px;
    line-height: 21px;
    overflow: hidden;
    white-space: nowrap;
}
.grid .gridTbody td div a {
    line-height: 21px;
}
.grid .gridRow {
    border-style: solid;
    border-width: 0 0 1px;
    cursor: default;
}
.grid .gridRow td.tdLast {
    border-right: 1px solid;
    padding: 0 4px 0 5px;
}
.grid .gridCol {
    display: block;
    height: 21px;
    line-height: 21px;
    overflow: hidden;
    white-space: nowrap;
    width: 100%;
}
.grid .gridTbody .selected td {
    border-bottom-style: dotted;
    border-bottom-width: 1px;
}
.grid .gridTbody .selected .tdSelected {
}
.grid .gridTbody .tdSelected {
}
.grid .resizeMarker, .grid .resizeProxy {
    display: block;
    overflow: hidden;
    position: absolute;
    width: 1px;
}
.grid .left {
    text-align: left;
}
.grid .right {
    text-align: right;
}
.grid .center {
    text-align: center;
}
table.list {
    border: 1px solid #ededed;
    border-collapse: collapse;
}
table.list thead tr {
    background: #f0eff0 url("../default/images/grid/tableth.png") repeat-x scroll 0 0;
}
table.list th {
    border-bottom: 1px solid #d0d0d0;
    border-right: 1px solid #d0d0d0;
    font-weight: bolder;
    line-height: 21px;
    padding: 1px 2px;
}
table.list td {
    border-right: 1px solid #ededed;
    line-height: 21px;
    padding: 1px 2px;
}
table.list th.asc, .grid .gridHeader th.asc {
    background: rgba(0, 0, 0, 0) url("../default/images/order_up.gif") no-repeat scroll right center;
    cursor: pointer;
}
table.list th.desc, .grid .gridHeader th.desc {
    background: rgba(0, 0, 0, 0) url("../default/images/order_down.gif") no-repeat scroll right center;
    cursor: pointer;
}
table.list tbody {
    background-color: #fff;
}
table.list .right {
    text-align: right;
}
table.list .trbg {
    background-color: #f8f8f8;
}
table.list .hot {
    background-color: #fff5c0;
}
table.list .hover {
    background-color: #e4f5ff;
}
table.list .selected {
    background-color: #7cc5e5;
    border-color: #b8d0d6;
}
table.list a {
    color: #3c7fb1;
    font-size: 11px;
    line-height: 20px;
}
table.list a:hover {
    line-height: 20px;
    text-decoration: underline;
}
table.list td span.error {
    z-index: -1;
}
table.nowrap tbody tr {
    border-bottom: 1px solid #ededed;
}
#taskbar {
    background-color: #112746;
    background-repeat: repeat-x;
    border-color: #0f3255;
    border-style: solid;
    border-width: 0;
    height: 29px;
    overflow: hidden;
    position: absolute;
    z-index: 30;
}
#taskbar .taskbarContent {
    display: block;
    height: 29px;
    overflow: hidden;
    position: relative;
}
#taskbar .taskbarMargin {
    margin: 0 20px;
}
#taskbar ul {
    position: absolute;
    width: 10000px;
}
#taskbar li {
    background-position: 0 -50px;
    cursor: pointer;
    float: left;
    height: 27px;
    margin-left: 2px;
    padding-left: 5px;
    position: relative;
}
#taskbar li .taskbutton {
    background-position: 100% -100px;
    display: block;
    float: left;
    height: 27px;
    overflow: hidden;
}
#taskbar li .taskbutton span {
    background-position: 3px -42px;
    display: block;
    height: 27px;
    line-height: 29px;
    overflow: hidden;
    padding: 0 25px 0 20px;
    width: 70px;
}
#taskbar .selected {
    background-position: 0 -250px;
}
#taskbar .selected .taskbutton {
    background-position: 100% -300px;
}
#taskbar .hover {
    background-position: 0 -150px;
}
#taskbar .hover .taskbutton {
    background-position: 100% -200px;
}
#taskbar .close, #taskbar .restore, #taskbar .minimize {
    display: block;
    height: 11px;
    overflow: hidden;
    padding: 0;
    position: absolute;
    text-indent: -1000px;
    top: 5px;
    width: 11px;
}
#taskbar .close {
    right: 3px;
}
#taskbar .closeHover {
    background-position: 0 -50px;
}
#taskbar .restore {
    background-position: 0 -100px;
    right: 14px;
}
#taskbar .restoreHover {
    background-position: 0 -150px;
}
#taskbar .minimize {
    background-position: 0 -200px;
    right: 14px;
}
#taskbar .minimizeHover {
    background-position: 0 -250px;
}
#taskbar .taskbarLeft, #taskbar .taskbarRight {
    display: block;
    height: 29px;
    overflow: hidden;
    position: absolute;
    text-indent: -1000px;
    top: 0;
    width: 18px;
}
#taskbar .taskbarLeft {
    background-position: 0 0;
    cursor: pointer;
    left: 2px;
}
#taskbar .taskbarLeftHover {
    background-position: 0 -50px;
}
#taskbar .taskbarLeftDisabled {
    background-position: 0 -100px;
    cursor: default;
}
#taskbar .taskbarRight {
    background-position: 0 -150px;
    cursor: pointer;
    right: 2px;
}
#taskbar .taskbarRightHover {
    background-position: 0 -200px;
}
#taskbar .taskbarRightDisabled {
    background-position: 0 -250px;
    cursor: default;
}
.progressBar {
    display: block;
    font-weight: bold;
    height: 28px;
    left: 50%;
    line-height: 27px;
    margin-left: -74px;
    margin-top: -14px;
    padding: 10px 10px 10px 50px;
    position: absolute;
    text-align: left;
    top: 50%;
    width: 148px;
    z-index: 2001;
}
.background {
    background: #fff none repeat scroll 0 0;
    display: block;
    height: 100%;
    left: 0;
    opacity: 0.4;
    position: absolute;
    top: 0;
    width: 100%;
    z-index: 2000;
}
.alertBackground {
    background: #fff none repeat scroll 0 0;
    display: none;
    height: 100%;
    left: 0;
    opacity: 0.4;
    position: absolute;
    top: 0;
    width: 100%;
    z-index: 1010;
}
.dialogBackground {
    background: #fff none repeat scroll 0 0;
    display: none;
    height: 100%;
    left: 0;
    opacity: 0.4;
    position: absolute;
    top: 0;
    width: 100%;
    z-index: 900;
}
.textInput, input.focus, input.required, input.error, input.readonly, input.disabled, textarea.focus, textarea.required, textarea.error, textarea.readonly, textarea.disabled {
    border-style: solid;
    border-width: 1px;
    font-size: 12px;
    line-height: 15px;
    margin: 0;
    padding: 2px;
}
input.required, textarea.required {
    background-position: 100% 0;
}
input.gray, textarea.gray {
    color: gray;
}
select {
    -moz-border-bottom-colors: none;
    -moz-border-left-colors: none;
    -moz-border-right-colors: none;
    -moz-border-top-colors: none;
    border-color: #a2bac0 #b8d0d6 #b8d0d6 #a2bac0;
    border-image: none;
    border-style: solid;
    border-width: 1px;
}
.inputButton {
    display: block;
    float: left;
    height: 21px;
    overflow: hidden;
    text-indent: -1000px;
    width: 16px;
}
.inputButton:hover {
    background-position: 0 -50px;
}
.inputDateButton {
    background-position: 0 -100px;
    display: block;
    float: left;
    height: 21px;
    overflow: hidden;
    text-indent: -1000px;
    width: 16px;
}
.inputDateButton:hover {
    background-position: 0 -150px;
}
span.error {
    background: #f00 none repeat scroll 0 0;
    color: #fff;
    display: block;
    height: 21px;
    left: 318px;
    line-height: 21px;
    overflow: hidden;
    padding: 0 3px;
    position: absolute;
    top: 5px;
    width: 165px;
}
label.alt {
    display: block;
    line-height: 20px;
    overflow: hidden;
    position: absolute;
}
.nowrap span.error {
    position: static;
}
.button, .buttonActive, .buttonDisabled {
    display: block;
    float: left;
    height: 25px;
    overflow: hidden;
    padding-left: 5px;
}
.button span, .buttonDisabled span, .buttonActive span, .button .buttonContent, .buttonHover, .buttonHover .buttonContent, .buttonActive .buttonContent, .buttonDisabled .buttonContent {
    display: block;
    float: left;
    font-weight: bold;
    height: 25px;
    line-height: 25px;
    overflow: hidden;
}
.button {
    background-position: 0 0;
}
.button .buttonContent {
    background-position: 100% -50px;
    padding: 0 5px 0 0;
}
.button span {
    background-position: 100% -50px;
    cursor: pointer;
    padding: 0 10px 0 5px;
}
.button:hover {
    background-position: 0 -100px;
    text-decoration: none;
}
.button:hover span {
    background-position: 100% -150px;
}
.buttonHover {
    background-position: 0 -100px;
    padding-left: 5px;
}
.buttonHover .buttonContent {
    background-position: 100% -150px;
    padding: 0 5px 0 0;
}
.buttonActive {
    background-position: 0 -200px;
}
.buttonActive .buttonContent {
    background-position: 100% -250px;
    padding: 0 5px 0 0;
}
.buttonActive span {
    background-position: 100% -250px;
    cursor: pointer;
    padding: 0 10px 0 5px;
}
.buttonActive:hover {
    background-position: 0 -300px;
    text-decoration: none;
}
.buttonActive:hover span {
    background-position: 100% -350px;
}
.buttonActiveHover {
    background-position: 0 -300px;
}
.buttonActiveHover .buttonContent {
    background-position: 100% -350px;
    padding: 0 5px 0 0;
}
.buttonDisabled, .buttonDisabled:hover {
    background-position: 0 -400px;
    text-decoration: none;
}
.buttonDisabled span, .buttonDisabled:hover span {
    background-position: 100% -450px;
    cursor: default;
    padding: 0 10px 0 5px;
}
.buttonDisabled .buttonContent {
    background-position: 100% -450px;
    padding: 0 5px 0 0;
}
.button button, .buttonHover button, .buttonActive button, .buttonDisabled button {
    background: rgba(0, 0, 0, 0) none repeat scroll 0 0;
    border: 0 none;
    cursor: pointer;
    font-size: 12px;
    font-weight: bold;
    height: 25px;
    margin: 0;
    padding: 0 0 5px;
    width: auto;
}
.buttonDisabled button {
    cursor: default;
}
#layout {
    text-align: left;
}
#layout {
    display: block;
    height: 100%;
    position: relative;
}
#header {
    display: block;
    height: 50px;
    overflow: hidden;
    z-index: 30;
}
#leftside {
    left: 0;
    position: absolute;
    top: 55px;
    z-index: 20;
}
#sidebar {
    left: 5px;
    overflow: hidden;
    position: absolute;
    top: 0;
    width: 200px;
    z-index: 20;
}
#sidebar_s {
    display: none;
    left: 0;
    position: absolute;
    top: 0;
    width: 24px;
    z-index: 21;
}
#container {
    left: 210px;
    position: absolute;
    top: 55px;
}
#splitBar {
    cursor: col-resize;
    display: block;
    left: 205px;
    overflow: hidden;
    position: absolute;
    top: 55px;
    width: 5px;
    z-index: 20;
}
#splitBarProxy {
    border-style: solid;
    border-width: 1px;
    cursor: col-resize;
    display: none;
    left: 205px;
    overflow: hidden;
    position: absolute;
    top: 55px;
    width: 3px;
    z-index: 20;
}
#footer {
    bottom: 0;
    height: 21px;
    left: 0;
    padding: 0 5px;
    position: absolute;
    text-align: center;
    width: 100%;
    z-index: -1;
}
#header .headerNav {
    background-position: 100% -50px;
    background-repeat: no-repeat;
    height: 50px;
}
#header .logo {
    float: left;
    height: 50px;
    text-indent: -1000px;
    width: 250px;
}
#header .nav {
    display: block;
    height: 21px;
    position: absolute;
    right: 0;
    top: 8px;
    z-index: 31;
}
#header .nav li {
    float: left;
    line-height: 11px;
    margin-left: -1px;
    padding: 0 10px;
    position: relative;
}
#header .nav li a {
    line-height: 11px;
}
#header .nav ul {
    background: #fff none repeat scroll 0 0;
    border: 1px solid #06223e;
    display: none;
    overflow: hidden;
    position: absolute;
    right: 0;
    top: 20px;
    width: 230px;
}
#header .nav ul li {
    height: 21px;
    margin-top: 10px;
}
#header .nav ul li a {
    color: #000;
}
#header .nav .selected ul {
    display: block;
}
#header .themeList {
    position: absolute;
    right: 10px;
    top: 30px;
}
#header .themeList li {
    float: left;
    padding: 0 3px;
}
#header .themeList li div {
    cursor: pointer;
    display: block;
    height: 11px;
    overflow: hidden;
    text-indent: -100px;
    width: 13px;
}
#header .themeList li.default div {
    background-position: 0 0;
}
#header .themeList li.default .selected {
    background-position: 0 -20px;
}
#header .themeList li.green div {
    background-position: -20px 0;
}
#header .themeList li.green .selected {
    background-position: -20px -20px;
}
#header .themeList li.red div {
    background-position: -40px 0;
}
#header .themeList li.red .selected {
    background-position: -40px -20px;
}
#header .themeList li.purple div {
    background-position: -60px 0;
}
#header .themeList li.purple .selected {
    background-position: -60px -20px;
}
#header .themeList li.silver div {
    background-position: -80px 0;
}
#header .themeList li.silver .selected {
    background-position: -80px -20px;
}
#header .themeList li.azure div {
    background-position: -100px 0;
}
#header .themeList li.azure .selected {
    background-position: -100px -20px;
}
.toggleCollapse {
    background-repeat: repeat-x;
    border-style: solid;
    border-width: 1px 1px 0;
    display: block;
    height: 26px;
    overflow: hidden;
}
.toggleCollapse h2 {
    float: left;
    line-height: 26px;
    padding-left: 8px;
}
.toggleCollapse div {
    background-position: 100% -47px;
    cursor: pointer;
    display: block;
    float: right;
    height: 25px;
    overflow: hidden;
    text-indent: -100px;
    width: 25px;
}
.toggleCollapse div:hover {
    background-position: 100% -97px;
}
#sidebar_s .collapse {
    cursor: pointer;
    height: 430px;
}
#sidebar_s .toggleCollapse {
    background: rgba(0, 0, 0, 0) none repeat scroll 0 0;
    border: 0 none;
}
#sidebar_s .toggleCollapse div {
    background-position: -2px -147px;
    border: 0 none;
    width: 21px;
}
#sidebar_s .toggleCollapse div:hover {
    background-position: -2px -197px;
}
#navMenu {
    background-repeat: repeat-x;
    display: block;
    height: 35px;
    overflow: hidden;
    padding: 0 5px;
}
#navMenu ul {
    display: block;
    float: left;
    overflow: hidden;
    position: relative;
}
#navMenu li {
    background-position: 0 -50px;
    float: left;
    margin-left: -1px;
    position: relative;
}
#navMenu li a {
    float: left;
}
#navMenu li span {
    cursor: pointer;
    float: left;
    font-weight: bold;
    padding: 0 15px;
}
#navMenu li a, #navMenu li span {
    font-size: 14px;
    line-height: 35px;
}
#navMenu li.selected {
    z-index: 1;
}
#navMenu li.selected a, #navMenu li.selected a:hover {
    background-position: 100% -150px;
    text-decoration: none;
}
#navMenu li.selected span {
    background-position: 0 -100px;
    padding: 0 8px;
}
.page {
    display: block;
    float: left;
    overflow: hidden;
    width: 100%;
}
.pageHeader {
    border-style: solid;
    border-width: 0 0 1px;
    display: block;
    margin-bottom: 1px;
    overflow: auto;
    padding: 5px;
    position: relative;
}
.searchBar {
}
.searchBar ul.searchContent {
    display: block;
    height: 25px;
    overflow: hidden;
}
.searchBar ul.searchContent li {
    display: block;
    float: left;
    height: 21px;
    overflow: hidden;
    padding: 2px 0;
    width: 300px;
}
.searchBar label {
    float: left;
    line-height: 23px;
    padding: 0 5px;
    width: 80px;
}
.searchBar .searchContent td {
    height: 25px;
    padding-right: 20px;
    white-space: nowrap;
}
.searchBar .subBar {
    height: 25px;
}
.searchBar .subBar ul {
    float: right;
}
.searchBar .subBar li {
    float: left;
    margin-left: 5px;
}
.pageContent {
    display: block;
    overflow: auto;
    position: relative;
}
.dateRange input {
    width: 72px;
}
.dateRange .limit {
    line-height: 21px;
    text-align: center;
    width: 15px;
}
.pageForm {
    display: block;
    overflow: auto;
}
.pageFormContent {
    display: block;
    overflow: auto;
    padding: 10px 5px;
    position: relative;
}
.pageFormContent div.unit {
    clear: both;
    display: block;
    margin: 0;
    padding: 5px 0;
    position: relative;
}
.pageFormContent p {
    display: block;
    float: left;
    height: 21px;
    margin: 0;
    padding: 5px 0;
    position: relative;
    width: 400px;
}
.pageFormContent p.nowrap {
    height: auto;
    width: 100%;
}
.pageFormContent .radioGroup {
    display: block;
    float: left;
    overflow: hidden;
}
.pageFormContent label {
    float: left;
    line-height: 21px;
    padding: 0 5px;
    width: 140px;
}
.pageFormContent label.radioButton {
    float: left;
    line-height: 21px;
    padding: 0 10px 0 0;
    width: auto;
}
.pageFormContent .textInput {
    float: left;
}
.pageFormContent select {
    float: left;
}
.pageFormContent .inputInfo {
    line-height: 21px;
    padding: 0 5px;
}
.pageFormContent span.unit, .pageFormContent a.unit {
    line-height: 21px;
    padding: 0 5px;
}
.pageFormContent span.info {
    color: #7f7f7f;
    display: block;
    float: left;
    line-height: 21px;
}
.pageFormContent dl {
    display: block;
    float: left;
    height: 21px;
    margin: 0;
    padding: 5px 0;
    position: relative;
    width: 380px;
}
.pageFormContent dt {
    float: left;
    line-height: 21px;
    padding: 0 5px;
    width: 120px;
}
.pageFormContent dd {
    display: block;
    float: left;
    line-height: 21px;
    width: 220px;
}
.pageFormContent dl.nowrap, .nowrap dl {
    clear: both;
    height: auto;
    width: 100%;
}
.pageFormContent dl.nowrap dd, .nowrap dd {
    width: 560px;
}
fieldset {
    border: 1px dotted #b8d0d6;
    margin: 0 0 5px;
    padding: 3px;
}
fieldset legend {
    border: 1px dotted #b8d0d6;
    font-weight: bold;
    padding: 2px;
}
fieldset legend:hover {
    background-color: #dddddd;
}
.formBar {
    border-style: solid;
    border-width: 1px 0 0;
    clear: both;
    height: 30px;
    padding: 5px 5px 0;
}
.formBar ul {
    float: right;
}
.formBar li {
    float: left;
    margin-left: 5px;
}
.divider {
    border-style: dotted;
    border-width: 0 0 1px;
    clear: both;
    display: block;
    height: 1px;
    margin-bottom: 5px;
    overflow: hidden;
    padding: 4px 0 0;
    text-indent: -1000px;
    width: auto;
}
.dialog .pageHeader {
    border-style: solid;
    border-width: 1px;
    margin: 0;
}
.dialog .pageContent {
    background-color: #fff;
    border-style: solid;
    border-width: 0 1px;
}
.dialog .pageFormContent, .dialog .viewInfo {
    border-style: solid;
    border-width: 1px 0 0;
}
.dialog .formBar {
    border-style: solid;
    border-width: 1px 0;
}
.combox {
    background-position: 100% -25px;
    float: left;
    margin-right: 3px;
}
.combox select {
    display: none;
}
.combox .select {
    float: left;
}
.combox .select a {
    background-position: 100% -50px;
    display: block;
    float: left;
    font-size: 12px;
    height: 23px;
    line-height: 21px;
    overflow: hidden;
    padding: 0 22px 0 6px;
    text-decoration: none;
}
.comboxop {
    background: #fff none repeat scroll 0 0;
    border-style: solid;
    border-width: 1px 2px 2px 1px;
    display: none;
    left: 1px;
    padding: 4px;
    position: absolute;
    top: 22px;
    z-index: 1001;
}
.comboxop a {
    background: rgba(0, 0, 0, 0) none repeat scroll 0 0;
    color: #000;
    height: 21px;
    line-height: 21px;
    padding: 0 5px;
    text-align: left;
    width: 50px;
}
.comboxop a:hover {
    background: #e0e0e0 none repeat scroll 0 0;
}
.comboxop .selected {
    background: #e0e0e0 none repeat scroll 0 0;
}
.comboxop li {
    text-align: left;
}
h2.contentTitle {
    border-bottom: 1px solid #ccc;
    font-size: 14px;
    line-height: 30px;
    margin-bottom: 10px;
    padding: 0 10px;
}
h3.contentTitle {
    border-bottom: 1px solid #ccc;
    clear: both;
    font-size: 13px;
    line-height: 25px;
    margin-bottom: 5px;
}
.dialog h2.contentTitle {
    border: medium none;
}
#contextmenu {
    display: none;
    left: 0;
    position: absolute;
    top: 0;
    z-index: 500;
}
#contextmenu ul {
    background-color: #fff;
    border: 1px solid #999;
    list-style: outside none none;
    margin: 0;
    padding: 1px;
    width: 150px;
}
#contextmenu li {
    background-color: transparent;
    border: 1px solid #fff;
    color: #000;
    cursor: default;
    display: block;
    margin: 0;
    padding: 3px;
    text-align: left;
}
#contextmenu li.hover {
    background-color: #b6bdd2;
    border: 1px solid #0a246a;
}
#contextmenu li.disabled {
    color: #666;
}
#contextmenuShadow {
    background-color: #000;
    display: none;
    opacity: 0.2;
    position: absolute;
    z-index: 499;
}
#calendar {
    border: 1px solid #b3e4eb;
    display: none;
    margin: 0;
    position: absolute;
    width: 208px;
    z-index: 1001;
}
#calendar * {
    font-size: 12px;
    line-height: 18px;
    margin: 0;
    padding: 0;
}
#calendar .main {
    background: #ffffff none repeat scroll 0 0;
    margin: auto;
    padding: 2px;
    position: relative;
    text-align: center;
}
#calendar .head {
    background: #edf8ff none repeat scroll 0 0;
    border: 1px solid #bee9f0;
}
#calendar .head select {
    width: 60px;
}
#calendar .body {
    border: 1px solid #bee9f0;
    clear: both;
    margin: 2px 0;
    overflow: hidden;
    padding: 2px;
    position: relative;
}
#calendar .foot {
    background: #edf8ff none repeat scroll 0 0;
    border: 1px solid #bee9f0;
    padding: 1px;
    text-align: right;
}
#calendar .nodate .head, #calendar .nodate .body {
    display: none;
}
#calendar dl {
    clear: both;
    margin: auto;
    overflow: hidden;
}
#calendar dt, #calendar dd, #calendar span {
    border: 1px solid #fff;
    display: block;
    float: left;
    height: 18px;
    overflow: hidden;
    width: 26px;
}
#calendar dt {
    color: #666666;
    font-weight: bold;
    margin-top: 4px;
}
#calendar .days dd {
    cursor: pointer;
}
#calendar .days dd.other {
    color: #6a6aff;
}
#calendar .days dd.slt {
    background: #b3e4eb none repeat scroll 0 0;
    border: 1px solid #66cccc;
}
#calendar .days dd:hover {
    border: 1px solid #66cccc;
}
#calendar .days dd.disabled {
    background: #ccc none repeat scroll 0 0;
}
#calendar .close {
    border: 1px solid #ccc;
    cursor: pointer;
    display: block;
    font-size: 16px;
    height: 16px;
    text-align: center;
    width: 16px;
}
#calendar .clearBut, #calendar .okBut {
    background-color: #cfebee;
    border: 1px solid #38b1b9;
    color: #08575b;
    width: 40px;
}
#calendar .time {
    background-color: #fff;
    border-collapse: collapse;
    display: none;
    float: left;
}
#calendar .time td {
    border: 1px solid #61cad0;
    line-height: 16px;
}
#calendar .time .hh, #calendar .time .mm, #calendar .time .ss {
    border: medium none;
    height: 16px;
    width: 18px;
}
#calendar .time ul {
    list-style: outside none none;
}
#calendar .time .up, #calendar .time .down {
    border: 1px solid #bee9f0;
    cursor: pointer;
    font-size: 8px;
    height: 8px;
    line-height: 100%;
}
#calendar .tm {
    text-align: center;
}
#calendar .tm .hh, #calendar .tm .mm, #calendar .tm .ss {
    background-color: #ffffc0;
    border: 1px solid #a3c6c8;
    bottom: 34px;
    display: none;
    left: 4px;
    position: absolute;
    width: 120px;
}
#calendar .hh .hh, #calendar .mm .mm, #calendar .ss .ss {
    display: block;
}
#calendar .tm .hh li, #calendar .tm .mm li, #calendar .tm .ss li {
    cursor: pointer;
    display: block;
    float: left;
    line-height: 21px;
    width: 20px;
}
#calendar .tm .hh li:hover, #calendar .tm .mm li:hover, #calendar .tm .ss li:hover {
    background: #b3e4eb none repeat scroll 0 0;
}
#suggest {
    left: 0;
    position: absolute;
    top: 0;
    z-index: 2000;
}
#suggest ul {
    background-color: #fff;
    border: 1px solid #999;
    list-style: outside none none;
    margin: 0;
    padding: 1px;
    width: 150px;
}
#suggest li {
    background-color: transparent;
    border: 1px solid #fff;
    color: #000;
    cursor: default;
    display: block;
    margin: 0;
    padding: 3px;
    text-align: left;
}
#suggest li.selected {
    background-color: #b6bdd2;
    border: 1px solid #0a246a;
}
a.btnAdd, a.btnDel, a.btnView, a.btnEdit, a.btnSelect, a.btnInfo, a.btnAssign, a.btnLook, a.btnAttach {
    background: rgba(0, 0, 0, 0) url("../default/images/button/imgX.gif") no-repeat scroll 0 0;
    display: block;
    float: left;
    height: 20px;
    margin-right: 3px;
    overflow: hidden;
    text-indent: -1000px;
    width: 22px;
}
a.btnAdd {
    background-position: 0 0;
}
a.btnDel {
    background-position: -23px 0;
}
a.btnInfo {
    background-position: -46px 0;
}
a.btnAssign {
    background-position: -69px 0;
}
a.btnView {
    background-position: -115px 0;
}
a.btnEdit {
    background-position: -138px 0;
}
a.btnSelect {
    background-position: -92px 0;
}
a.btnLook {
    background-position: -161px 0;
}
a.btnAttach {
    background-position: -183px 0;
}
.center a.btnAdd, .center a.btnDel, .center a.btnView, .center a.btnEdit, .center a.btnSelect, .center a.btnInfo, .center a.btnAssign, .center a.btnLook, .center a.btnAttach {
    display: inline-block;
    float: none;
}
.right a.btnAdd, .right a.btnDel, .right a.btnView, .right a.btnEdit, .right a.btnSelect, .right a.btnInfo, .right a.btnAssign, .right a.btnLook, .right a.btnAttach {
    display: inline-block;
    float: none;
    text-indent: 1000px;
}
.viewInfo {
    padding: 10px 5px;
}
.viewInfo dl {
    display: block;
    float: left;
    height: 21px;
    margin: 0;
    padding: 5px 0;
    position: relative;
    width: 380px;
}
.viewInfo dt {
    float: left;
    line-height: 21px;
    padding: 0 5px;
    text-align: right;
    width: 120px;
}
.viewInfo dd {
    border-bottom: 1px dotted #999;
    display: block;
    float: left;
    line-height: 21px;
    min-height: 21px;
    width: 220px;
}
.viewInfo dl.nowrap {
    clear: both;
    height: auto;
    width: 100%;
}
.viewInfo dl.nowrap dt {
}
.viewInfo dl.nowrap dd {
    width: auto;
}
.dialogContent .viewInfo {
    background-color: #fff;
}
.pageContent .panel {
    clear: both;
    margin: 5px;
}
.sortDragPlaceholder {
    border: 1px dashed #ccc;
}
#printBox {
    display: none;
}

.uploader {
    -moz-user-select: none;
    border: 2px dotted #a5a5c7;
    color: #92aab0;
    cursor: default;
    font-size: 200%;
    margin-bottom: 10px;
    padding: 30px 0;
    text-align: center;
    vertical-align: middle;
    width: 100%;
}
.uploader div.or {
    color: #c0c0c0;
    font-size: 50%;
    font-weight: bold;
    padding: 10px;
}
.uploader div.browser label {
    background-color: #5a7bc2;
    border-radius: 2px;
    box-shadow: 2px 2px 2px #888888;
    color: white;
    cursor: pointer;
    display: block;
    font-size: 40%;
    font-weight: bold;
    margin: 20px auto 0;
    overflow: hidden;
    padding: 6px 0;
    position: relative;
    width: 300px;
}
.uploader div.browser span {
    cursor: pointer;
}
.uploader div.browser input {
    -moz-border-bottom-colors: none;
    -moz-border-left-colors: none;
    -moz-border-right-colors: none;
    -moz-border-top-colors: none;
    border-color: transparent;
    border-image: none;
    border-style: solid;
    border-width: 0 0 100px 200px;
    cursor: pointer;
    direction: ltr;
    margin: 0;
    opacity: 0;
    position: absolute;
    right: 0;
    top: 0;
    transform: translate(-300px, 0px) scale(4);
}
.uploader div.browser label:hover {
    background-color: #427fed;
}
.progress {
    background-color: #f5f5f5;
    border-radius: 4px;
    box-shadow: 0 1px 2px rgba(0, 0, 0, 0.1) inset;
    height: 20px;
    margin-bottom: 20px;
    overflow: hidden;
}
.progress-bar {
    background-color: #428bca;
    box-shadow: 0 -1px 0 rgba(0, 0, 0, 0.15) inset;
    color: #fff;
    float: left;
    font-size: 12px;
    height: 100%;
    line-height: 20px;
    text-align: center;
    transition: width 0.6s ease 0s;
    width: 0;
}
.progress-striped .progress-bar {
    background-image: linear-gradient(45deg, rgba(255, 255, 255, 0.15) 25%, transparent 25%, transparent 50%, rgba(255, 255, 255, 0.15) 50%, rgba(255, 255, 255, 0.15) 75%, transparent 75%, transparent);
    background-size: 40px 40px;
}
.progress.active .progress-bar {
    animation: 2s linear 0s normal none infinite running progress-bar-stripes;
}
.progress-bar-success {
    background-color: #5cb85c;
}
.progress-striped .progress-bar-success {
    background-image: linear-gradient(45deg, rgba(255, 255, 255, 0.15) 25%, transparent 25%, transparent 50%, rgba(255, 255, 255, 0.15) 50%, rgba(255, 255, 255, 0.15) 75%, transparent 75%, transparent);
}
.progress-bar-info {
    background-color: #5bc0de;
}
.progress-striped .progress-bar-info {
    background-image: linear-gradient(45deg, rgba(255, 255, 255, 0.15) 25%, transparent 25%, transparent 50%, rgba(255, 255, 255, 0.15) 50%, rgba(255, 255, 255, 0.15) 75%, transparent 75%, transparent);
}
.progress-bar-warning {
    background-color: #f0ad4e;
}
.progress-striped .progress-bar-warning {
    background-image: linear-gradient(45deg, rgba(255, 255, 255, 0.15) 25%, transparent 25%, transparent 50%, rgba(255, 255, 255, 0.15) 50%, rgba(255, 255, 255, 0.15) 75%, transparent 75%, transparent);
}
.progress-bar-danger {
    background-color: #d9534f;
}
.progress-striped .progress-bar-danger {
    background-image: linear-gradient(45deg, rgba(255, 255, 255, 0.15) 25%, transparent 25%, transparent 50%, rgba(255, 255, 255, 0.15) 50%, rgba(255, 255, 255, 0.15) 75%, transparent 75%, transparent);
}

#demo-nav {
    background: rgba(0, 0, 0, 0) url("images/dark-bg.png") repeat scroll 0 0;
    min-height: 40px;
}
#demo-nav a {
    font-size: 12px;
    font-weight: bold;
    padding: 10px 15px;
}
#demo-nav a.home {
    color: #ffbf00;
    font-size: 16px;
    padding-left: 0;
}
.demo-wrapper {
}
.demo-columns {
}
.demo-note {
    color: gray;
    font-style: italic;
}
.demo-footer {
    border-top: 1px solid #e5e5e5;
    color: #777;
    padding-top: 19px;
}
.demo-panel-debug {
    cursor: default;
    max-height: 90px;
    min-height: 90px;
    overflow: auto;
}
.demo-panel-debug ul {
    list-style-type: none;
    margin-bottom: 0;
    padding-left: 0;
}
.demo-panel-debug ul li, .demo-panel-debug ul li.demo-default {
    color: gray;
}
.demo-panel-debug ul li.demo-error {
    color: #990000;
}
.demo-panel-debug ul li.demo-success {
    color: #009900;
}
.demo-panel-debug ul li.demo-info {
    color: #000099;
}
.demo-panel-files {
    cursor: default;
    max-height: 290px;
    min-height: 290px;
    overflow: auto;
}
.demo-file-id {
    font-weight: bold;
}
.demo-file-size {
    color: gray;
    font-size: 90%;
    font-style: italic;
}
.demo-file-status, .demo-file-status-default {
    color: gray;
}
.demo-file-status-error {
    color: #990000;
}
.demo-file-status-success {
    color: #009900;
}
.demo-image-preview {
    border-radius: 4px;
    float: left;
    height: 56px;
    margin-right: 10px;
    margin-top: 4px;
    width: 56px;
}

</style>
<title>下载列表</title>
</head>
<script src="<%=path %>/dwz/js/jquery-1.7.2.js" type="text/javascript"></script>
<script src="<%=path %>/dwz/js/jquery-1.7.2.min.js" type="text/javascript"></script>
<script type="text/javascript">
function selectAcceptAll(){  
    if ($("#selectAcceptAll").attr("checked")) {  
    	$("input[name='selectAcceptAll_ids']").each(function(){
	        $(this).attr("checked", true);  
    	})
    } else {  
    	$("input[name='selectAcceptAll_ids']").each(function(){
	        $(this).attr("checked", false);  
    	})
    }  
}
$(document).ready(function (){
	var data = '${sb}';
	var arr = data.split(';');
	var str = '<br/>';
	for(var i=0;i<arr.length;i++){
		str = str +'<span style="color:red;">' + arr[i] + '</span><br/>';
	}
	if(str){
		$("#showData").html(str);
	}else{
		$("#showData").html("无");
	}
	var data = '${transFail}';
	var arr = data.split(';');
	var str = '<br/>';
	for(var i=0;i<arr.length;i++){
		str = str +'<span style="color:red;">' + arr[i] + '</span><br/>';
	}
	if(str){
		$("#showData1").html(str);
	}else{
		$("#showData1").html("无");
	}
});
function showData(){
	$("#div_data").toggle();
}
function showData1(){
	$("#div_data1").toggle();
}
function showData2(){
	$("#div_data2").toggle();
}
function goExportTransInfo(){
	location.href="<%=path %>/transchangemgr/exportTransInfo?sbr=${sbr}";
}

function batchAcceptCheckUpdateById(){
	var str=""; 
	 $("input[name='selectAcceptAll_ids']:checked").each(function(){ 
		str+="tradeNos="+$(this).val()+"&"; 
	});
	if(!str){
		alert("请选择上传的数据");
	}else{
		$("#upupup").attr("href","#");
		var url="<%=path %>"+"/faffmgr/batchAcceptCheckUpdateByTradeNos";
		$.ajax({
			url:url,
			type:'post',
			dataType:'json',
			data:str,
			success:function(data){
				var str = data.message.split(",")
				alert(str[0]);
				for(var i=1;i<str.length;i++){
					if(str[i]){
						var tr = $("#ids"+str[i]).parent().parent();
						tr.remove();
					}
				}
			}
		});
	}
}
</script>
<body>
<div id="div_data" class="pageContent" style="display:block;">
	<div class="panelBar"><br/><h3 style="color:red;">未匹配数据</h3></div>
	<div id="showData"></div>
</div>
<div id="div_data1" class="pageContent" style="display:block;">
	<div class="panelBar"><br/><h3 style="color:red;">异常数据</h3></div>
	<div id="showData1"></div>
</div>
<div class="pageContent" style="width:100%;height:100%;clear:#183152;">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a id="upupup" class="edit" href="javaScript:batchAcceptCheckUpdateById()"><span style="color:green;">批量上传</span></a></li>
			<li class="line">line</li>
			<li><a class="edit" href="javaScript:showData()"><span style="color:green;">显示未匹配数据</span></a></li>
			<li><a class="edit" href="javaScript:showData1()"><span style="color:green;">显示异常数据</span></a></li>
			<li><span style="color:red;">警告：选中的数据会直接上传 总金额：${totalAmount}</span></li>
		</ul>
	</div>
	<div id="w_list_print_list">
	<table class="list" width="100%" targetType="navTab" layoutH="140" style="text-align: center;">
		<thead>
			<tr>
				<th><input type="checkbox" name="selectAll" id="selectAcceptAll" onclick="selectAcceptAll()"></th>
				<c:if test="${faffUploadType=='sales' }">
				<th>商户号</th>
				<th>终端号</th>
				<th>流水号</th>
				<th>交易时间</th>
				<th>银行交易金额</th>
				<th>银行结算金额</th>
				<th>差额</th>
				<th>交易状态</th>
				<th>勾兑状态</th>
				<th>入账状态</th>
				<th>通道名称</th>
				</c:if>
				<c:if test="${faffUploadType=='dis' }">
				<th>商户号</th>
				<th>终端号</th>
				<th>流水号</th>
				<th>交易时间</th>
				<th>银行交易金额</th>
				<th>交易状态</th>
				<th>拒付状态</th>
				<th>拒付金额</th>
				<th>银行核对拒付金额</th>
				<th>拒付时间</th>
				<th>通道名称</th>
				</c:if>
				<c:if test="${faffUploadType=='refund' }">
				<th>商户号</th>
				<th>终端号</th>
				<th>流水号</th>
				<th>交易时间</th>
				<th>银行交易金额</th>
				<th>交易状态</th>
				<th>退款状态</th>
				<th>退款金额</th>
				<th>银行核对退款金额</th>
				<th>退款时间</th>
				<th>通道名称</th>
				</c:if>
				<c:if test="${faffUploadType=='bond' }">
				<th>商户号</th>
				<th>终端号</th>
				<th>流水号</th>
				<th>交易时间</th>
				<th>银行交易金额</th>
				<th>交易状态</th>
				<th>交易数据核对状态</th>
				<th>银行结算金额</th>
				<th>通道名称</th>
				</c:if>
			</tr>
		</thead>
		<tbody>
		<c:if test="${faffUploadType=='sales' }">
			<c:forEach items="${successChecked}" var="trans">
				<tr  target="transCheckID" rel="${trans.tradeNo }"   >
					<td>
						<input type="hidden" id="ids${trans.tradeNo}">
						<input type="checkbox" name="selectAcceptAll_ids" value="${trans.tradeNo}">
					</td>
					<td>${trans.merNo }</td>
					<td>${trans.terNo }</td>
					<td>${trans.tradeNo }</td>
					<td>${trans.transDate }</td>
					<td>${trans.bankCurrency }&nbsp;${trans.bankTransAmount }</td>
					<td>${trans.bankCurrency }&nbsp;${trans.faffAmount }</td>
					<td><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${trans.bankTransAmount-trans.faffAmount }"/> </td>
					<td>${trans.respCode=='00'?'<font color=red>支付成功<font>':'支付失败' }</td>
					<td>${trans.isChecked=='1'?'<font color=red>已勾兑<font>':'未勾兑' }</td>
					<td>${trans.access=='1'?'<font color=red>已入账<font>':'未入账' }</td>
					<td>${trans.currencyName }</td>
				</tr>
			</c:forEach>
		</c:if>
		<c:if test="${faffUploadType=='dis' }">
			<c:forEach items="${successChecked}" var="trans">
				<tr  target="transCheckID" rel="${trans.tradeNo }"   >
					<td>
						<input type="hidden" id="ids${trans.tradeNo}">
						<input type="checkbox" name="selectAcceptAll_ids" value="${trans.tradeNo}">
					</td>
					<td>${trans.merNo }</td>
					<td>${trans.terNo }</td>
					<td>${trans.tradeNo }</td>
					<td>${trans.transDate }</td>
					<td>${trans.bankCurrency }&nbsp;${trans.bankTransAmount }</td>
					<td>${trans.respCode=='00'?'<font color=red>支付成功<font>':'支付失败' }</td>
					<td>${trans.isDis>0?'<font color=red>已拒付<font>':'未拒付' }</td>
					<td>${trans.bankCurrency }&nbsp;<fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${trans.disAmount }"/></td>
					<td>${trans.bankCurrency }&nbsp;<fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${trans.faffAmount }"/></td>
					<td>${trans.disDate }</td>
					<td>${trans.currencyName }</td>
				</tr>
			</c:forEach>
		</c:if>
		<c:if test="${faffUploadType=='refund' }">
			<c:forEach items="${successChecked}" var="trans">
				<tr  target="transCheckID" rel="${trans.tradeNo }"   >
					<td>
						<input type="hidden" id="ids${trans.tradeNo}">
						<input type="checkbox" name="selectAcceptAll_ids" value="${trans.tradeNo}">
					</td>
					<td>${trans.merNo }</td>
					<td>${trans.terNo }</td>
					<td>${trans.tradeNo }</td>
					<td>${trans.transDate }</td>
					<td>${trans.bankCurrency }&nbsp;${trans.bankTransAmount }</td>
					<td>${trans.respCode=='00'?'<font color=red>支付成功<font>':'支付失败' }</td>
					<td>${trans.isRefund>0?'<font color=red>已退款<font>':'未退款' }</td>
					<td>${trans.bankCurrency }&nbsp;<fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${trans.refundAmount }"/></td>
					<td>${trans.bankCurrency }&nbsp;<fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${trans.faffAmount }"/></td>
					<td>${trans.refundDate }</td>
					<td>${trans.currencyName }</td>
				</tr>
			</c:forEach>
		</c:if>
		<c:if test="${faffUploadType=='bond' }">
			<c:forEach items="${successChecked}" var="trans">
				<tr  target="transCheckID" rel="${trans.tradeNo }"   >
					<td>
						<input type="hidden" id="ids${trans.tradeNo}">
						<input type="checkbox" name="selectAcceptAll_ids" value="${trans.tradeNo}">
					</td>
					<td>${trans.merNo }</td>
					<td>${trans.terNo }</td>
					<td>${trans.tradeNo }</td>
					<td>${trans.transDate }</td>
					<td>${trans.bankCurrency }&nbsp;${trans.bankTransAmount }</td>
					<td>${trans.respCode=='00'?'<font color=red>支付成功<font>':'支付失败' }</td>
					<td>${trans.isFaff>0?'<font color=red>已核对<font>':'未核对' }</td>
					<td>${trans.bankCurrency }&nbsp;${trans.faffAmount }</td>
					<td>${trans.currencyName }</td>
				</tr>
			</c:forEach>
		</c:if>
		</tbody>
	</table>
	</div>
</div>
</body>
</html>