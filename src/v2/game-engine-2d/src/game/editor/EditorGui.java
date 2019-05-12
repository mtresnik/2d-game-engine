package game.editor;

import game.Game;
import game.objects.Player;
import game.objects.Structure;
import game.data.LevelData;
import game.data.intuitive.BackgroundCollection;
import game.data.intuitive.BackgroundData;
import game.data.intuitive.EntityCollection;
import game.data.intuitive.EntityData;
import game.data.intuitive.PlayerData;
import game.data.intuitive.StructureCollection;
import game.data.intuitive.StructureData;
import game.editor.modify.ModifyGetEvent;
import game.editor.modify.ModifyGetEventData;
import game.editor.modify.ModifyGetEventData.LevelDataCallbackI;
import game.editor.modify.ModifyRemoveEvent;
import game.editor.modify.ModifyRemoveEventData;
import game.editor.modify.ModifySetEvent;
import game.editor.modify.ModifySetEventData;
import game.editor.serial.LoadEvent;
import game.editor.serial.LoadEventData;
import game.editor.serial.SaveEvent;
import game.editor.serial.SaveEventData;
import game.objects.Background;
import game.objects.Entity;
import game.physics.Hitbox;
import java.io.File;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Queue;
import java.util.function.Function;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Pair;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import game.render.Display;
import util.Initializable;

public class EditorGui extends EditorFireEventObject implements Initializable {

    public static int SPACE = 100;
    public static Stage primaryStage;
    public static EditorFireEventObject fireInstance;

    public EditorGui() {
    }

    public static void main(String[] args) {
        new EditorGui().init();
    }

    public static void fireEditorEvent(EditorEvent event) {
        if (Game.instance != null) {
            Game.messageRecieved_s(event);
        }
    }

    @Override
    public void init() {
        fireInstance = this;
        new ActionWindow().extLaunch(new String[]{});
    }

    public static class ActionWindow extends Application {

        public static final int OFFSET = 10;
        public static final int HEIGHT = 50;
        public static int width = 240, height = HEIGHT * 5 + OFFSET * 6;
        public static List<Pair<int[], Boolean>> positionList;
        public static ClearWindow cw;
        public static LoadWindow lw;
        public static SaveWindow sw;
        public static ModifyObject mo;
        public static double prevx, prevy;

        public void extLaunch(String[] args) {
            launch(args);
        }

        @Override
        public void start(Stage primaryStage) throws Exception {
            EditorGui.primaryStage = primaryStage;
            primaryStage.setResizable(false);
            primaryStage.setAlwaysOnTop(true);

            positionList = new ArrayList();

            Button clearWindowButton = new Button();

            // <editor-fold desc="ClearWindow">
            clearWindowButton.setText("Clear Level");
            clearWindowButton.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    try {
                        if (cw != null) {
                            cw.window.show();
                            return;
                        }
                        updatePositionList();
                        cw = new ClearWindow(getOpenSpot());
                        cw.start(primaryStage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            clearWindowButton.setLayoutX(OFFSET);
            clearWindowButton.setLayoutY(OFFSET);
            clearWindowButton.setPrefHeight(HEIGHT);
            clearWindowButton.setPrefWidth(width - 2 * OFFSET);
            // </editor-fold>

            Button loadWindowButton = new Button();

            // <editor-fold desc="LoadWindow">
            loadWindowButton.setText("Load Level");
            loadWindowButton.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    try {
                        if (lw != null) {
                            return;
                        }
                        updatePositionList();
                        lw = new LoadWindow(getOpenSpot());
                        lw.start(primaryStage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            loadWindowButton.setLayoutX(OFFSET);
            loadWindowButton.setLayoutY(HEIGHT + 2 * OFFSET);
            loadWindowButton.setPrefHeight(HEIGHT);
            loadWindowButton.setPrefWidth(width - 2 * OFFSET);
            // </editor-fold>

            Button saveWindowButton = new Button();

            // <editor-fold desc="Save Window">
            saveWindowButton.setText("Save Level");
            saveWindowButton.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    try {
                        if (sw != null) {
                            return;
                        }
                        updatePositionList();
                        sw = new SaveWindow(getOpenSpot());
                        sw.start(primaryStage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            saveWindowButton.setLayoutX(OFFSET);
            saveWindowButton.setLayoutY(HEIGHT * 2 + 3 * OFFSET);
            saveWindowButton.setPrefHeight(HEIGHT);
            saveWindowButton.setPrefWidth(width - 2 * OFFSET);
            // </editor-fold>

            Button addObjectButton = new Button();

            // <editor-fold desc="Add Object">
            addObjectButton.setText("Add Object");
            addObjectButton.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    try {
                        if (mo != null) {
                            if (mo.autoFill == false) {
                                mo.closeAll();
                            } else {
                                return;
                            }
                        }
                        updatePositionList();
                        mo = new ModifyObject(getOpenSpot());
                        mo.start(primaryStage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            addObjectButton.setLayoutX(OFFSET);
            addObjectButton.setLayoutY((3 * HEIGHT + 4 * OFFSET));
            addObjectButton.setPrefHeight(HEIGHT);
            addObjectButton.setPrefWidth(width - 2 * OFFSET);

            // </editor-fold>
            Button modifyObjectButton = new Button();

            // <editor-fold desc="Modify Object">
            modifyObjectButton.setText("Modify Object");
            modifyObjectButton.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    try {
                        if (mo != null) {
                            if (mo.autoFill) {
                                mo.closeAll();
                            } else {
                                return;
                            }
                        }
                        updatePositionList();
                        mo = new ModifyObject(getOpenSpot(), false);
                        mo.start(primaryStage);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            modifyObjectButton.setLayoutX(OFFSET);
            modifyObjectButton.setLayoutY((4 * HEIGHT + 5 * OFFSET));
            modifyObjectButton.setPrefHeight(HEIGHT);
            modifyObjectButton.setPrefWidth(width - 2 * OFFSET);

            // </editor-fold>
            Pane root = new Pane();
            root.getChildren().add(clearWindowButton);
            root.getChildren().add(loadWindowButton);
            root.getChildren().add(saveWindowButton);
            root.getChildren().add(addObjectButton);
            root.getChildren().add(modifyObjectButton);

            Scene scene = new Scene(root, width, height);
            primaryStage.setTitle("ACTIONS");
            primaryStage.setScene(scene);
            if (Display.isOpen) {
                primaryStage.setX(Display.xpos + Display.dimensions[0]);
                primaryStage.setY(Display.ypos);
            }
            primaryStage.show();

            primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    if (cw != null) {
                        cw.window.close();
                    }
                    if (lw != null) {
                        lw.window.close();
                    }
                    if (sw != null) {
                        sw.window.close();
                    }
                    if (mo != null) {
                        mo.window.close();
                    }
                    primaryStage.close();
                    System.exit(0);
                }
            });

            updatePositionList();

        }

        public static void updatePositionList() {
            if (primaryStage.getX() == prevx && primaryStage.getY() == prevy) {
                return;
            }
            prevx = primaryStage.getX();
            prevy = primaryStage.getY();
            positionList.clear();
            int tSPACE = SPACE + 40;
            for (int i = 0; i < 10; i++) {
                positionList.add(new Pair(new int[]{(int) (primaryStage.getX() + width), (int) (primaryStage.getY() + i * tSPACE)}, false));
            }
        }

        public static int[] getOpenSpot() {
            // return the lowest false int array
            List<int[]> falseArray = new ArrayList();
            for (Pair<int[], Boolean> pair : positionList) {
                if (pair.getValue() == false) {
                    falseArray.add(pair.getKey());
                }
            }
            falseArray.sort(new Comparator<int[]>() {
                @Override
                public int compare(int[] t, int[] t1) {
                    return Integer.compare(t[1], t1[1]);
                }
            });
            if (falseArray.isEmpty()) {
                return null;
            }
            return falseArray.get(0);
        }

        public static void occupy(int[] array) {
            for (int i = 0; i < positionList.size(); i++) {
                Pair<int[], Boolean> currPair = positionList.get(i);
                if (currPair.getValue() == true) { //already freed
                    continue;
                }
                if (Arrays.equals(array, currPair.getKey())) {
                    positionList.set(i, new Pair(array, true));
                    break;
                }
            }
        }

        public static void free(int[] array) {
            for (int i = 0; i < positionList.size(); i++) {
                Pair<int[], Boolean> currPair = positionList.get(i);
                if (currPair.getValue() == false) { // already occupied
                    continue;
                }
                if (Arrays.equals(array, currPair.getKey())) {
                    positionList.set(i, new Pair(array, false));
                    break;
                }
            }
        }

        public static class ClearWindow extends Application {

            Stage window;
            int[] arrayRef;
            Label question;
            Button yesButton;
            Button noButton;

            public ClearWindow(int[] arrayRef) {
                this.arrayRef = arrayRef;
            }

            @Override
            public void start(Stage primaryStage) throws Exception {

                GridPane grid = new GridPane();
                grid.setPadding(new Insets(10, 10, 10, 10));
                grid.setVgap(5);
                grid.setHgap(5);
                //Defining the Name text field
                question = new Label("Really clear level?");
                GridPane.setConstraints(question, 2, 0);
                grid.getChildren().add(question);
                //Defining the Submit button
                yesButton = new Button("Yes");
                GridPane.setConstraints(yesButton, 1, 1);
                yesButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        fireEditorEvent(new ClearEvent(this, null));
                        window.close();
                    }
                });
                grid.getChildren().add(yesButton);
                // Load button
                noButton = new Button("No");
                GridPane.setConstraints(noButton, 3, 1);
                noButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        System.out.println("No handle");
                        window.close();

                    }
                });
                grid.getChildren().add(noButton);

                Scene secondScene = new Scene(grid, 230, SPACE);

                // New window (Stage)
                window = new Stage();
                window.setTitle("CLEAR CONFIRM");
                window.setScene(secondScene);
                window.setAlwaysOnTop(true);

                // Set position of second window, related to primary window.
                window.setX(arrayRef[0]);
                window.setY(arrayRef[1]);
                ActionWindow.occupy(arrayRef);

                window.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent event) {
                        window.close();
                        ActionWindow.lw = null;
                        ActionWindow.free(arrayRef);
                    }
                });

                window.setResizable(false);
                window.show();
            }

        }

        public static class LoadWindow extends Application {

            Stage window;
            int[] arrayRef;
            Label infoLabel;
            TextField fileLocationField;
            Label promptText;
            Button openFile;
            Button loadButton;

            public LoadWindow(int[] arrayRef) {
                this.arrayRef = arrayRef;
            }

            @Override
            public void start(Stage primaryStage) throws Exception {

                GridPane grid = new GridPane();
                grid.setPadding(new Insets(10, 10, 10, 10));
                grid.setVgap(5);
                grid.setHgap(5);
                //Defining the Name text field
                infoLabel = new Label("File Location");
                GridPane.setConstraints(infoLabel, 0, 0);
                grid.getChildren().add(infoLabel);
                //Defining the Last Name text field
                fileLocationField = new TextField();
                GridPane.setConstraints(fileLocationField, 0, 1);
                grid.getChildren().add(fileLocationField);
                //Defining the Comment text field
                promptText = new Label("(is valid level?)");
                GridPane.setConstraints(promptText, 0, 2);
                grid.getChildren().add(promptText);
                //Defining the Submit button
                openFile = new Button("...");
                GridPane.setConstraints(openFile, 1, 1);
                openFile.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
                        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                                "Level files", "xml");
                        chooser.setFileFilter(filter);
                        int returnVal = chooser.showOpenDialog(null);
                        if (returnVal == JFileChooser.APPROVE_OPTION) {
                            fileLocationField.setText(chooser.getSelectedFile().getAbsolutePath());
                        }
                    }
                });
                grid.getChildren().add(openFile);
                // Load button
                loadButton = new Button("Load");
                GridPane.setConstraints(loadButton, 1, 2);
                loadButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        if (mo != null) {
                            mo.closeAll();
                            mo = null;
                        }
                        if (Game.instance != null) {
                            fireEditorEvent(new LoadEvent(this, new LoadEventData(fileLocationField.getText())));
                        }
                    }
                });
                grid.getChildren().add(loadButton);

                Scene secondScene = new Scene(grid, 230, SPACE);

                // New window (Stage)
                window = new Stage();
                window.setTitle("LOAD");
                window.setScene(secondScene);
                window.setAlwaysOnTop(true);

                // Set position of second window, related to primary window.
                window.setX(arrayRef[0]);
                window.setY(arrayRef[1]);
                ActionWindow.occupy(arrayRef);

                window.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent event) {
                        window.close();
                        ActionWindow.lw = null;
                        ActionWindow.free(arrayRef);
                    }
                });

                window.setResizable(false);
                window.show();
            }

        }

        public static class SaveWindow extends Application {

            Stage window;
            int[] arrayRef;
            Label infoLabel;
            TextField fileLocationField;
            Label promptText;
            Button openFile;
            Button saveButton;

            public SaveWindow(int[] arrayRef) {
                this.arrayRef = arrayRef;
            }

            @Override
            public void start(Stage primaryStage) throws Exception {

                GridPane grid = new GridPane();
                grid.setPadding(new Insets(10, 10, 10, 10));
                grid.setVgap(5);
                grid.setHgap(5);
                //Defining the Name text field
                infoLabel = new Label("File Location");
                GridPane.setConstraints(infoLabel, 0, 0);
                grid.getChildren().add(infoLabel);
                //Defining the Last Name text field
                fileLocationField = new TextField();
                GridPane.setConstraints(fileLocationField, 0, 1);
                grid.getChildren().add(fileLocationField);
                //Defining the Comment text field
                promptText = new Label("");
                GridPane.setConstraints(promptText, 0, 2);
                grid.getChildren().add(promptText);
                //Defining the Submit button
                openFile = new Button("...");
                GridPane.setConstraints(openFile, 1, 1);
                openFile.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
                        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                                "Level files", "xml");
                        chooser.setFileFilter(filter);
                        int returnVal = chooser.showOpenDialog(null);
                        if (returnVal == JFileChooser.APPROVE_OPTION) {
                            fileLocationField.setText(chooser.getSelectedFile().getAbsolutePath());
                        }
                    }
                });
                grid.getChildren().add(openFile);
                // Load button
                saveButton = new Button("Save");
                GridPane.setConstraints(saveButton, 1, 2);
                grid.getChildren().add(saveButton);
                saveButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        fireEditorEvent(new SaveEvent(this, new SaveEventData(fileLocationField.getText())));
                    }

                });
                Scene secondScene = new Scene(grid, 230, SPACE);

                // New window (Stage)
                window = new Stage();
                window.setTitle("SAVE");
                window.setScene(secondScene);
                window.setAlwaysOnTop(true);

                // Set position of second window, related to primary window.
                window.setX(arrayRef[0]);
                window.setY(arrayRef[1]);
                ActionWindow.occupy(arrayRef);
                window.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent event) {
                        window.close();
                        ActionWindow.sw = null;
                        ActionWindow.free(arrayRef);
                    }
                });

                window.setResizable(false);

                window.show();
            }

        }

        public static class ModifyObject extends Application implements LevelDataCallbackI {

            static Stage window;
            int[] arrayRef;
            static boolean autoFill;
            static GridPane entityPane;
            static ComboBox comboBox;
            static PlayerWindow pw;
            static EntityWindow ew;
            static StructureWindow sw;
            static BackgroundWindow bw;
            static ModifyObject instance;
            public static Queue<LevelData> dataQueue = new ArrayDeque();

            public ModifyObject(int[] arrayRef) {
                this(arrayRef, true);
            }

            public ModifyObject(int[] arrayRef, boolean _autoFill) {
                if (dataQueue == null) {
                    dataQueue = new ArrayDeque();
                }
                dataQueue.clear();
                this.arrayRef = arrayRef;
                autoFill = _autoFill;
                instance = this;
            }

            @Override
            public void start(Stage primaryStage) throws Exception {

                comboBox = initComboBox();

                entityPane = new GridPane();
                entityPane.setPadding(new Insets(10, 10, 10, 10));
                entityPane.setVgap(5);
                entityPane.setHgap(5);
                //Defining the Name text field
                GridPane.setConstraints(comboBox, 2, 0);
                entityPane.getChildren().add(comboBox);

                Scene secondScene = new Scene(entityPane, 230, SPACE);

                // New window (Stage)
                window = new Stage();
                window.setTitle((autoFill ? "ADD OBJECT" : "MODIFY OBJECT"));
                window.setScene(secondScene);

                // Set position of second window, related to primary window.
                window.setX(arrayRef[0]);
                window.setY(arrayRef[1]);
                ActionWindow.occupy(arrayRef);
                window.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent event) {
                        closeAll();
                    }
                });

                window.setResizable(false);
                window.setAlwaysOnTop(true);

                window.show();
            }

            public void closeAllExisting() {
                if (pw != null) {
                    pw.window.close();
                }
                if (ew != null) {
                    ew.window.close();
                }
                if (sw != null) {
                    sw.window.close();
                }
                if (bw != null) {
                    bw.window.close();
                }
            }

            public void closeAll() {
                ActionWindow.mo = null;
                ActionWindow.free(arrayRef);
                closeAllExisting();
                window.close();
            }

            private static ComboBox initComboBox() {
                ObservableList<String> options = FXCollections.observableArrayList(
                        "Player",
                        "Entity",
                        "Structure",
                        "Background"
                );

                ComboBox retBox = new ComboBox(options);

                retBox.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        if (retBox.getValue() == null) {
                            return;
                        }
                        System.out.println(retBox.getValue().toString());
                        final String switcher = retBox.getValue().toString();
                        instance.closeAllExisting();
                        try {
                            switch (switcher) {
                                case "Player": {
                                    if (pw != null) {
                                        pw.window.close();
                                    }
                                    PlayerWindow tempWindow = new PlayerWindow(instance, autoFill);
                                    tempWindow.start(window);
                                    pw = tempWindow;
                                    break;
                                }
                                case "Entity": {
                                    if (ew != null) {
                                        ew.window.close();
                                    }
                                    EntityWindow tempWindow = new EntityWindow(instance, autoFill);
                                    tempWindow.start(window);
                                    ew = tempWindow;
                                    break;
                                }
                                case "Structure": {
                                    if (sw != null) {
                                        sw.window.close();
                                    }
                                    StructureWindow tempWindow = new StructureWindow(instance, autoFill);
                                    tempWindow.start(window);
                                    sw = tempWindow;
                                    break;
                                }
                                case "Background": {
                                    if (bw != null) {
                                        bw.window.close();
                                    }
                                    BackgroundWindow tempWindow = new BackgroundWindow(instance, autoFill);
                                    tempWindow.start(window);
                                    bw = tempWindow;
                                    break;
                                }
                                default:
                                    System.out.println("Invalid Option:" + switcher);
                            }
                        } catch (java.lang.IndexOutOfBoundsException ioobe) {
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
                retBox.getEditor().textProperty().addListener((observer, old_value, new_value) -> {
                    retBox.setValue(new_value);
                }
                );
                return retBox;
            }

            public static void resetComboBox() {
                entityPane.getChildren().remove(comboBox);
                comboBox = initComboBox();
                GridPane.setConstraints(comboBox, 2, 0);
                entityPane.getChildren().add(comboBox);
            }

            @Override
            public void update(LevelData data) {
//                System.out.println("current_thread:" + Thread.currentThread());
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        Editor.et.executeFunction(new Function<Void, Void>() {
                            @Override
                            public Void apply(Void t) {
                                EditorGui.ActionWindow.mo.updateSubClasses(data);
                                return null;
                            }
                        });
                    }
                });

            }

            public void updateSubClasses(LevelData data) {
                if (pw != null) {
                    pw.update(data.player_data);
                }
                if (ew != null) {
                    ew.update(data.entity_data);
                }
                if (sw != null) {
                    sw.update(data.structure_collection);
                }
                if (bw != null) {
                    bw.update(data.background_collection);
                }
            }

            public static class PlayerWindow extends Application {

                Stage window;
                boolean autoFill;
                PlayerData player_data;
                ModifyObject mo;
                TextField nameField;
                TextField xField, yField, wField, hField;
                TextField fileLocationField;
                Button confirmButton;
                Button deleteButton;

                public PlayerWindow(ModifyObject mo, boolean autoFill) {
                    this.mo = mo;
                    this.autoFill = autoFill;
                }

                @Override
                public void start(Stage primaryStage) throws Exception {

                    GridPane grid = new GridPane();
                    grid.setPadding(new Insets(10, 10, 10, 10));
                    grid.setVgap(5);
                    grid.setHgap(5);

                    nameField = new TextField();
                    nameField.setPromptText("Name");
                    GridPane.setConstraints(nameField, 0, 0);
                    GridPane.setColumnSpan(nameField, 2);
                    grid.getChildren().add(nameField);

                    xField = new TextField();
                    xField.setPromptText("x");
                    GridPane.setConstraints(xField, 0, 1);
                    grid.getChildren().add(xField);

                    yField = new TextField();
                    yField.setPromptText("y");
                    GridPane.setConstraints(yField, 1, 1);
                    grid.getChildren().add(yField);

                    wField = new TextField();
                    wField.setPromptText("width");
                    GridPane.setConstraints(wField, 0, 2);
                    grid.getChildren().add(wField);

                    hField = new TextField();
                    hField.setPromptText("height");
                    GridPane.setConstraints(hField, 1, 2);
                    grid.getChildren().add(hField);

                    fileLocationField = new TextField();
                    fileLocationField.setPromptText("File Location");
                    GridPane.setConstraints(fileLocationField, 0, 3);
                    GridPane.setColumnSpan(fileLocationField, 3);
                    grid.getChildren().add(fileLocationField);

                    confirmButton = new Button("Confirm");

                    confirmButton.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            if (verifyInputs() == false) {
                                crossFields();
                                return;
                            }
                            updatePlayerData();
                            LevelData tempLevelData = new LevelData();
                            tempLevelData.player_data = player_data;
                            ModifySetEventData eventData = new ModifySetEventData(tempLevelData);
                            ModifySetEvent setEvent = new ModifySetEvent(this, eventData);
                            fireEditorEvent(setEvent);
                        }
                    });

                    GridPane.setConstraints(confirmButton, 0, 4);
                    GridPane.setColumnSpan(confirmButton, 2);
                    grid.getChildren().add(confirmButton);

                    if (autoFill == false) {
                        deleteButton = new Button("Delete");

                        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                deleteHandle();
                            }
                        });

                        GridPane.setConstraints(deleteButton, 1, 4);
                        GridPane.setColumnSpan(deleteButton, 2);
                        grid.getChildren().add(deleteButton);
                    }

                    Scene secondScene = new Scene(grid, 230, 1.5 * SPACE);

                    // New window (Stage)
                    window = new Stage();
                    window.setTitle("PLAYER");
                    window.setScene(secondScene);
                    window.setAlwaysOnTop(true);
                    window.setX(primaryStage.getX() + primaryStage.getWidth());
                    window.setY(primaryStage.getY());

                    window.setOnCloseRequest(new EventHandler<WindowEvent>() {
                        @Override
                        public void handle(WindowEvent event) {
                            ModifyObject.resetComboBox();
                            ModifyObject.pw = null;
                        }
                    });

                    window.setResizable(false);
                    window.show();
                    ModifyGetEventData tempData = null;
                    tempData = new ModifyGetEventData(this.mo);
                    fireEditorEvent(new ModifyGetEvent(this, tempData));

                }

                private void deleteHandle() {
                    final String name = nameField.getText();
                    if (name == null || name.length() == 0) {
                        return;
                    }
                    ConfirmWindow cw = new ConfirmWindow() {
                        @Override
                        public String promptText() {
                            return "Really delete player: " + name + "?";
                        }

                        @Override
                        public void confirm() {
                            LevelData tempLevelData = new LevelData();
                            Player blank = Player.generateBlank(name);
                            tempLevelData.player_data = new PlayerData(player_data.doc, blank);
                            ModifyRemoveEventData eventData = new ModifyRemoveEventData(tempLevelData);
                            ModifyRemoveEvent setEvent = new ModifyRemoveEvent(this, eventData);
                            fireEditorEvent(setEvent);
                        }
                    };
                    try {
                        cw.start(primaryStage);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

                public boolean verifyInputs() {
                    boolean retVal = true;
                    // make sure the name field is non empty
                    if (nameField.getText().isEmpty()
                            || xField.getText().isEmpty()
                            || yField.getText().isEmpty()
                            || wField.getText().isEmpty()
                            || hField.getText().isEmpty()
                            || fileLocationField.getText().isEmpty()) {
                        System.out.println("ONE OR MORE FIELDS ARE EMPTY");
                        return false;
                    }
                    String name = nameField.getText();
                    Double x_loc = null, y_loc = null, w_dim = null, h_dim = null;
                    String fileLoc;
                    try {
                        x_loc = Double.parseDouble(xField.getText());
                    } catch (Exception e) {
                        System.out.println("x failed");
                        retVal = false;
                    }
                    try {
                        y_loc = Double.parseDouble(yField.getText());
                    } catch (Exception e) {
                        System.out.println("y failed");
                        retVal = false;
                    }
                    try {
                        w_dim = Double.parseDouble(wField.getText());
                    } catch (Exception e) {
                        System.out.println("w failed");
                        retVal = false;
                    }
                    try {
                        h_dim = Double.parseDouble(hField.getText());
                    } catch (Exception e) {
                        System.out.println("h failed");
                        retVal = false;
                    }

                    try {
                        fileLoc = fileLocationField.getText();
                        if (new File(fileLoc).exists() == false) {
                            throw new Exception();
                        }
                    } catch (Exception e) {
                        System.out.println("fileLoc failed");
                        retVal = false;
                    }
                    if (retVal) {
//                        System.out.printf("x:%s\ty:%s\tw:%s\th:%s\n", x_loc, y_loc, w_dim, h_dim);
                    }

                    return retVal;
                }

                public void crossFields() {

                }

                private void updatePlayerData() {
                    PlayerData tempData;
                    String name = nameField.getText();
                    Double x_loc = null, y_loc = null, w_dim = null, h_dim = null;
                    String fileLoc;
                    try {
                        x_loc = Double.parseDouble(xField.getText());
                    } catch (Exception e) {
                        return;
                    }
                    try {
                        y_loc = Double.parseDouble(yField.getText());
                    } catch (Exception e) {
                        return;
                    }
                    try {
                        w_dim = Double.parseDouble(wField.getText());
                    } catch (Exception e) {
                        return;
                    }
                    try {
                        h_dim = Double.parseDouble(hField.getText());
                    } catch (Exception e) {
                        return;
                    }

                    try {
                        fileLoc = fileLocationField.getText();
                        if (new File(fileLoc).exists() == false) {
                            throw new Exception();
                        }
                    } catch (Exception e) {
                        return;
                    }
                    float[] coordinates = new float[]{x_loc.floatValue(), y_loc.floatValue()};
                    float[] dimensions = new float[]{w_dim.floatValue(), h_dim.floatValue()};
                    Player tempPlayer = Player.generateLegacy(name, coordinates, dimensions, Hitbox.DEFAULT, fileLoc);
                    tempData = new PlayerData(this.player_data.doc, tempPlayer);
                    this.player_data = tempData;
                    LevelData toPass = new LevelData();
                    toPass.player_data = this.player_data;
                    ModifySetEventData eventData = new ModifySetEventData(toPass);
                    fireEditorEvent(new ModifySetEvent(this, eventData));
                }

                public void update(PlayerData data) {
                    this.player_data = data;
                    System.out.println("player_data:" + player_data);
                }

            }

            public static class EntityWindow extends Application {

                EntityCollection entity_data;
                ModifyObject mo;
                boolean autoFill;
                Stage window;
                ComboBox nameBox;
                TextField xField, yField, wField, hField;
                TextField fileLocationField;
                Button confirmButton;
                Button deleteButton;
                GridPane entityPane;
                static EntityWindow e_instance;
                EntityData selected;

                public EntityWindow(ModifyObject mo, boolean autoFill) {
                    this.mo = mo;
                    this.autoFill = autoFill;
                }

                @Override
                public void start(Stage primaryStage) throws Exception {

                    GridPane grid = new GridPane();
                    grid.setPadding(new Insets(10, 10, 10, 10));
                    grid.setVgap(5);
                    grid.setHgap(5);
                    this.entityPane = grid;

                    initNameBox(null);

                    xField = new TextField();
                    xField.setPromptText("x");
                    GridPane.setConstraints(xField, 0, 1);
                    grid.getChildren().add(xField);

                    yField = new TextField();
                    yField.setPromptText("y");
                    GridPane.setConstraints(yField, 1, 1);
                    grid.getChildren().add(yField);

                    wField = new TextField();
                    wField.setPromptText("width");
                    GridPane.setConstraints(wField, 0, 2);
                    grid.getChildren().add(wField);

                    hField = new TextField();
                    hField.setPromptText("height");
                    GridPane.setConstraints(hField, 1, 2);
                    grid.getChildren().add(hField);

                    fileLocationField = new TextField();
                    fileLocationField.setPromptText("File Location");
                    GridPane.setConstraints(fileLocationField, 0, 3);
                    GridPane.setColumnSpan(fileLocationField, 3);
                    grid.getChildren().add(fileLocationField);

                    confirmButton = new Button("Confirm");

                    confirmButton.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            if (verifyInputs() == false) {
                                crossFields();
                                return;
                            }
                            updateEntityObject();
                            LevelData tempLevelData = new LevelData();
                            tempLevelData.entity_data = entity_data;
                            ModifySetEventData eventData = new ModifySetEventData(tempLevelData);
                            ModifySetEvent setEvent = new ModifySetEvent(this, eventData);
                            fireEditorEvent(setEvent);
                            window.close();
                            ModifyObject.resetComboBox();
                        }
                    });

                    GridPane.setConstraints(confirmButton, 0, 4);
                    GridPane.setColumnSpan(confirmButton, 2);
                    grid.getChildren().add(confirmButton);

                    if (autoFill == false) {
                        deleteButton = new Button("Delete");

                        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                deleteHandle();
                            }
                        });

                        GridPane.setConstraints(deleteButton, 1, 4);
                        GridPane.setColumnSpan(deleteButton, 2);
                        grid.getChildren().add(deleteButton);
                    }
                    Scene secondScene = new Scene(grid, 230, 1.5 * SPACE);

                    // New window (Stage)
                    window = new Stage();
                    window.setTitle("ENTITY");
                    window.setScene(secondScene);
                    window.setAlwaysOnTop(true);
                    window.setX(primaryStage.getX() + primaryStage.getWidth());
                    window.setY(primaryStage.getY());

                    window.setOnCloseRequest(new EventHandler<WindowEvent>() {
                        @Override
                        public void handle(WindowEvent event) {
                            ModifyObject.resetComboBox();
                            ModifyObject.pw = null;
                        }
                    });

                    window.setResizable(false);
                    window.show();
                    e_instance = this;
                    ModifyGetEventData tempData = null;
                    tempData = new ModifyGetEventData(this.mo);
                    fireEditorEvent(new ModifyGetEvent(this, tempData));

                }

                public void update(EntityCollection entity_collection) {
                    this.entity_data = entity_collection;
                    System.out.println("entity_data:" + entity_collection);
                    List<String> nameOptions = new ArrayList();
                    for (EntityData e : entity_collection.elements) {
                        nameOptions.add(e.name);
                    }

                    ObservableList<String> options = FXCollections.observableList(nameOptions);
                    e_instance.initNameBox(options);

                }

                private void deleteHandle() {
                    String name = nameBox.getEditor().getText();
                    if (name == null || name.length() == 0) {
                        return;
                    }
                    ConfirmWindow cw = new ConfirmWindow() {
                        @Override
                        public String promptText() {
                            return "Really delete entity: " + name + "?";
                        }

                        @Override
                        public void confirm() {
                            LevelData tempLevelData = new LevelData();
                            tempLevelData.entity_data = new EntityCollection(entity_data.doc, Arrays.asList(new EntityData(entity_data.doc, Entity.generateBlank(name))));
                            ModifyRemoveEventData eventData = new ModifyRemoveEventData(tempLevelData);
                            ModifyRemoveEvent setEvent = new ModifyRemoveEvent(this, eventData);
                            fireEditorEvent(setEvent);
                        }
                    };
                    try {
                        cw.start(primaryStage);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

                public void initNameBox(ObservableList<String> options) {
                    if (nameBox != null) {
                        entityPane.getChildren().remove(nameBox);
                    }
                    if (options != null) {
                        nameBox = new ComboBox(options);
                        nameBox.setOnAction(new EventHandler() {
                            @Override
                            public void handle(Event event) {
                                String selectedName = (nameBox.getEditor().getText());
                                for (EntityData eel : entity_data.elements) {
                                    if (eel.name.equals(selectedName)) {
                                        selected = eel;
                                        break;
                                    }
                                }
                                if (selected != null) {
                                    xField.setText("" + selected.physicsObjectData.location_meters[0]);
                                    yField.setText("" + selected.physicsObjectData.location_meters[1]);
                                    wField.setText("" + selected.physicsObjectData.dimensions_meters[0]);
                                    hField.setText("" + selected.physicsObjectData.dimensions_meters[1]);
                                    fileLocationField.setText(selected.renderObjectData.file_location);
                                    selected.replacement = selected.name;
                                    System.out.println("REPLACE PREVIOUS:" + selected.replacement);
                                }
                                System.out.println(selected);
                            }
                        });
                    } else {
                        nameBox = new ComboBox();
                    }
                    nameBox.setPromptText("Name");
                    nameBox.setEditable(true);
                    GridPane.setConstraints(nameBox, 0, 0);
                    GridPane.setColumnSpan(nameBox, 2);
                    entityPane.getChildren().add(nameBox);
                }

                public boolean verifyInputs() {
                    boolean retVal = true;
                    // make sure the name field is non empty
                    if (nameBox.getEditor().getText() == null || nameBox.getEditor().getText().isEmpty()
                            || xField.getText().isEmpty()
                            || yField.getText().isEmpty()
                            || wField.getText().isEmpty()
                            || hField.getText().isEmpty()
                            || fileLocationField.getText().isEmpty()) {
                        System.out.println("ONE OR MORE FIELDS ARE EMPTY");
                        return false;
                    }
                    String name = nameBox.getEditor().getText();
                    Double x_loc = null, y_loc = null, w_dim = null, h_dim = null;
                    String fileLoc;
                    try {
                        x_loc = Double.parseDouble(xField.getText());
                    } catch (Exception e) {
                        System.out.println("x failed");
                        retVal = false;
                    }
                    try {
                        y_loc = Double.parseDouble(yField.getText());
                    } catch (Exception e) {
                        System.out.println("y failed");
                        retVal = false;
                    }
                    try {
                        w_dim = Double.parseDouble(wField.getText());
                    } catch (Exception e) {
                        System.out.println("w failed");
                        retVal = false;
                    }
                    try {
                        h_dim = Double.parseDouble(hField.getText());
                    } catch (Exception e) {
                        System.out.println("h failed");
                        retVal = false;
                    }

                    try {
                        fileLoc = fileLocationField.getText();
                        if (new File(fileLoc).exists() == false) {
                            throw new Exception();
                        }
                    } catch (Exception e) {
                        System.out.println("fileLoc failed");
                        retVal = false;
                    }
                    if (retVal) {
//                        System.out.printf("x:%s\ty:%s\tw:%s\th:%s\n", x_loc, y_loc, w_dim, h_dim);
                    }

                    return retVal;
                }

                public void updateEntityObject() {
                    String name = nameBox.getEditor().getText();
                    Double x_loc = null, y_loc = null, w_dim = null, h_dim = null;
                    String fileLoc;
                    try {
                        x_loc = Double.parseDouble(xField.getText());
                    } catch (Exception e) {
                        System.out.println("x failed");
                        return;
                    }
                    try {
                        y_loc = Double.parseDouble(yField.getText());
                    } catch (Exception e) {
                        System.out.println("y failed");
                        return;
                    }
                    try {
                        w_dim = Double.parseDouble(wField.getText());
                    } catch (Exception e) {
                        System.out.println("w failed");
                        return;
                    }
                    try {
                        h_dim = Double.parseDouble(hField.getText());
                    } catch (Exception e) {
                        System.out.println("h failed");
                        return;
                    }

                    try {
                        fileLoc = fileLocationField.getText();
                        if (new File(fileLoc).exists() == false) {
                            throw new Exception();
                        }
                    } catch (Exception e) {
                        System.out.println("fileLoc failed");
                        return;
                    }
                    float[] coordinates = new float[]{x_loc.floatValue(), y_loc.floatValue()};
                    float[] dimensions = new float[]{w_dim.floatValue(), h_dim.floatValue()};
                    if (selected != null) {
                        selected.name = name;
                        selected.physicsObjectData.location_meters = coordinates;
                        selected.physicsObjectData.dimensions_meters = dimensions;
                        selected.renderObjectData.file_location = fileLoc;
                    }
                    EntityData toAdd = (selected != null ? selected : new EntityData(entity_data.doc, Entity.generateLegacy(name, coordinates, dimensions, fileLoc)));
                    EntityCollection tempData = new EntityCollection(entity_data.doc, Arrays.asList(toAdd));
                    this.entity_data = tempData;
                    System.out.println("toAdd:" + toAdd);
                }

                public void crossFields() {

                }

            }

            public static class StructureWindow extends Application {

                Stage window;
                ModifyObject mo;
                boolean autoFill;
                StructureCollection structure_data;
                ComboBox nameBox;
                TextField xField, yField, wField, hField;
                TextField fileLocationField;
                Button confirmButton;
                Button deleteButton;
                GridPane grid;
                StructureData selected;
                ConfirmWindow cw;

                public StructureWindow(ModifyObject mo, boolean autoFill) {
                    this.mo = mo;
                    this.autoFill = autoFill;
                }

                @Override
                public void start(Stage primaryStage) throws Exception {

                    grid = new GridPane();
                    grid.setPadding(new Insets(10, 10, 10, 10));
                    grid.setVgap(5);
                    grid.setHgap(5);

                    initNameBox(null);

                    xField = new TextField();
                    xField.setPromptText("x");
                    GridPane.setConstraints(xField, 0, 1);
                    grid.getChildren().add(xField);

                    yField = new TextField();
                    yField.setPromptText("y");
                    GridPane.setConstraints(yField, 1, 1);
                    grid.getChildren().add(yField);

                    wField = new TextField();
                    wField.setPromptText("width");
                    GridPane.setConstraints(wField, 0, 2);
                    grid.getChildren().add(wField);

                    hField = new TextField();
                    hField.setPromptText("height");
                    GridPane.setConstraints(hField, 1, 2);
                    grid.getChildren().add(hField);

                    fileLocationField = new TextField();
                    fileLocationField.setPromptText("File Location");
                    GridPane.setConstraints(fileLocationField, 0, 3);
                    GridPane.setColumnSpan(fileLocationField, 3);
                    grid.getChildren().add(fileLocationField);

                    confirmButton = new Button("Confirm");

                    confirmButton.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            if (verifyInputs() == false) {
                                crossFields();
                                return;
                            }
                            if (autoFill && selected != null) {
                                System.out.println("Confirm Diolog. Selected != null");
                                if (cw != null) {
                                    cw.window.close();
                                }
                                cw = new ConfirmWindow() {
                                    @Override
                                    public String promptText() {
                                        return selected.getClass().getSimpleName() + ": " + selected.name + " already exists. Really overwrite?";
                                    }

                                    @Override
                                    public void confirm() {
                                        updateEvents();
                                    }
                                };
                                try {
                                    cw.start(window);
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            } else {
                                System.out.println("modify window");
                                updateEvents();
                            }
                        }
                    });

                    GridPane.setConstraints(confirmButton, 0, 4);
                    GridPane.setColumnSpan(confirmButton, 2);
                    grid.getChildren().add(confirmButton);

                    if (autoFill == false) {
                        deleteButton = new Button("Delete");

                        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                deleteHandle();
                            }
                        });

                        GridPane.setConstraints(deleteButton, 1, 4);
                        GridPane.setColumnSpan(deleteButton, 2);
                        grid.getChildren().add(deleteButton);
                    }
                    Scene secondScene = new Scene(grid, 230, 1.5 * SPACE);

                    // New window (Stage)
                    window = new Stage();
                    window.setTitle("STRUCTURE");
                    window.setScene(secondScene);
                    window.setAlwaysOnTop(true);
                    window.setX(primaryStage.getX() + primaryStage.getWidth());
                    window.setY(primaryStage.getY());

                    window.setOnCloseRequest(new EventHandler<WindowEvent>() {
                        @Override
                        public void handle(WindowEvent event) {
                            ModifyObject.resetComboBox();
                            ModifyObject.pw = null;
                        }
                    });

                    window.setResizable(false);
                    window.show();
                    ModifyGetEventData tempData = null;
                    tempData = new ModifyGetEventData(this.mo);
                    fireEditorEvent(new ModifyGetEvent(this, tempData));

                }

                private void initNameBox(ObservableList<String> options) {
                    if (nameBox != null) {
                        grid.getChildren().remove(nameBox);
                    }
                    if (options != null) {
                        nameBox = new ComboBox(options);
                        nameBox.setOnAction(new EventHandler() {
                            @Override
                            public void handle(Event event) {
                                String selectedName = (nameBox.getEditor().getText());
                                for (StructureData se : structure_data.elements) {
                                    if (se.name.equals(selectedName)) {
                                        selected = se;
                                        break;
                                    }
                                }
                                if (selected != null) {
                                    xField.setText("" + selected.physicsObjectData.location_meters[0]);
                                    yField.setText("" + selected.physicsObjectData.location_meters[1]);
                                    wField.setText("" + selected.physicsObjectData.dimensions_meters[0]);
                                    hField.setText("" + selected.physicsObjectData.dimensions_meters[1]);
                                    fileLocationField.setText(selected.renderObjectData.file_location);
                                    selected.replacement = selected.name;
                                }
                                System.out.println(selected);
                            }
                        });
                    } else {
                        nameBox = new ComboBox();
                    }
                    nameBox.setPromptText("Name");
                    nameBox.setEditable(true);
                    GridPane.setConstraints(nameBox, 0, 0);
                    GridPane.setColumnSpan(nameBox, 2);
                    grid.getChildren().add(nameBox);
                }

                public boolean verifyInputs() {
                    boolean retVal = true;
                    // make sure the name field is non empty
                    if (nameBox.getEditor().getText() == null || nameBox.getEditor().getText().isEmpty()
                            || xField.getText().isEmpty()
                            || yField.getText().isEmpty()
                            || wField.getText().isEmpty()
                            || hField.getText().isEmpty()
                            || fileLocationField.getText().isEmpty()) {
                        System.out.println("ONE OR MORE FIELDS ARE EMPTY");
                        return false;
                    }
                    String name = nameBox.getEditor().getText();
                    Double x_loc = null, y_loc = null, w_dim = null, h_dim = null;
                    String fileLoc;
                    try {
                        x_loc = Double.parseDouble(xField.getText());
                    } catch (Exception e) {
                        System.out.println("x failed");
                        retVal = false;
                    }
                    try {
                        y_loc = Double.parseDouble(yField.getText());
                    } catch (Exception e) {
                        System.out.println("y failed");
                        retVal = false;
                    }
                    try {
                        w_dim = Double.parseDouble(wField.getText());
                    } catch (Exception e) {
                        System.out.println("w failed");
                        retVal = false;
                    }
                    try {
                        h_dim = Double.parseDouble(hField.getText());
                    } catch (Exception e) {
                        System.out.println("h failed");
                        retVal = false;
                    }

                    try {
                        fileLoc = fileLocationField.getText();
                        if (new File(fileLoc).exists() == false) {
                            throw new Exception();
                        }
                    } catch (Exception e) {
                        System.out.println("fileLoc failed");
                        retVal = false;
                    }
                    if (retVal) {
//                        System.out.printf("x:%s\ty:%s\tw:%s\th:%s\n", x_loc, y_loc, w_dim, h_dim);
                    }

                    return retVal;
                }

                private void deleteHandle() {
                    String name = nameBox.getEditor().getText();
                    if (name == null || name.length() == 0) {
                        return;
                    }
                    ConfirmWindow cw = new ConfirmWindow() {
                        @Override
                        public String promptText() {
                            return "Really delete structure: " + name + "?";
                        }

                        @Override
                        public void confirm() {
                            LevelData tempLevelData = new LevelData();
                            Structure temp = Structure.generateLegacy(name, new float[]{0, 0}, new float[]{0, 0}, "TEMP");
                            tempLevelData.structure_collection = new StructureCollection(
                                    structure_data.doc,
                                    Arrays.asList(new StructureData(
                                                    structure_data.doc,
                                                    temp)));
                            ModifyRemoveEventData eventData = new ModifyRemoveEventData(tempLevelData);
                            ModifyRemoveEvent setEvent = new ModifyRemoveEvent(this, eventData);
                            fireEditorEvent(setEvent);
                        }
                    };
                    try {
                        cw.start(primaryStage);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

                public void crossFields() {

                }

                public void update(StructureCollection structure_collection) {
                    this.structure_data = structure_collection;
                    System.out.println("structure_collection:" + structure_collection);
                    List<String> nameOptions = new ArrayList();
                    for (StructureData e : structure_collection.elements) {
                        nameOptions.add(e.name);
                    }

                    ObservableList<String> options = FXCollections.observableList(nameOptions);
                    this.initNameBox(options);

                }

                public void updateStructureObject() {
                    String name = nameBox.getEditor().getText();
                    Double x_loc = null, y_loc = null, w_dim = null, h_dim = null;
                    String fileLoc;
                    try {
                        x_loc = Double.parseDouble(xField.getText());
                    } catch (Exception e) {
                        System.out.println("x failed");
                        return;
                    }
                    try {
                        y_loc = Double.parseDouble(yField.getText());
                    } catch (Exception e) {
                        System.out.println("y failed");
                        return;
                    }
                    try {
                        w_dim = Double.parseDouble(wField.getText());
                    } catch (Exception e) {
                        System.out.println("w failed");
                        return;
                    }
                    try {
                        h_dim = Double.parseDouble(hField.getText());
                    } catch (Exception e) {
                        System.out.println("h failed");
                        return;
                    }

                    try {
                        fileLoc = fileLocationField.getText();
                        if (new File(fileLoc).exists() == false) {
                            throw new Exception();
                        }
                    } catch (Exception e) {
                        System.out.println("fileLoc failed");
                        return;
                    }
                    float[] coordinates = new float[]{x_loc.floatValue(), y_loc.floatValue()};
                    float[] dimensions = new float[]{w_dim.floatValue(), h_dim.floatValue()};
                    if (selected != null) {
                        selected.name = name;
                        selected.physicsObjectData.location_meters = coordinates;
                        selected.physicsObjectData.dimensions_meters = dimensions;
                        selected.renderObjectData.file_location = fileLoc;
                    }
                    StructureData toAdd = (selected != null ? selected : new StructureData(structure_data.doc, Structure.generateLegacy(name, coordinates, dimensions, fileLoc)));
                    StructureCollection tempData = new StructureCollection(structure_data.doc, Arrays.asList(toAdd));
                    this.structure_data = tempData;
                    System.out.println("toAdd:" + toAdd);
                }

                public void updateEvents() {
                    updateStructureObject();
                    LevelData tempLevelData = new LevelData();
                    tempLevelData.structure_collection = structure_data;
                    ModifySetEventData eventData = new ModifySetEventData(tempLevelData);
                    ModifySetEvent setEvent = new ModifySetEvent(this, eventData);
                    fireEditorEvent(setEvent);
                    window.close();
                    ModifyObject.resetComboBox();
                }

            }

            public static class BackgroundWindow extends Application {

                Stage window;
                ModifyObject mo;
                boolean autoFill;
                BackgroundCollection background_collection;
                ComboBox nameBox;
                TextField xField, yField, wField, hField;
                TextField fileLocationField;
                Button confirmButton;
                Button deleteButton;
                GridPane grid;
                BackgroundData selected;
                ConfirmWindow cw;

                public BackgroundWindow(ModifyObject mo, boolean autoFill) {
                    this.mo = mo;
                    this.autoFill = autoFill;
                }

                @Override
                public void start(Stage primaryStage) throws Exception {

                    grid = new GridPane();
                    grid.setPadding(new Insets(10, 10, 10, 10));
                    grid.setVgap(5);
                    grid.setHgap(5);

                    initNameBox(null);

                    xField = new TextField();
                    xField.setPromptText("x");
                    GridPane.setConstraints(xField, 0, 1);
                    grid.getChildren().add(xField);

                    yField = new TextField();
                    yField.setPromptText("y");
                    GridPane.setConstraints(yField, 1, 1);
                    grid.getChildren().add(yField);

                    wField = new TextField();
                    wField.setPromptText("width");
                    GridPane.setConstraints(wField, 0, 2);
                    grid.getChildren().add(wField);

                    hField = new TextField();
                    hField.setPromptText("height");
                    GridPane.setConstraints(hField, 1, 2);
                    grid.getChildren().add(hField);

                    fileLocationField = new TextField();
                    fileLocationField.setPromptText("File Location");
                    GridPane.setConstraints(fileLocationField, 0, 3);
                    GridPane.setColumnSpan(fileLocationField, 3);
                    grid.getChildren().add(fileLocationField);

                    confirmButton = new Button("Confirm");

                    confirmButton.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            if (verifyInputs() == false) {
                                crossFields();
                                return;
                            }
                            if (autoFill && selected != null) {
                                System.out.println("Confirm Diolog. Selected != null");
                                if (cw != null) {
                                    cw.window.close();
                                }
                                cw = new ConfirmWindow() {
                                    @Override
                                    public String promptText() {
                                        return selected.getClass().getSimpleName() + ": " + selected.name + " already exists. Really overwrite?";
                                    }

                                    @Override
                                    public void confirm() {
                                        updateEvents();
                                    }
                                };
                                try {
                                    cw.start(window);
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            } else {
                                System.out.println("modify window");
                                updateEvents();
                            }
                        }
                    });

                    GridPane.setConstraints(confirmButton, 0, 4);
                    GridPane.setColumnSpan(confirmButton, 2);
                    grid.getChildren().add(confirmButton);

                    if (autoFill == false) {
                        deleteButton = new Button("Delete");

                        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                deleteHandle();
                            }
                        });

                        GridPane.setConstraints(deleteButton, 1, 4);
                        GridPane.setColumnSpan(deleteButton, 2);
                        grid.getChildren().add(deleteButton);
                    }
                    Scene secondScene = new Scene(grid, 230, 1.5 * SPACE);

                    // New window (Stage)
                    window = new Stage();
                    window.setTitle("BACKGROUND");
                    window.setScene(secondScene);
                    window.setAlwaysOnTop(true);
                    window.setX(primaryStage.getX() + primaryStage.getWidth());
                    window.setY(primaryStage.getY());

                    window.setOnCloseRequest(new EventHandler<WindowEvent>() {
                        @Override
                        public void handle(WindowEvent event) {
                            ModifyObject.resetComboBox();
                            ModifyObject.pw = null;
                        }
                    });

                    window.setResizable(false);
                    window.show();
                    ModifyGetEventData tempData = null;
                    tempData = new ModifyGetEventData(this.mo);
                    fireEditorEvent(new ModifyGetEvent(this, tempData));

                }

                private void initNameBox(ObservableList<String> options) {
                    if (nameBox != null) {
                        grid.getChildren().remove(nameBox);
                    }
                    if (options != null) {
                        nameBox = new ComboBox(options);
                        nameBox.setOnAction(new EventHandler() {
                            @Override
                            public void handle(Event event) {
                                String selectedName = (nameBox.getEditor().getText());
                                for (BackgroundData be : background_collection.elements) {
                                    if (be.name.equals(selectedName)) {
                                        selected = be;
                                        break;
                                    }
                                }
                                if (selected != null) {
                                    xField.setText("" + selected.physicsObjectData.location_meters[0]);
                                    yField.setText("" + selected.physicsObjectData.location_meters[1]);
                                    wField.setText("" + selected.physicsObjectData.dimensions_meters[0]);
                                    hField.setText("" + selected.physicsObjectData.dimensions_meters[1]);
                                    fileLocationField.setText(selected.renderObjectData.file_location);
                                    selected.replacement = selected.name;
                                }
                                System.out.println(selected);
                            }
                        });
                    } else {
                        nameBox = new ComboBox();
                    }
                    nameBox.setPromptText("Name");
                    nameBox.setEditable(true);
                    GridPane.setConstraints(nameBox, 0, 0);
                    GridPane.setColumnSpan(nameBox, 2);
                    grid.getChildren().add(nameBox);
                }

                public boolean verifyInputs() {
                    boolean retVal = true;
                    // make sure the name field is non empty
                    if (nameBox.getEditor().getText() == null || nameBox.getEditor().getText().isEmpty()
                            || xField.getText().isEmpty()
                            || yField.getText().isEmpty()
                            || wField.getText().isEmpty()
                            || hField.getText().isEmpty()
                            || fileLocationField.getText().isEmpty()) {
                        System.out.println("ONE OR MORE FIELDS ARE EMPTY");
                        return false;
                    }
                    String name = nameBox.getEditor().getText();
                    Double x_loc = null, y_loc = null, w_dim = null, h_dim = null;
                    String fileLoc;
                    try {
                        x_loc = Double.parseDouble(xField.getText());
                    } catch (Exception e) {
                        System.out.println("x failed");
                        retVal = false;
                    }
                    try {
                        y_loc = Double.parseDouble(yField.getText());
                    } catch (Exception e) {
                        System.out.println("y failed");
                        retVal = false;
                    }
                    try {
                        w_dim = Double.parseDouble(wField.getText());
                    } catch (Exception e) {
                        System.out.println("w failed");
                        retVal = false;
                    }
                    try {
                        h_dim = Double.parseDouble(hField.getText());
                    } catch (Exception e) {
                        System.out.println("h failed");
                        retVal = false;
                    }

                    try {
                        fileLoc = fileLocationField.getText();
                        if (new File(fileLoc).exists() == false) {
                            throw new Exception();
                        }
                    } catch (Exception e) {
                        System.out.println("fileLoc failed");
                        retVal = false;
                    }
                    if (retVal) {
//                        System.out.printf("x:%s\ty:%s\tw:%s\th:%s\n", x_loc, y_loc, w_dim, h_dim);
                    }

                    return retVal;
                }

                public void crossFields() {

                }

                public void updateBackgroundObject() {
                    String name = nameBox.getEditor().getText();
                    Double x_loc = null, y_loc = null, w_dim = null, h_dim = null;
                    String fileLoc;
                    try {
                        x_loc = Double.parseDouble(xField.getText());
                    } catch (Exception e) {
                        System.out.println("x failed");
                        return;
                    }
                    try {
                        y_loc = Double.parseDouble(yField.getText());
                    } catch (Exception e) {
                        System.out.println("y failed");
                        return;
                    }
                    try {
                        w_dim = Double.parseDouble(wField.getText());
                    } catch (Exception e) {
                        System.out.println("w failed");
                        return;
                    }
                    try {
                        h_dim = Double.parseDouble(hField.getText());
                    } catch (Exception e) {
                        System.out.println("h failed");
                        return;
                    }

                    try {
                        fileLoc = fileLocationField.getText();
                        if (new File(fileLoc).exists() == false) {
                            throw new Exception();
                        }
                    } catch (Exception e) {
                        System.out.println("fileLoc failed");
                        return;
                    }
                    float[] coordinates = new float[]{x_loc.floatValue(), y_loc.floatValue()};
                    float[] dimensions = new float[]{w_dim.floatValue(), h_dim.floatValue()};
                    if (selected != null) {
                        selected.name = name;
                        selected.physicsObjectData.location_meters = coordinates;
                        selected.physicsObjectData.dimensions_meters = dimensions;
                        selected.renderObjectData.file_location = fileLoc;
                    }
                    BackgroundData toAdd = (selected != null ? selected : new BackgroundData(background_collection.doc, Background.generateLegacy(name, coordinates, dimensions, fileLoc)));
                    BackgroundCollection tempData = new BackgroundCollection(background_collection.doc, Arrays.asList(toAdd));
                    this.background_collection = tempData;
                    System.out.println("toAdd:" + toAdd);
                }

                private void deleteHandle() {
                    String name = nameBox.getEditor().getText();
                    if (name == null || name.length() == 0) {
                        return;
                    }
                    ConfirmWindow cw = new ConfirmWindow() {
                        @Override
                        public String promptText() {
                            return "Really delete background: " + name + "?";
                        }

                        @Override
                        public void confirm() {
                            LevelData tempLevelData = new LevelData();
                            tempLevelData.background_collection = new BackgroundCollection(background_collection.doc, Arrays.asList(new BackgroundData(background_collection.doc, Background.generateBlank(name))));
                            ModifyRemoveEventData eventData = new ModifyRemoveEventData(tempLevelData);
                            ModifyRemoveEvent setEvent = new ModifyRemoveEvent(this, eventData);
                            fireEditorEvent(setEvent);
                        }
                    };
                    try {
                        cw.start(primaryStage);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

                public void updateEvents() {
                    updateBackgroundObject();
                    LevelData tempLevelData = new LevelData();
                    tempLevelData.background_collection = background_collection;
                    ModifySetEventData eventData = new ModifySetEventData(tempLevelData);
                    ModifySetEvent setEvent = new ModifySetEvent(this, eventData);
                    fireEditorEvent(setEvent);
                    window.close();
                    ModifyObject.resetComboBox();
                }

                public void update(BackgroundCollection background_collection) {
                    this.background_collection = background_collection;
                    System.out.println("background_data:" + background_collection);
                    List<String> nameOptions = new ArrayList();
                    for (BackgroundData e : background_collection.elements) {
                        nameOptions.add(e.name);
                    }
                    ObservableList<String> options = FXCollections.observableList(nameOptions);
                    this.initNameBox(options);
                }
            }

            public static abstract class ConfirmWindow extends Application {

                Stage window;
                Label infoLabel;
                TextField fileLocationField;
                Label promptText;
                Button yesButton;
                Button noButton;

                public ConfirmWindow() {
                }

                public abstract String promptText();

                public abstract void confirm();

                @Override
                public void start(Stage primaryStage) throws Exception {

                    GridPane grid = new GridPane();
                    grid.setPadding(new Insets(10, 10, 10, 10));
                    grid.setVgap(5);
                    grid.setHgap(5);
                    //Defining the Name text field
                    infoLabel = new Label(this.promptText());
                    GridPane.setConstraints(infoLabel, 1, 0);
                    grid.getChildren().add(infoLabel);
                    //Defining the Last Name text field
                    yesButton = new Button("Yes");
                    GridPane.setConstraints(yesButton, 1, 1);
                    //Defining the Comment text field
                    //Defining the Submit button
                    yesButton.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            confirm();
                            window.close();
                        }
                    });
                    grid.getChildren().add(yesButton);
                    // Load button
                    noButton = new Button("No");
                    GridPane.setConstraints(noButton, 1, 2);
                    noButton.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            window.close();
                        }
                    });
                    grid.getChildren().add(noButton);

                    Scene secondScene = new Scene(grid, 400, SPACE);

                    // New window (Stage)
                    window = new Stage();
                    window.setTitle("Confirm Dialog");
                    window.setScene(secondScene);
                    window.setAlwaysOnTop(true);

                    // Set position of second window, related to primary window.
                    window.setOnCloseRequest(new EventHandler<WindowEvent>() {
                        @Override
                        public void handle(WindowEvent event) {
                            window.close();
                        }
                    });

                    window.setResizable(false);
                    window.show();
                }

            }

        }
    }

}
