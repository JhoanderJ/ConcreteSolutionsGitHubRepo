package com.jhoander.concretesolutionsgithubrepo.domain.models

import com.google.gson.annotations.SerializedName

data class RepositoryData(
    @SerializedName("title") private var title: String? = null,
    @SerializedName("body") private var body: String? = null,
    @SerializedName("created_at") private var created_at: String? = null,
    @SerializedName("html_url") private var html_url: String? = null,
    @SerializedName("user") private var user: Owner?) {

    fun OwnerRepo(title: String?, body: String?, created_at: String?, html_url: String?, user: Owner?) {
        this.title = title
        this.body = body
        this.created_at = created_at
        this.html_url = html_url
        this.user = user
    }

    fun getTitle(): String? {
        return title
    }

    fun setTitle(title: String?) {
        this.title = title
    }

    fun getBody(): String? {
        return body
    }

    fun setBody(body: String?) {
        this.body = body
    }

    fun getCreated_at(): String? {
        return created_at
    }

    fun setCreated_at(created_at: String?) {
        this.created_at = created_at
    }

    fun getHtml_url(): String? {
        return html_url
    }

    fun setHtml_url(html_url: String?) {
        this.html_url = html_url
    }

    fun getUser(): Owner? {
        return user
    }

    fun setUser(user: Owner?) {
        this.user = user
    }

    inner class Owner {
        var login: String? = null
        var avatar_url: String? = null
    }
}