import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.border.*;

public class GymGUI extends JFrame implements ActionListener
{
    private ArrayList<GymMember> memberList;

    private JTextField txtId, txtName, txtLocation, txtPhone, txtEmail, txtReferralSource, txtPaidAmount, txtRemovalReason, txtTrainerName;
    private JTextField txtPlanPrice, txtPremiumCharge, txtDiscountAmount;

    private JRadioButton rbMale, rbFemale;
    private ButtonGroup genderGroup;

    private JComboBox<String> cbPlan;

    private JComboBox<String> dobYearComboBox, dobMonthComboBox, dobDayComboBox;
    private JComboBox<String> msYearComboBox, msMonthComboBox, msDayComboBox;

    private JComboBox<String> cbAction;

    private JButton btnAddRegular, btnAddPremium, btnActivate, btnDeactivate;
    private JButton btnMarkAttendance, btnRevertRegular, btnRevertPremium, btnDisplay, btnClear;
    private JButton btnSaveToFile, btnReadFromFile, btnUpgradePlan, btnCalculateDiscount, btnPayDueAmount;

    private JLabel lblTitle, lblMemberInfo, lblActions;
    private JLabel lblId, lblName, lblLocation, lblPhone, lblEmail, lblGender;
    private JLabel lblDOB, lblStartDate, lblPlan, lblPlanPrice, lblTrainer;
    private JLabel lblPremiumCharge, lblReferralSource, lblPaidAmount, lblRemovalReason, lblDiscountAmount;
    private JLabel lblAction;

    private JPanel mainPanel, infoPanel, actionPanel, headerPanel, actionSelectionPanel;
    private JScrollPane infoScrollPane, actionScrollPane;

    private Color primaryColor = new Color(76, 40, 130);
    private Color secondaryColor = new Color(52, 152, 219);
    private Color accentColor = new Color(231, 76, 60);
    private Color backgroundColor = new Color(236, 240, 241);
    private Color textColor = new Color(44, 62, 80);
    private Color lightTextColor = Color.WHITE;

    private Font titleFont = new Font("Georgia", Font.BOLD, 26);
    private Font headerFont = new Font("Georgia", Font.BOLD, 18);
    private Font labelFont = new Font("Georgia", Font.PLAIN, 14);
    private Font buttonFont = new Font("Georgia", Font.BOLD, 14);

    /**
     * Constructor for GymGUI.
     * Initializes the member list, sets up the frame, and calls methods to create components and layout.
     */
    public GymGUI()
    {
        
        //create a button, add actionlistener to it, on clicking the button display "I am Pragya" in a JOptionPane
        /**
        JButton button = new JButton(); 
        button.addActionListener(new ActionListener)
        {
            public void actionPerformed(ActionEvent e)
            {
               JOptionPane.showMessageDialog(frame,"I am Pragya"); 
            }
        }
        */
        
        memberList = new ArrayList<>(); // Initialize empty member list

        setTitle("Gym Membership Management System");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        try
        {
            setIconImage(new ImageIcon("gym_icon.png").getImage()); // Set window icon
        }
        catch (Exception e)
        {
            // Icon not found, proceed without it
        }

        initComponents(); // Initialize all GUI components
        setupLayout();    // Arrange components in the frame
        registerListeners(); // Attach event listeners

        setVisible(true);
        setLocationRelativeTo(null); // Center the window

        updateFieldsAndButtons("Add Regular Member"); // Default action
    }

    /**
     * Initializes all GUI components such as text fields, buttons, and labels.
     */
    private void initComponents()
    {
        // Text fields for member details
        txtId = createStyledTextField(20);
        txtId.setToolTipText("Enter member ID");
        txtName = createStyledTextField(20);
        txtName.setToolTipText("Enter member's full name");
        txtLocation = createStyledTextField(20);
        txtLocation.setToolTipText("Enter member's location");
        txtPhone = createStyledTextField(20);
        txtPhone.setToolTipText("Enter member's phone number");
        txtEmail = createStyledTextField(20);
        txtEmail.setToolTipText("Enter member's email address");
        txtReferralSource = createStyledTextField(20);
        txtReferralSource.setToolTipText("Enter referral source");
        txtPaidAmount = createStyledTextField(20);
        txtPaidAmount.setToolTipText("Enter amount paid");
        txtRemovalReason = createStyledTextField(30);
        txtRemovalReason.setToolTipText("Enter reason for removal");
        txtTrainerName = createStyledTextField(20);
        txtTrainerName.setToolTipText("Enter trainer's name");

        // Read-only fields for plan price, premium charge, and discount
        txtPlanPrice = createStyledTextField(20);
        txtPlanPrice.setEditable(false);
        txtPlanPrice.setText("6500");
        txtPlanPrice.setBackground(backgroundColor.brighter());
        txtPlanPrice.setToolTipText("Plan price (non-editable)");

        txtPremiumCharge = createStyledTextField(20);
        txtPremiumCharge.setEditable(false);
        txtPremiumCharge.setText("50000");
        txtPremiumCharge.setBackground(backgroundColor.brighter());
        txtPremiumCharge.setToolTipText("Premium charge (non-editable)");

        txtDiscountAmount = createStyledTextField(20);
        txtDiscountAmount.setEditable(false);
        txtDiscountAmount.setText("0");
        txtDiscountAmount.setBackground(backgroundColor.brighter());
        txtDiscountAmount.setToolTipText("Discount amount (non-editable)");

        // Gender radio buttons
        rbMale = new JRadioButton("Male");
        rbMale.setFont(labelFont);
        rbMale.setBackground(backgroundColor);
        rbMale.setForeground(textColor);
        rbMale.setToolTipText("Select male");

        rbFemale = new JRadioButton("Female");
        rbFemale.setFont(labelFont);
        rbFemale.setBackground(backgroundColor);
        rbFemale.setForeground(textColor);
        rbFemale.setToolTipText("Select female");

        genderGroup = new ButtonGroup();
        genderGroup.add(rbMale);
        genderGroup.add(rbFemale);
        rbMale.setSelected(true); // Default to male

        // Date combo boxes for DOB and membership start date
        Calendar cal = Calendar.getInstance();
        int currentYear = cal.get(Calendar.YEAR);
        int currentMonth = cal.get(Calendar.MONTH);
        int currentDay = cal.get(Calendar.DAY_OF_MONTH);
        String[] years = new String[100];
        for (int i = 0; i < 100; i++) {
            years[i] = String.valueOf(currentYear - i); // Populate years from current to 100 years ago
        }

        String[] months = {
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        };

        String[] days = new String[31];
        for (int i = 0; i < 31; i++) {
            days[i] = String.format("%02d", i + 1); // Populate days 01-31
        }

        dobYearComboBox = createStyledComboBox(years);
        dobMonthComboBox = createStyledComboBox(months);
        dobDayComboBox = createStyledComboBox(days);
        dobYearComboBox.setToolTipText("Select birth year");
        dobMonthComboBox.setToolTipText("Select birth month");
        dobDayComboBox.setToolTipText("Select birth day");
        dobYearComboBox.setSelectedItem(String.valueOf(currentYear));
        dobMonthComboBox.setSelectedItem(months[currentMonth]);
        dobDayComboBox.setSelectedItem(String.format("%02d", currentDay));

        cal.add(Calendar.MONTH, 6); // Allow start dates up to 6 months in future
        int maxYear = cal.get(Calendar.YEAR);
        int minYear = currentYear - 10;
        String[] msYears = new String[maxYear - minYear + 1];
        int yearIndex = 0;
        for (int i = minYear; i <= maxYear; i++) {
            msYears[yearIndex++] = String.valueOf(i);
        }
        msYearComboBox = createStyledComboBox(msYears);
        msMonthComboBox = createStyledComboBox(months);
        msDayComboBox = createStyledComboBox(days);
        msYearComboBox.setToolTipText("Select membership start year");
        msMonthComboBox.setToolTipText("Select membership start month");
        msDayComboBox.setToolTipText("Select membership start day");
        msYearComboBox.setSelectedItem(String.valueOf(currentYear));
        msMonthComboBox.setSelectedItem(months[currentMonth]);
        msDayComboBox.setSelectedItem(String.format("%02d", currentDay));

        // Plan combo box
        String[] plans = {"Basic", "Standard", "Deluxe"};
        cbPlan = createStyledComboBox(plans);
        cbPlan.addActionListener(new PlanSelectionListener());
        cbPlan.setToolTipText("Select membership plan");

        // Action combo box
        String[] actions = {
            "Add Regular Member", "Add Premium Member", "Activate Membership",
            "Deactivate Membership", "Mark Attendance", "Upgrade Plan",
            "Calculate Discount", "Revert Regular Member", "Revert Premium Member",
            "Pay Due Amount", "Display", "Save to File", "Read from File"
        };
        cbAction = createStyledComboBox(actions);
        cbAction.setToolTipText("Select action to perform");

        // Buttons for actions
        btnAddRegular = createStyledButton("Add Regular Member", primaryColor);
        btnAddRegular.setToolTipText("Add a new regular member");
        btnAddPremium = createStyledButton("Add Premium Member", primaryColor);
        btnAddPremium.setToolTipText("Add a new premium member");
        btnActivate = createStyledButton("Activate Membership", primaryColor);
        btnActivate.setToolTipText("Activate an existing membership");
        btnDeactivate = createStyledButton("Deactivate Membership", primaryColor);
        btnDeactivate.setToolTipText("Deactivate an existing membership");
        btnMarkAttendance = createStyledButton("Mark Attendance", primaryColor);
        btnMarkAttendance.setToolTipText("Mark attendance for a member");
        btnRevertRegular = createStyledButton("Revert Regular Member", primaryColor);
        btnRevertRegular.setToolTipText("Revert a regular member's status");
        btnRevertPremium = createStyledButton("Revert Premium Member", primaryColor);
        btnRevertPremium.setToolTipText("Revert a premium member's status");
        btnDisplay = createStyledButton("Display", primaryColor);
        btnDisplay.setToolTipText("Display all members");
        btnClear = createStyledButton("Clear", accentColor);
        btnClear.setToolTipText("Clear all input fields");
        btnSaveToFile = createStyledButton("Save to File", primaryColor);
        btnSaveToFile.setToolTipText("Save members to file");
        btnReadFromFile = createStyledButton("Read from File", primaryColor);
        btnReadFromFile.setToolTipText("Load members from file");
        btnUpgradePlan = createStyledButton("Upgrade Plan", primaryColor);
        btnUpgradePlan.setToolTipText("Upgrade a regular member's plan");
        btnCalculateDiscount = createStyledButton("Calculate Discount", primaryColor);
        btnCalculateDiscount.setToolTipText("Calculate discount for a premium member");
        btnPayDueAmount = createStyledButton("Pay Due Amount", primaryColor);
        btnPayDueAmount.setToolTipText("Pay due amount for a premium member");

        // Labels for the GUI
        lblTitle = new JLabel("Gym Membership Management System", JLabel.CENTER);
        lblTitle.setFont(titleFont);
        lblTitle.setForeground(lightTextColor);

        lblMemberInfo = new JLabel("Member Information");
        lblMemberInfo.setFont(headerFont);
        lblMemberInfo.setForeground(primaryColor);

        lblActions = new JLabel("Actions");
        lblActions.setFont(headerFont);
        lblActions.setForeground(primaryColor);

        lblId = createStyledLabel("ID:");
        lblName = createStyledLabel("Name:");
        lblLocation = createStyledLabel("Location:");
        lblPhone = createStyledLabel("Phone:");
        lblEmail = createStyledLabel("Email:");
        lblGender = createStyledLabel("Gender:");
        lblDOB = createStyledLabel("Date of Birth:");
        lblStartDate = createStyledLabel("Membership Start Date:");
        lblPlan = createStyledLabel("Plan:");
        lblPlanPrice = createStyledLabel("Plan Price:");
        lblTrainer = createStyledLabel("Trainer's Name:");
        lblPremiumCharge = createStyledLabel("Premium Charge:");
        lblReferralSource = createStyledLabel("Referral Source:");
        lblPaidAmount = createStyledLabel("Paid Amount:");
        lblRemovalReason = createStyledLabel("Removal Reason:");
        lblDiscountAmount = createStyledLabel("Discount Amount:");
        lblAction = createStyledLabel("Select Action:");

        // Panels for layout
        mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(backgroundColor);

        headerPanel = new JPanel();
        headerPanel.setBackground(primaryColor);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        infoPanel = new JPanel(new GridBagLayout());
        infoPanel.setBackground(backgroundColor);

        infoScrollPane = new JScrollPane(infoPanel);
        infoScrollPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(10, 10, 10, 10),
            BorderFactory.createLineBorder(secondaryColor, 1, true)
        ));
        infoScrollPane.setPreferredSize(new Dimension(900, 500));
        infoScrollPane.setBackground(backgroundColor);
        infoScrollPane.getVerticalScrollBar().setUnitIncrement(16);

        actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        actionPanel.setBackground(backgroundColor);

        actionScrollPane = new JScrollPane(actionPanel);
        actionScrollPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(10, 10, 10, 10),
            BorderFactory.createLineBorder(secondaryColor, 1, true)
        ));
        actionScrollPane.setPreferredSize(new Dimension(900, 70));
        actionScrollPane.setBackground(backgroundColor);
        actionScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        actionScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        actionScrollPane.getHorizontalScrollBar().setUnitIncrement(16);

        actionSelectionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        actionSelectionPanel.setBackground(backgroundColor);
        actionSelectionPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    /**
     * Creates a styled JLabel with consistent font and color.
     * @param text The text to display on the label
     * @return A configured JLabel
     */
    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(labelFont);
        label.setForeground(textColor);
        return label;
    }

    /**
     * Creates a styled JTextField with specified columns.
     * @param columns The width of the text field in columns
     * @return A configured JTextField
     */
    private JTextField createStyledTextField(int columns)
    {
        JTextField textField = new JTextField(columns);
        textField.setFont(labelFont);
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(secondaryColor, 1, true),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        return textField;
    }

    /**
     * Creates a styled JComboBox with provided items.
     * @param items The array of items to populate the combo box
     * @return A configured JComboBox
     */
    private JComboBox<String> createStyledComboBox(String[] items)
    {
        JComboBox<String> comboBox = new JComboBox<>(items);
        comboBox.setFont(labelFont);
        comboBox.setBackground(Color.WHITE);
        comboBox.setForeground(textColor);
        comboBox.setMaximumRowCount(10);
        return comboBox;
    }

    /**
     * Creates a styled JButton with specified text and background color.
     * @param text The text to display on the button
     * @param bgColor The background color of the button
     * @return A configured JButton
     */
    private JButton createStyledButton(String text, Color bgColor)
    {
        JButton button = new JButton(text);
        button.setFont(buttonFont);
        button.setBackground(bgColor);
        button.setForeground(lightTextColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(new RoundBorder(10));
        button.setPreferredSize(new Dimension(180, 40));

        button.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseEntered(MouseEvent e)
            {
                button.setBackground(bgColor.darker()); // Darken on hover
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                button.setBackground(bgColor); // Restore original color
            }
        });

        return button;
    }

    /**
     * Inner class to create a rounded border for buttons.
     */
    private static class RoundBorder implements Border
    {
        private int radius;

        RoundBorder(int radius)
        {
            this.radius = radius;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height)
        {
            g.setColor(c.getBackground());
            g.fillRoundRect(x, y, width - 1, height - 1, radius, radius);
            g.setColor(Color.LIGHT_GRAY);
            g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }

        @Override
        public Insets getBorderInsets(Component c)
        {
            return new Insets(this.radius / 2, this.radius / 2, this.radius / 2, this.radius / 2);
        }

        @Override
        public boolean isBorderOpaque()
        {
            return false;
        }
    }

    /**
     * Inner class to handle plan selection changes in the cbPlan combo box.
     */
    private class PlanSelectionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            updatePlanPrice();
        }
    }

    /**
     * Sets up the layout of the GUI components using panels and constraints.
     */
    private void setupLayout()
    {
        headerPanel.setLayout(new BorderLayout());
        headerPanel.add(lblTitle, BorderLayout.CENTER);
        add(headerPanel, BorderLayout.NORTH);

        actionSelectionPanel.add(lblAction);
        actionSelectionPanel.add(cbAction);
        mainPanel.add(actionSelectionPanel, BorderLayout.NORTH);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        infoPanel.add(lblMemberInfo, gbc);

        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy = 1;
        infoPanel.add(lblId, gbc);
        gbc.gridx = 1;
        infoPanel.add(txtId, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        infoPanel.add(lblName, gbc);
        gbc.gridx = 1;
        infoPanel.add(txtName, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        infoPanel.add(lblLocation, gbc);
        gbc.gridx = 1;
        infoPanel.add(txtLocation, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        infoPanel.add(lblPhone, gbc);
        gbc.gridx = 1;
        infoPanel.add(txtPhone, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        infoPanel.add(lblEmail, gbc);
        gbc.gridx = 1;
        infoPanel.add(txtEmail, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        infoPanel.add(lblDOB, gbc);
        JPanel dobPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        dobPanel.setBackground(backgroundColor);
        dobPanel.add(dobYearComboBox);
        dobPanel.add(new JLabel("-"));
        dobPanel.add(dobMonthComboBox);
        dobPanel.add(new JLabel("-"));
        dobPanel.add(dobDayComboBox);
        gbc.gridx = 1;
        infoPanel.add(dobPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        infoPanel.add(lblReferralSource, gbc);
        gbc.gridx = 1;
        infoPanel.add(txtReferralSource, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        infoPanel.add(lblPaidAmount, gbc);
        gbc.gridx = 1;
        infoPanel.add(txtPaidAmount, gbc);

        gbc.gridx = 0;
        gbc.gridy = 9;
        infoPanel.add(lblRemovalReason, gbc);
        gbc.gridx = 1;
        infoPanel.add(txtRemovalReason, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        infoPanel.add(lblGender, gbc);
        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        genderPanel.setBackground(backgroundColor);
        genderPanel.add(rbMale);
        genderPanel.add(rbFemale);
        gbc.gridx = 3;
        infoPanel.add(genderPanel, gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        infoPanel.add(lblStartDate, gbc);
        JPanel msPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        msPanel.setBackground(backgroundColor);
        msPanel.add(msYearComboBox);
        msPanel.add(new JLabel("-"));
        msPanel.add(msMonthComboBox);
        msPanel.add(new JLabel("-"));
        msPanel.add(msDayComboBox);
        gbc.gridx = 3;
        infoPanel.add(msPanel, gbc);

        gbc.gridx = 2;
        gbc.gridy = 3;
        infoPanel.add(lblPlan, gbc);
        gbc.gridx = 3;
        infoPanel.add(cbPlan, gbc);

        gbc.gridx = 2;
        gbc.gridy = 4;
        infoPanel.add(lblPlanPrice, gbc);
        gbc.gridx = 3;
        infoPanel.add(txtPlanPrice, gbc);

        gbc.gridx = 2;
        gbc.gridy = 3;
        infoPanel.add(lblTrainer, gbc);
        gbc.gridx = 3;
        infoPanel.add(txtTrainerName, gbc);

        gbc.gridx = 2;
        gbc.gridy = 4;
        infoPanel.add(lblPremiumCharge, gbc);
        gbc.gridx = 3;
        infoPanel.add(txtPremiumCharge, gbc);

        gbc.gridx = 2;
        gbc.gridy = 5;
        infoPanel.add(lblDiscountAmount, gbc);
        gbc.gridx = 3;
        infoPanel.add(txtDiscountAmount, gbc);

        // Add buttons to action panel
        actionPanel.add(btnAddRegular);
        actionPanel.add(btnAddPremium);
        actionPanel.add(btnActivate);
        actionPanel.add(btnDeactivate);
        actionPanel.add(btnMarkAttendance);
        actionPanel.add(btnUpgradePlan);
        actionPanel.add(btnCalculateDiscount);
        actionPanel.add(btnRevertRegular);
        actionPanel.add(btnRevertPremium);
        actionPanel.add(btnPayDueAmount);
        actionPanel.add(btnDisplay);
        actionPanel.add(btnClear);
        actionPanel.add(btnSaveToFile);
        actionPanel.add(btnReadFromFile);

        mainPanel.add(infoScrollPane, BorderLayout.CENTER);
        mainPanel.add(actionScrollPane, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);
    }

    /**
     * Registers action listeners for buttons and combo box.
     */
    private void registerListeners()
    {
        btnAddRegular.addActionListener(this);
        btnAddPremium.addActionListener(this);
        btnActivate.addActionListener(this);
        btnDeactivate.addActionListener(this);
        btnMarkAttendance.addActionListener(this);
        btnRevertRegular.addActionListener(this);
        btnRevertPremium.addActionListener(this);
        btnDisplay.addActionListener(this);
        btnClear.addActionListener(this);
        btnSaveToFile.addActionListener(this);
        btnReadFromFile.addActionListener(this);
        btnUpgradePlan.addActionListener(this);
        btnCalculateDiscount.addActionListener(this);
        btnPayDueAmount.addActionListener(this);
        cbAction.addActionListener(this);
    }

    /**
     * Handles action events from buttons and combo box.
     * @param e The ActionEvent triggered by user interaction
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == cbAction)
        {
            String selectedAction = (String) cbAction.getSelectedItem();
            updateFieldsAndButtons(selectedAction);
        }
        else if (e.getSource() == btnAddRegular)
        {
            addRegularMember();
        }
        else if (e.getSource() == btnAddPremium)
        {
            addPremiumMember();
        }
        else if (e.getSource() == btnActivate)
        {
            activateMembership();
        }
        else if (e.getSource() == btnDeactivate)
        {
            deactivateMembership();
        }
        else if (e.getSource() == btnMarkAttendance)
        {
            markAttendance();
        }
        else if (e.getSource() == btnRevertRegular)
        {
            revertRegularMember();
        }
        else if (e.getSource() == btnRevertPremium)
        {
            revertPremiumMember();
        }
        else if (e.getSource() == btnDisplay)
        {
            displayMembers();
        }
        else if (e.getSource() == btnClear) {
            clearFields();
            updateFieldsAndButtons((String) cbAction.getSelectedItem());
        }
        else if (e.getSource() == btnSaveToFile)
        {
            saveToFile();
        }
        else if (e.getSource() == btnReadFromFile)
        {
            readFromFile();
        } else if (e.getSource() == btnUpgradePlan)
        {
            upgradePlan();
        } else if (e.getSource() == btnCalculateDiscount)
        {
            calculateDiscount();
        } else if (e.getSource() == btnPayDueAmount)
        {
            payDueAmount();
        }
    }

    /**
     * Constructs the Date of Birth string from combo box selections.
     * @return The formatted DOB string (yyyy-MM-dd)
     */
    private String getDOB()
    {
        String monthName = (String) dobMonthComboBox.getSelectedItem();
        String monthNumber = String.format("%02d", getMonthNumber(monthName));
        return dobYearComboBox.getSelectedItem() + "-" +
               monthNumber + "-" +
               dobDayComboBox.getSelectedItem();
    }

    /**
     * Constructs the Membership Start Date string from combo box selections.
     * @return The formatted start date string (yyyy-MM-dd)
     */
    private String getMembershipStartDate()
    {
        String monthName = (String) msMonthComboBox.getSelectedItem();
        String monthNumber = String.format("%02d", getMonthNumber(monthName));
        return msYearComboBox.getSelectedItem() + "-" +
               monthNumber + "-" +
               msDayComboBox.getSelectedItem();
    }

    /**
     * Converts a month name to its numerical representation.
     * @param monthName The name of the month
     * @return The month number (1-12)
     */
    private int getMonthNumber(String monthName)
    {
        switch (monthName)
        {
            case "January": return 1;
            case "February": return 2;
            case "March": return 3;
            case "April": return 4;
            case "May": return 5;
            case "June": return 6;
            case "July": return 7;
            case "August": return 8;
            case "September": return 9;
            case "October": return 10;
            case "November": return 11;
            case "December": return 12;
            default: return 1; // Default to January if invalid
        }
    }

    /**
     * Validates an email address.
     * @param email The email to validate
     * @return True if valid, false otherwise
     */
    private boolean isValidEmail(String email)
    {
        if (email == null || email.isEmpty())
        {
            return false;
        }
        return email.contains("@") && email.contains(".") && email.indexOf("@") < email.lastIndexOf(".");
    }

    /**
     * Checks if the member's age is under 7 years based on DOB.
     * @param dob The date of birth in yyyy-MM-dd format
     * @return True if under 7 years, false otherwise
     */
    private boolean isUnder7Years(String dob)
    {
        try {
            int birthYear = Integer.parseInt(dob.split("-")[0]);
            Calendar cal = Calendar.getInstance();
            int currentYear = cal.get(Calendar.YEAR);
            int age = currentYear - birthYear;
            return age < 7;
        } catch (NumberFormatException ex) {
            return false; // Invalid year format
        }
    }

    /**
     * Adds a new regular member to the system.
     */
    private void addRegularMember() 
    {            
            try
            {
                int id = Integer.parseInt(txtId.getText());
                if (isMemberIdExists(id)) 
                {
                    showErrorMessage("Member ID already exists!");
                    return;
            }

            String name = txtName.getText();
            String location = txtLocation.getText();
            String phone = txtPhone.getText();
            String email = txtEmail.getText();
            String gender = rbMale.isSelected() ? "Male" : "Female";
            String dob = getDOB();
            String startDate = getMembershipStartDate();
            String referralSource = txtReferralSource.getText().trim(); // Trim to remove leading/trailing spaces
            String plan = (String) cbPlan.getSelectedItem();

        // Set referralSource to "N/A" if empty'
        if (referralSource.isEmpty()) {
            referralSource = "N/A";
        }
            // Validate email
            if (!isValidEmail(email)) {
                showErrorMessage("Please enter a valid email address (must contain @ and .)!");
                return;
            }

            // Check age restriction
            if (isUnder7Years(dob)) {
                showErrorMessage("Members must be at least 7 years old!");
                return;
            }

            // Ensure all required fields are filled
            if (name.isEmpty() || location.isEmpty() || phone.isEmpty() || email.isEmpty() || dob.isEmpty() || startDate.isEmpty() || plan == null) {
                showErrorMessage("Please fill all required fields and select a plan!");
                return;
            }

            RegularMember member = new RegularMember(id, name, location, phone, email, gender, dob, startDate, referralSource);
            member.setInitialPlan(plan.toLowerCase());

            memberList.add(member);

            showSuccessMessage("Regular member added successfully with " + plan + " plan!");
            clearFields();
            updateFieldsAndButtons("Add Regular Member");
        } catch (NumberFormatException ex) {
            showErrorMessage("Please enter a valid ID!");
        }
    }
     

    /**
     * Adds a new premium member to the system.
     */
    private void addPremiumMember() 
    {
        try {
            int id = Integer.parseInt(txtId.getText());
            if (isMemberIdExists(id)) {
                showErrorMessage("Member ID already exists!");
                return;
            }

            String name = txtName.getText();
            String location = txtLocation.getText();
            String phone = txtPhone.getText();
            String email = txtEmail.getText();
            String gender = rbMale.isSelected() ? "Male" : "Female";
            String dob = getDOB();
            String startDate = getMembershipStartDate();
            String trainerName = txtTrainerName.getText();
            double paidAmount = txtPaidAmount.getText().isEmpty() ? 0 : Double.parseDouble(txtPaidAmount.getText());

            // Validate email
            if (!isValidEmail(email)) {
                showErrorMessage("Please enter a valid email address (must contain @ and .)!");
                return;
            }

            // Check age restriction
            if (isUnder7Years(dob)) {
                showErrorMessage("Members must be at least 7 years old!");
                return;
            }

            // Ensure all required fields are filled
            if (name.isEmpty() || location.isEmpty() || phone.isEmpty() || email.isEmpty() || dob.isEmpty() || startDate.isEmpty()) {
                showErrorMessage("Please fill all required fields!");
                return;
            }

            PremiumMember member = new PremiumMember(id, name, location, phone, email, gender, dob, startDate, trainerName);
            if (paidAmount > 0) {
                String paymentResult = member.payDueAmount(paidAmount);
                if (paymentResult.contains("exceeds")) {
                    showErrorMessage(paymentResult);
                    return;
                }
            }

            memberList.add(member);

            if (member.isFullPayment()) {
                member.calculateDiscount();
                txtDiscountAmount.setText(String.valueOf(member.getDiscountAmount()));
                showSuccessMessage("Premium member added successfully!\nDiscount applied: " + member.getDiscountAmount());
            } else {
                txtDiscountAmount.setText("0");
                showSuccessMessage("Premium member added successfully!\nNo discount applied. Pay the full amount to get a 10% discount.");
            }

            clearFields();
            updateFieldsAndButtons("Add Premium Member");
        } catch (NumberFormatException ex) {
            showErrorMessage("Please enter a valid ID or Paid Amount!");
        }
    }

    /**
     * Activates a member's membership.
     */
    private void activateMembership() {
        try {
            int id = Integer.parseInt(txtId.getText());
            GymMember member = findMemberById(id);

            if (member == null) {
                showErrorMessage("Member not found with ID: " + id);
                return;
            }

            if (member.getActiveStatus()) {
                showErrorMessage("Member is already activated!");
                return;
            }

            member.activateMembership();
            showSuccessMessage("Membership activated successfully!");
            clearFields();
            updateFieldsAndButtons("Activate Membership");
        } catch (NumberFormatException ex) {
            showErrorMessage("Please enter a valid ID!");
        }
    }

    /**
     * Deactivates a member's membership.
     */
    private void deactivateMembership() {
        try {
            int id = Integer.parseInt(txtId.getText());
            GymMember member = findMemberById(id);

            if (member == null) {
                showErrorMessage("Member not found with ID: " + id);
                return;
            }

            if (!member.getActiveStatus()) {
                showErrorMessage("Member is already deactivated!");
                return;
            }

            member.deactivateMembership();
            showSuccessMessage("Membership deactivated successfully!");
            clearFields();
            updateFieldsAndButtons("Deactivate Membership");
        } catch (NumberFormatException ex) {
            showErrorMessage("Please enter a valid ID!");
        }
    }

    /**
     * Marks attendance for a member.
     */
    private void markAttendance() {
        try {
            int id = Integer.parseInt(txtId.getText());
            GymMember member = findMemberById(id);

            if (member == null) {
                showErrorMessage("Member not found with ID: " + id);
                return;
            }

            if (!member.getActiveStatus()) {
                showErrorMessage("Member is not active. Please activate membership first.");
                return;
            }

            member.markAttendance();
            showSuccessMessage("Attendance marked successfully!");
            clearFields();
            updateFieldsAndButtons("Mark Attendance");
        } catch (NumberFormatException ex) {
            showErrorMessage("Please enter a valid ID!");
        }
    }

    /**
     * Upgrades a regular member's plan.
     */
    private void upgradePlan() {
        try {
            int id = Integer.parseInt(txtId.getText());
            GymMember member = findMemberById(id);

            if (member == null) {
                showErrorMessage("Member not found with ID: " + id);
                return;
            }

            if (!(member instanceof RegularMember)) {
                showErrorMessage("Member with ID " + id + " is not a Regular Member!");
                return;
            }

            if (!member.getActiveStatus()) {
                showErrorMessage("Member is not active. Please activate membership first.");
                return;
            }

            String selectedPlan = (String) cbPlan.getSelectedItem();
            RegularMember regularMember = (RegularMember) member;
            String result = regularMember.upgradePlan(selectedPlan.toLowerCase());
            showSuccessMessage(result);
            clearFields();
            updateFieldsAndButtons("Upgrade Plan");
        } catch (NumberFormatException ex) {
            showErrorMessage("Please enter a valid ID!");
        }
    }

    /**
     * Calculates the discount for a premium member.
     */
    private void calculateDiscount() {
        try {
            int id = Integer.parseInt(txtId.getText());
            GymMember member = findMemberById(id);

            if (member == null) {
                showErrorMessage("Member not found with ID: " + id);
                return;
            }

            if (!(member instanceof PremiumMember)) {
                showErrorMessage("Member with ID " + id + " is not a Premium Member!");
                return;
            }

            PremiumMember premiumMember = (PremiumMember) member;
            premiumMember.calculateDiscount();
            txtDiscountAmount.setText(String.valueOf(premiumMember.getDiscountAmount()));
            if (premiumMember.getDiscountAmount() > 0) {
                showSuccessMessage("Discount calculated successfully: " + premiumMember.getDiscountAmount());
            } else {
                showErrorMessage("No discount available. Complete the payment to get 10% discount.");
            }
            clearFields();
            updateFieldsAndButtons("Calculate Discount");
        } catch (NumberFormatException ex) {
            showErrorMessage("Please enter a valid ID!");
        }
    }

    /**
     * Processes a payment for a premium member's due amount.
     */
    private void payDueAmount() {
        try {
            int id = Integer.parseInt(txtId.getText());
            double amount = Double.parseDouble(txtPaidAmount.getText());
            GymMember member = findMemberById(id);

            if (member == null) {
                showErrorMessage("Member not found with ID: " + id);
                return;
            }

            if (!(member instanceof PremiumMember)) {
                showErrorMessage("Member with ID " + id + " is not a Premium Member!");
                return;
            }

            PremiumMember premiumMember = (PremiumMember) member;
            String result = premiumMember.payDueAmount(amount);
            if (result.contains("exceeds") || result.contains("complete")) {
                showErrorMessage(result);
            } else {
                showSuccessMessage(result);
                if (premiumMember.isFullPayment()) {
                    premiumMember.calculateDiscount();
                    txtDiscountAmount.setText(String.valueOf(premiumMember.getDiscountAmount()));
                }
            }
            clearFields();
            updateFieldsAndButtons("Pay Due Amount");
        } catch (NumberFormatException ex) {
            showErrorMessage("Please enter a valid ID or amount!");
        }
    }

    /**
     * Reverts a regular member's status with a removal reason.
     */
    private void revertRegularMember() {
        try {
            int id = Integer.parseInt(txtId.getText());
            String reason = txtRemovalReason.getText();

            if (reason.isEmpty()) {
                showErrorMessage("Please provide a removal reason!");
                return;
            }

            GymMember member = findMemberById(id);

            if (member == null) {
                showErrorMessage("Member not found with ID: " + id);
                return;
            }

            if (member instanceof RegularMember) {
                RegularMember regularMember = (RegularMember) member;
                regularMember.revertRegularMember(reason);
                showSuccessMessage("Regular member reverted successfully!");
            } else {
                showErrorMessage("Member with ID " + id + " is not a Regular Member!");
            }
            clearFields();
            updateFieldsAndButtons("Revert Regular Member");
        } catch (NumberFormatException ex) {
            showErrorMessage("Please enter a valid ID!");
        }
    }

    /**
     * Reverts a premium member's status.
     */
    private void revertPremiumMember() {
        try {
            int id = Integer.parseInt(txtId.getText());
            GymMember member = findMemberById(id);

            if (member == null) {
                showErrorMessage("Member not found with ID: " + id);
                return;
            }

            if (member instanceof PremiumMember) {
                PremiumMember premiumMember = (PremiumMember) member;
                premiumMember.revertPremiumMember();
                showSuccessMessage("Premium member reverted successfully!");
            } else {
                showErrorMessage("Member with ID " + id + " is not a Premium Member!");
            }
            clearFields();
            updateFieldsAndButtons("Revert Premium Member");
        } catch (NumberFormatException ex) {
            showErrorMessage("Please enter a valid ID!");
        }
    }

    /**
     * Displays all members in a scrollable dialog.
     */
    private void displayMembers() {
        if (memberList.isEmpty()) {
            showInfoMessage("No members to display", "Member List");
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (GymMember member : memberList) {
            sb.append("Member Details:\n");
            sb.append(member.display());
            // Add Referral Source
            String referralSource = member instanceof RegularMember ? ((RegularMember) member).getReferralSource() : "N/A";
            sb.append("Referral Source: ").append(referralSource).append("\n");
            sb.append("\n------------------------\n");
        }

        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        textArea.setBackground(backgroundColor);
        textArea.setForeground(textColor);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 400));

        JOptionPane.showMessageDialog(
            this,
            scrollPane,
            "Member List",
            JOptionPane.INFORMATION_MESSAGE
        );

        updateFieldsAndButtons("Display");
    }

    /**
     * Clears all input fields to their default values.
     */
    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtLocation.setText("");
        txtPhone.setText("");
        txtEmail.setText("");
        txtReferralSource.setText("");
        txtPaidAmount.setText("");
        txtRemovalReason.setText("");
        txtTrainerName.setText("");
        txtPlanPrice.setText("6500");
        txtDiscountAmount.setText("0");
        rbMale.setSelected(true);
        Calendar cal = Calendar.getInstance();
        int currentYear = cal.get(Calendar.YEAR);
        int currentMonth = cal.get(Calendar.MONTH);
        int currentDay = cal.get(Calendar.DAY_OF_MONTH);
        String[] months = {
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        };
        dobYearComboBox.setSelectedItem(String.valueOf(currentYear));
        dobMonthComboBox.setSelectedItem(months[currentMonth]);
        dobDayComboBox.setSelectedItem(String.format("%02d", currentDay));
        msYearComboBox.setSelectedItem(String.valueOf(currentYear));
        msMonthComboBox.setSelectedItem(months[currentMonth]);
        msDayComboBox.setSelectedItem(String.format("%02d", currentDay));
        cbPlan.setSelectedIndex(0);
    }

    /**
     * Saves member details to a text file named "MemberDetails.txt" by updating existing records
     * and appending new members only.
     */
    private void saveToFile() {
    try {
        // Read existing members from the file into a list
        ArrayList<String> fileLines = new ArrayList<>();
        File file = new File("MemberDetails.txt");
        boolean headerExists = file.exists() && file.length() > 0;

        if (headerExists) {
            try (BufferedReader reader = new BufferedReader(new FileReader("MemberDetails.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    fileLines.add(line);
                }
            }
        }

        // Create a list to store current member IDs for checking updates
        ArrayList<Integer> currentMemberIds = new ArrayList<>();
        for (GymMember member : memberList) {
            currentMemberIds.add(member.getId());
        }

        // Prepare output lines
        ArrayList<String> outputLines = new ArrayList<>();

        // Add header if the file is new or empty
        if (!headerExists) {
            outputLines.add("MemberType\tID\tName\tLocation\tPhone\tEmail\tDOB\tGender\tReferral\tStartDate\tPlan\tPrice\tAttend\tLoyalty\tActive\tFullPay\tDiscount\tNetPaid");
        }

        // Process existing file lines
        boolean isHeader = true;
        for (String line : fileLines) {
            if (isHeader) {
                outputLines.add(line); // Keep the header
                isHeader = false;
                continue;
            }
            // Extract ID from the line
            String[] parts = line.split("\t", -1);
            if (parts.length >= 2) {
                try {
                    int id = Integer.parseInt(parts[1]);
                    // Check if this ID exists in the current member list
                    GymMember member = findMemberById(id);
                    if (member != null) {
                        // Update the line with current member data
                        String memberType = member instanceof RegularMember ? "Regular" : "Premium";
                        String plan = member instanceof RegularMember ? ((RegularMember) member).getPlan() : "N/A";
                        String price = member instanceof RegularMember ? String.valueOf(((RegularMember) member).getPrice()) : String.valueOf(((PremiumMember) member).getPremiumCharge());
                        String fullPayment = member instanceof PremiumMember ? (((PremiumMember) member).isFullPayment() ? "Yes" : "No") : "N/A";
                        String discount = member instanceof PremiumMember ? String.valueOf(((PremiumMember) member).getDiscountAmount()) : "0";
                        String netPaid = member instanceof PremiumMember ? String.valueOf(((PremiumMember) member).getPaidAmount()) : "0";
                        String referralSource = member instanceof RegularMember ? ((RegularMember) member).getReferralSource() : "N/A";
                        // Quote both the name and location fields to handle spaces
                        String updatedLine = String.format("%s\t%d\t\"%s\"\t\"%s\"\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%d\t%.1f\t%s\t%s\t%s\t%s",
                            memberType, member.getId(), member.getName(), member.getLocation(), member.getPhone(), member.getEmail(),
                            member.getDOB(), member.getGender(), referralSource, member.getMembershipStartDate(), plan, price,
                            member.getAttendance(), member.getLoyaltyPoints(), String.valueOf(member.getActiveStatus()),
                            fullPayment, discount, netPaid);
                        outputLines.add(updatedLine);
                        currentMemberIds.remove((Integer) id); // Remove processed ID
                    } else {
                        // Keep lines for members no longer in the memberList (optional)
                        outputLines.add(line);
                    }
                } catch (NumberFormatException e) {
                    // Skip malformed lines
                    continue;
                }
            }
        }

        // Append new members (those not yet in the file)
        for (GymMember member : memberList) {
            if (currentMemberIds.contains(member.getId())) {
                String memberType = member instanceof RegularMember ? "Regular" : "Premium";
                String plan = member instanceof RegularMember ? ((RegularMember) member).getPlan() : "N/A";
                String price = member instanceof RegularMember ? String.valueOf(((RegularMember) member).getPrice()) : String.valueOf(((PremiumMember) member).getPremiumCharge());
                String fullPayment = member instanceof PremiumMember ? (((PremiumMember) member).isFullPayment() ? "Yes" : "No") : "N/A";
                String discount = member instanceof PremiumMember ? String.valueOf(((PremiumMember) member).getDiscountAmount()) : "0";
                String netPaid = member instanceof PremiumMember ? String.valueOf(((PremiumMember) member).getPaidAmount()) : "0";
                String referralSource = member instanceof RegularMember ? ((RegularMember) member).getReferralSource() : "N/A";
                // Quote both the name and location fields to handle spaces
                String newLine = String.format("%s\t%d\t\"%s\"\t\"%s\"\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%d\t%.1f\t%s\t%s\t%s\t%s",
                    memberType, member.getId(), member.getName(), member.getLocation(), member.getPhone(), member.getEmail(),
                    member.getDOB(), member.getGender(), referralSource, member.getMembershipStartDate(), plan, price,
                    member.getAttendance(), member.getLoyaltyPoints(), String.valueOf(member.getActiveStatus()),
                    fullPayment, discount, netPaid);
                outputLines.add(newLine);
            }
        }

        // Write to a temporary file
        File tempFile = new File("MemberDetails_temp.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            for (String line : outputLines) {
                writer.write(line);
                writer.newLine();
            }
        }

        // Replace the original file with the temporary file
        if (file.exists()) {
            file.delete();
        }
        tempFile.renameTo(file);

        showSuccessMessage("Members saved to MemberDetails.txt successfully!");
    } catch (IOException ex) {
        showErrorMessage("Error saving members to file: " + ex.getMessage());
    }
    updateFieldsAndButtons("Save to File");
}

    /**
     * Reads member data from a text file "MemberDetails.txt".
     */
    private void readFromFile() {
    try (BufferedReader reader = new BufferedReader(new FileReader("MemberDetails.txt"))) {
        memberList.clear(); // Clear existing list to avoid duplicates
        String line = reader.readLine(); // Read header line
        if (line == null || !line.startsWith("MemberType")) {
            showErrorMessage("Invalid file format or empty file!");
            return;
        }

        int lineNumber = 1; // Track line number for error reporting
        // Read each member line
        while ((line = reader.readLine()) != null) {
            lineNumber++;
            // Split the line by tabs
            String[] parts = line.split("\t", -1);
            if (parts.length != 18) {
                System.out.println("Skipping malformed line " + lineNumber + ": " + line);
                continue; // Skip malformed lines
            }

            try {
                // Parse fields
                String memberType = parts[0];
                int id = Integer.parseInt(parts[1]);
                // Remove quotes from the name and location fields
                String name = parts[2].replaceAll("^\"|\"$", "");
                String location = parts[3].replaceAll("^\"|\"$", "");
                String phone = parts[4];
                String email = parts[5];
                String dob = parts[6];
                String gender = parts[7];
                String referralSource = parts[8];
                String startDate = parts[9];
                String plan = parts[10]; // Plan as string for Regular, "N/A" for Premium
                double price = Double.parseDouble(parts[11]);
                int attendance = Integer.parseInt(parts[12]);
                double loyaltyPoints = Double.parseDouble(parts[13]);
                boolean activeStatus = Boolean.parseBoolean(parts[14]);
                String fullPayment = parts[15];
                String discount = parts[16];
                String netPaid = parts[17];

                if (memberType.equalsIgnoreCase("Regular")) {
                    RegularMember member = new RegularMember(id, name, location, phone, email, gender, dob, startDate, referralSource);
                    member.setPlan(plan); // Set plan directly as string
                    member.setPrice(price);
                    member.attendance = attendance;
                    member.loyaltyPoints = loyaltyPoints;
                    member.activeStatus = activeStatus;
                    member.setEligibleForUpgrade(attendance >= member.getAttendanceLimit());
                    memberList.add(member);
                } else if (memberType.equalsIgnoreCase("Premium")) {
                    PremiumMember member = new PremiumMember(id, name, location, phone, email, gender, dob, startDate, "");
                    member.attendance = attendance;
                    member.loyaltyPoints = loyaltyPoints;
                    member.activeStatus = activeStatus;
                    member.setPaidAmount(Double.parseDouble(netPaid));
                    member.setFullPayment(fullPayment.equalsIgnoreCase("Yes"));
                    member.setDiscountAmount(Double.parseDouble(discount));
                    memberList.add(member);
                } else {
                    System.out.println("Invalid member type at line " + lineNumber + ": " + memberType);
                    continue;
                }
            } catch (NumberFormatException ex) {
                String errorMsg = "Error parsing numeric values at line " + lineNumber + ": " + ex.getMessage() + "\nLine content: " + line;
                System.out.println(errorMsg);
                showErrorMessage(errorMsg);
                continue;
            } catch (ArrayIndexOutOfBoundsException ex) {
                String errorMsg = "Insufficient fields at line " + lineNumber + ": " + ex.getMessage() + "\nLine content: " + line;
                System.out.println(errorMsg);
                showErrorMessage(errorMsg);
                continue;
            }
        }
        if (memberList.isEmpty()) {
            showInfoMessage("No valid members were loaded from the file.", "Read from File");
        } else {
            showSuccessMessage("Members read from MemberDetails.txt successfully! Loaded " + memberList.size() + " members.");
        }
    } catch (IOException ex) {
        showErrorMessage("Error reading members from file: " + ex.getMessage());
    }
    updateFieldsAndButtons("Read from File");
}

    /**
     * Checks if a member ID already exists in the list.
     * @param id The ID to check
     * @return True if the ID exists, false otherwise
     */
    private boolean isMemberIdExists(int id) {
        for (GymMember member : memberList) {
            if (member.getId() == id) {
                return true;
            }
        }
        return false;
    }

    /**
     * Finds a member by their ID.
     * @param id The ID of the member to find
     * @return The GymMember object or null if not found
     */
    private GymMember findMemberById(int id) {
        for (GymMember member : memberList) {
            if (member.getId() == id) {
                return member;
            }
        }
        return null;
    }

    /**
     * Updates the plan price text field based on the selected plan.
     */
    private void updatePlanPrice() {
        String selectedPlan = (String) cbPlan.getSelectedItem();
        double price = 6500;
        switch (selectedPlan) {
            case "Standard":
                price = 12500;
                break;
            case "Deluxe":
                price = 18500;
                break;
        }
        txtPlanPrice.setText(String.valueOf(price));
    }

    /**
     * Updates the visibility of fields and buttons based on the selected action.
     * @param action The selected action from the combo box
     */
    private void updateFieldsAndButtons(String action) {
        // Hide all components initially
        lblId.setVisible(false);
        lblName.setVisible(false);
        lblLocation.setVisible(false);
        lblPhone.setVisible(false);
        lblEmail.setVisible(false);
        lblDOB.setVisible(false);
        lblStartDate.setVisible(false);
        lblReferralSource.setVisible(false);
        lblPlan.setVisible(false);
        lblPlanPrice.setVisible(false);
        lblTrainer.setVisible(false);
        lblPaidAmount.setVisible(false);
        lblRemovalReason.setVisible(false);
        lblPremiumCharge.setVisible(false);
        lblDiscountAmount.setVisible(false);
        lblGender.setVisible(false);

        txtId.setVisible(false);
        txtName.setVisible(false);
        txtLocation.setVisible(false);
        txtPhone.setVisible(false);
        txtEmail.setVisible(false);
        dobYearComboBox.setVisible(false);
        dobMonthComboBox.setVisible(false);
        dobDayComboBox.setVisible(false);
        msYearComboBox.setVisible(false);
        msMonthComboBox.setVisible(false);
        msDayComboBox.setVisible(false);
        txtReferralSource.setVisible(false);
        cbPlan.setVisible(false);
        txtPlanPrice.setVisible(false);
        txtTrainerName.setVisible(false);
        txtPaidAmount.setVisible(false);
        txtRemovalReason.setVisible(false);
        rbMale.setVisible(false);
        rbFemale.setVisible(false);
        txtPremiumCharge.setVisible(false);
        txtDiscountAmount.setVisible(false);

        btnAddRegular.setVisible(false);
        btnAddPremium.setVisible(false);
        btnActivate.setVisible(false);
        btnDeactivate.setVisible(false);
        btnMarkAttendance.setVisible(false);
        btnRevertRegular.setVisible(false);
        btnRevertPremium.setVisible(false);
        btnDisplay.setVisible(false);
        btnSaveToFile.setVisible(false);
        btnReadFromFile.setVisible(false);
        btnUpgradePlan.setVisible(false);
        btnCalculateDiscount.setVisible(false);
        btnPayDueAmount.setVisible(false);

        // Show relevant components based on action
        switch (action) {
            case "Add Regular Member":
                lblId.setVisible(true);
                lblName.setVisible(true);
                lblLocation.setVisible(true);
                lblPhone.setVisible(true);
                lblEmail.setVisible(true);
                lblDOB.setVisible(true);
                lblStartDate.setVisible(true);
                lblReferralSource.setVisible(true);
                lblPlan.setVisible(true);
                lblPlanPrice.setVisible(true);
                lblGender.setVisible(true);

                txtId.setVisible(true);
                txtName.setVisible(true);
                txtLocation.setVisible(true);
                txtPhone.setVisible(true);
                txtEmail.setVisible(true);
                dobYearComboBox.setVisible(true);
                dobMonthComboBox.setVisible(true);
                dobDayComboBox.setVisible(true);
                msYearComboBox.setVisible(true);
                msMonthComboBox.setVisible(true);
                msDayComboBox.setVisible(true);
                txtReferralSource.setVisible(true);
                cbPlan.setVisible(true);
                txtPlanPrice.setVisible(true);
                rbMale.setVisible(true);
                rbFemale.setVisible(true);
                btnAddRegular.setVisible(true);

                updatePlanPrice();
                break;

            case "Add Premium Member":
                lblId.setVisible(true);
                lblName.setVisible(true);
                lblLocation.setVisible(true);
                lblPhone.setVisible(true);
                lblEmail.setVisible(true);
                lblDOB.setVisible(true);
                lblStartDate.setVisible(true);
                lblTrainer.setVisible(true);
                lblPaidAmount.setVisible(true);
                lblPremiumCharge.setVisible(true);
                lblDiscountAmount.setVisible(true);
                lblGender.setVisible(true);

                txtId.setVisible(true);
                txtName.setVisible(true);
                txtLocation.setVisible(true);
                txtPhone.setVisible(true);
                txtEmail.setVisible(true);
                dobYearComboBox.setVisible(true);
                dobMonthComboBox.setVisible(true);
                dobDayComboBox.setVisible(true);
                msYearComboBox.setVisible(true);
                msMonthComboBox.setVisible(true);
                msDayComboBox.setVisible(true);
                txtTrainerName.setVisible(true);
                txtPaidAmount.setVisible(true);
                txtPremiumCharge.setVisible(true);
                txtDiscountAmount.setVisible(true);
                rbMale.setVisible(true);
                rbFemale.setVisible(true);
                btnAddPremium.setVisible(true);

                lblPlan.setVisible(false);
                cbPlan.setVisible(false);
                lblPlanPrice.setVisible(false);
                txtPlanPrice.setVisible(false);
                break;

            case "Activate Membership":
                lblId.setVisible(true);
                txtId.setVisible(true);
                btnActivate.setVisible(true);
                break;

            case "Deactivate Membership":
                lblId.setVisible(true);
                txtId.setVisible(true);
                btnDeactivate.setVisible(true);
                break;

            case "Mark Attendance":
                lblId.setVisible(true);
                txtId.setVisible(true);
                btnMarkAttendance.setVisible(true);
                break;

            case "Upgrade Plan":
                lblId.setVisible(true);
                lblPlan.setVisible(true);
                txtId.setVisible(true);
                cbPlan.setVisible(true);
                btnUpgradePlan.setVisible(true);
                break;

            case "Calculate Discount":
                lblId.setVisible(true);
                lblDiscountAmount.setVisible(true);
                txtId.setVisible(true);
                txtDiscountAmount.setVisible(true);
                btnCalculateDiscount.setVisible(true);
                break;

            case "Revert Regular Member":
                lblId.setVisible(true);
                lblRemovalReason.setVisible(true);
                txtId.setVisible(true);
                txtRemovalReason.setVisible(true);
                btnRevertRegular.setVisible(true);
                break;

            case "Revert Premium Member":
                lblId.setVisible(true);
                txtId.setVisible(true);
                btnRevertPremium.setVisible(true);
                break;

            case "Pay Due Amount":
                lblId.setVisible(true);
                lblPaidAmount.setVisible(true);
                txtId.setVisible(true);
                txtPaidAmount.setVisible(true);
                btnPayDueAmount.setVisible(true);
                break;

            case "Display":
                btnDisplay.setVisible(true);
                break;

            case "Save to File":
                btnSaveToFile.setVisible(true);
                break;

            case "Read from File":
                btnReadFromFile.setVisible(true);
                break;
        }

        btnClear.setVisible(true);
        infoPanel.revalidate();
        infoPanel.repaint();
        actionPanel.revalidate();
        actionPanel.repaint();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                infoScrollPane.getVerticalScrollBar().setValue(0);
                actionScrollPane.getHorizontalScrollBar().setValue(0);
            }
        });
    }

    /**
     * Shows an error message dialog.
     * @param message The error message to display
     */
    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(
            this,
            message,
            "Error",
            JOptionPane.ERROR_MESSAGE
        );
    }

    /**
     * Shows a success message dialog.
     * @param message The success message to display
     */
    private void showSuccessMessage(String message) {
        JOptionPane.showMessageDialog(
            this,
            message,
            "Success",
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    /**
     * Shows an information message dialog.
     * @param message The message to display
     * @param title The title of the dialog
     */
    private void showInfoMessage(String message, String title)
    {
        JOptionPane.showMessageDialog(
            this,
            message,
            title,
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GymGUI();
            }
        });
    }
}
