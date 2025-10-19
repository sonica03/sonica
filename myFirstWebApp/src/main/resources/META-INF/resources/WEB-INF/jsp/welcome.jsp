<%@ include file="common/header.jspf" %>
<%@ include file="common/navigation.jspf" %>
	<div class="container">
		<h1>My name is ${name}</h1>
			<a href="list-todos">Managing</a> My Todo List
			<c:url value="todo.jpg" var="imageUrl"/>
    <img src="${imageUrl}" 
         alt="Todo Picture" 
         class="mt-3"  <%-- ADD THIS CLASS for top margin --%>
         style="width: 1200px; height: auto;">
	</div>
<%@ include file="common/footer.jspf" %>