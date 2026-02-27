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
package com.vaadin.flow.demo;

import java.util.List;
import java.util.function.BiConsumer;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.theme.lumo.LumoUtility;

/**
 * A reusable builder that creates a tabbed layout with two tabs: an interactive
 * "Playground" tab (live preview + controls) and an "Examples" tab for static
 * demo sections.
 *
 * @param <T> the type of component being demonstrated
 */
public class Playground<T extends Component> extends Div {

    private final T target;
    private final Div previewArea;
    private final FormLayout controlsPanel;
    private final VerticalLayout examplesContent;

    public Playground(T target) {
        this.target = target;
        setWidthFull();

        // Preview area
        previewArea = new Div();
        previewArea.getStyle().set("flex", "1 1 300px");
        previewArea.getStyle().set("min-height", "200px");
        previewArea.getStyle().set("display", "flex");
        previewArea.getStyle().set("align-items", "center");
        previewArea.getStyle().set("justify-content", "center");
        previewArea.addClassNames(
                LumoUtility.Background.CONTRAST_5,
                LumoUtility.BorderRadius.MEDIUM,
                LumoUtility.Padding.LARGE);
        previewArea.add(target);

        // Controls panel
        controlsPanel = new FormLayout();
        controlsPanel.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1));
        controlsPanel.getStyle().set("flex", "0 0 280px");

        // Playground tab content: flex row, wrapping on narrow screens
        Div playgroundContent = new Div();
        playgroundContent.getStyle().set("display", "flex");
        playgroundContent.getStyle().set("flex-wrap", "wrap");
        playgroundContent.getStyle().set("gap", "var(--lumo-space-m)");
        playgroundContent.getStyle().set("padding",
                "var(--lumo-space-m) 0");
        playgroundContent.setWidthFull();
        playgroundContent.add(previewArea, controlsPanel);

        // Examples tab content
        examplesContent = new VerticalLayout();
        examplesContent.setSpacing(true);
        examplesContent.setPadding(false);
        examplesContent.setWidthFull();

        // TabSheet
        TabSheet tabSheet = new TabSheet();
        tabSheet.setWidthFull();
        tabSheet.add("Playground", playgroundContent);
        tabSheet.add("Examples", examplesContent);

        add(tabSheet);
    }

    /**
     * Returns the target component being demonstrated.
     */
    public T getTarget() {
        return target;
    }

    /**
     * Returns the preview area container, for adding extra components alongside
     * the target.
     */
    public Div getPreviewArea() {
        return previewArea;
    }

    /**
     * Adds an example section to the "Examples" tab.
     */
    public void addExample(String title,
            Component... components) {
        Div section = new Div();
        section.add(new H3(title));
        VerticalLayout layout = new VerticalLayout(components);
        layout.setSpacing(true);
        layout.setPadding(false);
        layout.setWidthFull();
        section.add(layout);
        examplesContent.add(section);
    }

    /**
     * Adds a checkbox control that toggles a boolean property on the target.
     */
    public Playground<T> withCheckbox(String label, boolean defaultValue,
            BiConsumer<T, Boolean> applier) {
        Checkbox checkbox = new Checkbox(label);
        checkbox.setValue(defaultValue);
        checkbox.addValueChangeListener(
                e -> applier.accept(target, e.getValue()));
        applier.accept(target, defaultValue);
        controlsPanel.add(checkbox);
        return this;
    }

    /**
     * Adds a text field control that sets a string property on the target.
     */
    public Playground<T> withTextField(String label, String defaultValue,
            BiConsumer<T, String> applier) {
        TextField textField = new TextField(label);
        textField.setValue(defaultValue);
        textField.setWidthFull();
        textField.addValueChangeListener(
                e -> applier.accept(target, e.getValue()));
        controlsPanel.add(textField);
        return this;
    }

    /**
     * Adds a select dropdown control that chooses from a list of string
     * options.
     */
    public Playground<T> withSelect(String label, String defaultValue,
            List<String> options, BiConsumer<T, String> applier) {
        Select<String> select = new Select<>();
        select.setLabel(label);
        select.setItems(options);
        select.setValue(defaultValue);
        select.setWidthFull();
        select.addValueChangeListener(
                e -> applier.accept(target, e.getValue()));
        applier.accept(target, defaultValue);
        controlsPanel.add(select);
        return this;
    }

    /**
     * Adds an integer field with step buttons for numeric properties.
     */
    public Playground<T> withSlider(String label, int min, int max,
            int defaultValue, BiConsumer<T, Integer> applier) {
        IntegerField field = new IntegerField(label);
        field.setMin(min);
        field.setMax(max);
        field.setValue(defaultValue);
        field.setStepButtonsVisible(true);
        field.setWidthFull();
        field.addValueChangeListener(e -> {
            if (e.getValue() != null) {
                applier.accept(target, e.getValue());
            }
        });
        applier.accept(target, defaultValue);
        controlsPanel.add(field);
        return this;
    }
}
