package com.jhoander.concretesolutionsgithubrepo.domain.models

data class JsonDataModel(
    /** Sever Json Parser Getter and Setter  */
    var nameRepo: String?,
    var descriptionRepo: String?,
    var nameUser: String?,
    var imageUser: String?,
    var forksRepo: Int,
    var photoUser : String,
    var starsRepo: Int) {
}

