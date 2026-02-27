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
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.theme.lumo.LumoUtility;

/**
 * A reusable builder that creates a side-by-side layout: live preview (left) +
 * controls panel (right), for interactively exploring component properties.
 *
 * @param <T> the type of component being demonstrated
 */
public class Playground<T extends Component> extends Div {

    private final T target;
    private final Div previewArea;
    private final FormLayout controlsPanel;

    public Playground(T target) {
        this.target = target;

        // Outer container: flex row, wrapping on narrow screens
        getStyle().set("display", "flex");
        getStyle().set("flex-wrap", "wrap");
        getStyle().set("gap", "var(--lumo-space-m)");
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
        controlsPanel.addClassNames(
                LumoUtility.Background.CONTRAST_5,
                LumoUtility.BorderRadius.MEDIUM,
                LumoUtility.Padding.MEDIUM);

        add(previewArea, controlsPanel);
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
