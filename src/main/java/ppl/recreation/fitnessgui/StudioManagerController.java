package ppl.recreation.fitnessgui;

import javafx.fxml.FXML;
import javafx.beans.property.*;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.scene.control.cell.CheckBoxTableCell;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class StudioManagerController {
    @FXML
    private TextArea appConsole;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private DatePicker birthdate;
    @FXML
    private Label useGuestPassCount;

    @FXML
    public Button loadScheduleButton;
    @FXML
    public Button loadMemberlistButton;
    @FXML
    public Button registerButton;
    @FXML
    public Button recordButton;
    @FXML
    public Button unrecordButton;
    @FXML
    public Button printMember;
    @FXML
    public Button printCounty;
    @FXML
    public Button printFee;
    @FXML
    public Button printAttendance;

    @FXML
    private TableView<Member> memberTable;
    @FXML
    private TableView<Member> deregistrationTable;
    @FXML
    private TableView<FitnessClass> scheduleTable;
    @FXML
    private TableView<Location> locationTable;

    @FXML
    private MenuButton locationSelector;
    @FXML
    private MenuButton recordMemberSelector;
    @FXML
    private MenuButton recordClassSelector;
    @FXML
    private MenuButton unrecordMemberSelector;
    @FXML
    private MenuButton unrecordClassSelector;


    @FXML
    private RadioButton basicButton;
    @FXML
    private RadioButton familyButton;
    @FXML
    private RadioButton premiumButton;
    @FXML
    private RadioButton useGuestPass;
    @FXML
    private RadioButton memberButton;
    @FXML
    private RadioButton guestButton;


    private ToggleGroup membershipTierGroup = new ToggleGroup();
    private ToggleGroup selectedStatusGroup = new ToggleGroup();

    ObservableList<Member> members = FXCollections.observableArrayList(StudioManagerMain.getMemberlist().getMembers());
    ObservableList<FitnessClass> classes = FXCollections.observableArrayList(StudioManagerMain.getSchedule().getClasses());
    ObservableList<Location> locations = FXCollections.observableArrayList(Location.values());

    private void initializeTable(TableView<Member> table) {
        TableColumn<Member, String> firstNameColumn = new TableColumn<>("First Name");
        firstNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProfile().getFname()));
        table.getColumns().add(firstNameColumn);

        TableColumn<Member, String> lastNameColumn = new TableColumn<>("Last Name");
        lastNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProfile().getLname()));
        table.getColumns().add(lastNameColumn);

        TableColumn<Member, Date> dobColumn = new TableColumn<>("Birthdate");
        dobColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getProfile().getDob()));
        table.getColumns().add(dobColumn);

        TableColumn<Member, String> homeStudioColumn = new TableColumn<>("Home Studio");
        homeStudioColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHomeStudio().getName()));
        table.getColumns().add(homeStudioColumn);

        TableColumn<Member, String> tierColumn = new TableColumn<>("Tier");
        tierColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClass().getSimpleName()));
        table.getColumns().add(tierColumn);

        TableColumn<Member, Number> guestPassColumn = new TableColumn<>("Guest Passes");
        guestPassColumn.setCellValueFactory(cellData -> new ReadOnlyIntegerWrapper(cellData.getValue().guestPassCount()));
        table.getColumns().add(guestPassColumn);

        TableColumn<Member, Date> expireColumn = new TableColumn<>("Expiration");
        expireColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getExpire()));
        table.getColumns().add(expireColumn);

        TableColumn<Member, Boolean> expiredColumn = new TableColumn<>("Expired");
        expiredColumn.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue().isExpired()));
        expiredColumn.setCellFactory(CheckBoxTableCell.forTableColumn(expiredColumn));
        table.getColumns().add(expiredColumn);
    }

    private void initializeMemberTable() {
        initializeTable(memberTable);
    }

    private void initializeDeregistrationTable() {
        deregistrationTable.setEditable(true);

        TableColumn<Member, Boolean> checkboxColumn = new TableColumn<>("");
        checkboxColumn.setCellValueFactory(cellData -> cellData.getValue().selectedProperty());
        checkboxColumn.setCellFactory(CheckBoxTableCell.forTableColumn(checkboxColumn));
        deregistrationTable.getColumns().add(checkboxColumn);

        initializeTable(deregistrationTable);
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

    private void initializeTierSelection() {
        this.basicButton.setToggleGroup(membershipTierGroup);
        this.familyButton.setToggleGroup(membershipTierGroup);
        this.premiumButton.setToggleGroup(membershipTierGroup);
    }

    private void initializeStatusSelection() {
        this.memberButton.setToggleGroup(selectedStatusGroup);
        this.guestButton.setToggleGroup(selectedStatusGroup);
    }

    private void initializeLocationMenuButton() {
        for (Location location : Location.values()) {
            MenuItem menuItem = new MenuItem(location.toString());
            menuItem.setOnAction(event -> handleLocationSelection(location));
            menuItem.setUserData(location);
            locationSelector.getItems().add(menuItem);
        }
    }

    private void initializeMemberMenuButton() {
        recordMemberSelector.getItems().clear();
        for (Member member : StudioManagerMain.getMemberlist().getMembers()) {
            if (!member.isExpired() && (member.isFree() || member.canGuest())) {
                MenuItem menuItem = new MenuItem(member.getProfile().getFname() + " " + member.getProfile().getLname() + " (" + member.getClass().getSimpleName() + ")");
                menuItem.setOnAction(event -> handleRecordMemberSelection(member));
                menuItem.setUserData(member);
                recordMemberSelector.getItems().add(menuItem);
            }
        }
    }

    private void initializeClassMenuButton() {
        unrecordClassSelector.getItems().clear();
        for (FitnessClass fitnessClass : StudioManagerMain.getSchedule().getClasses()) {
            if (fitnessClass.hasAttendance() || fitnessClass.hasGuestAttendance()) {
                MenuItem menuItem = new MenuItem(fitnessClass.getMenuString());
                menuItem.setOnAction(event -> handleUnrecordClassSelection(fitnessClass));
                menuItem.setUserData(fitnessClass);
                unrecordClassSelector.getItems().add(menuItem);
            }
        }
    }

    private void bindRegisterCondition() {
        BooleanBinding registerCondition = Bindings.createBooleanBinding(() ->
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

        registerButton.disableProperty().bind(registerCondition.not());
    }


    private void bindRecordClassSelector() {
        BooleanBinding recordClassCondition = Bindings.createBooleanBinding(() ->
                        !recordMemberSelector.getText().equals("Member"),
                recordMemberSelector.textProperty());

        recordClassSelector.disableProperty().bind(recordClassCondition.not());
    }

    private void bindRecordCondition() {
        BooleanBinding recordCondition = Bindings.createBooleanBinding(() ->
                        !recordClassSelector.getText().equals("Class") &&
                                !recordMemberSelector.getText().equals("Member"),
                recordClassSelector.textProperty(),
                recordMemberSelector.textProperty()
        );

        recordButton.disableProperty().bind(recordCondition.not());
    }


    private void bindUnrecordMemberSelector() {
        BooleanBinding unrecordMemberCondition = Bindings.createBooleanBinding(() ->
                        !unrecordClassSelector.getText().equals("Class"),
                unrecordClassSelector.textProperty());

        unrecordMemberSelector.disableProperty().bind(unrecordMemberCondition.not());
    }

    private void bindUnrecordCondition() {
        BooleanBinding unrecordCondition = Bindings.createBooleanBinding(() ->
                        selectedStatusGroup.getSelectedToggle() != null &&
                                !unrecordClassSelector.getText().equals("Class") &&
                                (!unrecordMemberSelector.getText().equals("Member") && !unrecordMemberSelector.getText().equals("Guest")),

                selectedStatusGroup.selectedToggleProperty(),
                unrecordClassSelector.textProperty(),
                unrecordMemberSelector.textProperty()
        );

        unrecordButton.disableProperty().bind(unrecordCondition.not());
    }

    private void handleLocationSelection(Location location) {
        locationSelector.setText(location.getName());
        locationSelector.setUserData(location);
    }

    private void handleRecordMemberSelection(Member member) {
        if (member == null) return;
        recordMemberSelector.setText(member.getProfile().getFname() + " " + member.getProfile().getLname());
        recordMemberSelector.setUserData(member);

        useGuestPassCount.setText("Guest Pass Count: " + member.guestPassCount());
        boolean isFreeAndCanGuest = member.isFree() && member.canGuest();
        useGuestPass.setDisable(!isFreeAndCanGuest);
        useGuestPass.setSelected(isFreeAndCanGuest && !member.isFree());

        recordClassSelector.getItems().clear();
        recordClassSelector.setUserData(null);
        recordClassSelector.setText("Class");

        handleUseGuestPassSelection();
    }

    @FXML
    private void handleUseGuestPassSelection() {
        if (recordMemberSelector.getUserData() == null) return;

        recordClassSelector.getItems().clear();

        Member member = (Member) recordMemberSelector.getUserData();
        for (FitnessClass fitnessClass : StudioManagerMain.getSchedule().getClasses()) {
            if (member instanceof Basic && !fitnessClass.getStudio().equals(member.getHomeStudio())) {
                continue;
            }

            FitnessClass[] memberAttendance = member.getAttendance();

            if (useGuestPass.isSelected() || memberAttendance[fitnessClass.getTime().ordinal()] == null) {
                MenuItem menuItem = new MenuItem(fitnessClass.getMenuString());
                menuItem.setOnAction(event -> handleRecordClassSelection(fitnessClass));
                menuItem.setUserData(fitnessClass);
                recordClassSelector.getItems().add(menuItem);
            }
        }
    }

    private void handleRecordClassSelection(FitnessClass fitnessClass) {
        if (fitnessClass == null) return;
        recordClassSelector.setText(fitnessClass.toString());
        recordClassSelector.setUserData(fitnessClass);
    }

    private void handleUnrecordClassSelection(FitnessClass fitnessClass) {
        unrecordClassSelector.setText(fitnessClass.toString());
        unrecordClassSelector.setUserData(fitnessClass);

        memberButton.setDisable(!fitnessClass.hasAttendance());
        guestButton.setDisable(!fitnessClass.hasGuestAttendance());
        if (!fitnessClass.hasAttendance() && fitnessClass.hasGuestAttendance()) {
            guestButton.setSelected(true);
        } else if (fitnessClass.hasAttendance() && !fitnessClass.hasGuestAttendance()) {
            memberButton.setSelected(true);
        }

        unrecordMemberSelector.setText("Member");
        unrecordMemberSelector.setUserData(null);
        unrecordMemberSelector.getItems().clear();

        handleUnrecordStatusSelection();
    }

    @FXML
    private void handleUnrecordStatusSelection() {
        if (unrecordClassSelector.getUserData() == null || selectedStatusGroup.getSelectedToggle() == null) return;

        unrecordMemberSelector.setUserData(null);
        FitnessClass fitnessClass = (FitnessClass) unrecordClassSelector.getUserData();

        if (memberButton.isSelected()) {
            unrecordMemberSelector.setText("Member");
            for (Member member : fitnessClass.getMembers().getMembers()) {
                MenuItem menuItem = new MenuItem(member.getProfile().getFname() + " " + member.getProfile().getLname());
                menuItem.setOnAction(event -> handleUnrecordMemberSelection(member));
                menuItem.setUserData(member);
                unrecordMemberSelector.getItems().add(menuItem);
            }
        } else if (guestButton.isSelected()) {
            unrecordMemberSelector.setText("Guest");
            for (Member guest : fitnessClass.getGuests().getMembers()) {
                MenuItem menuItem = new MenuItem(guest.getProfile().getFname() + " " + guest.getProfile().getLname());
                menuItem.setOnAction(event -> handleUnrecordMemberSelection(guest));
                menuItem.setUserData(guest);
                unrecordMemberSelector.getItems().add(menuItem);
            }
        }
    }

    private void handleUnrecordMemberSelection(Member member) {
        unrecordMemberSelector.setText(member.getProfile().getFname() + " " + member.getProfile().getLname());
        unrecordMemberSelector.setUserData(member);
    }


    @FXML
    public void initialize() {
        initializeMemberTable();
        initializeScheduleTable();
        initializeLocationTable();
        initializeDeregistrationTable();

        memberTable.setItems(members);
        deregistrationTable.setItems(members);
        scheduleTable.setItems(classes);
        locationTable.setItems(locations);

        initializeLocationMenuButton();
        initializeMemberMenuButton();
        initializeClassMenuButton();
        initializeTierSelection();
        initializeStatusSelection();

        bindRegisterCondition();
        bindRecordClassSelector();
        bindRecordCondition();
        bindUnrecordMemberSelector();
        bindUnrecordCondition();
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
            initializeClassMenuButton();
            recordMemberSelector.setText("Member");
            recordMemberSelector.setUserData(null);
            handleRecordMemberSelection((Member) recordMemberSelector.getUserData());
            handleUseGuestPassSelection();
            handleRecordClassSelection((FitnessClass) recordClassSelector.getUserData());
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
            initializeMemberMenuButton();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void onPrintMemberButtonClick() {
        appConsole.appendText(StudioManagerMain.getMemberlist().printByMember());
        members = FXCollections.observableArrayList(StudioManagerMain.getMemberlist().getMembers());
        memberTable.setItems(members);
        deregistrationTable.setItems(members);
        recordMemberSelector.getItems().clear();
        initializeMemberMenuButton();
    }

    @FXML
    protected void onPrintCountyButtonClick() {
        appConsole.appendText(StudioManagerMain.getMemberlist().printByCounty());
        members = FXCollections.observableArrayList(StudioManagerMain.getMemberlist().getMembers());
        memberTable.setItems(members);
        deregistrationTable.setItems(members);
        recordMemberSelector.getItems().clear();
        initializeMemberMenuButton();
    }

    @FXML
    protected void onPrintFeeButtonClick() {
        appConsole.appendText(StudioManagerMain.getMemberlist().printByFees());
        members = FXCollections.observableArrayList(StudioManagerMain.getMemberlist().getMembers());
        memberTable.setItems(members);
        deregistrationTable.setItems(members);
        recordMemberSelector.getItems().clear();
        initializeMemberMenuButton();
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
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Member Added");
            alert.setContentText(String.format("%s %s added.", fname, lname));
            alert.showAndWait();

            members = FXCollections.observableArrayList(StudioManagerMain.getMemberlist().getMembers());
            memberTable.setItems(members);
            deregistrationTable.setItems(members);

            firstName.setText(null);
            lastName.setText(null);
            birthdate.setValue(null);
            membershipTierGroup.getSelectedToggle().setSelected(false);
            locationSelector.setText("Home Studio");

            initializeMemberMenuButton();
        }
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
                StudioManagerMain.getMemberlist().getMembers().remove(selectedMemberIdx.get(i) - i);
            }
            members = FXCollections.observableArrayList(StudioManagerMain.getMemberlist().getMembers());
            memberTable.setItems(members);
            deregistrationTable.setItems(members);
            recordMemberSelector.getItems().clear();
            initializeMemberMenuButton();
        }
    }

    @FXML
    protected void onRecordMemberButtonClick() {
        Member member = (Member) recordMemberSelector.getUserData();
        FitnessClass target = (FitnessClass) recordClassSelector.getUserData();
        FitnessClass[] memberAttendance = member.getAttendance();
        if (memberAttendance[target.getTime().ordinal()] == null || useGuestPass.isSelected()) {
            String message;
            if (!useGuestPass.isSelected()) {
                memberAttendance[target.getTime().ordinal()] = target;
                target.addMember(member);
                message = String.format("%s %s's attendance has been marked for %s's %s class in %s.",
                        member.getProfile().getFname(), member.getProfile().getLname(), target.getInstructor().getName(), target.getClassInfo().getName(), target.getStudio().getName());
            } else {
                target.addGuest(member);
                member.useGuestPass();
                message = String.format("%s %s's guest attendance has been marked for %s's %s class in %s.",
                        member.getProfile().getFname(), member.getProfile().getLname(), target.getInstructor().getName(), target.getClassInfo().getName(), target.getStudio().getName());
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Attendance Marked");
            alert.setContentText(message);
            alert.showAndWait();

            recordMemberSelector.setText("Member");
            recordMemberSelector.setUserData(null);
            recordMemberSelector.getItems().clear();
            initializeMemberMenuButton();

            useGuestPassCount.setText("Guest Pass Count: ");
            useGuestPass.setDisable(true);
            useGuestPass.setSelected(false);

            recordClassSelector.setText("Class");
            recordClassSelector.setUserData(null);
            recordClassSelector.getItems().clear();
            initializeClassMenuButton();
        }
    }

    @FXML
    protected void onUnrecordMemberButtonClick() {
        Member member = (Member) unrecordMemberSelector.getUserData();
        FitnessClass target = (FitnessClass) unrecordClassSelector.getUserData();

        String message;
        if (memberButton.isSelected()) {
            member.getAttendance()[target.getTime().ordinal()] = null;
            target.removeMember(member);
            memberButton.setSelected(false);
            memberButton.setDisable(true);
            message = String.format("%s %s's attendance has been removed from %s's %s class in %s.",
                    member.getProfile().getFname(), member.getProfile().getLname(), target.getInstructor().getName(), target.getClassInfo().getName(), target.getStudio().getName());
        } else {
            target.removeGuest(member);
            guestButton.setSelected(false);
            guestButton.setDisable(true);
            message = String.format("%s %s's guest attendance has been removed from %s's %s class in %s.",
                    member.getProfile().getFname(), member.getProfile().getLname(), target.getInstructor().getName(), target.getClassInfo().getName(), target.getStudio().getName());
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Remove Attendance");
        alert.setContentText("Are you sure you want to unmark the selected member's attendance?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            unrecordMemberSelector.setText("Member");
            unrecordMemberSelector.setUserData(null);
            unrecordMemberSelector.getItems().clear();
            initializeMemberMenuButton();

            unrecordClassSelector.setText("Class");
            unrecordClassSelector.setUserData(null);
            unrecordClassSelector.getItems().clear();
            initializeClassMenuButton();

            handleUnrecordStatusSelection();

            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Complete");
            alert.setHeaderText("Attendance Removed");
            alert.setContentText(message);
            alert.showAndWait();
        }
    }
}