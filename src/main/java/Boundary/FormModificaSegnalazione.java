package Boundary;

import javax.swing.*;

public class FormModificaSegnalazione {

    private JFrame modificaFrame;
    private JPanel contentPanel;
    private JPanel labelPanel;
    private JPanel fieldPanel;
    private JTextField dataField;
    private JTextField immagineField;
    private JTextField posizioneField;
    private JTextField descrizioneField;
    private JTextField titoloField;
    private JButton modificaButton;
    private JComboBox categoriaBox;

    public FormModificaSegnalazione(){

    }

    public JFrame apriModificaForm(String titolo, String descrizione, String categoria, String posizione, String data, String urlImmagine){

        JFrame frame = new JFrame();
        frame.setTitle("ModificaFrame");
        frame.setContentPane(contentPanel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        titoloField.setText(titolo);
        descrizioneField.setText(titolo);
        titoloField.setText(titolo);
        posizioneField.setText(titolo);
        dataField.setText(titolo);
        immagineField.setText(titolo);

        modificaFrame = frame;

        return frame;

    }
}
