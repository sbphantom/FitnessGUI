package ppl.recreation.fitnessgui;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
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
import java.util.ArrayList;
import java.util.Optional;

public class StudioManagerController {
    @FXML
    private TextArea appConsole;
    @FXML
    public Button loadScheduleButton;
    @FXML
    public Button loadMemberlistButton;

    @FXML
    private TableView<Member> memberTable;

    @FXML
    private TableView<FitnessClass> scheduleTable;

    @FXML
    private TableView<Location> locationTable;

    @FXML
    private TableView<Member> deregistrationTable;

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private DatePicker birthdate;

    @FXML
    private MenuButton locationSelector;

    private ToggleGroup membershipTierGroup = new ToggleGroup();


    @FXML
    private RadioButton basicButton;

    @FXML
    private RadioButton familyButton;

    @FXML
    private RadioButton premiumButton;

    @FXML
    private Button registerButton;
    @FXML
    public Button printMember;
    @FXML
    public Button printCounty;
    @FXML
    public Button printFee;
    @FXML
    public Button printAttendance;
    ObservableList<Member> members = FXCollections.observableArrayList(StudioManagerMain.getMemberlist().getMembers());
    ObservableList<FitnessClass> classes = FXCollections.observableArrayList(StudioManagerMain.getSchedule().getClasses());
    ObservableList<Location> locations = FXCollections.observableArrayList(Location.values());

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
        this.basicButton.setToggleGroup(membershipTierGroup);
        this.familyButton.setToggleGroup(membershipTierGroup);
        this.premiumButton.setToggleGroup(membershipTierGroup);
    }

    private void handleLocationSelection(Location location) {
        locationSelector.setText(location.getName());
    }

    private void initializeLocationMenuButton() {
        for (Location location : Location.values()) {
            MenuItem menuItem = new MenuItem(location.toString());
            menuItem.setOnAction(event -> handleLocationSelection(location));
            locationSelector.getItems().add(menuItem);
        }
    }




    @FXML
    public void initialize() {
        memberTable.setItems(members);
        scheduleTable.setItems(classes);
        locationTable.setItems(locations);
        deregistrationTable.setItems(members);
        initializeLocationMenuButton();

        initializeMemberTable();
        initializeScheduleTable();
        initializeLocationTable();
        initializeRegistrationForm();
        initializeDeregistrationTable();

        BooleanBinding condition = Bindings.createBooleanBinding(() ->
                        membershipTierGroup.getSelectedToggle() != null &&
                                !locationSelector.getText().equals("Home Studio") &&
                                firstName.getText() != null && !firstName.getText().isEmpty() &&
                                lastName.getText() != null && !lastName.getText().isEmpty() &&
                                birthdate.getValue() != null,
                membershipTierGroup.selectedToggleProperty(),
                locationSelector.textProperty(),
                firstName.textProperty(),
                lastName.textProperty(),
                birthdate.valueProperty());

        registerButton.disableProperty().bind(condition.not());
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
            loadMemberlistButton.setDisable(true);
            loadMemberlistButton.setText("Memberlist Loaded");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    protected void onPrintMemberButtonClick() {
        appConsole.appendText(StudioManagerMain.getMemberlist().printByMember());
    }

    @FXML
    protected void onPrintCountyButtonClick() {
        appConsole.appendText(StudioManagerMain.getMemberlist().printByCounty());
    }

    @FXML
    protected void onPrintFeeButtonClick() {
        appConsole.appendText(StudioManagerMain.getMemberlist().printByFees());
    }

    @FXML
    protected void onPrintAttendanceButtonClick() {
        appConsole.appendText(StudioManagerMain.getSchedule().listString());
    }

    @FXML
    protected void onAddMemberButtonClick() {
        String fname = firstName.getText();
        String lname = lastName.getText();
        String[] date = birthdate.getValue().toString().split("-");
        Location home = Location.getLocation(locationSelector.getText().split(",")[0]);
        Date dob = new Date(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]));
        Profile profile = new Profile(fname, lname, dob);

        String error = null;
        String errorMessage = null;

        if (!dob.isValid()) {
            error = "Date Error";
            errorMessage = String.format("DOB %s: invalid calendar date!", dob);
        } else if (dob.compareTo(Date.todayDate()) >= 0) {
            error = "Date Error";
            errorMessage = String.format("DOB %s: cannot be today or a future date!", dob);
        } else if (profile.isMinor()) {
            error = "Unable to Register";
            errorMessage = String.format("DOB %s: must be 18 or older to join!", dob);
        }
        boolean valid = errorMessage == null;


        boolean result = false;
        if (valid && basicButton.isSelected()) {
            result = StudioManagerMain.getMemberlist().add(new Basic(profile, Date.todayDate().addMonths(1), home));
        } else if (valid && familyButton.isSelected()) {
            result = StudioManagerMain.getMemberlist().add(new Family(profile, Date.todayDate().addMonths(3), home));
        } else if (valid && premiumButton.isSelected()) {
            result = StudioManagerMain.getMemberlist().add(new Premium(profile, Date.todayDate().addYears(1), home));
        }
        if (valid && !result) {
            error = "Duplicate Member";
            errorMessage = String.format("%s %s is already in the member database.", fname, lname);
        }

        if (errorMessage != null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(error);
            alert.setContentText(errorMessage);
            alert.showAndWait();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Member Added");
            alert.setContentText(String.format("%s %s added.", fname, lname));
            alert.showAndWait();
        }



        appConsole.appendText(Boolean.toString(result));
        members = FXCollections.observableArrayList(StudioManagerMain.getMemberlist().getMembers());
        memberTable.setItems(members);
        deregistrationTable.setItems(members);

        firstName.setText(null);
        lastName.setText(null);
        birthdate.setValue(null);
        membershipTierGroup.getSelectedToggle().setSelected(false);
        locationSelector.setText("Home Studio");
    }

    @FXML
    protected void onRemoveMemberButtonClick() {
        ArrayList<Integer> selectedMemberIdx = new ArrayList<>();
        for (int i = 0; i < StudioManagerMain.getMemberlist().getSize(); i++) {
            Member m = StudioManagerMain.getMemberlist().getMembers().get(i);
            if (m.isSelected()) {
                selectedMemberIdx.add(i);
            }
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Remove Members");
        alert.setContentText("Are you sure you want to remove the selected members?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            for (int i = 0; i < selectedMemberIdx.size(); i++) {
                members.remove(selectedMemberIdx.get(i) - i);
                StudioManagerMain.getMemberlist().getMembers().remove(selectedMemberIdx.get(i) - i);
            }
            memberTable.setItems(members);
            deregistrationTable.setItems(members);
        }
    }
}