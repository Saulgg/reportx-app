package com.dev.sandia.reportx

class Tickets  (val _id: String, val building: String, val message: String, val name: String, val priority: String, val room: String, val working: String){
    constructor(): this("","","", "", "", "", ""){

    }
}