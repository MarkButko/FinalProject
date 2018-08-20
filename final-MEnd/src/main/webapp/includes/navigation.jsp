<nav class="navbar navbar-default">
	<div class="container">
		<div class="navbar-header">
			<a href="#" class="navbar-brand"><fmt:message key="nav.site.name"/></a>
		</div>
		<div>
			<form id="lang-form" class="form-inline">
				<button class="btn btn-link" name="language" type="submit" value="en" ${language == 'en' ? 'selected' : ''}>EN</button> |
				<button	class="btn btn-link" name="language" type="submit" value="ru" ${language == 'ru' ? 'selected' : ''}>RU</button>
			</form>
		</div>
	</div>
</nav>