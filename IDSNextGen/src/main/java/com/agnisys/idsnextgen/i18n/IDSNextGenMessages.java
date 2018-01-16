/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agnisys.idsnextgen.i18n;

import com.ibatis.common.resources.Resources;
import java.io.IOException;
import java.util.List;
import java.util.ListIterator;
import java.util.Properties;
import java.util.regex.Matcher;

/**
 *
 * @author avdhesh
 */
public final class IDSNextGenMessages {

    Properties props = null;

    final String INFO = "INFO-B : ";
    final String ERROR = "ERROR-B : ";
    final String WARN = "WARN-B : ";

    public IDSNextGenMessages() {
        Resources.setDefaultClassLoader(getClass().getClassLoader());
        loadDefaultConfig();
    }

    public void loadDefaultConfig() {
        try {
            //props = Resources.getResourceAsProperties("us/agnisys/idsbatch/messages.properties");
            props = Resources.getResourceAsProperties("messages.properties");
        } catch (IOException ex) {
            //     FileManager.log(Level.FINER, ex.getMessage());
        }
        //  FileManager.log(Level.FINER, "Prop = " + props.getProperty("100"));
    }

    public String getMsgString(long code) {
        String message_code = Long.toString(code);
        return props.getProperty(message_code);
    }

    public String getError(long code) {
        String message_code = Long.toString(code);
        String msg = ERROR;
        msg = msg.concat(props.getProperty(message_code));

        return msg;
    }

    public String getError(long code, List args) {
        String message_code = Long.toString(code);
        String msg = ERROR;
        msg = msg.concat(props.getProperty(message_code));
        ListIterator it = args.listIterator();
        while (it.hasNext()) {
            Object orig_arg = it.next();
            Object arg = null;
            if (orig_arg instanceof Integer) {
                arg = String.valueOf(orig_arg);
            } else if (orig_arg instanceof String) {
                arg = orig_arg;
            } else {
                arg = orig_arg.toString();
            }
            String argStr = "";
            if (arg != null) {
                argStr = (String) arg;
            }
            argStr = Matcher.quoteReplacement(argStr);
            msg = msg.replaceFirst("%s", argStr);
        }
        return msg;
    }

    public String getWarn(long code) {
        String message_code = Long.toString(code);
        String msg = WARN;
        msg = msg.concat(props.getProperty(message_code));
        return msg;
    }

    public String getInfo(long code) {
        String message_code = Long.toString(code);
        String msg = INFO;
        msg = msg.concat(props.getProperty(message_code));
        return msg;
    }

    public String getInfo(long code, List args) {

        String message_code = Long.toString(code);
        String msg = INFO;
        msg = msg.concat(props.getProperty(message_code));
        ListIterator it = args.listIterator();
        while (it.hasNext()) {
            Object arg = it.next();
            if (arg instanceof Integer) {
                arg = String.valueOf(arg);
            } else if (arg instanceof String) {
                arg = arg;
            } else {
                arg = arg.toString();
            }
            String argStr = (String) arg;
            argStr = Matcher.quoteReplacement(argStr);
            msg = msg.replaceFirst("%s", argStr);

        }
        return msg;
    }

}
