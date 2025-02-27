/*
 * IdeaVim - Vim emulator for IDEs based on the IntelliJ platform
 * Copyright (C) 2003-2019 The IdeaVim authors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package com.maddyhome.idea.vim.action.motion.scroll

import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.editor.Editor
import com.maddyhome.idea.vim.VimPlugin
import com.maddyhome.idea.vim.command.Command
import com.maddyhome.idea.vim.command.CommandFlags
import com.maddyhome.idea.vim.command.MappingMode
import com.maddyhome.idea.vim.handler.VimActionHandler
import com.maddyhome.idea.vim.helper.enumSetOf
import java.awt.event.KeyEvent
import java.util.*
import javax.swing.KeyStroke


class MotionScrollPageDownAction : VimActionHandler.SingleExecution() {
  override val mappingModes: Set<MappingMode> = MappingMode.NVO

  override val keyStrokesSet: Set<List<KeyStroke>> = parseKeysSet("<C-F>", "<PageDown>")

  override val type: Command.Type = Command.Type.OTHER_READONLY

  override fun execute(editor: Editor, context: DataContext, cmd: Command): Boolean {
    return VimPlugin.getMotion().scrollFullPage(editor, cmd.count)
  }
}

class MotionScrollPageDownInsertModeAction : VimActionHandler.SingleExecution() {

  override val mappingModes: Set<MappingMode> = MappingMode.I
  override val keyStrokesSet: Set<List<KeyStroke>> = setOf(
    listOf(KeyStroke.getKeyStroke(KeyEvent.VK_PAGE_DOWN, 0)),
    listOf(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, KeyEvent.CTRL_MASK)),
    listOf(KeyStroke.getKeyStroke(KeyEvent.VK_KP_DOWN, KeyEvent.CTRL_MASK)),
    listOf(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, KeyEvent.SHIFT_MASK)),
    listOf(KeyStroke.getKeyStroke(KeyEvent.VK_KP_DOWN, KeyEvent.SHIFT_MASK))
  )

  override val type: Command.Type = Command.Type.OTHER_READONLY

  override val flags: EnumSet<CommandFlags> = enumSetOf(CommandFlags.FLAG_CLEAR_STROKES)

  override fun execute(editor: Editor, context: DataContext, cmd: Command): Boolean {
    return VimPlugin.getMotion().scrollFullPage(editor, cmd.count)
  }
}
