/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agnisys.idsnextgen.strings;

import com.agnisys.idsnextgen.i18n.IDSNGLocale;

/**
 *
 * @author Agnisys
 */
public class IDSString {

    //Main Menu
    public static final String MENU_FILE = "File";
    public static final String MENU_EDIT = "Edit";
    public static final String MENU_VIEW = "View";
    public static final String MENU_NAVIGATOR = "Navigator";
    public static final String MENU_RUN = "Run";
    public static final String MENU_TOOL = "Tool";
    public static final String MENU_HELP = "Help";

    public static final String MENU_FILE_NEW = "New";
    public static final String MENU_FILE_NEW_PROJ = "Project";
    public static final String MENU_CREATE_PROJ = "Create Project";
    public static final String MENU_NEW_FILE = "File";
    public static final String MENU_NEW_IP = "Create IP";
    public static final String MENU_NEW_REG = "Register Spec";
    public static final String MENU_NEW_TEXT = "Text File";
    public static final String MENU_NEW_CANVAS = "Canvas";

    public static final String MENU_OPEN = "Open";
    public static final String MENU_OPEN_PROJ = "Project";
    public static final String MENU_OPEN_FILE = "File";
    public static final String MENU_CLOSE_PROJ = "Close Project";
    public static final String MENU_IMPORT_PROJ = "Import Project";
    public static final String MENU_EXPORT_PROJ = "Export Project";
    public static final String MENU_RESTART_IDS = "Restart IDS";
    public static final String MENU_SAVE = "Save";
    public static final String MENU_SAVE_ALL = "Save All";
    public static final String MENU_EXIT = "Exit";

    public static final String MENU_UNDO = "Undo";
    public static final String MENU_REDO = "Redo";
    public static final String MENU_COPY = "Copy";
    public static final String MENU_CUT = "Cut";
    public static final String MENU_PASTE = "Paste";
    public static final String MENU_DELETE = "Delete";
    public static final String MENU_FIND = "Find";
    public static final String MENU_REPLACE = "Replace";

    public static final String MENU_CUST_TOOLBAR = "Customize Toolbar";
    public static final String MENU_IDS_EDITOR = "IDS Editor";
    public static final String MENU_GRAPHICAL_VIEW = "Graphical View";
    public static final String MENU_PROJ_EXP = "Project Explorer";
    public static final String MENU_PROP_WINDOW = "Property Window";
    public static final String MENU_HIER_VIEW = "Hierarchy View";
    public static final String MENU_CONSOLE = "Console";

    public static final String MENU_GOTO_FILE = "GOTO File";
    public static final String MENU_GOTO_LINE = "GOTO Line";

    public static final String MENU_RUN_PROJ = "Run";
    public static final String MENU_SETTING = "Settings";
    public static final String MENU_CONFIG = "Configuration";

    public static final String TOOLBAR_CONFIG = "Configure";
    public static final String TOOLBAR_OUTDIR = "OutDir";
    public static final String TOOLBAR_SYSTEM = "System";
    public static final String TOOLBAR_BOARD = "Board";
    public static final String TOOLBAR_CHIP = "Chip";
    public static final String TOOLBAR_BLOCK = "Block";
    public static final String TOOLBAR_REG_GROUP = "RegGroup";
    public static final String TOOLBAR_REGISTER = "Reg";
    public static final String TOOLBAR_TRIGBUF = "TrigBuf";
    public static final String TOOLBAR_TOC = "ToC";
    public static final String TOOLBAR_Ref = "Ref";
    public static final String TOOLBAR_MEMORY = "Memory";
    public static final String TOOLBAR_ENUM = "Enum";
    public static final String TOOLBAR_DEFINE = "Define";
    public static final String TOOLBAR_VARIENT = "Variant";
    public static final String TOOLBAR_IMPORT = "Import";
    public static final String TOOLBAR_MARK_BIT = "MarkBits";
    public static final String TOOLBAR_HIERARCHY = "Hierarchy";
    public static final String TOOLBAR_SEQ = "Sequence";
    public static final String TOOLBAR_BUSDOMAIN = "BusDomain";
    public static final String TOOLBAR_SIGNAL = "Signal";
    public static final String TOOLBAR_CHECK = "Check";
    public static final String TOOLBAR_GENERATE = "Generate";
    public static final String TOOLBAR_TURBO = "Turbo";

    //toolbar tooltips
    public static final String TOOLTIP_CONFIG = "Select outputs, settings, preferences";
    public static final String TOOLTIP_OUTDIR = "Open output directory";
    public static final String TOOLTIP_SYSTEM = "Add a System";
    public static final String TOOLTIP_BOARD = "Add a Board";
    public static final String TOOLTIP_CHIP = "Add a Chip";
    public static final String TOOLTIP_BLOCK = "Add a Block";
    public static final String TOOLTIP_REGGROUP = "Add a Register Group";
    public static final String TOOLTIP_REGISTER = "Add a Register with default width";
    public static final String TOOLTIP_TRIGBUFF = "Add a trigger buffer register";
    public static final String TOOLTIP_TOC = "Add a Table Of Contents";
    public static final String TOOLTIP_REF = "Add a Reference table";
    public static final String TOOLTIP_MEMORY = "Add a Memory table";
    public static final String TOOLTIP_ENUM = "Add an Enumeration table";
    public static final String TOOLTIP_DEFINE = "Add a Define table";
    public static final String TOOLTIP_VARIENT = "Add a Variant table";
    public static final String TOOLTIP_IMPORT = "Import files";
    public static final String TOOLTIP_MARKBITS = "Mark occupied/overlapping bits";
    public static final String TOOLTIP_HIER = "Open Hierarchy window";
    public static final String TOOLTIP_SEQ = "Add a Sequence";
    public static final String TOOLTIP_BUSDOMAIN = "Add a Bus Domains table";
    public static final String TOOLTIP_SIGNAL = "Add a Signal table";
    public static final String TOOLTIP_CHECK = "Perform sanity checks";
    public static final String TOOLTIP_GENERATE = "Generate outputs";
    public static final String TOOLTIP_TURBO = "Generate outputs without document update";

    IDSNGLocale idsngLocal;

    public IDSString() {
        idsngLocal = new IDSNGLocale();
    }

    public String get_COMPANY() {
        return idsngLocal.getBundle().getString("COMPANY");
    }

    public String get_COPYRIGHT() {
        return idsngLocal.getBundle().getString("COPYRIGHT");
    }

    public String get_SAVE_DOCUMENTL() {
        return idsngLocal.getBundle().getString("SAVE_DOCUMENT");
    }

    public String get_GENERAL() {
        return idsngLocal.getBundle().getString("GENERAL");
    }

    public String get_OUTPUTS() {
        return idsngLocal.getBundle().getString("OUTPUTS");
    }

    public String get_USER_DEFINED_OUTPUTS() {
        return idsngLocal.getBundle().getString("USER-DEFINED_OUTPUTS");
    }

    public String get_SETTINGSL() {
        return idsngLocal.getBundle().getString("SETTINGS");
    }

    public String get_VARIANT() {
        return idsngLocal.getBundle().getString("VARIANT");
    }

    public String get_FORMATING() {
        return idsngLocal.getBundle().getString("FORMATING");
    }

    public String get_DATASHEET() {
        return idsngLocal.getBundle().getString("DATASHEET");
    }

    public String get_CUSTOM_CSV() {
        return idsngLocal.getBundle().getString("CUSTOM_CSV");
    }

    public String get_ADVANCE_VERIFICATION() {
        return idsngLocal.getBundle().getString("ADVANCE_VERIFICATION");
    }

    public String get_ADVANCE_DESIGN() {
        return idsngLocal.getBundle().getString("ADVANCE_DESIGN");
    }

    public String get_CONFIG_MAIN_TITLE() {
        return idsngLocal.getBundle().getString("CONFIG_MAIN_TITLE");
    }

    public String get_RENAME() {
        return idsngLocal.getBundle().getString("RENAME");
    }

    public String get_OK() {
        return idsngLocal.getBundle().getString("OK");
    }

    public String get_CHANGE_NAME() {
        return idsngLocal.getBundle().getString("CHANGE_NAME");
    }

    public String get_MENU_FOLDER() {
        return idsngLocal.getBundle().getString("FILE_FOLDER");
    }

    public String get_FOLDER_LOCATION() {
        return idsngLocal.getBundle().getString("FOLDER_LOCATION");
    }

    public String get_FOLDER_NAME() {
        return idsngLocal.getBundle().getString("FOLDER_NAME");
    }

    public String get_FOLDER_WIZARD_TITLE() {
        return idsngLocal.getBundle().getString("FOLDER_WIZARD_TITLE");
    }

    public String get_NewProj() {
        return idsngLocal.getBundle().getString("NEW_PROJ");
    }

    public String get_PROJ_NAME() {
        return idsngLocal.getBundle().getString("PROJ_NAME");
    }

    public String get_PROJ_LOC() {
        return idsngLocal.getBundle().getString("PROJ_LOC");
    }

    public String get_CREATE_PROJ() {
        return idsngLocal.getBundle().getString("CREATE_PROJ");
    }

    public String get_APPLICATION_TITLE() {
        return idsngLocal.getBundle().getString("APPLICATION_TITLE");
    }

    public String get_IP_WIZARD_TITLE() {
        return idsngLocal.getBundle().getString("IP_WIZARD_TITLE");
    }

    public String get_IPXACT() {
        return idsngLocal.getBundle().getString("IPXACT");
    }

    public String get_SYSTEM_RDL() {
        return idsngLocal.getBundle().getString("SYSTEM_RDL");
    }

    public String get_RALF() {
        return idsngLocal.getBundle().getString("RALF");
    }

    public String get_FILE_WIZARD_TITLE() {
        return idsngLocal.getBundle().getString("FILE_WIZARD_TITLE");
    }

    public String get_FILE_TYPE() {
        return idsngLocal.getBundle().getString("FILE_TYPE");
    }

    public String get_FILE_WORD() {
        return idsngLocal.getBundle().getString("FILE_WORD");
    }

    public String get_FILE_EXCEL() {
        return idsngLocal.getBundle().getString("FILE_EXCEL");
    }

    public String get_FILE_TEXT() {
        return idsngLocal.getBundle().getString("FILE_TEXT");
    }

    public String get_FILE_NAME() {
        return idsngLocal.getBundle().getString("FILE_NAME");
    }

    public String get_FILE_LOCATION() {
        return idsngLocal.getBundle().getString("FILE_LOCATION");
    }

    public String get_FILE_BROWSE() {
        return idsngLocal.getBundle().getString("FILE_BROWSE");
    }

    public String get_FILE_CREATE() {
        return idsngLocal.getBundle().getString("FILE_CREATE");
    }

    public String get_CANCEL() {
        return idsngLocal.getBundle().getString("FILE_CANCEL");
    }
}
