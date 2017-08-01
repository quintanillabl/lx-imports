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
        <th>Vto</th>
        <th>Corte</th>
        <th>Atraso</th>
        <th>M.Mes</th>
        <th>D.Pena</th>
        <th>Pena</th>
        <th>Valor U</th>
        <th>Importe</th>
        <th>Unidad SAT</th>
        %{-- <th>Comentario</th> --}%
      </tr>
    </thead>
    <tbody>
      <g:each in="${ventaInstance.conceptos}" var="row">
        <tr id="${row.id}">
          <td>${fieldValue(bean: row, field: "cantidad")}</td>
          <td>${fieldValue(bean: row, field: "numeroDeIdentificacion")}</td>
          <td>${fieldValue(bean: row, field: "documento")}</td>
          <td><lx:shortDate date="${row.vto}" /></td>
          <td><lx:shortDate date="${row.corte}" /></td>
          <td><g:formatNumber number="${row.atraso }" /></td>
          <th>${row.mismoMes ? 'SI' : 'NO'}</th>
          <td><g:formatNumber number="${row.diasPena }" /></td>
          <td><lx:moneyFormat number="${row.penaPorDia }" /></td>

          <td><lx:moneyFormat number="${row.valorUnitario }" /></td>
          <td><lx:moneyFormat number="${row.importe }" /></td>
          %{-- <td><lx:shortDate date="${rowfechaDocumento}" /></td>
          <td><lx:moneyFormat number="${row.totalDocumento }" /></td>
          <td><lx:moneyFormat number="${row.total }" /></td>
          <td>${fieldValue(bean: row, field: "embarque.id")}</td>
          <td><lx:shortDate date="${row?.factura?.vencimiento}" /></td>
        --}%
          <td>${fieldValue(bean: row, field: "unidad")}</td>
          %{-- <td>${fieldValue(bean: row, field: "comentario")}</td> --}%
        </tr>
      </g:each>
    </tbody>
  </table>
</div>