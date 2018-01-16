/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agnisys.idsnextgen.team;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;

/**
 *
 * @author avdhesh
 */
public final class GitRunnable implements TeamRunnable {

    Repository repo;
    Git git;

    @Override
    public void createRepository(String repoPath) throws IOException {
        repo = FileRepositoryBuilder.create(new File(repoPath));

    }

    @Override
    public void openRepository(String repoPath) throws IOException {
        repo = new FileRepositoryBuilder().setGitDir(new File(repoPath)).build();
    }

    public GitRunnable() {//default constructor..
    }

    public GitRunnable(File projPath) throws IOException {

        if (projPath.exists()) {
            openRepository(projPath.getAbsolutePath());
        } else {
            createRepository(projPath.getAbsolutePath());
        }
        System.out.println("Git repo successfully");
        git = new Git(repo);
        System.out.println("Done " + repo);
    }

    @Override
    public void commit(String message) throws GitAPIException {
        git.commit().setMessage(message).call();

    }

    @Override
    public void createBranch(String name) throws GitAPIException {
        git.branchCreate().setName(name).call();

    }

    @Override
    public void renameBranch(String name) throws GitAPIException {
        git.branchRename().setNewName(name).call();
    }

    @Override
    public void checkout(String name) throws GitAPIException {
        git.checkout().setName(name).call();

    }

    @Override
    public void history() throws IOException, NoHeadException, GitAPIException {//It will print on standard out ..It might be redirectedin in future..
        List<String> logMessages = new ArrayList<String>();
        Iterable<RevCommit> log = git.log().call();
        RevCommit previousCommit = null;
        for (RevCommit commit : log) {
            if (previousCommit != null) {
                AbstractTreeIterator oldTreeIterator = getCanonicalTreeParser(previousCommit);
                AbstractTreeIterator newTreeIterator = getCanonicalTreeParser(commit);
                OutputStream outputStream = new ByteArrayOutputStream();
                try (DiffFormatter formatter = new DiffFormatter(outputStream)) {
                    formatter.setRepository(git.getRepository());
                    formatter.format(oldTreeIterator, newTreeIterator);
                }
                String diff = outputStream.toString();
                System.out.println(diff);
            }
            System.out.println("LogCommit: " + commit);
            String logMessage = commit.getFullMessage();
            System.out.println("LogMessage: " + logMessage);
            logMessages.add(logMessage.trim());
            previousCommit = commit;
        }

    }

    private AbstractTreeIterator getCanonicalTreeParser(ObjectId commitId) throws IOException {
        try (RevWalk walk = new RevWalk(git.getRepository())) {
            RevCommit commit = walk.parseCommit(commitId);
            ObjectId treeId = commit.getTree().getId();
            try (ObjectReader reader = git.getRepository().newObjectReader()) {
                return new CanonicalTreeParser(null, reader, treeId);
            }
        }
    }

    @Override
    public void clone(String user, String password, String remoteUrl, File localPath) throws IOException, GitAPIException {

        git = Git.cloneRepository().setURI(remoteUrl).setCredentialsProvider(new UsernamePasswordCredentialsProvider(user, password))
                .setDirectory(localPath)
                .call();
    }

    @Override
    public void pull() throws GitAPIException { //TODO : If require set ProgressBar Moniter.. and print pullResults
        git.pull().call();
    }

    @Override
    public void push() throws GitAPIException {// TODO : If require set ProgressBar Moniter.. and print pushResults
        git.push().call();

    }

}
