package com.mycompany.threadrender;

import javax.swing.*;

public class MergeSort extends SortVisualizer {

    public MergeSort(JPanel panel, int[] arreglo) {
        super(panel, arreglo);
    }

    public MergeSort(JPanel panel, int[] arreglo, boolean ascendente, int velocidad) {
        super(panel, arreglo, ascendente, velocidad);
    }

    @Override
    protected String getNombre() { return "Merge Sort"; }

    @Override
    protected void ordenar(int[] arr) {
        mergeSort(arr, 0, arr.length - 1);
    }

    private void mergeSort(int[] arr, int izq, int der) {
        if (izq < der) {
            int medio = (izq + der) / 2;

            mergeSort(arr, izq, medio);
            mergeSort(arr, medio + 1, der);
            merge(arr, izq, medio, der);
        }
    }

    private void merge(int[] arr, int izq, int medio, int der) {
        int n1 = medio - izq + 1;
        int n2 = der - medio;

        int[] izqArr = new int[n1];
        int[] derArr = new int[n2];

        for (int i = 0; i < n1; i++) izqArr[i] = arr[izq + i];
        for (int j = 0; j < n2; j++) derArr[j] = arr[medio + 1 + j];

        int i = 0, j = 0, k = izq;

        while (i < n1 && j < n2) {

            if (!debeIntercambiar(izqArr[i], derArr[j])) {
                arr[k] = izqArr[i];
                i++;
            } else {
                arr[k] = derArr[j];
                j++;
            }

            final int idx1 = k, idx2 = medio;
            final int[] snap = arr.clone();

            SwingUtilities.invokeLater(() -> {
                actualizarDataset(snap, idx1, idx2);
                lblEstado.setText("Combinando: posición i" + idx1 + " ↔ mitad en i" + idx2);
            });

            pausa();
            k++;
        }


        while (i < n1) {
            arr[k] = izqArr[i];

            final int idx1 = k;
            final int[] snap = arr.clone();

            SwingUtilities.invokeLater(() -> {
                actualizarDataset(snap, idx1, -1);
                lblEstado.setText("Copiando resto izquierdo en i" + idx1);
            });

            pausa();
            i++;
            k++;
        }

 
        while (j < n2) {
            arr[k] = derArr[j];

            final int idx1 = k;
            final int[] snap = arr.clone();

            SwingUtilities.invokeLater(() -> {
                actualizarDataset(snap, -1, idx1);
                lblEstado.setText("Copiando resto derecho en i" + idx1);
            });

            pausa();
            j++;
            k++;
        }
    }
}