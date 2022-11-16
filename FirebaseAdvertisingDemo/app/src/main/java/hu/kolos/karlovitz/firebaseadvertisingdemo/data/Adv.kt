package hu.kolos.karlovitz.firebaseadvertisingdemo.data


data class Adv(
    var uid: String = "",
    var author: String = "",
    var title: String = "",
    var text: String = "",
    var price: Int = 0,
    var email: String = "",
    var phone: String = "",
    var timeStamp: Long = 0
)