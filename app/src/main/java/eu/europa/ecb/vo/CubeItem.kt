package eu.europa.ecb.vo

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Root


@Root(name = "Cube")
@Entity
data class CubeItem constructor(
        @field:Attribute(name = "currency")
        @PrimaryKey
        var currency: String = "",
        @field:Attribute(name = "rate")
        var rate: String? = null
)