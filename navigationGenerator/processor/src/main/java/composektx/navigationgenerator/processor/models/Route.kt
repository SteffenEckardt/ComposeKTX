package composektx.navigationgenerator.processor.models

import composektx.navigationgenerator.processor.extensions.deepEquals

internal sealed class Route(protected val base: String) {
    abstract fun asString(): String

    init {
        require(base.isNotBlank()) { "The base route of the destination must not be blank or empty." }
    }

    class SimpleRoute(base: String) : Route(base) {
        override fun asString() = base
        fun asNavigation() = base
    }

    class NonOptionalRoute(base: String, private vararg val parameterKeys: String) : Route(base) {

        init {
            require(parameterKeys.isNotEmpty()) { "Required arguments: Use Route.SimpleRoute for destinations with no arguments." }
        }

        override fun asString() = "$base/${parameterKeys.joinToString(separator = "/") { key -> "{$key}" }}"

        fun asNavigation(vararg actualValues: Pair<String, Any>) = asNavigation(actualValues.toMap())

        private fun asNavigation(actualValues: Map<String, Any>): String {
            require(parameterKeys.toList().deepEquals(actualValues.keys)) { "The provided key-value list did not match the required parameters of the route." }

            return buildString {
                append(base)
                append("/")
                append(parameterKeys.joinToString(separator = "/") { key ->
                    actualValues[key].toString()
                })
            }
        }
    }

    class OptionalRoute(base: String, private vararg val parameterKeys: String) : Route(base) {

        init {
            require(parameterKeys.isNotEmpty()) { "Required arguments: Use Route.SimpleRoute for destinations with no arguments." }
        }

        override fun asString() = "$base${parameterKeys.joinToString(separator = "") { key -> "&$key={${key}}" }.replaceFirst('&', '?')}"

        fun asNavigation(vararg actualValues: Pair<String, Any?>) = asNavigation(actualValues.toMap())

        private fun asNavigation(actualValues: Map<String, Any?>): String {
            require(parameterKeys.toList().containsAll(actualValues.keys)) { "The provided key-value list did not match the required parameters of the route." }

            return buildString {
                append(base)

                if (actualValues.isEmpty()) return@buildString

                append("?")
                append(actualValues.entries.joinToString(separator = "&") { (key, value) ->
                    "$key=${value.toString()}"
                })
            }
        }
    }
}