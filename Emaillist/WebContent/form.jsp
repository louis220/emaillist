<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메일링 리스트 가입</title>
</head>
<body>
	<h1> 메일링 리스트 가입</h1>
	
			method = "POST">
		<label for = "last_name">성</label>
		<input type="text" name="last_name">
		<br>
		<label for = "first_name">이름</label>
		<input type="text" name="first_name">
		<br>
		<label for = "email">이메일</label>
		<input type="text" name="email">
		<br>
		<input type = "reset"
			value="다시 작성">
		<input type = "submit"
		value = "등록">
		</form>

</body>
</html>