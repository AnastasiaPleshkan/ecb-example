package eu.europa.ecb.vo

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root
class BaseCube{
    @field:Element(name = "Cube")
    var cube: Cube? = null
}