/*
 * Copyright 2000-2026 Vaadin Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.vaadin.flow.demo.views;

import java.util.List;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.component.progressbar.ProgressBarVariant;
import com.vaadin.flow.theme.lumo.LumoUtility;
import com.vaadin.flow.demo.MainLayout;
import com.vaadin.flow.demo.Playground;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

/**
 * Demo view for ProgressBar component.
 */
@Route(value = "progress-bar", layout = MainLayout.class)
@PageTitle("Progress Bar | Vaadin Kitchen Sink")
public class ProgressBarDemoView extends VerticalLayout {

    public ProgressBarDemoView() {
        setSpacing(true);
        setPadding(true);
        setMaxWidth("900px");

        add(new H1("Progress Bar Component"));
        add(new Paragraph("ProgressBar displays the progress of a task."));

        // Interactive playground
        add(new H3("Playground"));
        ProgressBar playgroundBar = new ProgressBar();
        playgroundBar.setValue(0.5);
        playgroundBar.setWidthFull();
        add(new Playground<>(playgroundBar)
                .withCheckbox("Indeterminate", false,
                        ProgressBar::setIndeterminate)
                .withSlider("Value (%)", 0, 100, 50,
                        (pb, val) -> pb.setValue(val / 100.0))
                .withSelect("Variant", "Default",
                        List.of("Default", "Success", "Error",
                                "Contrast"),
                        (pb, variant) -> {
                            pb.removeThemeVariants(
                                    ProgressBarVariant.LUMO_SUCCESS,
                                    ProgressBarVariant.LUMO_ERROR,
                                    ProgressBarVariant.LUMO_CONTRAST);
                            switch (variant) {
                            case "Success" -> pb.addThemeVariants(
                                    ProgressBarVariant.LUMO_SUCCESS);
                            case "Error" -> pb.addThemeVariants(
                                    ProgressBarVariant.LUMO_ERROR);
                            case "Contrast" -> pb.addThemeVariants(
                                    ProgressBarVariant.LUMO_CONTRAST);
                            }
                        }));

        // Progress levels
        VerticalLayout levels = new VerticalLayout();
        levels.setPadding(false);
        levels.setSpacing(true);
        for (double[] level : new double[][]{{0, 0}, {0.25, 25}, {0.5, 50}, {0.75, 75}, {1.0, 100}}) {
            ProgressBar bar = new ProgressBar();
            bar.setValue(level[0]);
            Span label = new Span((int) level[1] + "%");
            label.setMinWidth("40px");
            HorizontalLayout row = new HorizontalLayout(label, bar);
            row.setAlignItems(FlexComponent.Alignment.CENTER);
            row.addClassName(LumoUtility.Gap.MEDIUM);
            row.setWidthFull();
            row.setFlexGrow(1, bar);
            levels.add(row);
        }
        addSection("Progress Levels", levels);

        // Indeterminate progress bar
        ProgressBar indeterminate = new ProgressBar();
        indeterminate.setIndeterminate(true);
        indeterminate.setWidthFull();
        addSection("Indeterminate (Loading)", indeterminate);

        // With custom range
        ProgressBar customRange = new ProgressBar(0, 10, 7);
        customRange.setWidthFull();
        addSection("Custom Range (7 of 10)", customRange);

        // Theme variants
        ProgressBar success = new ProgressBar();
        success.setValue(0.8);
        success.addThemeVariants(ProgressBarVariant.LUMO_SUCCESS);
        HorizontalLayout successRow = new HorizontalLayout(new Span("Success"), success);
        successRow.setAlignItems(FlexComponent.Alignment.CENTER);
        successRow.addClassName(LumoUtility.Gap.MEDIUM);
        successRow.setWidthFull();
        successRow.setFlexGrow(1, success);

        ProgressBar error = new ProgressBar();
        error.setValue(0.3);
        error.addThemeVariants(ProgressBarVariant.LUMO_ERROR);
        HorizontalLayout errorRow = new HorizontalLayout(new Span("Error"), error);
        errorRow.setAlignItems(FlexComponent.Alignment.CENTER);
        errorRow.addClassName(LumoUtility.Gap.MEDIUM);
        errorRow.setWidthFull();
        errorRow.setFlexGrow(1, error);

        ProgressBar contrast = new ProgressBar();
        contrast.setValue(0.6);
        contrast.addThemeVariants(ProgressBarVariant.LUMO_CONTRAST);
        HorizontalLayout contrastRow = new HorizontalLayout(new Span("Contrast"), contrast);
        contrastRow.setAlignItems(FlexComponent.Alignment.CENTER);
        contrastRow.addClassName(LumoUtility.Gap.MEDIUM);
        contrastRow.setWidthFull();
        contrastRow.setFlexGrow(1, contrast);

        addSection("Theme Variants", successRow, errorRow, contrastRow);

        // Combination examples
        Div downloadExample = new Div();
        downloadExample.add(new Paragraph("Downloading file.zip..."));
        ProgressBar downloadProgress = new ProgressBar();
        downloadProgress.setValue(0.65);
        downloadProgress.setWidthFull();
        downloadExample.add(downloadProgress);
        downloadExample.add(new Paragraph("65% complete - 3.2 MB of 5 MB"));
        addSection("Download Progress Example", downloadExample);

        Div uploadExample = new Div();
        uploadExample.add(new Paragraph("Uploading images..."));
        ProgressBar uploadProgress = new ProgressBar();
        uploadProgress.setValue(0.4);
        uploadProgress.addThemeVariants(ProgressBarVariant.LUMO_SUCCESS);
        uploadProgress.setWidthFull();
        uploadExample.add(uploadProgress);
        uploadExample.add(new Paragraph("2 of 5 files uploaded"));
        addSection("Upload Progress Example", uploadExample);
    }

    private void addSection(String title, com.vaadin.flow.component.Component... components) {
        Div section = new Div();
        section.add(new H3(title));
        VerticalLayout layout = new VerticalLayout(components);
        layout.setSpacing(true);
        layout.setPadding(false);
        layout.setWidthFull();
        section.add(layout);
        add(section);
    }
}
