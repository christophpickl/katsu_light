package katsu.view

import java.awt.event.ActionListener
import javax.swing.JButton
import javax.swing.JPanel

fun JPanel.addButton(label: String, onClick: ActionListener) {
    add(JButton(label).apply { addActionListener(onClick) })
}
