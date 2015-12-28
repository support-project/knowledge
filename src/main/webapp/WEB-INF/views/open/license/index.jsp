<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"
	errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>

<c:import url="/WEB-INF/views/commons/layout/layoutMain.jsp">

<c:param name="PARAM_HEAD">
<link rel="stylesheet" href="<%= jspUtil.mustReloadFile("/css/markdown.css") %>" />
</c:param>

<c:param name="PARAM_SCRIPTS">
<script>
$(document).ready(function(){
	hljs.initHighlightingOnLoad();
	marked.setOptions({
		langPrefix: '',
		highlight: function(code, lang) {
			console.log('[highlight]' + lang);
			return code;
		}
	});
	$('#content').html(marked($('#content').text()));
});
</script>
</c:param>

	<c:param name="PARAM_CONTENT">
	
<div id="content">

## Knowledge - <%= jspUtil.label("label.version") %>
  
  
## This Project License
- This project is provided under Apache License, Version 2.0
- http://www.apache.org/licenses/LICENSE-2.0


## Third-Party License

### Server side Library

- H2 Database Engine
   - License: [MPL 2.0] http://www.h2database.com/html/license.html
   - project-url: http://www.h2database.com/html/main.html
   
- javassist
   - License: [MPL 1.1] https://github.com/jboss-javassist/javassist/blob/3.18/License.html
   - project-url: http://www.csg.ci.i.u-tokyo.ac.jp/~chiba/javassist/
   
- log4j
   - License: [Apache License, Version 2.0] http://www.apache.org/licenses/LICENSE-2.0
   - project-url: http://logging.apache.org/log4j/1.2/
   
- Commons Lang
   - License: [Apache License, Version 2.0] http://www.apache.org/licenses/LICENSE-2.0
   - project-url: http://commons.apache.org/proper/commons-lang/
   
- JSONIC
   - License: [Apache License, Version 2.0] http://www.apache.org/licenses/LICENSE-2.0
   - project-url: http://jsonic.sourceforge.jp/
   
- Simple-Xml
   - License: [Apache License, Version 2.0] http://www.apache.org/licenses/LICENSE-2.0
   - project-url: http://simple.sourceforge.net/

- JUnit
   - License: [Eclipse Public License - v 1.0] https://github.com/junit-team/junit/blob/master/LICENSE-junit.txt
   - project-url: http://junit.org/

- Commons FileUpload
   - License: [Apache License, Version 2.0] http://www.apache.org/licenses/LICENSE-2.0
   - project-url: http://commons.apache.org/proper/commons-fileupload/

- OWASP AntiSamy
   - License: [Creative Commons Attribution-ShareAlike 3.0 license] http://creativecommons.org/licenses/by-sa/3.0/
   - project-url: https://www.owasp.org/index.php/Category:OWASP_AntiSamy_Project

- OWASP/java-html-sanitizer
   - License: [Apache License, Version 2.0] http://www.apache.org/licenses/LICENSE-2.0
   - project-url: https://github.com/owasp/java-html-sanitizer

- Apache Lucene
   - License: [Apache License, Version 2.0] http://www.apache.org/licenses/LICENSE-2.0
   - project-url: http://lucene.apache.org/

- Apache Directory
   - License: [Apache License, Version 2.0] http://www.apache.org/licenses/LICENSE-2.0
   - project-url: https://directory.apache.org/

- PostgreSQL JDBC
   - License: [BSD License] https://jdbc.postgresql.org/about/license.html
   - project-url: http://www.postgresql.org/

- java-diff-utils
   - License: [Apache License, Version 2.0] http://www.apache.org/licenses/LICENSE-2.0
   - project-url: https://code.google.com/p/java-diff-utils/

- pegdown
   - License: [Apache License, Version 2.0] http://www.apache.org/licenses/LICENSE-2.0
   - project-url: https://github.com/sirthias/pegdown

- Apache Httpcomponents
   - License: [Apache License, Version 2.0] http://www.apache.org/licenses/LICENSE-2.0
   - project-url: https://hc.apache.org/




### Front end Library
- jQuery
   - License: [MIT] https://jquery.org/license/
   - project-url: http://jquery.com/

- Bootstrap
   - License: [MIT] https://github.com/twbs/bootstrap/blob/master/LICENSE
   - project-url: http://getbootstrap.com/

- Bootswatch
   - License: [MIT] https://github.com/dbtek/bootswatch-dist/blob/master/LICENSE
   - project-url: https://bootswatch.com/

- Font Awesome
   - License: [MIT] http://fortawesome.github.io/Font-Awesome/license/
   - project-url: http://fortawesome.github.io/Font-Awesome/

- notifyjs
   - License: [MIT] https://github.com/jpillora/notifyjs
   - project-url: http://notifyjs.com/

- marked
   - License: [MIT] https://github.com/chjj/marked/blob/master/LICENSE
   - project-url: https://github.com/chjj/marked

- highlightjs
   - License: [BSD] https://github.com/isagalaev/highlight.js/blob/master/LICENSE
   - project-url: https://highlightjs.org/

- bootbox
   - License: [MIT] https://github.com/makeusabrew/bootbox/blob/master/LICENSE.md
   - project-url: http://bootboxjs.com/

- bootstrap-tagsinput
   - License: [MIT] https://github.com/TimSchlechter/bootstrap-tagsinput/blob/master/LICENSE
   - project-url: http://timschlechter.github.io/bootstrap-tagsinput/examples/

- jquery-file-upload
   - License: [MIT] http://opensource.org/licenses/MIT
   - project-url: https://blueimp.github.io/jQuery-File-Upload/

- teambox.free-file-icons
   - License: [MIT] https://github.com/teambox/Free-file-icons/blob/master/LICENSE
   - project-url: https://github.com/teambox/Free-file-icons

- echojs
   - License: [MIT] http://opensource.org/licenses/mit-license.php
   - project-url: https://github.com/toddmotto/echo

- notify.js
   - License: [MIT] https://github.com/alexgibson/notify.js/blob/master/LICENSE.md
   - project-url: https://github.com/alexgibson/notify.js

- emoji-parser
   - License: [MIT] https://github.com/frissdiegurke/emoji-parser/blob/master/LICENSE.md
   - project-url: https://github.com/frissdiegurke/emoji-parser
   
- bluebird
   - License: [MIT] https://github.com/petkaantonov/bluebird/blob/master/LICENSE
   - project-url: https://github.com/petkaantonov/bluebird
   
- jquery-oembed-all
   - License: [MIT] https://github.com/starfishmod/jquery-oembed-all/blob/master/jquery.oembed.js
   - project-url: https://github.com/starfishmod/jquery-oembed-all
   
- flag-icon-css
   - License: [MIT] https://github.com/lipis/flag-icon-css/blob/master/LICENSE
   - project-url: https://github.com/lipis/flag-icon-css




   
</div>

	</c:param>

</c:import>

