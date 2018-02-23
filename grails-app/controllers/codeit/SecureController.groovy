package codeit

import grails.plugin.springsecurity.annotation.Secured

@Secured('ROLE_USER')
class SecureController {

    def index() {
        render view:'/index'
    }
}
