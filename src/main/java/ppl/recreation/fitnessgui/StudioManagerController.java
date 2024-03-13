package ppl.recreation.fitnessgui;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

import java.io.File;
import java.io.IOException;

public class StudioManagerController {
    @FXML
    public Button loadScheduleButton;
    @FXML
    public Button loadMemberlistButton;
    @FXML

    public Button printMember;
    @FXML

    public Button printCounty;
    @FXML

    public Button printFee;
    @FXML
    private TextArea appConsole;

    ObservableList<Member> members = FXCollections.observableArrayList(StudioManagerMain.getMemberlist().getMembers());
    ObservableList<FitnessClass> classes = FXCollections.observableArrayList(StudioManagerMain.getSchedule().getClasses());
    ObservableList<Location> locations = FXCollections.observableArrayList(Location.values());


    @FXML
    private TableView<Member> memberTable;

    @FXML
    private TableView<FitnessClass> scheduleTable;

    @FXML
    private TableView<Location> locationTable;
    @FXML
    private RadioButton basicButton;
    @FXML
    private RadioButton familyButton;
    @FXML
    private RadioButton premiumButton;

    @FXML
    private TableView<Member> deregistrationTable;

    private void initializeDeregistrationTable() {
        deregistrationTable.setEditable(true);

        TableColumn<Member, Boolean> checkboxColumn = new TableColumn<>("");
        checkboxColumn.setCellValueFactory(cellData -> cellData.getValue().selectedProperty());
        checkboxColumn.setCellFactory(CheckBoxTableCell.forTableColumn(checkboxColumn));
        deregistrationTable.getColumns().add(checkboxColumn);

        TableColumn<Member, String> firstNameColumn = new TableColumn<>("First Name");
        firstNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProfile().getFname()));
        deregistrationTable.getColumns().add(firstNameColumn);

        TableColumn<Member, String> lastNameColumn = new TableColumn<>("Last Name");
        lastNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProfile().getLname()));
        deregistrationTable.getColumns().add(lastNameColumn);

        TableColumn<Member, Date> dobColumn = new TableColumn<>("Birthdate");
        dobColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getProfile().getDob()));
        deregistrationTable.getColumns().add(dobColumn);

        TableColumn<Member, String> homeStudioColumn = new TableColumn<>("Home Studio");
        homeStudioColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHomeStudio().getName()));
        deregistrationTable.getColumns().add(homeStudioColumn);

        TableColumn<Member, Date> expireColumn = new TableColumn<>("Expiration");
        expireColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getExpire()));
        deregistrationTable.getColumns().add(expireColumn);
    }

    private void initializeMemberTable() {
        TableColumn<Member, String> firstNameColumn = new TableColumn<>("First Name");
        firstNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProfile().getFname()));
        memberTable.getColumns().add(firstNameColumn);

        TableColumn<Member, String> lastNameColumn = new TableColumn<>("Last Name");
        lastNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProfile().getLname()));
        memberTable.getColumns().add(lastNameColumn);

        TableColumn<Member, Date> dobColumn = new TableColumn<>("Birthdate");
        dobColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getProfile().getDob()));
        memberTable.getColumns().add(dobColumn);

        TableColumn<Member, String> homeStudioColumn = new TableColumn<>("Home Studio");
        homeStudioColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHomeStudio().getName()));
        memberTable.getColumns().add(homeStudioColumn);

        TableColumn<Member, Date> expireColumn = new TableColumn<>("Expiration");
        expireColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getExpire()));
        memberTable.getColumns().add(expireColumn);
    }

    private void initializeScheduleTable() {
        TableColumn<FitnessClass, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClassInfo().getName()));
        scheduleTable.getColumns().add(nameColumn);

        TableColumn<FitnessClass, String> instructorColumn = new TableColumn<>("Instructor");
        instructorColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getInstructor().getName()));
        scheduleTable.getColumns().add(instructorColumn);

        TableColumn<FitnessClass, String> locationColumn = new TableColumn<>("Location");
        locationColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getStudio().getName()));
        scheduleTable.getColumns().add(locationColumn);

        TableColumn<FitnessClass, Time> timeColumn = new TableColumn<>("Time");
        timeColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getTime()));
        scheduleTable.getColumns().add(timeColumn);
    }

    private void initializeLocationTable() {
        TableColumn<Location, String> cityColumn = new TableColumn<>("City");
        cityColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        locationTable.getColumns().add(cityColumn);

        TableColumn<Location, String> zipCodeColumn = new TableColumn<>("Zipcode");
        zipCodeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getZipCode()));
        locationTable.getColumns().add(zipCodeColumn);

        TableColumn<Location, String> countyColumn = new TableColumn<>("County");
        countyColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCounty()));
        locationTable.getColumns().add(countyColumn);

    }

    private void initializeRegistrationForm() {
        ToggleGroup membershipTierGroup = new ToggleGroup();
        this.basicButton.setToggleGroup(membershipTierGroup);
        this.familyButton.setToggleGroup(membershipTierGroup);
        this.premiumButton.setToggleGroup(membershipTierGroup);
    }


    @FXML
    public void initialize() {
        memberTable.setItems(members);
        scheduleTable.setItems(classes);
        locationTable.setItems(locations);
        deregistrationTable.setItems(members);

        initializeMemberTable();
        initializeScheduleTable();
        initializeLocationTable();
        initializeRegistrationForm();
        initializeDeregistrationTable();
    }

    @FXML
    protected void onLoadScheduleButtonClick() {
        try {
            StudioManagerMain.getSchedule().load(new File("data/classSchedule.txt"));
            appConsole.appendText("Schedule successfully loaded!\n");
            classes = FXCollections.observableArrayList(StudioManagerMain.getSchedule().getClasses());
            scheduleTable.setItems(classes);
            loadScheduleButton.setDisable(true);
            loadScheduleButton.setText("Schedule Loaded");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void onLoadMemberListButtonClick() {
        try {
            StudioManagerMain.getMemberlist().load(new File("data/memberList.txt"));
            appConsole.appendText("Memberlist successfully loaded!\n");
            members = FXCollections.observableArrayList(StudioManagerMain.getMemberlist().getMembers());
            memberTable.setItems(members);
            deregistrationTable.setItems(members);
//            StudioManagerMain.commandPrint("PM");
            loadMemberlistButton.setDisable(true);
            loadMemberlistButton.setText("Memberlist Loaded");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    protected void onPrintMemberButtonClick(){
        appConsole.appendText(StudioManagerMain.getMemberlist().printByMember());
    }
    @FXML
    protected void onPrintCountyButtonClick(){
        appConsole.appendText(StudioManagerMain.getMemberlist().printByCounty());
    }
    @FXML
    protected void onPrintFeeButtonClick(){
        appConsole.appendText(StudioManagerMain.getMemberlist().printByFees());
    }

}