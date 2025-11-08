package io.github.model;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

public class GerarGrafico {

    public static BufferedImage gerarGrafico(String title, String labelX, String labelY,
            ArrayList<Double> valors)
            throws Exception {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (int i = 0; i < valors.size(); i++) {
            double error = valors.get(i);
            String epoch = String.valueOf(i + 1);
            dataset.addValue(error, "Erro", epoch);
        }

        JFreeChart chart = ChartFactory.createLineChart(
                title,
                labelX,
                labelY,
                dataset);

        return chart.createBufferedImage(900, 600);
    }

}