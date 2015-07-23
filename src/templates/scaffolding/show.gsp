<% import grails.persistence.Event %>
<%=packageName%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<g:set var="entityName" value="\${message(code: '${domainClass.propertyName}.label', default: '${className}')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<div class="container">
			
			<div class="row row-header">
				<div class="col-md-8 col-sm-offset-2 toolbar-panel">
					<div class="btn-group">
					    <lx:backButton/>
					    <lx:createButton/>
					    <lx:editButton id="\${${propertyName}?.id}"/>
					    <lx:printButton/>
					</div>
				</div>
			</div> <!-- End .row 1 -->
			<div class="row">
			    <div class="col-md-8 col-sm-offset-2">
					<div class="panel panel-primary">
						<div class="panel-heading"> \${entityName}: \${${propertyName}} </div>
						<g:if test="\${flash.message}">
							<small><span class="label label-info ">\${flash.message}</span></small>
						</g:if> 
					  	<div class="panel-body">
					  		<g:form class="form-horizontal">
					  			<%  excludedProps = Event.allEvents.toList() << 'id' << 'version'
					  				allowedNames = domainClass.persistentProperties*.name << 'dateCreated' << 'lastUpdated'
					  				props = domainClass.properties.findAll { allowedNames.contains(it.name) && !excludedProps.contains(it.name) && (domainClass.constrainedProperties[it.name] ? domainClass.constrainedProperties[it.name].display : true) }
					  				Collections.sort(props, comparator.constructors[0].newInstance([domainClass] as Object[]))
					  				props.each { p -> %>
					  					<% if (p.name == 'dateCreated' || p.name == 'lastCreated') { %>
								<f:display property="${p.name}" bean="${propertyName}" widget="datetime"/>
					  					<%  } else { %>
					  			<f:display property="${p.name}" bean="${propertyName}"/>
					  					<%  } %>
					  			<%  } %>
					  		</g:form>
					  </div>
					</div>
			    </div>
			</div><!-- End .row 2 -->

		</div><!-- End container -->

	</body>
</html>


