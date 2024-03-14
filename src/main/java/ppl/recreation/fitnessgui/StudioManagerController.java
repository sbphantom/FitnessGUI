package ppl.recreation.fitnessgui;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;


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
    private MenuButton recordMemberSelector;
    @FXML
    private MenuButton recordClassSelector;
    @FXML
    private MenuButton unrecordMemberSelector;
    @FXML
    private MenuButton unrecordClassSelector;
    @FXML
    private MenuButton locationSelector;

    private ToggleGroup membershipTierGroup = new ToggleGroup();

    private ToggleGroup selectedStatusGroup = new ToggleGroup();

    @FXML
    private Label useGuestPassCount;

    @FXML
    private RadioButton useGuestPass;

    @FXML
    private RadioButton memberButton;

    @FXML
    private RadioButton guestButton;


    @FXML
    private RadioButton basicButton;

    @FXML
    private RadioButton familyButton;

    @FXML
    private RadioButton premiumButton;

    @FXML
    private Button registerButton;

    @FXML
    private Button recordButton;
    @FXML
    private Button unrecordButton;
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

        TableColumn<Member, String> tierColumn = new TableColumn<>("Tier");
        tierColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClass().getSimpleName()));
        deregistrationTable.getColumns().add(tierColumn);

        TableColumn<Member, Number> guestPassColumn = new TableColumn<>("Guest Passes");
        guestPassColumn.setCellValueFactory(cellData -> new ReadOnlyIntegerWrapper(cellData.getValue().guestPassCount()));
        deregistrationTable.getColumns().add(guestPassColumn);

        TableColumn<Member, Date> expireColumn = new TableColumn<>("Expiration Date");
        expireColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getExpire()));
        deregistrationTable.getColumns().add(expireColumn);

        TableColumn<Member, Boolean> expiredColumn = new TableColumn<>("Expired");
        expiredColumn.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue().isExpired()));
        expiredColumn.setCellFactory(CheckBoxTableCell.forTableColumn(expiredColumn));
        expiredColumn.setEditable(false);
        deregistrationTable.getColumns().add(expiredColumn);


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

        TableColumn<Member, String> tierColumn = new TableColumn<>("Tier");
        tierColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClass().getSimpleName()));
        memberTable.getColumns().add(tierColumn);

        TableColumn<Member, Number> guestPassColumn = new TableColumn<>("Guest Passes");
        guestPassColumn.setCellValueFactory(cellData -> new ReadOnlyIntegerWrapper(cellData.getValue().guestPassCount()));
        memberTable.getColumns().add(guestPassColumn);

        TableColumn<Member, Date> expireColumn = new TableColumn<>("Expiration");
        expireColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getExpire()));
        memberTable.getColumns().add(expireColumn);

        TableColumn<Member, Boolean> expiredColumn = new TableColumn<>("Expired");
        expiredColumn.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue().isExpired()));
        expiredColumn.setCellFactory(CheckBoxTableCell.forTableColumn(expiredColumn));
        memberTable.getColumns().add(expiredColumn);
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

    private void initializeStatusSelection() {
        this.memberButton.setToggleGroup(selectedStatusGroup);
        this.guestButton.setToggleGroup(selectedStatusGroup);
    }


    private void handleRecordMemberSelection(Member member) {
        recordMemberSelector.setText(member.getProfile().getFname() + " " + member.getProfile().getLname());
        recordMemberSelector.setUserData(member);

        useGuestPassCount.setText("Guest Pass Count: " + member.guestPassCount());

        if (member.isFree() && !member.canGuest()) {
            useGuestPass.setDisable(true);
            useGuestPass.setSelected(false);
        } else if (!member.isFree() && member.canGuest()) {
            useGuestPass.setDisable(true);
            useGuestPass.setSelected(true);
        } else if (member.isFree() && member.canGuest()) {
            useGuestPass.setDisable(false);
        }

        recordClassSelector.getItems().clear();
        recordClassSelector.setUserData(null);
        recordClassSelector.setText("Class");

        handleUseGuestPassSelection(member, useGuestPass.isSelected());

    }

    private void handleUnrecordClassSelection(FitnessClass fitnessClass) {
        unrecordClassSelector.setText(fitnessClass.toString());
        unrecordClassSelector.setUserData(fitnessClass);

        if (fitnessClass.hasAttendance() && fitnessClass.hasGuestAttendance()) {
            memberButton.setDisable(false);
            guestButton.setDisable(false);
        } else if (fitnessClass.hasAttendance() && !fitnessClass.hasGuestAttendance()) {
            memberButton.setDisable(true);
            guestButton.setDisable(true);
            memberButton.setSelected(true);
        } else if (!fitnessClass.hasAttendance() && fitnessClass.hasGuestAttendance()) {
            memberButton.setDisable(true);
            guestButton.setDisable(true);
            guestButton.setSelected(true);
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
            System.out.println(fitnessClass.getMembers().getMembers().size());
            for (Member m : fitnessClass.getMembers().getMembers()) {
                MenuItem menuItem = new MenuItem(m.getProfile().getFname() + " " + m.getProfile().getLname());
                menuItem.setOnAction(event -> handleUnrecordMemberSelection(m));
                menuItem.setUserData(m);
                unrecordMemberSelector.getItems().add(menuItem);
            }

        } else if (guestButton.isSelected()) {
            unrecordMemberSelector.setText("Guest");
            System.out.println(unrecordMemberSelector.getUserData());
            System.out.println(fitnessClass.getGuests().getMembers().size());
            for (Member g : fitnessClass.getGuests().getMembers()) {
                MenuItem menuItem = new MenuItem(g.getProfile().getFname() + " " + g.getProfile().getLname());
                menuItem.setOnAction(event -> handleUnrecordMemberSelection(g));

                menuItem.setUserData(g);
                unrecordMemberSelector.getItems().add(menuItem);
            }
        }
    }

    @FXML
    protected void onUseGuestPassSelection() {
        handleUseGuestPassSelection((Member) recordMemberSelector.getUserData(), useGuestPass.isSelected());
    }



    @FXML
    private void handleUseGuestPassSelection(Member member, boolean use) {
        recordClassSelector.getItems().clear();
        if (!use) {
            for (FitnessClass fitnessClass : StudioManagerMain.getSchedule().getClasses()) {
                if (member instanceof Basic && !fitnessClass.getStudio().equals(member.getHomeStudio())) {
                    continue;
                }
                FitnessClass[] memberAttendance = member.getAttendance();

                if (memberAttendance[fitnessClass.getTime().ordinal()] == null) {
                    MenuItem menuItem = new MenuItem(fitnessClass.getMenuString());
                    menuItem.setOnAction(event -> handleRecordClassSelection(fitnessClass));
                    menuItem.setUserData(fitnessClass);
                    recordClassSelector.getItems().add(menuItem);

                }

            }
        } else {
            for (FitnessClass fitnessClass : StudioManagerMain.getSchedule().getClasses()) {
                if (member instanceof Basic && !fitnessClass.getStudio().equals(member.getHomeStudio())) {
                    continue;
                }
                MenuItem menuItem = new MenuItem(fitnessClass.getMenuString());
                menuItem.setOnAction(event -> handleRecordClassSelection(fitnessClass));
                menuItem.setUserData(fitnessClass);
                recordClassSelector.getItems().add(menuItem);


            }
        }
    }


    private void handleRecordClassSelection(FitnessClass fitnessClass) {
        recordClassSelector.setText(fitnessClass.toString());
        recordClassSelector.setUserData(fitnessClass);
    }

    private void handleUnrecordMemberSelection(Member member) {
        unrecordMemberSelector.setText(member.getProfile().getFname() + " " + member.getProfile().getLname());
        unrecordMemberSelector.setUserData(member);
    }


    private void handleLocationSelection(Location location) {
        locationSelector.setText(location.getName());
        locationSelector.setUserData(location);
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
        unrecordMemberSelector.getItems().clear();
        for (Member member : StudioManagerMain.getMemberlist().getMembers()) {
            if (!member.isExpired()) {
                if (member.isFree()) {
                    MenuItem menuItem = new MenuItem(member.getProfile().getFname() + " " + member.getProfile().getLname() + " (" + member.getClass().getSimpleName() + ")");
                    menuItem.setOnAction(event -> handleRecordMemberSelection(member));
                    menuItem.setUserData(member);
                    recordMemberSelector.getItems().add(menuItem);

                    menuItem = new MenuItem(member.getProfile().getFname() + " " + member.getProfile().getLname() + " (" + member.getClass().getSimpleName() + ")");
                    menuItem.setOnAction(event -> handleUnrecordMemberSelection(member));
                    menuItem.setUserData(member);
                    unrecordMemberSelector.getItems().add(menuItem);
                } else if (member.canGuest()) {
                    MenuItem menuItem = new MenuItem(member.getProfile().getFname() + " " + member.getProfile().getLname() + " (" + member.getClass().getSimpleName() + ")");
                    menuItem.setOnAction(event -> handleRecordMemberSelection(member));
                    menuItem.setUserData(member);
                    recordMemberSelector.getItems().add(menuItem);
                }
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


    @FXML
    public void initialize() {
        memberTable.setItems(members);
        scheduleTable.setItems(classes);
        locationTable.setItems(locations);
        deregistrationTable.setItems(members);
        initializeLocationMenuButton();
        initializeClassMenuButton();
        initializeMemberMenuButton();

        initializeMemberTable();
        initializeScheduleTable();
        initializeLocationTable();
        initializeRegistrationForm();
        initializeDeregistrationTable();
        initializeStatusSelection();

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


        BooleanBinding recordClassCondition = Bindings.createBooleanBinding(() ->
                        !recordMemberSelector.getText().equals("Member"),
                recordMemberSelector.textProperty());

        recordClassSelector.disableProperty().bind(recordClassCondition.not());


        BooleanBinding recordCondition = Bindings.createBooleanBinding(() ->

                        !recordClassSelector.getText().equals("Class") &&
                                !recordMemberSelector.getText().equals("Member"),


                recordClassSelector.textProperty(),
                recordMemberSelector.textProperty()
        );

        recordButton.disableProperty().bind(recordCondition.not());


        BooleanBinding unrecordMemberCondition = Bindings.createBooleanBinding(() ->
                        !unrecordClassSelector.getText().equals("Class"),
                unrecordClassSelector.textProperty());

        unrecordMemberSelector.disableProperty().bind(unrecordMemberCondition.not());

        recordClassSelector.disableProperty().bind(recordClassCondition.not());

        BooleanBinding unRecordCondition = Bindings.createBooleanBinding(() ->
                        selectedStatusGroup.getSelectedToggle() != null &&
                                !unrecordClassSelector.getText().equals("Class") &&
                                (!unrecordMemberSelector.getText().equals("Member") && !unrecordMemberSelector.getText().equals("Guest")),

                selectedStatusGroup.selectedToggleProperty(),
                unrecordClassSelector.textProperty(),
                unrecordMemberSelector.textProperty()
        );

        unrecordButton.disableProperty().bind(unRecordCondition.not());

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
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
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
                members.remove(selectedMemberIdx.get(i) - i);
                StudioManagerMain.getMemberlist().getMembers().remove(selectedMemberIdx.get(i) - i);
            }
            memberTable.setItems(members);
            deregistrationTable.setItems(members);
            recordMemberSelector.getItems().clear();
            initializeMemberMenuButton();



        }
    }

    @FXML
    protected void onRecordMemberButtonClick() {
        System.out.println(recordClassSelector.getUserData());
        Member member = (Member) recordMemberSelector.getUserData();
        FitnessClass target = (FitnessClass) recordClassSelector.getUserData();
        FitnessClass[] memberAttendance = member.getAttendance();
        if (memberAttendance[target.getTime().ordinal()] == null || useGuestPass.isSelected()) {
            if (!useGuestPass.isSelected()) {
                memberAttendance[target.getTime().ordinal()] = (target);
                target.addMember(member);
            } else {
                target.addGuest(member);
                member.useGuestPass();
            }
            if (member instanceof Basic basic && !useGuestPass.isSelected()) {
                basic.incrementClassCount();
            }

            System.out.printf("%s attendance recorded %s%n", member.getProfile().getFname(), target.getClassInfo());

            recordMemberSelector.setText("Member");
            recordMemberSelector.setUserData(null);
            recordMemberSelector.getItems().clear();
            recordClassSelector.setText("Class");
            recordClassSelector.setUserData(null);
            recordClassSelector.getItems().clear();

            useGuestPassCount.setText("Guest Pass Count: ");
            useGuestPass.setDisable(true);
            useGuestPass.setSelected(false);

            recordMemberSelector.getItems().clear();
            initializeMemberMenuButton();
            initializeClassMenuButton();
        }

    }


    @FXML
    protected void onUnrecordMemberButtonClick() {
        Member member = (Member) unrecordMemberSelector.getUserData();
        FitnessClass target = (FitnessClass) unrecordClassSelector.getUserData();

        if (memberButton.isSelected()) {
            member.getAttendance()[target.getTime().ordinal()] = null;
            target.removeMember(member);
            memberButton.setSelected(false);
        } else if (guestButton.isSelected()) {
            target.removeGuest(member);
            guestButton.setSelected(false);
        }

        unrecordMemberSelector.setText("Member");
        unrecordMemberSelector.setUserData(null);
        unrecordMemberSelector.getItems().clear();
        unrecordClassSelector.setText("Class");
        unrecordClassSelector.setUserData(null);
        unrecordClassSelector.getItems().clear();


        handleUnrecordStatusSelection();

        unrecordMemberSelector.getItems().clear();
        initializeMemberMenuButton();
        initializeClassMenuButton();

    }
}