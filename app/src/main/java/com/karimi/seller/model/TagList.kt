package com.karimi.seller.model

class TagList {
    var title:String? = null
    var icon:Int? = null
    var tag:String? = null

    /* test */
    var titleArray:ArrayList<String>? = null
    var tagArray:ArrayList<String>? = null


    constructor()
    constructor(title: String?, icon: Int?, tag: String?) {
        this.title = title
        this.icon = icon
        this.tag = tag
    }
    constructor(title: String?, tag: String?) {
        this.title = title
        this.tag = tag
    }

    constructor(title: String?) {
        this.title = title
    }

    constructor(titleArray: ArrayList<String>?, tagArray: ArrayList<String>?) {
        this.titleArray = titleArray
        this.tagArray = tagArray
    }



}