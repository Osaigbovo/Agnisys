/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agnisys.idsnextgen.classes;

import com.agnisys.idsnextgen.controllers.AboutController;
import com.agnisys.idsnextgen.controllers.ApplicationMainGUIController;
import com.agnisys.idsnextgen.global.IDSUtils;
import com.agnisys.idsnextgen.transformation.Transformation;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author Sumeet
 */
public class IDSTerminal {

    TextArea txtTerminal;
    TextArea txtCmd;
    ArrayList<String> cmdLogList = new ArrayList();
    int cmdLogIndex = 0;
    int currIndex = 0;
    static String workingspace;

    private IDSTerminal() {
    }

    public IDSTerminal(TextArea txt, TextArea cmd) {
        txtTerminal = txt;
        txtCmd = cmd;
        initilization();
    }

    private void initilization() {
        txtCmd.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                terminalKeyEventHandler(keyEvent);
//                return;
            }
        });
        workingspace = ApplicationMainGUIController.APPLICATION_OBJECT.getCURRENT_PROJECT_ROOT();
    }

    private void terminalKeyEventHandler(KeyEvent keyEvent) {
        String cmd = txtCmd.getText().trim();
        if (keyEvent.getCode() == KeyCode.ENTER) {
            runcommand(cmd);
            txtCmd.setText("");
            cmdLogList.add(cmd);
            cmdLogIndex++;
            keyEvent.consume();
            //txtCmd.positionCaret(1);
            txtCmd.requestFocus();
            txtCmd.end();

        } else if (keyEvent.getCode() == KeyCode.UP) {
            try {
                if (cmdLogIndex > 0) {
                    cmdLogIndex--;
                    txtCmd.setText(cmdLogList.get(cmdLogIndex));
                }
            } catch (Exception e) {
                System.err.println("ERROR cmd up : " + e.getMessage());
            }
            txtCmd.requestFocus();
            txtCmd.end();

        } else if (keyEvent.getCode() == KeyCode.DOWN) {
            try {
                if (cmdLogIndex < cmdLogList.size()) {
                    cmdLogIndex++;
                    txtCmd.setText(cmdLogList.get(cmdLogIndex));
                }
            } catch (Exception e) {
                System.err.println("ERROR cmd down : " + e.getMessage());
            }
            txtCmd.requestFocus();
            txtCmd.end();

        } else if (keyEvent.getCode() == KeyCode.TAB) {
            if (tabCommand == null) {
                tabCommand = txtCmd.getText();
            }
            browseFiles(tabCommand);
            keyEvent.consume();
            txtCmd.requestFocus();
            txtCmd.end();

        } else {
            tabCommand = null;
        }
    }

    String lastPath = "";
    ArrayList<File> listOfFiles;
    static int count = 0;
    static String tabCommand = null;

    private void browseFiles(String cmd) {
        if (IDSUtils.isWindow()) {
            browseWindow(cmd);
        } else {
            browseLinux(cmd);
        }
    }

    private void browseWindow(String cmd) {
        String command;
        if (cmd.endsWith(" ") || "".equals(cmd)) {
            command = "";
        } else {
            String[] saparateSpace = cmd.split(" ");
            command = saparateSpace[saparateSpace.length - 1];
        }

        String searchpath;
        if (command.contains("\\")) {
            searchpath = command.substring(0, command.lastIndexOf("\\"));
        } else {
            searchpath = "";
        }
        searchpath = workingspace + File.separator + searchpath;
        //System.out.println("--Full search path=" + searchpath);

        File searchFile = new File(searchpath);
        String searchName = command.substring(command.lastIndexOf("\\") + 1, command.length());
        String temp = searchpath + File.separator + searchName;

        if (!lastPath.equals(temp)) {
            listOfFiles = getSearchedFileName(searchFile, searchName);
            lastPath = temp;
            count = 0;
        }

        //searchName = searchName.replaceAll("\\\\", "");
        if (count == listOfFiles.size()) {
            count = 0;
        }
        String fileName = listOfFiles.get(count).getName();
        count++;
        int cmdlastIndex = cmd.lastIndexOf("\\");
        String finalOut;
        if (cmdlastIndex >= 0) {

            finalOut = cmd.substring(0, cmd.lastIndexOf("\\") + 1) + fileName;
        } else {
            finalOut = cmd.substring(0, cmd.lastIndexOf(" ") + 1) + fileName;
        }

        txtCmd.setText(finalOut);
    }

    private void browseLinux(String cmd) {
        String[] saparateSpace = cmd.split(" ");
        String command = saparateSpace[saparateSpace.length - 1];
        String searchpath;
        if (command.contains("/")) {
            searchpath = command.substring(0, command.lastIndexOf("/"));
        } else {
            searchpath = "";
        }
        searchpath = workingspace + File.separator + searchpath;
        //System.out.println("--Full search path=" + searchpath);

        File searchFile = new File(searchpath);
        String searchName = command.substring(command.lastIndexOf("/") + 1, command.length());

        if (!lastPath.equals(searchpath)) {
            listOfFiles = getSearchedFileName(searchFile, searchName);
            lastPath = searchpath;
            count = 0;
        }

        //searchName = searchName.replaceAll("\\\\", "");
        if (count == listOfFiles.size()) {
            count = 0;
        }
        String fileName = listOfFiles.get(count).getName();
        count++;
        String finalOut = cmd.substring(0, cmd.lastIndexOf("/") + 1) + fileName;
        txtCmd.setText(finalOut);
    }

    private ArrayList<File> getSearchedFileName(File searchDir, String searchFile) {
        //System.out.println("--searchPath=" + searchDir.getAbsolutePath());
        //System.out.println("--searchName=" + searchFile);
        ArrayList<File> listFiles = new ArrayList<>();

        for (File f : searchDir.listFiles()) {
            if (f.getName().startsWith(searchFile)) {
                listFiles.add(f);
            }
        }
        return listFiles;
    }

    private void runshellcommand(String cmd) {
        String output;
        if (IDSUtils.isWindow()) {
            cmd = "cmd /c " + cmd;
            output = executeCommand(cmd, workingspace);
        } else {
            System.out.println("--LINUX CMD");
            cmd = "bash -c " + cmd;
            output = executeCommand(cmd, workingspace);
        }
        setIDSTerminalText(output);
    }

    public void setIDSTerminalText(String output) {
        txtTerminal.appendText("\n" + output);
    }

    private String executeCommand(String command, String workingPath) {
        //command = "cmd /c dir";
        //System.out.println("--cmd=" + command);
        //System.out.println("--workingpath=" + workingPath);
        StringBuffer output = new StringBuffer();

        Process p;
        try {
            p = Runtime.getRuntime().exec(command, null, new File(workingPath));
            //p = Runtime.getRuntime().exec(command);
            p.waitFor();
            BufferedReader reader
                    = new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

        } catch (IOException | InterruptedException e) {
        }

        return output.toString();

    }

    private void runcommand(String cmd) {
        //cmd = cmd.trim();
        // System.out.println("--final cmd=" + cmd);
        //txtTerminal.appendText("\n" + cmd);
        String startCmd = cmd.split(" ")[0];
        //System.out.println("-cmd run com=" + cmd);
        switch (startCmd) {
            case "version":
            case "ver":
                getIDSNGVersion();
                break;
            case "clear":
                clear();
                break;
            case "cd":
                cmdCd();
                break;
            case "check":
                cmdCheck(cmd);
                break;
            case "generate":
                cmdGenerate(cmd);
                break;
            case "help":
                cmdHelp();
                break;
            default:
                runshellcommand(cmd);
                break;

        }
        //IDSManager.ngBatchWrapper(cmd)
    }

    private void cmdHelp() {
        String str = "-------------IDSNG COMMAND LIST------------\n"
                + "check <file path>      : run check \n"
                + "generate <file path> : generate ouputput which is selected in config \n"
                + "clear                        : clear console\n"
                + "help                         : help\n"
                + "version                     : show IDSNG version\n"
                + "run other shell commands";
        setIDSTerminalText(str);
    }

    private void cmdCheck(String cmd) {
        setIDSTerminalText("Info : your document is checking...");
        String filePath = workingspace + File.separator + cmd.split(" ")[1];
        String messFromBatch = Transformation.getTransformer().validateSpec(true, filePath);

        if (messFromBatch != null && !messFromBatch.equals(Transformation.GUIBATCHERROR)) {
            setIDSTerminalText(messFromBatch);
            ApplicationMainGUIController.APPLICATION_OBJECT.refreshProject();
        } else if (messFromBatch != null && messFromBatch.equals(Transformation.GUIBATCHERROR)) {

        } else if (messFromBatch == null) {
            setIDSTerminalText("Info : Document Checked Successfully !");
            ApplicationMainGUIController.APPLICATION_OBJECT.refreshProject();
        } else {
            setIDSTerminalText("Unexpected Error !");
        }
    }

    private void cmdGenerate(String cmd) {
        setIDSTerminalText("Info : Output generating...");
        String filePath = workingspace + File.separator + cmd.split(" ")[1];
        String messFromBatch = Transformation.getTransformer().validateSpec(false, filePath);

        if (messFromBatch != null && !messFromBatch.equals(Transformation.GUIBATCHERROR)) {
            setIDSTerminalText(messFromBatch);
            ApplicationMainGUIController.APPLICATION_OBJECT.refreshProject();
        } else if (messFromBatch != null && messFromBatch.equals(Transformation.GUIBATCHERROR)) {

        } else if (messFromBatch == null) {
            setIDSTerminalText("Info : Output generated Successfully ! \n Output file location : <yourfile>/idsng");
            ApplicationMainGUIController.APPLICATION_OBJECT.refreshProject();
        } else {
            setIDSTerminalText("Unexpected Error !");
        }
    }

    private void cmdCd() {
        setIDSTerminalText("Error : This command is not supported yet");
    }

    private void clear() {
        txtTerminal.setText("");
    }

    private void getIDSNGVersion() {
        setIDSTerminalText(AboutController.VERSION);
    }
}
