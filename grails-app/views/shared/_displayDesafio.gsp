<div class="card_wrapper">
    <div class="card">
        <h2><g:link method="GET" resource="${desafio}" >${desafio.titulo}</g:link><br/></h2>
        <tmpl:/shared/displayPuntaje puntaje="${desafio.puntaje}" />
    </div>
</div>