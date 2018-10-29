<jsp:directive.include file="includes/sso_top.jsp" />
		<div>
			<script>
					window.location.href="${fn:escapeXml(param.url)}";
			</script>
		</div>
<jsp:directive.include file="includes/sso_bottom.jsp" />