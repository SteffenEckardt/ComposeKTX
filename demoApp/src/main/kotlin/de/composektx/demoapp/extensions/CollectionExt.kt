package de.composektx.demoapp.extensions

internal fun Collection<Any>.deepEquals(other: Collection<Any>) = this.containsAll(other) && other.containsAll(this)
