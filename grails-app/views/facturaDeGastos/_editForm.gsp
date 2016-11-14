<br>
<g:form name="updateForm" action="update" class="form-horizontal" id="${facturaDeGastosInstance.id}" method="PUT">
	<f:with bean="facturaDeGastosInstance">
		<div class="row">
			<div class="col-md-6">
				<f:display property="proveedor" widget-class="form-control" 
					wrapper="bootstrap3" widget-required="true"/>
				<f:field property="documento" widget-class="form-control" wrapper="bootstrap3"/>
				<f:field property="fecha" wrapper="bootstrap3" widget-required="true"/>
				<f:field property="vencimiento" wrapper="bootstrap3"  />
				<f:display property="moneda" wrapper="bootstrap3"/>
				<f:display property="tc" widget-class="form-control" wrapper="bootstrap3"/>
				<f:field property="gastoPorComprobar" widget-class="form-control "  wrapper="bootstrap3"/>
				<f:field property="concepto" wrapper="bootstrap3">
					<g:select class="form-control"  
						name="${property}" 
						value="${value}"
						from="${com.luxsoft.impapx.FacturaDeGastos.CONCEPTOS}" 
						/>
				</f:field>
				<f:field property="comentario" widget-class="form-control" wrapper="bootstrap3"/>
			</div>
			<div class="col-md-6">
				<f:display property="importe" widget="money" wrapper="bootstrap3" />
				<f:display property="subTotal" widget="money" wrapper="bootstrap3"/>
				<f:display property="descuentos" widget="money" wrapper="bootstrap3"/>
				<f:display property="impuestos" widget="money" wrapper="bootstrap3"/>
				<f:display property="tasaDeImpuesto" widget="porcentaje" widget-class="form-control" wrapper="bootstrap3"/>
				<f:display property="retTasa" widget="porcentaje" widget-class="form-control" wrapper="bootstrap3"/>
				<f:display property="retImp" widget="money" wrapper="bootstrap3"/>
				<f:display property="total" widget="money" wrapper="bootstrap3"/>

				<f:display property="descuento" widget="money" wrapper="bootstrap3"/>
				<f:display property="rembolso" widget="money" wrapper="bootstrap3" label="Vales"/>
				<f:display property="otros" widget="money" wrapper="bootstrap3"/>
				<f:display property="saldoActual" widget="money" wrapper="bootstrap3" label="Saldo"/>
			</div>
		</div>
	</f:with>
	<div class="form-group">
	    <div class="col-lg-offset-3 col-lg-9">
	        <button id="saveBtn" class="btn btn-primary ">
	            <i class="fa fa-floppy-o"></i> Actualizar
	        </button>
	        <a href="" class="btn btn-danger " data-toggle="modal" data-target="#deleteDialog"><i class="fa fa-trash"></i> Eliminar</a> 
	    </div>
	</div>
</g:form>
<g:render template="/common/deleteDialog" bean="${facturaDeGastosInstance}"/>
