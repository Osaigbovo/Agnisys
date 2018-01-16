/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agnisys.idsnextgen.classes;

/**
 *
 * @author Agnisys
 */
public interface Command {

    /**
     * This is called to execute the command from implementing class.
     */
    public abstract void execute();

    /**
     * This is called to undo last command.
     */
    public abstract void undo();
}
