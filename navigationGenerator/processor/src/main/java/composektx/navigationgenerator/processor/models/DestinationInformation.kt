package composektx.navigationgenerator.processor.models

import java.lang.reflect.Type

data class DestinationInformation(val name: String, val packageName: String, val destinationParams: List<DestinationParameter> = emptyList())

data class DestinationParameter(val name: String, val type: Type, val isOptional: Boolean, val isDefault: Boolean)
