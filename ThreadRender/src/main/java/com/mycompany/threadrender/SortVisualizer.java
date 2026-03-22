package com.mycompany.threadrender;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.DefaultCategoryDataset;

public abstract class SortVisualizer {

    protected DefaultCategoryDataset dataset;
    protected JLabel lblEstado;
    protected JButton btnOrdenar;
    protected JPanel panel;
    protected int[] arreglo;
    protected int velocidad;
    protected boolean ascendente;


    public SortVisualizer(JPanel panel, int[] arreglo, boolean ascendente, int velocidad) {
        this.panel      = panel;
        this.arreglo    = arreglo;
        this.ascendente = ascendente;
        this.velocidad  = velocidad;
        inicializarUI();
    }


    public SortVisualizer(JPanel panel, int[] arreglo) {
        this(panel, arreglo, true, 300);
    }

   
    private void inicializarUI() {
        dataset = new DefaultCategoryDataset();
        actualizarDataset(arreglo.clone(), -1, -1);
        String titulo = getNombre() + " (" + (ascendente ? "ASC" : "DESC") + ") ";
        JFreeChart chart = ChartFactory.createBarChart(
            titulo, "Indice", "Valor", dataset
        );
        CategoryPlot plot = chart.getCategoryPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(70, 130, 180));  // normal
        renderer.setSeriesPaint(1, new Color(220, 50, 50));   // comparando
        renderer.setSeriesPaint(2, new Color(50, 180, 50));   // ordenado
        renderer.setShadowVisible(false);
        renderer.setBarPainter(new StandardBarPainter());

        ChartPanel chartPanel = new ChartPanel(chart);

        lblEstado = new JLabel("Presiona para Iniciar", SwingConstants.CENTER);

        btnOrdenar = new JButton("Iniciar Ordenamiento");
        btnOrdenar.addActionListener(e -> {
            btnOrdenar.setEnabled(false);
            lblEstado.setText("Ordenando " + (ascendente ? "ascendente..." : "descendente..."));
            iniciarOrdenamiento(arreglo.clone());
        });

        JPanel panelSur = new JPanel(new BorderLayout(5, 5));
        panelSur.add(lblEstado,  BorderLayout.NORTH);
        panelSur.add(btnOrdenar, BorderLayout.SOUTH);

        panel.removeAll();
        panel.setLayout(new BorderLayout());
        panel.add(chartPanel, BorderLayout.CENTER);
        panel.add(panelSur,   BorderLayout.SOUTH);
        panel.setPreferredSize(new Dimension(600, 400));
        panel.revalidate();
        panel.repaint();
    }


    private void iniciarOrdenamiento(int[] copia) {
        Thread hilo = new Thread(() -> {
            ordenar(copia);

            SwingUtilities.invokeLater(() -> {
                actualizarDataset(copia, -1, -1);
                btnOrdenar.setEnabled(true);
                btnOrdenar.setText("Ordenar de nuevo");
                lblEstado.setText("Ordenado con " + getNombre());
            });
        });

        hilo.setDaemon(true);
        hilo.start();
    }

    // Actualizar grafico
    protected void actualizarDataset(int[] arr, int idx1, int idx2) {
        dataset.clear();
        for (int i = 0; i < arr.length; i++) {
            String etiqueta = "i" + i;
            if (i == idx1 || i == idx2) {
                dataset.addValue(arr[i], "Comparando", etiqueta);
                dataset.addValue(null,   "Valores",    etiqueta);
                dataset.addValue(null,   "Ordenado",   etiqueta);
            } else {
                dataset.addValue(null,   "Comparando", etiqueta);
                dataset.addValue(arr[i], "Valores",    etiqueta);
                dataset.addValue(null,   "Ordenado",   etiqueta);
            }
        }
    }

    protected void marcarOrdenado(int[] arr, int idxOrdenado) {
        dataset.clear();
        for (int i = 0; i < arr.length; i++) {
            String etiqueta = "i" + i;
            if (i == idxOrdenado) {
                dataset.addValue(null,   "Comparando", etiqueta);
                dataset.addValue(null,   "Valores",    etiqueta);
                dataset.addValue(arr[i], "Ordenado",   etiqueta);
            } else {
                dataset.addValue(null,   "Comparando", etiqueta);
                dataset.addValue(arr[i], "Valores",    etiqueta);
                dataset.addValue(null,   "Ordenado",   etiqueta);
            }
        }
    }

    // Comparacion de intercambio
    protected boolean debeIntercambiar(int a, int b) {
        return ascendente ? a > b : a < b;
    }


    protected void pausa() {
        try {
            Thread.sleep(velocidad);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    protected abstract String getNombre();
    protected abstract void ordenar(int[] arr);
}