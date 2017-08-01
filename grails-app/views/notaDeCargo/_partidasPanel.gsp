<div class="partidas-panel">
  <div class="btn-group">
      <g:if test="${!ventaInstance.cfdi}">
        <g:link action="selectorDeFacturas" 
          class="btn btn-primary btn-outline " id="${ventaInstance.id}">
            <i class="fa fa-cart-plus"></i> Agregar facturas
        </g:link> 
      </g:if> 
  </div>
  <table class=" grid table  table-hover table-bordered table-condensed">
    <thead>
      <tr>
        <th>Cantidad</th>
        <th>Clave SAT</th>
        <th>Factura</th>
        %{-- <th>A Pagar</th>
        <th>Embarque</th>
        <th>Vto</th> --}%
        <th>Valor U</th>
        <th>Importe</th>
        <th>Unidad SAT</th>
        <th>Comentario</th>
      </tr>
    </thead>
    <tbody>
      <g:each in="${ventaInstance.conceptos}" var="row">
        <tr id="${row.id}">
          <td>${fieldValue(bean: row, field: "cantidad")}</td>
          <td>${fieldValue(bean: row, field: "numeroDeIdentificacion")}</td>
          <td>${row.comentario}</td>
          
          <td><lx:moneyFormat number="${row.valorUnitario }" /></td>
          <td><lx:moneyFormat number="${row.importe }" /></td>
          %{-- <td><lx:shortDate date="${rowfechaDocumento}" /></td>
          <td><lx:moneyFormat number="${row.totalDocumento }" /></td>
          <td><lx:moneyFormat number="${row.total }" /></td>
          <td>${fieldValue(bean: row, field: "embarque.id")}</td>
          <td><lx:shortDate date="${row?.factura?.vencimiento}" /></td>
        --}%
          <td>${fieldValue(bean: row, field: "unidad")}</td>
          <td>${fieldValue(bean: row, field: "descripcion")}</td>
        </tr>
      </g:each>
    </tbody>
  </table>
</div>