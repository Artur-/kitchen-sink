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

import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.combobox.MultiSelectComboBoxVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.demo.MainLayout;
import com.vaadin.flow.demo.Playground;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Demo view for MultiSelectComboBox component.
 */
@Route(value = "multi-select-combo-box", layout = MainLayout.class)
@PageTitle("Multi-Select Combo Box | Vaadin Kitchen Sink")
public class MultiSelectComboBoxDemoView extends VerticalLayout {

    private static final List<String> SKILLS = Arrays.asList(
        "Java", "JavaScript", "TypeScript", "Python", "Go", "Rust",
        "C++", "C#", "Ruby", "Kotlin", "Swift", "PHP"
    );

    public MultiSelectComboBoxDemoView() {
        setSpacing(true);
        setPadding(true);
        setMaxWidth("900px");

        add(new H1("Multi-Select Combo Box Component"));
        add(new Paragraph("MultiSelectComboBox allows selecting multiple values from a filterable list."));

        // Interactive playground
        add(new H3("Playground"));
        MultiSelectComboBox<String> playgroundMscb = new MultiSelectComboBox<>(
                "Skills");
        playgroundMscb.setItems(SKILLS);
        playgroundMscb.setPlaceholder("Select skills");
        add(new Playground<>(playgroundMscb)
                .withCheckbox("Enabled", true,
                        MultiSelectComboBox::setEnabled)
                .withCheckbox("Read-only", false,
                        MultiSelectComboBox::setReadOnly)
                .withCheckbox("Required", false, (cb, val) -> {
                    cb.setRequired(val);
                    cb.setRequiredIndicatorVisible(val);
                })
                .withCheckbox("Clear button", false,
                        MultiSelectComboBox::setClearButtonVisible)
                .withTextField("Label", "Skills",
                        MultiSelectComboBox::setLabel)
                .withTextField("Placeholder", "Select skills",
                        MultiSelectComboBox::setPlaceholder)
                .withSelect("Variant", "Default",
                        List.of("Default", "Small"),
                        (cb, variant) -> {
                            cb.removeThemeVariants(
                                    MultiSelectComboBoxVariant.LUMO_SMALL);
                            if ("Small".equals(variant)) {
                                cb.addThemeVariants(
                                        MultiSelectComboBoxVariant.LUMO_SMALL);
                            }
                        }));

        // Basic multi-select
        MultiSelectComboBox<String> basic = new MultiSelectComboBox<>("Programming Skills");
        basic.setItems(SKILLS);
        basic.setPlaceholder("Select skills");
        basic.setWidthFull();
        basic.addValueChangeListener(e ->
            Notification.show("Selected: " + e.getValue()));
        addSection("Basic Multi-Select", basic);

        // Pre-selected values
        MultiSelectComboBox<String> preSelected = new MultiSelectComboBox<>("Pre-selected");
        preSelected.setItems(SKILLS);
        preSelected.select("Java", "JavaScript", "TypeScript");
        preSelected.setWidthFull();
        addSection("Pre-selected Values", preSelected);

        // With clear button
        MultiSelectComboBox<String> clearable = new MultiSelectComboBox<>("Clearable");
        clearable.setItems(SKILLS);
        clearable.select("Python", "Go");
        clearable.setClearButtonVisible(true);
        clearable.setWidthFull();
        addSection("With Clear Button", clearable);

        // With helper text
        MultiSelectComboBox<String> withHelper = new MultiSelectComboBox<>("Technical skills");
        withHelper.setItems(SKILLS);
        withHelper.setHelperText("Select all applicable skills");
        withHelper.setWidthFull();
        addSection("With Helper Text", withHelper);

        // Required
        MultiSelectComboBox<String> required = new MultiSelectComboBox<>("Required skills");
        required.setItems(SKILLS);
        required.setRequired(true);
        required.setRequiredIndicatorVisible(true);
        required.setWidthFull();
        addSection("Required Field", required);

        // Compact mode
        MultiSelectComboBox<String> compact = new MultiSelectComboBox<>("Compact mode");
        compact.setItems(SKILLS);
        compact.select("Java", "JavaScript", "Python", "Go");
        compact.setSelectedItemsOnTop(true);
        compact.setWidthFull();
        addSection("Selected Items on Top", compact);

        // Small variant
        MultiSelectComboBox<String> small = new MultiSelectComboBox<>("Small variant");
        small.setItems(SKILLS);
        small.addThemeVariants(MultiSelectComboBoxVariant.LUMO_SMALL);
        small.setWidthFull();
        addSection("Small Variant", small);

        // Allow custom values
        MultiSelectComboBox<String> allowCustom = new MultiSelectComboBox<>("Allow custom");
        allowCustom.setItems(SKILLS);
        allowCustom.setAllowCustomValue(true);
        allowCustom.addCustomValueSetListener(e ->
            Notification.show("Custom value: " + e.getDetail()));
        allowCustom.setHelperText("You can add custom skills");
        allowCustom.setWidthFull();
        addSection("Allow Custom Values", allowCustom);

        // Read-only
        MultiSelectComboBox<String> readonly = new MultiSelectComboBox<>("Read-only");
        readonly.setItems(SKILLS);
        readonly.select("Java", "Python");
        readonly.setReadOnly(true);
        readonly.setWidthFull();
        addSection("Read-only", readonly);

        // Disabled
        MultiSelectComboBox<String> disabled = new MultiSelectComboBox<>("Disabled");
        disabled.setItems(SKILLS);
        disabled.select("JavaScript");
        disabled.setEnabled(false);
        disabled.setWidthFull();
        addSection("Disabled", disabled);

        // Invalid state
        MultiSelectComboBox<String> invalid = new MultiSelectComboBox<>("Invalid");
        invalid.setItems(SKILLS);
        invalid.setInvalid(true);
        invalid.setErrorMessage("Please select at least one skill");
        invalid.setWidthFull();
        addSection("Invalid State", invalid);
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
