@echo off

set SCRIPT_DIR=%~dp0
cd /d "%SCRIPT_DIR%"

set JAR_FILE=ServerUI.jar
set MODULE_PATH=C:\Users\Gaming\Documents\Software Engineering\Semester 4\OOP\javafx-sdk-18\lib

java --module-path "%MODULE_PATH%" --add-modules javafx.controls,javafx.fxml,com.jfoenix --add-exports javafx.controls/com.sun.javafx.scene.control.behavior=com.jfoenix --add-exports javafx.controls/com.sun.javafx.scene.control=com.jfoenix --add-exports javafx.base/com.sun.javafx.binding=com.jfoenix --add-exports javafx.graphics/com.sun.javafx.stage=com.jfoenix --add-exports javafx.base/com.sun.javafx.event=com.jfoenix -jar "%JAR_FILE%"
