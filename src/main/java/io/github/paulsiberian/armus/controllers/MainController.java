/*
 * Copyright (c) Храпунов П. Н., 2019.
 */

package io.github.paulsiberian.armus.controllers;

import io.github.paulsiberian.armus.GUIManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public abstract class MainController implements Initializable {

    /* File menu */

    @FXML
    public final void file_new_workspace() {
    }

    @FXML
    public final void file_openWorkspace() {
    }

    @FXML
    public final void file_closeWorkspace() {
    }

    @FXML
    public final void file_backupWorkspace() {
    }

    @FXML
    public final void file_settings() {
    }

    @FXML
    public final void file_exit() {
        GUIManager.getInstance().getWindow().close();
    }

    /* ============= */



    /* Database menu */

    @FXML
    public final void database_add_institute() {
    }

    @FXML
    public final void database_add_department() {
    }

    @FXML
    public final void database_add_employee() {
    }

    @FXML
    public final void database_add_employeePosition() {
    }

    @FXML
    public final void database_add_group() {
    }

    @FXML
    public final void database_add_student() {
    }

    @FXML
    public final void database_add_discipline() {
    }

    @FXML
    public final void database_settings() {
    }

    @FXML
    public final void database_view() {
    }

    /* ============= */



    /* Help menu */

    @FXML
    public final void help_site() {
    }

    @FXML
    public final void help_checkForUpdates() {
    }

    @FXML
    public final void help_about() {
    }

    /* ============= */

}
