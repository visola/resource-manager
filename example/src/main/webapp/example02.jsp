<%@taglib prefix="b" uri="http://www.bearprogrammer.com/web/resources" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Resource Test</title>
		<b:resources>
			js/01.js
			js/02.js
			css/01.css
			css/02.css
		</b:resources>
	</head>
	<body>
		<jsp:include page="/WEB-INF/includes/menu.jsp" />
		<p>
			<!-- Some sample text. -->
			Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer nec odio. 
			Praesent libero. Sed cursus ante dapibus diam. Sed nisi. Nulla quis sem at 
			nibh elementum imperdiet. Duis sagittis ipsum. Praesent mauris. Fusce nec 
			tellus sed augue semper porta. Mauris massa. Vestibulum lacinia arcu eget 
			nulla. Class aptent taciti sociosqu ad litora torquent per conubia nostra, 
			per inceptos himenaeos.
		</p>
		<p class="red">If 01.css was loaded, this should be red.</p>
		<p class="green">If 02.css was loaded, this should be green.</p>
	</body>
</html>