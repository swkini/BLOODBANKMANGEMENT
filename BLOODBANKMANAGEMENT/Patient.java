package BLOODBANKMANAGEMENT;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;

public class Patient extends JFrame {
    private JTextField pfirstnameField;
    private JTextField plastNameField;
    private JComboBox<String> pHospitalNameComboBox;
    private JTextField pmobileField;
    private JTextField paddressField;
    private JComboBox<String> pBloodTypeComboBox;
    private JComboBox<String> bloodBankComboBox;

    Patient() {
        JFrame frame = new JFrame("Patient Details Form");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel pfirstnameLabel = new JLabel("Patient First Name:");
        JLabel plastNameLabel = new JLabel("Patient Last Name:");
        JLabel pHospitalNameLabel = new JLabel("Hospital Name:");
        JLabel pmobileLabel = new JLabel("Mobile Number:");
        JLabel paddressLabel = new JLabel("Address:");
        JLabel pBloodTypeLabel = new JLabel("Blood Type:");
        JLabel bloodBankLabel = new JLabel("Blood Bank:");

        pfirstnameField = new JTextField(20);
        plastNameField = new JTextField(20);
        pHospitalNameComboBox = new JComboBox<>();
        pmobileField = new JTextField(20);
        paddressField = new JTextField(20);
        pBloodTypeComboBox = new JComboBox<>();
        bloodBankComboBox = new JComboBox<>();

        // Adding options to combo boxes
        String[] bloodTypes = { "A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-" };
        pBloodTypeComboBox = new JComboBox<>(bloodTypes);

        String[] hospitalNames = { "Jeevan Jyoti Hospital", "St. Joseph Hospital", "Kamal Hospital" };
        pHospitalNameComboBox = new JComboBox<>(hospitalNames);

        String[] bloodBankOptions = { "XYZ Blood Bank", "Lahu Blood Bank", "Krishna Blood Bank", "Shiva Blood Bank" };
        bloodBankComboBox = new JComboBox<>(bloodBankOptions);

        JButton submitButton2 = new JButton("Submit");

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(pfirstnameLabel, gbc);

        gbc.gridx = 1;
        panel.add(pfirstnameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(plastNameLabel, gbc);

        gbc.gridx = 1;
        panel.add(plastNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(pHospitalNameLabel, gbc);

        gbc.gridx = 1;
        panel.add(pHospitalNameComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(pBloodTypeLabel, gbc);

        gbc.gridx = 1;
        panel.add(pBloodTypeComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(pmobileLabel, gbc);

        gbc.gridx = 1;
        panel.add(pmobileField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(paddressLabel, gbc);

        gbc.gridx = 1;
        panel.add(paddressField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(bloodBankLabel, gbc);

        gbc.gridx = 1;
        panel.add(bloodBankComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        panel.add(submitButton2, gbc);

        submitButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertPatientDetails();
                dispose();
            }
        });

        frame.add(panel);

        frame.setSize(800, 400);
        frame.setVisible(true);
    }

    private void insertPatientDetails() {
        String firstName = pfirstnameField.getText();
        String lastName = plastNameField.getText();
        String hospitalName = (String) pHospitalNameComboBox.getSelectedItem();
        String bloodType = (String) pBloodTypeComboBox.getSelectedItem();
        long phoneNumber = Long.parseLong(pmobileField.getText());
        String address = paddressField.getText();
        String bloodBankName = (String) bloodBankComboBox.getSelectedItem(); // Get selected blood bank name

        try {
            Connection con = connection.getConnection();

            // Insert patient details into the PATIENTS table
            String sql = "INSERT INTO PATIENTS (FIRST_NAME, LAST_NAME, HOSPITAL_NAME, PATIENTS_PHONENUMBER, PATIENTS_ADDRESS, BLOODTYPE) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, hospitalName);
            pstmt.setLong(4, phoneNumber);
            pstmt.setString(5, address);
            pstmt.setString(6, bloodType);
            pstmt.executeUpdate();

            // Retrieve the auto-generated patient ID
            ResultSet rs = pstmt.getGeneratedKeys();
            int patientID = 0;
            if (rs.next()) {
                patientID = rs.getInt(1);
            }
            rs.close();
            pstmt.close();

            // Retrieve blood bank ID based on the selected blood bank name
            sql = "SELECT BANK_ID FROM BLOODBANK WHERE BANK_NAME = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, bloodBankName);
            rs = pstmt.executeQuery();
            int bloodBankID = 0;
            if (rs.next()) {
                bloodBankID = rs.getInt("BANK_ID");
            }
            rs.close();
            pstmt.close();

            // Insert into RBLOOD_DELIVERY table with patient ID and blood bank ID
            sql = "INSERT INTO RBLOOD_DELIVERY (PATIENTS_ID, BANK_ID) VALUES (?, ?)";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, patientID);
            pstmt.setInt(2, bloodBankID);
            pstmt.executeUpdate();

            con.close();

            JOptionPane.showMessageDialog(this, "Patient details inserted successfully.");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: Unable to insert patient details.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

}
