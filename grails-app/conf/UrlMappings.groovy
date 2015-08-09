class UrlMappings {

	static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }
        "/"(controller:"home")
        "/requisiciones"(resources:'requisicionRest')
        "500"(view:'/error')
        
	}
}
