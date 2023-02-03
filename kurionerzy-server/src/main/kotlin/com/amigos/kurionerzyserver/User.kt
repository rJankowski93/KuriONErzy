package com.amigos.kurionerzyserver

import com.fasterxml.jackson.annotation.JsonProperty

data class User(@JsonProperty("id") val id: String) {

}
