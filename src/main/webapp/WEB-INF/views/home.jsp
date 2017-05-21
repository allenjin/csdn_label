<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="global.jsp" %>
<html>
<head>
    <title>CSDN标注</title>
    <style>
        .container {
            position: relative;
        }

        .box {
            height: 100px;
        }

        .line {
            float: left;
            padding: 5px 10px;
        }

        #submitBtn {
            background: #00aa7f;
            padding: 3px 5px;
            font-size: 16px;
            cursor: pointer;
            outline: none;
            border: none;
            color: #fff;
            border-radius: 5px;
        }

        #submitBtn:hover {
            background: #107c10;
        }

        .label-list {
            padding: 0;
            margin: 0;
            list-style: none;
        }

        .label-list li {
            display: inline-block;
            margin: 5px;
            padding: 5px 8px;
            background: #3B5998;
            border-radius: 8px;
            color: #fff;
            cursor: pointer;
        }

        #pageFrame {
            border: 1px solid #999;
            left: 0;
            position: absolute;
            width: 100%;
            height: 600px;
        }

        #jumpBtn {
            position: absolute;
            top: 10px;
            right: 10px;
            margin-left: 20px;
            background: #DD4B39;
            border-radius: 5px;
            border: none;
            color: #fff;
            cursor: pointer;
            outline: none;
        }

        #jumpBtn:hover {
            background: #bb2310;
        }

        #submitForm {
            position: relative;
        }

    </style>
</head>
<body>
<div class="container">
    <div class="box">
        当前序号:${not empty item ? item.id: mark.sn}, 用户名:${name}
        <ul class="label-list">
            <c:if test="${not empty item}">
                <li>${item.key1}</li>
                <li>${item.key2}</li>
                <li>${item.key3}</li>
                <li>${item.key4}</li>
                <li>${item.key5}</li>
                <li>${item.key6}</li>
                <li>${item.key7}</li>
                <li>${item.key8}</li>
                <li>${item.key9}</li>
                <li>${item.key10}</li>
            </c:if>
        </ul>
        <form id="submitForm" action="${ctx}/mark" method="post">
            <input type="hidden" name="username" value="${name}">
            <input type="hidden" name="sn" value="${not empty item ? item.id: mark.sn}">

            <div class="line">
                <label for="ztc1Input">主题词1</label>
                <input type="text" id="ztc1Input" name="ztc1" value="${mark.key1}" width="100px">
            </div>
            <div class="line">
                <label for="ztc2Input">主题词2</label>
                <input type="text" id="ztc2Input" name="ztc2" value="${mark.key2}" width="100px">
            </div>
            <div class="line">
                <label for="ztc3Input">主题词3</label>
                <input type="text" id="ztc3Input" name="ztc3" value="${mark.key3}" width="100px">
            </div>
            <div class="line">
                <button id="submitBtn">提交</button>
                <button id="jumpBtn">跳过</button>
            </div>
        </form>
    </div>
    <iframe src="${item.address}" id="pageFrame" frameborder="0"></iframe>
</div>
</body>
<script src="https://code.jquery.com/jquery-1.12.4.min.js"
        integrity="sha256-ZosEbRLbNQzLpnKIkEdrPv7lOy9C27hHQ+Xp8a4MxAQ=" crossorigin="anonymous"></script>
<script type="text/javascript">
    (function () {

        $('.label-list').on('click', 'li', function (e) {
            var text = $(this).text();
            if (checkNull($('#ztc1Input').val())) {
                $('#ztc1Input').val(text);
            } else if (checkNull($('#ztc2Input').val())) {
                $('#ztc2Input').val(text);
            } else if (checkNull($('#ztc3Input').val())) {
                $('#ztc3Input').val(text);
            }
        });
        $('#submitBtn').click(function (e) {
            e.preventDefault();
            if (!checkNull($('#ztc1Input').val()) && !checkNull($('#ztc2Input').val()) && !checkNull($('#ztc3Input').val())) {
                $('#submitForm').submit();
            } else {
                alert('主题词不能为空');
            }
        });

        $('#jumpBtn').click(function (e) {
            e.preventDefault();
            var r = confirm("确认跳过");
            if (r === true) {
                $('#submitForm').submit();
            }
        });
        function checkNull(input) {
            if (input == null || input == "") {
                return true;
            }
            return false;
        }
    })
    ();
</script>
</html>
