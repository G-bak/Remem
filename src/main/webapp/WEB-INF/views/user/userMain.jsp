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
	<h1>userMain 페이지</h1>
	
	
	<h2>${userId}</h2>
	
	
	<h3>친구 리스트</h3>
	<c:if test="${not empty myFriendList}">
		<c:forEach var="user" items="${myFriendList}">
			<p onclick="friendDetail('${user.userId}')">${user.userId} ${user.userName}</p>
		</c:forEach>
        
    </c:if>
	
	<form action="/myFriend" method="post" id="frm-friendDetail">
		<input type="hidden" name="friendDetailId" id="friendDetailId">
	</form>
	
	
	
	
	
	
	
	
	<form action="" method="post">
		<label><input name="serchUserId" placeholder="친구이름검색"></label>
		<button type="submit">검색</button>
	</form>
	
	
	
	<c:if test="${not empty user}">
<!-- 	자신의 아이디를 검색했을 경우 예외처리 -->
	
        <p>${user.userId} ${user.userName}
        	<c:if test="${!(isFriend == true) }">
        		<a href="/join/friend?myId=${userId}&friendId=${user.userId}"><button type="button">친구추가</button></a>
        	</c:if>
        </p>
    </c:if>
    
    
    <br/>
    <button type="button" id="btn-requestfriend" onclick="requestFriendsConfirm()">받은신청함</button>
    
    <form action="/requestFriends" method="post" id="frm-requestFriends">
    	<input type="hidden" name="confirmId" value="${userId}">
    </form>
    

	<!-- 친구 추천 -->
	
	<h1>친구추천</h1>
	<c:if test="${not empty friendRecommendList}">
		<c:forEach var="user" items="${friendRecommendList}">
			<p >${user.userId} ${user.userName}
				
					<a href="/join/friend?myId=${userId}&friendId=${user.userId}"><button type="button">친구추가</button></a>
				
			</p>
		</c:forEach>
        
    </c:if>
	


















 
	<script>
		function requestFriendsConfirm(){
			document.querySelector("#frm-requestFriends").submit();
		}
		
		function friendDetail(friendId){
			
			 document.querySelector("#friendDetailId").value = friendId;
	         console.log(document.querySelector("#friendDetailId").value + "****" + friendId);
	         document.querySelector("#frm-friendDetail").submit();
			
		}
		
	</script>
	
	
</body>
</html>