<jsp:directive.include file="includes/sso_top.jsp" />
		<div>
			<c:if test="${not empty param['url']}">
			<script>
					window.location.href="${fn:escapeXml(param.url)}";
			</script>
			</c:if>
		</div>
<jsp:directive.include file="includes/sso_bottom.jsp" />