package com.example.application.services.utilApp;

import com.github.appreciated.apexcharts.ApexCharts;
import com.github.appreciated.apexcharts.ApexChartsBuilder;
import com.github.appreciated.apexcharts.config.builder.*;
import com.github.appreciated.apexcharts.config.chart.Type;
import com.github.appreciated.apexcharts.config.chart.builder.ZoomBuilder;
import com.github.appreciated.apexcharts.config.plotoptions.builder.RadialBarBuilder;
import com.github.appreciated.apexcharts.config.plotoptions.radialbar.builder.HollowBuilder;
import com.github.appreciated.apexcharts.config.stroke.Curve;
import com.github.appreciated.apexcharts.config.subtitle.Align;
import com.github.appreciated.apexcharts.helper.Series;

public class Charts {

    public static ApexCharts radialBar(String label, Double value, String color){
        ApexCharts chart = ApexChartsBuilder.get()
                .withChart(ChartBuilder.get()
                        .withType(Type.radialBar)
                        .build())
                .withPlotOptions(PlotOptionsBuilder.get()
                        .withRadialBar(RadialBarBuilder.get()
                                .withHollow(HollowBuilder.get()
                                        .withSize("70%")
                                        .build())
                                .build())
                        .build())
                .withSeries(value)
                .withLabels(label)
                .build();
        if(color!=null){
            chart.setColors(color);
        }
        return chart;
    }

    public static ApexCharts lineChart(double[] valores){
        ApexCharts chart = ApexChartsBuilder.get()
                .withChart(ChartBuilder.get()
                        .withType(Type.line)
                        .withZoom(ZoomBuilder.get()
                                .withEnabled(false)
                                .build())
                        .withForeColor("#EB5905")
                        .build())
                .withStroke(StrokeBuilder.get()
                        .withCurve(Curve.straight)
                        .build())
                .withTitle(TitleSubtitleBuilder.get()
                        .withText("Probabilidad(LLegadas de clientes)")
                        .withAlign(Align.left)
                        .build())
                .withXaxis(XAxisBuilder.get()
                        .withCategories("1", "2", "3", "4", "5", "6", "7", "8", "9", "10")
                        .build())
                .withSeries(new Series("p", redo(valores[0]), redo(valores[1]), redo(valores[2]), redo(valores[3]), redo(valores[4]), redo(valores[5]),
                        redo(valores[6]), redo(valores[7]), redo(valores[8]), redo(valores[9]) ))
                .build();
        return chart;
    }

    public static ApexCharts barChart(double[] s){
        ApexCharts chart = ApexChartsBuilder.get()
                .withChart(ChartBuilder.get()
                        .withType(Type.bar)
                        .withZoom(ZoomBuilder.get()
                                .withEnabled(false)
                                .build())
                        .withForeColor("#5dc1b9")
                        .build())
                .withTitle(TitleSubtitleBuilder.get()
                        .withText("Probabilidad(Atender en el tiempo)")
                        .withAlign(Align.left)
                        .build())
                .withColors("#5dc1b9")
                .withSeries(new Series("p",redo(s[0]), redo(s[1]), redo(s[2]), redo(s[3]), redo(s[4]), redo(s[5]) ))
                .withXaxis(XAxisBuilder.get()
                        .withCategories("10 min", "20 min", "30 min", "40 min", "50 min", "60 min")
                        .build())
                .build();
        return chart;
    }

    public static double redo(double n){
        return Math.rint(n*1000)/1000;
    }

}
