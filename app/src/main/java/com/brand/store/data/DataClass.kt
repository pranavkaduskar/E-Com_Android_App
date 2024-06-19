package com.brand.store.data


class DataClass {
    var dataTitle: String? = null
    var dataDesc: String? = null
    var dataPrice: String? = null
    var dataImage: String? = null

    constructor() {
        // Default no-argument constructor required by Firebase.
    }

    constructor(dataTitle: String?, dataDesc: String?, dataPrice: String?, dataImage: String?) {
        this.dataTitle = dataTitle
        this.dataDesc = dataDesc
        this.dataImage = dataImage
        this.dataPrice = dataPrice
    }
}
