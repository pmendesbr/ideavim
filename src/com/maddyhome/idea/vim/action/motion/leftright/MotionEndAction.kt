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

package com.maddyhome.idea.vim.action.motion.leftright

import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.editor.Caret
import com.intellij.openapi.editor.Editor
import com.maddyhome.idea.vim.VimPlugin
import com.maddyhome.idea.vim.command.Argument
import com.maddyhome.idea.vim.command.Command
import com.maddyhome.idea.vim.command.MappingMode
import com.maddyhome.idea.vim.command.MotionType
import com.maddyhome.idea.vim.group.MotionGroup
import com.maddyhome.idea.vim.handler.NonShiftedSpecialKeyHandler
import com.maddyhome.idea.vim.helper.inInsertMode
import com.maddyhome.idea.vim.helper.inSelectMode
import com.maddyhome.idea.vim.helper.inVisualMode
import com.maddyhome.idea.vim.helper.vimLastColumn
import com.maddyhome.idea.vim.option.OptionsManager
import javax.swing.KeyStroke

class MotionEndAction : NonShiftedSpecialKeyHandler() {
  override val motionType: MotionType = MotionType.EXCLUSIVE

  override val mappingModes: MutableSet<MappingMode> = MappingMode.NVOS

  override val keyStrokesSet: Set<List<KeyStroke>> = parseKeysSet("<End>")

  override fun offset(editor: Editor, caret: Caret, context: DataContext, count: Int,
                      rawCount: Int, argument: Argument?): Int {
    var allow = false
    if (editor.inInsertMode) {
      allow = true
    } else if (editor.inVisualMode || editor.inSelectMode) {
      val opt = OptionsManager.selection
      if (opt.value != "old") {
        allow = true
      }
    }

    return VimPlugin.getMotion().moveCaretToLineEndOffset(editor, caret, count - 1, allow)
  }

  override fun preMove(editor: Editor, caret: Caret, context: DataContext, cmd: Command) {
    caret.vimLastColumn = MotionGroup.LAST_COLUMN
  }

  override fun postMove(editor: Editor, caret: Caret, context: DataContext, cmd: Command) {
    caret.vimLastColumn = MotionGroup.LAST_COLUMN
  }
}
