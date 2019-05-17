package eu.europa.ecb.vo

import org.simpleframework.xml.Default
import org.simpleframework.xml.DefaultType
import org.simpleframework.xml.Element

@Default(value = DefaultType.FIELD)
class Envelope{
    @field:Element(name = "Cube")
    var cube: BaseCube? = null
    @field:Element(name = "subject")
    var subject:String? = null
    @field:Element(name = "Sender")
    var sender: Sender? = null
}