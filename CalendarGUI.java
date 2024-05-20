import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

public class CalendarGUI extends JFrame {
    private JComboBox<String> monthComboBox;
    private JTextField yearTextField;
    private JTextArea calendarTextArea;

    public CalendarGUI() {
        setTitle("Calendar");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());

        String[] months = {"January", "February", "March", "April", "May", "June", 
                           "July", "August", "September", "October", "November", "December"};
        monthComboBox = new JComboBox<>(months);
        topPanel.add(new JLabel("Month:"));
        topPanel.add(monthComboBox);

        yearTextField = new JTextField(5);
        topPanel.add(new JLabel("Year:"));
        topPanel.add(yearTextField);

        JButton generateButton = new JButton("Generate Calendar");
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateCalendar();
            }
        });
        topPanel.add(generateButton);

        add(topPanel, BorderLayout.NORTH);

        calendarTextArea = new JTextArea();
        calendarTextArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        calendarTextArea.setEditable(false);
        calendarTextArea.setBackground(Color.WHITE);
        Border border = BorderFactory.createLineBorder(Color.BLACK, 3);
        calendarTextArea.setBorder(border);

        add(new JScrollPane(calendarTextArea), BorderLayout.CENTER);
    }

    private void generateCalendar() {
        int month = monthComboBox.getSelectedIndex();
        int year;
        try {
            year = Integer.parseInt(yearTextField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid year.", "Invalid Year", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, 1);

        String[] dayNames = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};

        StringBuilder calendarText = new StringBuilder();
        calendarText.append("  ").append(monthComboBox.getSelectedItem()).append(" ").append(year).append("\n");

        for (String dayName : dayNames) {
            calendarText.append(dayName).append(" ");
        }
        calendarText.append("\n");

        int startDay = calendar.get(Calendar.DAY_OF_WEEK);
        int numberOfDaysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        for (int i = 1; i < startDay; i++) {
            calendarText.append("    ");
        }

        for (int day = 1; day <= numberOfDaysInMonth; day++) {
            calendarText.append(String.format("%3d ", day));
            if ((day + startDay - 1) % 7 == 0) {
                calendarText.append("\n");
            }
        }

        calendarTextArea.setText(calendarText.toString());
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CalendarGUI().setVisible(true);
            }
        });
    }
}
