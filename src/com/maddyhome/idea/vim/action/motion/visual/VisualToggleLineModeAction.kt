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

package com.maddyhome.idea.vim.action.motion.visual

import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.editor.Editor
import com.maddyhome.idea.vim.VimPlugin
import com.maddyhome.idea.vim.command.Command
import com.maddyhome.idea.vim.command.CommandFlags
import com.maddyhome.idea.vim.command.CommandState
import com.maddyhome.idea.vim.command.MappingMode
import com.maddyhome.idea.vim.group.visual.vimSetSelection
import com.maddyhome.idea.vim.handler.VimActionHandler
import com.maddyhome.idea.vim.helper.enumSetOf
import com.maddyhome.idea.vim.helper.vimForEachCaret
import com.maddyhome.idea.vim.option.OptionsManager
import java.util.*
import javax.swing.KeyStroke


class VisualToggleLineModeAction : VimActionHandler.SingleExecution() {


  override val mappingModes: MutableSet<MappingMode> = MappingMode.NV

  override val keyStrokesSet: Set<List<KeyStroke>> = parseKeysSet("V")

  override val type: Command.Type = Command.Type.OTHER_READONLY

  override val flags: EnumSet<CommandFlags> = enumSetOf(CommandFlags.FLAG_MOT_LINEWISE)
  override fun execute(editor: Editor, context: DataContext, cmd: Command): Boolean {
    val listOption = OptionsManager.selectmode
    return if ("cmd" in listOption) {
      VimPlugin.getVisualMotion().enterSelectMode(editor, CommandState.SubMode.VISUAL_LINE).also {
        editor.vimForEachCaret { it.vimSetSelection(it.offset) }
      }
    } else VimPlugin.getVisualMotion()
      .toggleVisual(editor, cmd.count, cmd.rawCount, CommandState.SubMode.VISUAL_LINE)
  }
}
