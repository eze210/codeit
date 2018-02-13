<h1>${name}</h1>
<div id="list" class="content scaffold-list" role="main">
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <tmpl:participanteTable collection="${list}" />
</div>