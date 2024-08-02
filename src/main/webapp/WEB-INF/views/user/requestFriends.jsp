<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>${userId}</h1>
	<c:if test="${not empty requestFriendList}">
		<c:forEach var="user" items="${requestFriendList}">
			<p>${user.userId} ${user.userName}
        		<button type="button" onclick="acceptFriend('${user.userId}')">친구받기</button>
        	</p>
		</c:forEach>
        
    </c:if>
    
    
    <form action="/accept" method="post" id="frm-acceptFriend">
    	<input type="hidden" name="myId" id="myId" value="${userId}">
    	<input type="hidden" name="acceptUserId" id="acceptUserId">
    </form>
    
    
    
   <script>
	   function acceptFriend(userId){
		   console.log(userId);
		   console.log( document.getElementById('myId').value);
		   
		   document.getElementById('acceptUserId').value = userId;
		   document.getElementById('frm-acceptFriend').submit();
	   }
   </script>
    
    
</body>
</html>