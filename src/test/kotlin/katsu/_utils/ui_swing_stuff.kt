package katsu._utils

import javax.swing.UIManager

fun main() {
    UIManager.getLookAndFeelDefaults()
            .filter { it.key.toString().startsWith("List.") }
            .forEach { (t, u) ->
                println("[$t]=[$u]")
            }
}
