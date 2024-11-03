package com.example.instagram.Models

class PostModel {
    var postUrl: String = ""
    var caption: String = ""
    constructor()
    constructor(postUrl: String, caption: String) {
        this.postUrl = postUrl
        this.caption = caption
    }
}