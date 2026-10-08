package vista;

import controlador.ControladorDeLibros;
import modelo.Categoria;
import modelo.Libro;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;

public class VentanaGestionLibros extends JFrame {
    private ControladorDeLibros controladorDeLibros;
    private JTextField campoTitulo;
    private JTextField campoAutor;
    private JTextField campoIsbn;
    private JTextField campoEditorial;
    private JTextField campoStock;
    private JComboBox<Categoria> campoCategoria;
    private JTable tablaLibros;
    private DefaultTableModel modeloTablaLibros;
    private JButton btnRegistrar;
    private JButton btnModificar;
    private JButton btnEliminar;
    private JButton btnLimpiar;

    public VentanaGestionLibros() {
        controladorDeLibros = new ControladorDeLibros();
        estructuraBase();
        panelVentana();
        setLocationRelativeTo(null);
    }

    private void estructuraBase() {
        setTitle("Gestión de Libros");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocation(500, 300);
        getContentPane().setBackground(new Color(241, 245, 244));
    }
    private void panelVentana() {
        setLayout(new BorderLayout(0, 10));
        add(campos(), BorderLayout.NORTH);
        cargarCategorias();
        listadodeLibros();
        add(panelBotones(), BorderLayout.SOUTH);

    }
    private void cargarCategorias() {
        campoCategoria.removeAllItems();

        try {
            for (Categoria categoria : controladorDeLibros.listarCategorias()) {
                campoCategoria.addItem(categoria);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "No fue posible cargar las categorías: " + e.getMessage(),
                    "Error de base de datos", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JPanel campos() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 4, 4, 4);
        campoTitulo = new JTextField(10);
        campoAutor = new JTextField(10);
        campoIsbn = new JTextField(10);
        campoEditorial = new JTextField(10);
        campoStock = new JTextField(10);
        campoCategoria = new JComboBox<>();
        distribucionCamposTextos(panel);

        return panel;
    }

    private void distribucionCamposTextos(JPanel panel) {
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 4, 4, 4);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 0;
        c.weightx = 1;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.CENTER;
        panel.add(new JLabel("Registre los datos del Libro"), c);

        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 0;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("Título:"), c);

        c.gridx = 1;
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.WEST;
        panel.add(campoTitulo, c);

        c.gridx = 0;
        c.gridy = 2;
        c.weightx = 0;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("Autor:"), c);

        c.gridx = 1;
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.WEST;
        panel.add(campoAutor, c);


        c.gridx = 0;
        c.gridy = 3;
        c.weightx = 0;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("ISBN:"), c);

        c.gridx = 1;
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.WEST;
        panel.add(campoIsbn, c);

        c.gridx = 0;
        c.gridy = 4;
        c.weightx = 0;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("Editorial:"), c);

        c.gridx = 1;
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.WEST;
        panel.add(campoEditorial, c);

        c.gridx = 0;
        c.gridy = 5;
        c.weightx = 0;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("Stock:"), c);

        c.gridx = 1;
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.WEST;
        panel.add(campoStock, c);

        c.gridx = 0;
        c.gridy = 6;
        c.weightx = 0;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("Categoría"), c);

        c.gridx = 1;
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.WEST;
        panel.add(campoCategoria, c);

    }

    private void listadodeLibros() {
        String [] columnas = {"ID", "Título", "Autor", "ISBN", "Editorial", "Stock", "Categoría"};
        modeloTablaLibros = new DefaultTableModel(columnas, 0);
        tablaLibros = new JTable(modeloTablaLibros);
        tablaLibros.setDefaultEditor(Object.class, null);

        tablaLibros.getSelectionModel().addListSelectionListener(evento -> {
            if (!evento.getValueIsAdjusting()) {
                cargarLibroSeleccionado();
            }
        });
        add(new JScrollPane(tablaLibros), BorderLayout.CENTER);
        tablaLibros.getColumnModel().getColumn(0).setMinWidth(0);
        tablaLibros.getColumnModel().getColumn(0).setMaxWidth(0);
        tablaLibros.getColumnModel().getColumn(0).setPreferredWidth(0);
        cargarLibros();
    }

    public JPanel panelBotones() {
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnRegistrar = new JButton("Registrar");
        btnModificar = new JButton("Modificar");
        btnEliminar = new JButton("Eliminar");
        btnLimpiar = new JButton("Limpiar");

        btnRegistrar.addActionListener(e -> registrarLibro());
        btnModificar.addActionListener(e -> modificarLibro());
        btnEliminar.addActionListener(e -> eliminarLibro());
        btnLimpiar.addActionListener(e -> limpiarCampos());
        panelBotones.add(btnRegistrar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);
        return panelBotones;
    }

    private void eliminarLibro() {
        int fila = tablaLibros.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione una fila para eliminar.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int idLibro = (int) modeloTablaLibros.getValueAt(fila, 0);
        int opcion = JOptionPane.showConfirmDialog(this, "¿Desea eliminar el libro seleccionado?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

        if (opcion != JOptionPane.YES_OPTION) {
            return;
        }
        try {
            if (controladorDeLibros.eliminarLibro(idLibro)) {
                JOptionPane.showMessageDialog(this, "Libro eliminado exitosamente");
                cargarLibros();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "No fue posible eliminar el libro. Puede tener préstamos asociados.",
                    "Error de base de datos", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void modificarLibro() {
        int fila = tablaLibros.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione una fila para modificar.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int idLibro = (int) modeloTablaLibros.getValueAt(fila, 0);
        String titulo = campoTitulo.getText().trim();
        String autor = campoAutor.getText().trim();
        String isbn = campoIsbn.getText().trim();
        String editorial = campoEditorial.getText().trim();
        String stock = campoStock.getText().trim();
        Categoria categoriaSeleccionada = (Categoria) campoCategoria.getSelectedItem();

         if (titulo.isEmpty() || autor.isEmpty() || isbn.isEmpty() ||
                 editorial.isEmpty() || stock.isEmpty() || categoriaSeleccionada == null) {
             JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.",
                     "Error", JOptionPane.ERROR_MESSAGE);
             return;
         }

         try {
             int nuevoStock = validarStock(stock);
             Libro libroModificado = new Libro(idLibro, titulo, autor, isbn, editorial, nuevoStock, categoriaSeleccionada);
             boolean modificado = controladorDeLibros.actualizarLibro(libroModificado);
             if (modificado) {
                 JOptionPane.showMessageDialog(this, "Libro modificado exitosamente");
                 cargarLibros();
             } else {
                 JOptionPane.showMessageDialog(this, "No se pudo modificar el libro.", "" +
                         "Error", JOptionPane.ERROR_MESSAGE);
             }
            limpiarCampos();
             tablaLibros.clearSelection();
         } catch (NumberFormatException e) {
             JOptionPane.showMessageDialog(this, "El stock debe ser un número entero.",
                     "Dato incorrecto", JOptionPane.ERROR_MESSAGE);
         } catch (IllegalArgumentException e) {
             JOptionPane.showMessageDialog(this, e.getMessage(), "Datos inválidos", JOptionPane.ERROR_MESSAGE);
         } catch (SQLException e) {
             JOptionPane.showMessageDialog(this, "No fue posible guardar el libro en la base de datos. "
                     + "Verifique que el ISBN no esté registrado.", "Error de base de datos", JOptionPane.ERROR_MESSAGE);

         }

    }

    private void cargarLibroSeleccionado() {
        int fila = tablaLibros.getSelectedRow();

        if (fila < 0) {
            return;
        }
        campoTitulo.setText(modeloTablaLibros.getValueAt(fila, 1).toString());
        campoAutor.setText(modeloTablaLibros.getValueAt(fila, 2).toString());
        campoIsbn.setText(modeloTablaLibros.getValueAt(fila, 3).toString());
        campoEditorial.setText(modeloTablaLibros.getValueAt(fila, 4).toString());
        campoStock.setText(modeloTablaLibros.getValueAt(fila, 5).toString());

        Categoria categoriaLibro = (Categoria) modeloTablaLibros.getValueAt(fila, 6);

        for (int i = 0; i < campoCategoria.getItemCount(); i++) {
            Categoria categoria = campoCategoria.getItemAt(i);

            if (categoria.getIdCategoria() == categoriaLibro.getIdCategoria()) {
                campoCategoria.setSelectedIndex(i);
                break;
            }
    }
    }

    private void registrarLibro() {
       String titulo = campoTitulo.getText().trim();
       String autor = campoAutor.getText().trim();
       String isbn = campoIsbn.getText().trim();
       String editorial = campoEditorial.getText().trim();
       String stock = campoStock.getText().trim();
        Categoria categoriaSeleccionada = (Categoria) campoCategoria.getSelectedItem();

       if (titulo.isEmpty() || autor.isEmpty() || isbn.isEmpty() ||
               editorial.isEmpty() || stock.isEmpty() || categoriaSeleccionada == null) {
           JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.",
                   "Error", JOptionPane.ERROR_MESSAGE);
           return;
       }
       int idLibro = 0;

       try {
           int stockValidado = validarStock(stock);
           Libro libroRegistrado = new Libro(idLibro, titulo, autor, isbn, editorial, stockValidado, categoriaSeleccionada);
          boolean registrado = controladorDeLibros.registrarLibro(libroRegistrado);
          if(!registrado) {
              JOptionPane.showMessageDialog(this, "Hubo un error al registrar el libro.", "" +
                      "Error", JOptionPane.ERROR_MESSAGE);
          } else {
              JOptionPane.showMessageDialog(this, "Registro exitoso",
                      "Confirmación", JOptionPane.INFORMATION_MESSAGE);
              cargarLibros();
          }
           limpiarCampos();

       } catch (NumberFormatException e) {
           JOptionPane.showMessageDialog(this, "El stock debe ser un número entero.",
                   "Dato incorrecto", JOptionPane.ERROR_MESSAGE);
       } catch (IllegalArgumentException e) {
           JOptionPane.showMessageDialog(this, e.getMessage(), "Datos inválidos", JOptionPane.ERROR_MESSAGE);
       } catch (SQLException e) {
           JOptionPane.showMessageDialog(this, "No fue posible guardar el libro en la base de datos. "
                           + "Verifique que el ISBN no esté registrado.", "Error de base de datos", JOptionPane.ERROR_MESSAGE);

       }
    }

    private void limpiarCampos() {
        campoTitulo.setText("");
        campoAutor.setText("");
        campoIsbn.setText("");
        campoEditorial.setText("");
        campoStock.setText("");
        campoCategoria.setSelectedIndex(0);
    }

    public int validarStock(String stock) throws NumberFormatException {
        return Integer.parseInt(stock);
    }

    public void cargarLibros()  {
        modeloTablaLibros.setRowCount(0);
        try {
            for (Libro libros : controladorDeLibros.listarLibros()) {
                Object[] fila = { libros.getIdLibro(),
                        libros.getTitulo(),
                        libros.getAutor(),
                        libros.getIsbn(),
                        libros.getEditorial(),
                        libros.getStock(),
                        libros.getCategoria()};
                modeloTablaLibros.addRow(fila);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "No fue posible cargar los libros: " + e.getMessage(),
                    "Error de base de datos", JOptionPane.ERROR_MESSAGE);
        }

    }
}
