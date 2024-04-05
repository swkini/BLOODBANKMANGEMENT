package BLOODBANKMANAGEMENT;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Donor extends JFrame {
    private JTextField dnameField;
    private JComboBox<String> dgenderField;
    private JTextField dbloodPressureField;
    private JTextField ddateOfBirthField;
    private JTextField dmobileField;
    private JTextField daddressField;
    private JComboBox<String> bloodGroupComboBox;
    private JComboBox<String> bloodBankComboBox;
    private JComboBox<String> doctorNameComboBox; 

    Donor() {
        JFrame frame = new JFrame("Donor Details Form");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel dnameLabel = new JLabel("Donor Name:");
        JLabel dgenderLabel = new JLabel("Gender:");
        JLabel dbloodPressureLabel = new JLabel("Blood Pressure:");
        JLabel ddateOfBirthLabel = new JLabel("Date of Birth:");
        JLabel dmobileLabel = new JLabel("Mobile Number:");
        JLabel daddressLabel = new JLabel("Address:");
        JLabel bloodGroupLabel = new JLabel("Blood Group:");
        JLabel bloodBankLabel = new JLabel("Blood Bank:");
        JLabel doctorNameLabel = new JLabel("Doctor Name:"); 

        // Adjusted text field sizes
        dnameField = new JTextField(20);
        dbloodPressureField = new JTextField(20);
        ddateOfBirthField = new JTextField(20);
        dmobileField = new JTextField(20);
        daddressField = new JTextField(20);
        String[] GenderOptions = { "Male", "Female", "Other" };
        dgenderField = new JComboBox<>(GenderOptions);

        String[] bloodGroupOptions = { "A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-" };
        bloodGroupComboBox = new JComboBox<>(bloodGroupOptions);

        String[] bloodBankOptions = { "XYZ Blood Bank", "Lahu Blood Bank", "Krishna Blood Bank", "Shiva Blood Bank" };
        bloodBankComboBox = new JComboBox<>(bloodBankOptions);

        String[] doctorNameOptions = { "Dr. Hardik", "Dr. Keshav", "Dr. Sandhya", "Dr. Ahmed", "Dr. K S Manish" };
        doctorNameComboBox = new JComboBox<>(doctorNameOptions);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(dnameLabel, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        panel.add(dnameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(dgenderLabel, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        panel.add(dgenderField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(dbloodPressureLabel, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        panel.add(dbloodPressureField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(ddateOfBirthLabel, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        panel.add(ddateOfBirthField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(dmobileLabel, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        panel.add(dmobileField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(daddressLabel, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        panel.add(daddressField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(bloodGroupLabel, gbc);

        gbc.gridx = 1;
        panel.add(bloodGroupComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        panel.add(bloodBankLabel, gbc);

        gbc.gridx = 1;
        panel.add(bloodBankComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        panel.add(doctorNameLabel, gbc);

        gbc.gridx = 1;
        panel.add(doctorNameComboBox, gbc);

        JButton submitButton1 = new JButton("Submit");
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        panel.add(submitButton1, gbc);

        frame.add(panel);
        frame.setSize(800, 400);
        frame.setVisible(true);

        // Adding action listener to the submit button
        submitButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertDonorDetails();
                dispose();
            }
        });
    }

    // Method to insert donor details into the database
    private void insertDonorDetails() {
        String donorName = dnameField.getText();
        String gender = (String) dgenderField.getSelectedItem();
        String bloodPressure = dbloodPressureField.getText();
        String dateOfBirth = ddateOfBirthField.getText();
        long phoneNumber = Long.parseLong(dmobileField.getText());
        String address = daddressField.getText();
        String bloodGroup = (String) bloodGroupComboBox.getSelectedItem();
        String bloodBank = (String) bloodBankComboBox.getSelectedItem();
        String doctorName = (String) doctorNameComboBox.getSelectedItem();

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // Connect to the database
            con = connection.getConnection();

            // Retrieve doctor ID based on the selected doctor name
            String getDoctorIDSQL = "SELECT DOCTOR_ID FROM DOCTOR WHERE DOCTOR_NAME = ?";
            pstmt = con.prepareStatement(getDoctorIDSQL);
            pstmt.setString(1, doctorName);
            rs = pstmt.executeQuery();
            int doctorID = 0;
            if (rs.next()) {
                doctorID = rs.getInt("DOCTOR_ID");
            }

            // Insert into the DONOR table to obtain auto-generated DONOR_ID
            String insertDonorSQL = "INSERT INTO DONOR (DONOR_NAME, GENDER, BLOODPRESSURE, DATE_OF_BIRTH, ADDRESS, PHONENUMBER) VALUES (?, ?, ?, ?, ?, ?)";
            pstmt = con.prepareStatement(insertDonorSQL, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, donorName);
            pstmt.setString(2, gender);
            pstmt.setString(3, bloodPressure);
            pstmt.setString(4, dateOfBirth);
            pstmt.setString(5, address);
            pstmt.setLong(6, phoneNumber);
            int rowsAffected = pstmt.executeUpdate();

            // If insertion is successful, retrieve the auto-generated DONOR_ID
            if (rowsAffected > 0) {
                rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    int donorID = rs.getInt(1);

                    // Insert into the BLOOD table using the obtained DONOR_ID
                    String insertBloodSQL = "INSERT INTO BLOOD (DONOR_ID, BLOOD_TYPE, BLOOD_BANK_ID) VALUES (?, ?, (SELECT BANK_ID FROM BLOODBANK WHERE BANK_NAME = ?))";
                    pstmt = con.prepareStatement(insertBloodSQL);
                    pstmt.setInt(1, donorID);
                    pstmt.setString(2, bloodGroup);
                    pstmt.setString(3, bloodBank);
                    pstmt.executeUpdate();

                    // Insert into the R_EXAMINES table using the obtained DOCTOR_ID and DONOR_ID
                    String insertRExaminesSQL = "INSERT INTO R_EXAMINES (DOCTOR_ID, DONOR_ID) VALUES (?, ?)";
                    pstmt = con.prepareStatement(insertRExaminesSQL);
                    pstmt.setInt(1, doctorID);
                    pstmt.setInt(2, donorID);
                    pstmt.executeUpdate();

                    JOptionPane.showMessageDialog(this, "Donor details inserted successfully.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Error: Unable to insert donor details.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: Unable to insert donor details.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } finally {
            // Close resources
            try {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
                if (con != null)
                    con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
