/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agnisys.idsnextgen.team;

import java.io.File;
import java.io.IOException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoHeadException;

/**
 *
 * @author avdhesh
 */
public interface TeamRunnable {

    public void createRepository(String repoPath) throws IOException;

    public void openRepository(String repoPath) throws IOException;

    public void commit(String message) throws GitAPIException;

    public void createBranch(String name) throws GitAPIException;

    public void renameBranch(String name) throws GitAPIException;

    public void checkout(String name) throws GitAPIException;

    public void history() throws IOException, NoHeadException, GitAPIException;

    public void pull() throws GitAPIException;

    public void push() throws GitAPIException;

    public void clone(String user, String password, String remoteUrl, File localPath) throws IOException, GitAPIException;

}
