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
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.select.SelectVariant;
import com.vaadin.flow.demo.MainLayout;
import com.vaadin.flow.demo.Playground;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

/**
 * Demo view for Select component.
 */
@Route(value = "select", layout = MainLayout.class)
@PageTitle("Select | Vaadin Kitchen Sink")
public class SelectDemoView extends VerticalLayout {

    public SelectDemoView() {
        setSpacing(true);
        setPadding(true);
        setMaxWidth("900px");

        add(new H1("Select Component"));
        add(new Paragraph("The Select component provides a dropdown list for selecting a single value."));

        // Interactive playground
        add(new H3("Playground"));
        Select<String> playgroundSelect = new Select<>();
        playgroundSelect.setLabel("Country");
        playgroundSelect.setItems("United States", "Canada",
                "United Kingdom", "Germany", "France");
        playgroundSelect.setPlaceholder("Select a country");
        add(new Playground<>(playgroundSelect)
                .withCheckbox("Enabled", true, Select::setEnabled)
                .withCheckbox("Read-only", false, Select::setReadOnly)
                .withCheckbox("Required", false, (s, val) ->
                        s.setRequiredIndicatorVisible(val))
                .withTextField("Label", "Country", Select::setLabel)
                .withTextField("Placeholder", "Select a country",
                        Select::setPlaceholder)
                .withTextField("Helper text", "",
                        Select::setHelperText)
                .withSelect("Variant", "Default",
                        List.of("Default", "Small"),
                        (s, variant) -> {
                            s.removeThemeVariants(
                                    SelectVariant.LUMO_SMALL);
                            if ("Small".equals(variant)) {
                                s.addThemeVariants(
                                        SelectVariant.LUMO_SMALL);
                            }
                        }));

        // Basic select
        Select<String> basic = new Select<>();
        basic.setLabel("Country");
        basic.setItems("United States", "Canada", "United Kingdom", "Germany", "France");
        basic.setPlaceholder("Select a country");
        basic.addValueChangeListener(e ->
            Notification.show("Selected: " + e.getValue()));
        addSection("Basic Select", basic);

        // Pre-selected value
        Select<String> preSelected = new Select<>();
        preSelected.setLabel("Language");
        preSelected.setItems("English", "Spanish", "French", "German", "Chinese");
        preSelected.setValue("English");
        addSection("Pre-selected Value", preSelected);

        // With helper text
        Select<String> withHelper = new Select<>();
        withHelper.setLabel("Time Zone");
        withHelper.setItems("UTC-8 (Pacific)", "UTC-5 (Eastern)", "UTC+0 (London)", "UTC+1 (Paris)", "UTC+9 (Tokyo)");
        withHelper.setHelperText("Select your local time zone");
        addSection("With Helper Text", withHelper);

        // Required
        Select<String> required = new Select<>();
        required.setLabel("Department");
        required.setItems("Engineering", "Marketing", "Sales", "HR", "Finance");
        required.setPlaceholder("Select department");
        required.setRequiredIndicatorVisible(true);
        addSection("Required Selection", required);

        // Small variant
        Select<String> small = new Select<>();
        small.setLabel("Size (Small variant)");
        small.setItems("XS", "S", "M", "L", "XL");
        small.addThemeVariants(SelectVariant.LUMO_SMALL);
        addSection("Small Variant", small);

        // Disabled items
        Select<String> partiallyDisabled = new Select<>();
        partiallyDisabled.setLabel("Subscription");
        partiallyDisabled.setItems("Free", "Basic", "Premium (unavailable)", "Enterprise");
        partiallyDisabled.setItemEnabledProvider(item -> !item.contains("unavailable"));
        addSection("Disabled Items", partiallyDisabled);

        // Read-only
        Select<String> readonly = new Select<>();
        readonly.setLabel("Selected plan (read-only)");
        readonly.setItems("Starter", "Professional", "Business");
        readonly.setValue("Professional");
        readonly.setReadOnly(true);
        addSection("Read-only", readonly);

        // Disabled
        Select<String> disabled = new Select<>();
        disabled.setLabel("Disabled select");
        disabled.setItems("Option A", "Option B", "Option C");
        disabled.setValue("Option A");
        disabled.setEnabled(false);
        addSection("Disabled", disabled);

        // Invalid state
        Select<String> invalid = new Select<>();
        invalid.setLabel("Invalid selection");
        invalid.setItems("Choice 1", "Choice 2", "Choice 3");
        invalid.setInvalid(true);
        invalid.setErrorMessage("Please select a valid option");
        addSection("Invalid State", invalid);

        // Empty selection allowed
        Select<String> emptyAllowed = new Select<>();
        emptyAllowed.setLabel("Optional selection");
        emptyAllowed.setItems("Option 1", "Option 2", "Option 3");
        emptyAllowed.setEmptySelectionAllowed(true);
        emptyAllowed.setEmptySelectionCaption("None");
        addSection("Empty Selection Allowed", emptyAllowed);
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
