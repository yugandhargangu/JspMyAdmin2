<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Upload</title>
</head>
<body>
	<form action="${pageContext.request.contextPath}/table_upload.html"
		method="post" enctype="multipart/form-data">
		<input type="hidden" name="values" value="value1">
		<input type="hidden" name="columns" value="columns1"> 
		<input type="hidden" name="changes" value="changes1">
		<input type="hidden" name="columns" value="columns2"> 
		<input type="hidden" name="changes" value="changes2"> 
		<input type="file" name="values" id="fileToUpload"> 
		<input type="file" name="values" id="fileToUpload1">
		<input type="hidden" name="columns" value=""> 
		<input type="submit" value="Upload Image" name="submit">
	</form>
</body>
</html>