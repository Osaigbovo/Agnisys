/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agnisys.idsnextgen.classes;

import java.util.Stack;

/**
 *
 * @author Agnisys
 */
public final class History {

    // ...
    private static History instance = null;
    private final Stack<Command> undoStack = new Stack<Command>();
    // ...

    public void execute(final Command cmd) {
        undoStack.push(cmd);
        cmd.execute();
    }

    public void undo() {
        if (!undoStack.isEmpty()) {
            Command cmd = undoStack.pop();
            cmd.undo();
        } else {
            System.out.println("Nothing to undo.");
        }
    }

    public static History getInstance() {
        if (History.instance == null) {
            synchronized (History.class) {
                if (History.instance == null) {
                    History.instance = new History();
                }
            }
        }
        return History.instance;
    }

    private History() {
    }
}
