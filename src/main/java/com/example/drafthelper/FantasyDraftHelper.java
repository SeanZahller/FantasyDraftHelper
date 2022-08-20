package com.example.drafthelper;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.Event;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;
import javafx.event.EventHandler;
import javafx.util.Duration;

public class FantasyDraftHelper extends Application {

    private TableView table;
    private ObservableList data;
    private Text actionStatus;
    private String[] contestants = {"Fernie", "Brandon", "Austin", "AJ", "Jordan","Josh","Xavier","Anthony","Masaih","Easton","Bryce","Sean"};
    private Label teamPicking;
    private Label nextTeamPicking;
    private Label currentPick;
    private Label nextPick;
    private Label timer;
    private Separator sep;
    private int countUp = 0;
    private int countDown = contestants.length - 1;
    private static final Integer STARTTIME = 60;
    private Timeline timeline;
    private Label timerLabel = new Label();
    private IntegerProperty timeSeconds =
            new SimpleIntegerProperty(STARTTIME);
    private int pressed = 0;

    public static void main(String [] args) {

        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        primaryStage.setTitle("Basement Boyz Fantasy Draft 2022");

        // Table view, data, columns and properties
        table = new TableView();
        data = getTopPlayers();
        table.setItems(data);
        table.setEditable(true);

        TableColumn rankCol = new TableColumn("Rank");
        rankCol.setCellValueFactory(new PropertyValueFactory("rank"));
        rankCol.setCellFactory(TextFieldTableCell.forTableColumn());
        rankCol.setOnEditCommit(new EventHandler<CellEditEvent>() {
            @Override
            public void handle(CellEditEvent t) {
                ((Player) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                ).setRank((String) t.getNewValue());
            }
        });
        rankCol.setPrefWidth(120.0);
        rankCol.setStyle( "-fx-alignment: CENTER;");



        TableColumn nameCol = new TableColumn("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory("name"));
        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        nameCol.setOnEditCommit(new EventHandler<CellEditEvent>() {
            @Override
            public void handle(CellEditEvent t) {
                ((Player) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                ).setName((String) t.getNewValue());
            }
        });
        nameCol.setPrefWidth(330.0);
        nameCol.setStyle( "-fx-alignment: CENTER;");


        TableColumn posCol = new TableColumn("Position");
        posCol.setCellValueFactory(new PropertyValueFactory("position"));
        posCol.setCellFactory(TextFieldTableCell.forTableColumn());
        posCol.setOnEditCommit(new EventHandler<CellEditEvent>() {
            @Override
            public void handle(CellEditEvent t) {
                ((Player) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                ).setPosition((String) t.getNewValue());
            }
        });
        posCol.setPrefWidth(150.0);
        posCol.setStyle( "-fx-alignment: CENTER;");

        TableColumn teamCol = new TableColumn("Team");
        teamCol.setCellValueFactory(new PropertyValueFactory("team"));
        teamCol.setCellFactory(TextFieldTableCell.forTableColumn());
        teamCol.setOnEditCommit(new EventHandler<CellEditEvent>() {
            @Override
            public void handle(CellEditEvent t) {
                ((Player) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                ).setTeam((String) t.getNewValue());
            }
        });
        teamCol.setPrefWidth(150.0);
        teamCol.setStyle( "-fx-alignment: CENTER;");


        table.getColumns().setAll(rankCol, nameCol, posCol, teamCol);
        table.setLayoutX(164.0);
        table.setLayoutY(-1.0);
        table.setPrefHeight(690);//542.0
        table.setPrefWidth(750);//609.0
        table.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);


        TitledPane background = new TitledPane();
        background.setPrefHeight(595.0);
        background.setPrefWidth(777.0);



        Button startTimer = new Button("Start Timer");
        startTimer.setLayoutX(45.0);
        startTimer.setLayoutY(450.0);
        startTimer.setScaleX(1.5);
        startTimer.setScaleY(1.5);

        Button draftPlayer = new Button("Draft Player");
        draftPlayer.setLayoutX(45.0);
        draftPlayer.setLayoutY(550.0);
        draftPlayer.setScaleX(2.0);
        draftPlayer.setScaleY(2.0);

        draftPlayer.setOnAction(actionEvent -> {
            if(countUp < contestants.length - 1)
            {
                teamPicking.setText(contestants[countUp]);
                nextTeamPicking.setText(contestants[countUp + 1]);
                countUp = countUp + 1;
            }

            else if(countUp == contestants.length - 1) // gets to last player
            {
                if(countDown == contestants.length - 1){
                    nextTeamPicking.setText(contestants[countDown]);
                    teamPicking.setText(contestants[countDown]);
                }
                else if(countDown > 0) {
                    teamPicking.setText(contestants[countDown + 1]);
                    nextTeamPicking.setText(contestants[countDown]);
                }
                else
                {
                    countUp = countUp + 1;
                }
                countDown = countDown - 1;
            }

            if(countDown < 0){
                teamPicking.setText(contestants[countDown + 1]);
                nextTeamPicking.setText(contestants[countDown + 1]);
                countUp = 0;
                countDown = contestants.length - 1;
            }
            if(pressed > 0) {
                removeFromTable();
            }
            pressed = pressed + 1;


        });




        startTimer.setOnAction(new EventHandler() {

            @Override
            public void handle(Event event) {
                if (timeline != null) {
                    timeline.stop();
                }
                timeSeconds.set(STARTTIME);
                timeline = new Timeline();
                timeline.getKeyFrames().add(
                        new KeyFrame(Duration.seconds(STARTTIME+1),
                                new KeyValue(timeSeconds, 0)));
                timeline.playFromStart();
            }
        });



        Separator sep = new Separator();
        sep.setLayoutX(151.0);
        sep.setOrientation(Orientation.VERTICAL);
        sep.setPrefHeight(690.0);
        sep.setPrefWidth(31.0);

        Label currentPick = new Label();
        currentPick.setLayoutX(50.0);
        currentPick.setLayoutY(130.0);
        currentPick.setText("Current Pick");
        currentPick.setUnderline(Boolean.TRUE);
        currentPick.setFont(Font.font("System Bold Italic"));
        currentPick.setScaleX(2.0);
        currentPick.setScaleY(2.0);

        teamPicking = new Label();
        teamPicking.setLayoutY(160.0);
        teamPicking.setLayoutX(65.0);
        teamPicking.setPrefHeight(41.0);
        teamPicking.setPrefWidth(55.0);
        teamPicking.setText("Team 1");
        teamPicking.setScaleX(2.0);
        teamPicking.setScaleY(2.0);

        Label nextPick = new Label();
        nextPick.setLayoutX(55.0);
        nextPick.setLayoutY(250.0);
        nextPick.setText("Next Pick");
        nextPick.setUnderline(Boolean.TRUE);
        nextPick.setFont(Font.font("System Bold Italic"));
        nextPick.setScaleX(2.0);
        nextPick.setScaleY(2.0);

        nextTeamPicking = new Label();
        nextTeamPicking.setLayoutX(57.0);
        nextTeamPicking.setLayoutY(290.0);
        nextTeamPicking.setText("Team 2");
        nextTeamPicking.setScaleX(2.0);
        nextTeamPicking.setScaleY(2.0);

        Label timer = new Label();
        timer.setLayoutX(68.0);
        timer.setLayoutY(70.0);
        //timer.setText(String.valueOf(timeSeconds.getValue()));
        timer.setFont(Font.font("System Bold Italic"));
        timer.setScaleX(4.0);
        timer.setScaleY(4.0);
        timer.textProperty().bind(timeSeconds.asString());

        Group everything = new Group();

        everything.getChildren().addAll(background, table, sep, currentPick,
                nextPick, teamPicking, nextTeamPicking, timer, startTimer, draftPlayer);

        // Scene
        Scene scene = new Scene(everything, 500, 500); // w x h
        primaryStage.setScene(scene);
        primaryStage.show();

    }



    private ObservableList getTopPlayers() throws IOException {

        Collection<Player> list = Files.readAllLines(new File("c:/users/seanz/workspace/DraftHelper/test.txt").toPath())
                .stream()
                .map(line -> {
                    String[] details = line.split(":");
                    Player cd = new Player(details[0], details[1], details[2], details[3]);
                    return cd;
                })
                .collect(Collectors.toList());

        ObservableList<Player> details = FXCollections.observableArrayList(list);
        return details;
    }

    private void removeFromTable(){
        int ix = table.getSelectionModel().getSelectedIndex();
        Player uno = (Player) table.getSelectionModel().getSelectedItem();
        data.remove(ix);

        if (table.getItems().size() == 0) {

            actionStatus.setText("No data in table !");
            return;
        }

        if (ix != 0) {

            ix = ix -1;
        }

        table.requestFocus();
        table.getSelectionModel().select(ix);
        table.getFocusModel().focus(ix);
    }


}