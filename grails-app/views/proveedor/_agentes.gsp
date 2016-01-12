
<table id="grid" class="table  table-bordered table-condensed ">
	<thead>
		<tr>
			<th>Nombre</th>
			<g:if test="$webRequest.actionName=='edit'}">
				<th>Eliminar</th>
			</g:if>
		</tr>
		
	</thead>
	<tbody>
		<g:each in="${proveedorInstance?.agentes}" var="agente">
		<tr>
			<td>
				${agente}
			</td>
			<g:if test="$webRequest.actionName=='edit'}">
				<th>
					<g:link action="eliminarAgenteAduanal" onclick="return confirm('Eliminar agente aduanal');" 
						id="${proveedorInstance.id}" params="[agente:agente]"> 
						<i class="fa fa-trash"></i>
					</g:link>
				</th>
			</g:if>
		</tr>
		</g:each>
	</tbody>
</table>
