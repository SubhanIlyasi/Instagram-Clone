package com.example.instagram.Models

class ReelModel {
    var videoUrl: String = ""
    var caption: String = ""
    constructor()
    constructor(vidoeUrl: String, caption: String) {
        this.videoUrl = vidoeUrl
        this.caption = caption
    }
}