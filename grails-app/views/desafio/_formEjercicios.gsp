<g:javascript>
    require('jquery-ui');
    require('jquery-ui/ui/widgets/sortable');

    jQuery(document).ready( function() {
        $('#ejerciciosListTableBody').sortable({
            stop: function (event, ui) {
                updateNames($(this))
            },
            handle: '.moveRow',
        });
        function updateNames($tbody) {
            $tbody.find('tr').each(function (idx) {
                var $inp = $(this).find('input,select');
                $(this).attr('rowId', idx);
                $inp.each(function () {
                    this.name = this.name.replace(/(\[\d\])/, '[' + idx + ']');
                })
            });
        }

        $(document).on("click", ".delete",function(event) {

            var tableBody = $('#ejerciciosListTableBody');
            var rowToDelete = $(this).closest('tr');

            $(rowToDelete).remove();
            updateNames(tableBody);
            return false;
        });
        $('.add').click( function() {
            var allExercices = $("#ejerciciosListTableBody tr[rowId]");
            var rowId = 0;
            // This operation is performed to allow safe-delete of newly created (not saved in DB) articles
            if( allExercices.length ) {
                var maxId = 0;
                allExercices.each(function() {
                    maxId = Math.max(maxId, parseInt($(this).attr('rowId')));
                });
                rowId = maxId+1;
            }

            $("#ejerciciosListTableBody").append( '<tr newRow="true" rowId="' + rowId  + '">' +
                '<td><input required="true" type="text"  name="ejercicios[' + rowId + '].enunciado" value=""/></td>' +
                '<td><select name="ejercicios[' + rowId + '].type" >' +
                '<td  class="moveRow"><a href="#" class="delete">Borrar ejercicio</a></td><td class="moveRow">Mover</td></tr>');

            return false;
        });

    } );
</g:javascript>

<table id="ejerciciosList">
    <thead>
    <tr>
        <th>Enunciado</th>
        <th>Delete</th>
        <th>Move</th>
    </tr>
    </thead>
    <tbody id="ejerciciosListTableBody">
    <g:each in="${desafio?.ejercicios}" var="ejercicio" status="i">
        <tr <g:if test="${!ejercicio?.id}">newRow="true"</g:if> rowId="${i}">
            <td>
                <g:textArea id="" required="true" name="ejercicios[$i].enunciado" value="${ejercicio?.enunciado}"/>
                <g:if test="${ejercicio?.id}">
                    <g:hiddenField id="" name="ejercicios[$i].id" value="${ejercicio?.id}"/>
                </g:if>
            </td>
            <td>
                <a href="#" class="delete">Borrar ejercicio</a>
            </td>
            <td class="moveRow">
                Mover
            </td>
        </tr>
    </g:each>
    </tbody>
</table>
<a href="#" class="add">Agregar ejercicio</a>