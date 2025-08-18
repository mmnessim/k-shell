package org.example

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

    fun dispatchCommand(cwd: String): Pair<String, String> {
        if (command.isEmpty()) {
            return Pair(cwd, "")
        }
        when (command.lowercase()) {
            "cat"   -> return ShellFunctions().cat(cwd, this)
            "ls"    -> return Pair(cwd, ShellFunctions().ls(cwd, this))
            "exit"  -> System.exit(0)
            "cd"    -> return ShellFunctions().cd(cwd, this)
            "touch" -> return ShellFunctions().touch(cwd, this)
            "rm"    -> return ShellFunctions().rm(cwd, this)
            "help"  -> return Pair(cwd, ShellFunctions().help())
            ""      -> return Pair(cwd, "")
            else    -> return Pair(cwd, ShellFunctions().unknownCommand(this))
        }
        return Pair(cwd, "")
    }

}

fun main() {
    var cwd = System.getProperty("user.dir")

    while (true) {
        val prompt = "[" + cwd + "]" + " \$k-shell: "
        print(prompt)
        val input = readLine() ?: ""
        if (input.isEmpty()) {
            continue
        }
        val p = ParsedInput(input)

        // This chunk of code will need to be rewritten eventually
        // All ShellFunctions will eventually return a pair of
        // cwd and their output
        var output: String

        val pair = p.dispatchCommand(cwd)
        cwd = pair.first
        output = pair.second

        if (output.isEmpty()) {
            continue
        }
        println(output)
    }
}
