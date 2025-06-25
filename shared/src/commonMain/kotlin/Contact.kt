// File: shared/src/commonMain/kotlin/com/dexmatic/shared/Contact.kt

package com.dexmatic.shared

import java.io.Serializable

/**
 * A simple contact model; implements Serializable so we can safely put it into a SavedStateHandle or Nav arguments.
 */
data class Contact(
  val name: String,
  val phone: String? = null,
  val email: String? = null,
  val company: String? = null
) : Serializable
