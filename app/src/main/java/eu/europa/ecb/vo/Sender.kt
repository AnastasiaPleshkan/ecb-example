package eu.europa.ecb.vo

import org.simpleframework.xml.Element

class Sender{
    @field:Element(name = "name")
    var name:String? = null
}