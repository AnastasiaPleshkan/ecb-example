package eu.europa.ecb.vo

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root
class Cube {
    @field:Attribute(name = "time")
    var time: String? = null
    @field:ElementList(inline = true)
    var cube: List<CubeItem>? = null
}