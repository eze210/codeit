package codeit

class UrlMappings {

    static mappings = {

        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(uri:"/desafio/index")
        "500"(view:'/error')
        "404"(view:'/notFound')

    }
}
