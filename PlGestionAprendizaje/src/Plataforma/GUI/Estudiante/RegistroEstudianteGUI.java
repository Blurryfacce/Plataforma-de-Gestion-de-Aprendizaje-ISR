package Plataforma.GUI.Estudiante;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Plataforma.Controllers.Validaciones;
import Plataforma.Controllers.DAO.EstudianteDAO;

public class RegistroEstudianteGUI extends JFrame {
    private JTextField txtCedula, txtNombre, txtApellido, txtEmail, txtDireccion;
    private JButton btnRegistrarEstudiante;

    public RegistroEstudianteGUI() {
        setTitle("Registro de Estudiante");
        setSize(500, 400); // Formulario más amplio
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Configuración del layout y estilo
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15)); // Márgenes internos
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Separación entre componentes
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Etiqueta y campo para Nombre
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Nombre:"), gbc);
        txtNombre = new JTextField();
        estilizarCampo(txtNombre);
        gbc.gridx = 1; gbc.gridy = 0;
        panel.add(txtNombre, gbc);

        // Etiqueta y campo para Apellido
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Apellido:"), gbc);
        txtApellido = new JTextField();
        estilizarCampo(txtApellido);
        gbc.gridx = 1; gbc.gridy = 1;
        panel.add(txtApellido, gbc);

        // Etiqueta y campo para Cédula
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Cédula:"), gbc);
        txtCedula = new JTextField();
        estilizarCampo(txtCedula);
        gbc.gridx = 1; gbc.gridy = 2;
        panel.add(txtCedula, gbc);

        // Etiqueta y campo para Email
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Email:"), gbc);
        txtEmail = new JTextField();
        estilizarCampo(txtEmail);
        gbc.gridx = 1; gbc.gridy = 3;
        panel.add(txtEmail, gbc);

        // Etiqueta y campo para Dirección (opcional)
        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(new JLabel("Dirección (opcional):"), gbc);
        txtDireccion = new JTextField();
        estilizarCampo(txtDireccion);
        gbc.gridx = 1; gbc.gridy = 4;
        panel.add(txtDireccion, gbc);

        // Botón de Registrar
        btnRegistrarEstudiante = new JButton("Registrar Estudiante");
        estilizarBoton(btnRegistrarEstudiante);
        btnRegistrarEstudiante.addActionListener(this::registrarEstudiante);
        gbc.gridx = 0; gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(btnRegistrarEstudiante, gbc);
        getRootPane().setDefaultButton(btnRegistrarEstudiante);

        add(panel);
    }

    /**
     * Funcion que registra en base de datos y valida los datos ingresados por el estudiante
     * @param e
     */
    private void registrarEstudiante(ActionEvent e) {
        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String cedula = txtCedula.getText().trim();
        String email = txtEmail.getText().trim();
        String direccion = txtDireccion.getText().trim();

        
        // Validar los campos
        if (!Validaciones.validarNombreOApellido(nombre)) {
            JOptionPane.showMessageDialog(this, "El nombre no es válido. No debe contener números y debe tener entre 2 y 50 caracteres.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!Validaciones.validarNombreOApellido(apellido)) {
            JOptionPane.showMessageDialog(this, "El apellido no es válido. No debe contener números y debe tener entre 2 y 50 caracteres.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!Validaciones.validarCedula(cedula)) {
            JOptionPane.showMessageDialog(this, "La cédula debe contener exactamente 10 dígitos numéricos.", "Error",JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!Validaciones.validarEmail(email)) {
            JOptionPane.showMessageDialog(this, "El email no es válido. Debe contener '@' y terminar en '.com' o '.edu'.", "Error",JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!direccion.isEmpty() && !Validaciones.validarDireccion(direccion)) {
            JOptionPane.showMessageDialog(this, "La dirección no es válida. Debe ser una ubicación real sin códigos postales.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Lógica para registrar al estudiante en la base de datos
        boolean registrado = EstudianteDAO.insertarEstudiante(nombre, apellido, cedula, email, direccion);

        if (registrado) {
            JOptionPane.showMessageDialog(this, "Estudiante registrado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this, "Error al registrar al estudiante.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Método para limpiar los campos del registro 
     */
    private void limpiarCampos() {
        txtNombre.setText("");
        txtApellido.setText("");
        txtCedula.setText("");
        txtEmail.setText("");
        txtDireccion.setText("");
    }

    /**
     * Método para personalizar campos de registro del estudiante
     * @param campo
     */
    private void estilizarCampo(JTextField campo) {
        campo.setPreferredSize(new Dimension(300, 30));
        campo.setBackground(new Color(30, 30, 30));
        campo.setForeground(Color.WHITE);
        campo.setCaretColor(Color.WHITE);
        campo.setBorder(BorderFactory.createLineBorder(new Color(70, 70, 70)));
        campo.setFont(new Font("Arial", Font.PLAIN, 14));
    }

    /**
     * Método para personalizar botón de registro del estudiante
     * @param boton
     */
    private void estilizarBoton(JButton boton) {
        boton.setBackground(new Color(45, 45, 45));
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Arial", Font.BOLD, 14));
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }

}
