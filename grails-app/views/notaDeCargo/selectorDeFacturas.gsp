<!doctype html>
<html>
<head>
  <title>Facturas pendientes de pago</title>
  <meta name="layout" content="luxor">
</head>
<body>

<content tag="header">Facturas pendientes de pago </content>
<content tag="subHeader">
  <g:link action='edit' id="${venta.id}"   >
     Nota: ${venta.id} Cliente:${venta.cliente.nombre} 
  </g:link>
  <p> Facturas: ${facturas.size()}  Saldo total: <g:formatNumber number="${saldoTotal}" format="###,###,###.##"/></p>
  
</content>

<content tag="document">
  <div class="ibox float-e-margins">
          
          <div class="ibox-title">
            <lx:backButton label="Nota de cargo: ${venta.id}" action="edit" id="${venta.id}"/>
              <a id="asignar" class="btn btn-outline btn-success">Agregar</a>
          </div>
          
            <div class="ibox-content">
            <table id="grid" class=" grid table  table-hover table-bordered table-condensed">
              <thead>
                <tr>
                  <th>Folio</th>
                  <th>Documento</th>
                  <th>Fecha</th>
                  <th>Vencimiento</th>
                  <th>Moneda</th>
                  <th>Total</th>
                  <th>Aplicaciones</th>
                  <th>Saldo</th>
                </tr>
              </thead>
              <tbody>
                <g:each in="${facturas}" var="row">
                <tr id="${row.id}"> 
                  <td>${row.id}</td>
                  <td>${fieldValue(bean: row, field: "facturaFolio")}</td>  
                  <td>${fieldValue(bean: row, field: "fechaFactura")}</td>
                  <td><lx:shortDate date="${row.vencimiento }"/></td>
                  <td>${fieldValue(bean: row, field: "moneda")}</td>
                  <td><lx:moneyFormat number="${row.total }"/></td>
                  <td><lx:moneyFormat number="${row.pagosAplicados }"/></td>
                  <td><lx:moneyFormat number="${row.saldoActual }"/></td>
                </tr>
                </g:each> 
              </tbody>
            </table>
            </div>
    </div>

  <script type="text/javascript">
    $(function(){

      // Grid y seleccion
      $('#grid').dataTable( {
          "paging":   false,
          "ordering": false,
          "info":     false,
          "language": {
        "url": "${assetPath(src: 'datatables/dataTables.spanish.txt')}"
        },
        //"dom": '',
        "order": []
      } );
      
      $(".grid tbody tr").click(function(){
        $(this).toggleClass("success selected");
      });

      var selectRows=function(){
        var res=[];
        var data=$(".grid .selected").each(function(){
          var tr=$(this);
          res.push(tr.attr("id"));
        });
        return res;
      };
      $("#asignar").on('click',function(){
        var res=selectRows();
        if(res==""){
          alert("Debe seleccionar al menos una factura");
          return
        }
        var ok=confirm('Generar conceptos a  ' + res.length+' factura(s) al la nota:'+${venta.id}+'?');
        if(!ok)
          return;
        $.post(
          "${createLink(action:'agregarConceptos')}",
          {id:${venta.id},conceptos:JSON.stringify(res)})
        .done(function(data){
          console.log('Rres: '+data.documento);
          window.location.href='${createLink(action:'edit',params:[id:venta.id])}';
        }).fail(function(jqXHR, textStatus, errorThrown){
          alert("Error asignando facturas: "+errorThrown);
        });
      });
    });

    
  </script>
  
</content>
  

</body>
</html>


