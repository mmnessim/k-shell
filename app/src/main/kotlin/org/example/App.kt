package org.example

class App {
    val greeting: String
        get() {
            return "Hello World!"
        }
}

class ParsedInput {
    var command: String = ""
    var flags: CharArray = charArrayOf()
    var arguments: Array<String> = arrayOf()

    constructor(input: String) {
        val parts = input.split(" ")
        when (parts.size) {
            0 -> {}
            1 -> {
                command = parts[0]
            }
            2 -> {
                command = parts[0]
                if (isFlag(parts[1])) {
                    flags = parts[1].substring(1).toCharArray()
                } else {
                    arguments = parts.slice(1 until parts.size).toTypedArray()
                }

            }
            else -> {
                command = parts[0]
                if (isFlag(parts[1])) {
                    flags = parts[1].substring(1).toCharArray()
                    arguments = parts.slice(2 until parts.size).toTypedArray()
                } else {
                    arguments = parts.slice(1 until parts.size).toTypedArray()
                }
            }
        }
    }

    fun isFlag(input: String): Boolean {
        return input.startsWith("-")
    }

    fun dispatchCommand(): String {
        if (command.isEmpty()) {
            return ""
        }
        when (command.lowercase()) {
            "cat" -> return ShellFunctions().cat(this)
            "stats" -> return ShellFunctions().stats()
            "exit" -> System.exit(0)
            else -> return ""
        }
        return ""
    }

}

fun main() {
    while (true) {
        print("k-shell: ")
        val input = readLine() ?: ""
        if (input.isEmpty()) {
            continue
        }
        val p = ParsedInput(input)
        val output = p.dispatchCommand()
        if (output.isEmpty()) {
            continue
        }
        println(output)
    }

}
