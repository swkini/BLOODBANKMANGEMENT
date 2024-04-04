package BLOODBANKMANAGEMENT;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class MainFrame extends JFrame implements ActionListener {
    JTextField text;
    JButton patient, donor;

    MainFrame() {
        getContentPane().setBackground(Color.LIGHT_GRAY);
        setLayout(null);
        setSize(1000, 700);
        setLocation(200, 20);

        /* give heading to app */
        JLabel heading = new JLabel("BLOOD BANK MANAGEMENT");
        heading.setBounds(100, 40, 2220, 50);
        Font font = new Font("Roboto", Font.BOLD, 40);
        heading.setFont(font);
        heading.setForeground(new Color(0, 0, 0));
        add(heading);
        JLabel photoLabel = new JLabel(new ImageIcon(
                "C:\\Users\\Swathi\\Documents\\dbms\\DBMS\\BLOODBANKMANGEMENT\\BLOODBANKMANAGEMENT\\bloodbank.jpg"));
        photoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        photoLabel.setBounds(400, 100, 600, 600);
        add(photoLabel);

        donor = new JButton("Donor");
        donor.setBounds(90, 200, 200, 105);
        donor.setBackground(new Color(250, 250, 0));
        donor.setForeground(new Color(0, 0, 0));
        donor.addActionListener(this);
        add(donor);
        patient = new JButton("Patient");
        patient.setBounds(90, 400, 200, 105);
        patient.setBackground(new Color(250, 250, 0));
        patient.setForeground(new Color(0, 0, 0));
        patient.addActionListener(this);
        add(patient);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == patient) {
            setVisible(false);
            new Patient();
        } else if (e.getSource() == donor) {
            setVisible(false);
            new Donor();
        }

    }

    public static void main(String[] args) {
        new MainFrame();
    }
}
