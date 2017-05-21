<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="global.jsp" %>
<html>
<head>
    <title>CSDN标注</title>
    <style>
        table.gridtable {
            width: 500px;
            margin: 0 auto;
            color: #333;
            border: 1px #666 solid;
            border-collapse: collapse;
        }

        table.gridtable th {
            padding: 8px;
            border: 1px solid #666;
            background-color: #dedede;
        }

        table.gridtable td {
            border: 1px solid #666;
            padding: 6px 8px;
            text-align: center;
        }
    </style>
</head>
<body>
<div class="container">
    <table class="gridtable">
        <tr>
            <th>用户</th>
            <th>任务数</th>
            <th>完成数</th>
            <th>跳过数</th>
            <th>剩余数</th>
        </tr>
        <c:set var="total" value="0"></c:set>
        <c:set var="finished" value="0"></c:set>
        <c:set var="escape" value="0"></c:set>
        <c:forEach items="${nameList}" var="name">
            <tr>
                <td><a href="${ctx}/bz?name=${name}">${name}</a></td>
                <td>${workMap[name].total}</td>
                <td>${workMap[name].finished}</td>
                <td>${workMap[name].escape}</td>
                <td>${workMap[name].total- workMap[name].finished - workMap[name].escape}</td>
            </tr>
            <c:set var="total" value="${total + workMap[name].total}"></c:set>
            <c:set var="finished" value="${finished + workMap[name].finished}"></c:set>
            <c:set var="escape" value="${escape + workMap[name].escape}"></c:set>
        </c:forEach>
        <tr>
            <td>总计</td>
            <td>${total}</td>
            <td>${finished}</td>
            <td>${escape}</td>
            <td>${total - finished - escape}</td>
        </tr>
    </table>
    <form action="${ctx}/import" enctype="multipart/form-data" method="post">
        <label for="upFileInput">导入文件：</label>
        <input type="file" id="upFileInput" name="upfile"/>
        <input type="submit" value="导入">
    </form>
</div>
</body>
</html>
