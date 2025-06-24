package com.dexmatic.shared

class CardParser {
    fun parse(text: String): Contact {
        val lines = text.lines().map { it.trim() }.filter { it.isNotEmpty() }
        var name: String = ""
        var phone: String? = null
        var email: String? = null
        var company: String? = null

        val emailRegex = "[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}".toRegex(setOf(RegexOption.IGNORE_CASE))
        val phoneRegex = "([+(]?\\d[\\d\\s().-]{7,}\\d)".toRegex()

        for (line in lines) {
            when {
                email == null && emailRegex.containsMatchIn(line) -> {
                    email = emailRegex.find(line)?.value
                }
                phone == null && phoneRegex.containsMatchIn(line) -> {
                    phone = phoneRegex.find(line)?.value
                }
                company == null && (line.contains("Inc", ignoreCase = true) ||
                    line.contains("LLC", ignoreCase = true) ||
                    line.contains("Company", ignoreCase = true) ||
                    line.all { it.isUpperCase() || !it.isLetter() }) -> {
                    company = line
                }
                name.isEmpty() -> name = line
            }
        }

        if (name.isEmpty() && company != null) {
            name = lines.firstOrNull { it != company && it != email && it != phone } ?: ""
        }

        return Contact(
            name = name,
            phone = phone,
            email = email,
            company = company
        )
    }
}
