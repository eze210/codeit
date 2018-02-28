<div class="card_wrapper">
    <div class="card">
        <g:link method="GET" resource="${desafio}" >${desafio.titulo}</g:link><br/>
        <tmpl:/shared/displayPuntaje puntaje="${desafio.puntaje}" />
    </div>
</div>