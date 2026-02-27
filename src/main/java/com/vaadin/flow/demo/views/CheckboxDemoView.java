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

import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.checkbox.CheckboxGroupVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.demo.MainLayout;
import com.vaadin.flow.demo.Playground;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


/**
 * Demo view for Checkbox and CheckboxGroup components.
 */
@Route(value = "checkbox", layout = MainLayout.class)
@PageTitle("Checkbox | Vaadin Kitchen Sink")
public class CheckboxDemoView extends VerticalLayout {

    public CheckboxDemoView() {
        setSpacing(true);
        setPadding(true);
        setMaxWidth("900px");

        add(new H1("Checkbox Components"));
        add(new Paragraph("Checkboxes allow users to select one or more options."));

        // Interactive playground
        Playground<Checkbox> playground = new Playground<>(new Checkbox("I agree"))
                .withCheckbox("Enabled", true, Checkbox::setEnabled)
                .withCheckbox("Read-only", false, Checkbox::setReadOnly)
                .withCheckbox("Indeterminate", false,
                        Checkbox::setIndeterminate)
                .withTextField("Label", "I agree", Checkbox::setLabel);

        // Basic checkbox
        Checkbox basic = new Checkbox("I agree to the terms and conditions");
        basic.addValueChangeListener(e ->
            Notification.show("Checkbox: " + (e.getValue() ? "checked" : "unchecked")));
        playground.addExample("Basic Checkbox", basic);

        // Pre-checked
        Checkbox preChecked = new Checkbox("Subscribe to newsletter", true);
        playground.addExample("Pre-checked Checkbox", preChecked);

        // Indeterminate state
        Checkbox indeterminate = new Checkbox("Select all");
        indeterminate.setIndeterminate(true);
        indeterminate.addValueChangeListener(e -> {
            if (e.isFromClient()) {
                indeterminate.setIndeterminate(false);
            }
        });
        playground.addExample("Indeterminate State", indeterminate);

        // Disabled checkbox
        Checkbox disabled = new Checkbox("Disabled unchecked");
        disabled.setEnabled(false);

        Checkbox disabledChecked = new Checkbox("Disabled checked", true);
        disabledChecked.setEnabled(false);
        playground.addExample("Disabled Checkboxes", disabled, disabledChecked);

        // Read-only checkbox
        Checkbox readonly = new Checkbox("Read-only checkbox", true);
        readonly.setReadOnly(true);
        playground.addExample("Read-only Checkbox", readonly);

        // Checkbox Group
        CheckboxGroup<String> group = new CheckboxGroup<>();
        group.setLabel("Select programming languages");
        group.setItems("Java", "JavaScript", "Python", "TypeScript", "Go");
        group.select("Java");
        group.addValueChangeListener(e ->
            Notification.show("Selected: " + e.getValue()));
        playground.addExample("Checkbox Group", group);

        // Checkbox Group with helper text
        CheckboxGroup<String> groupWithHelper = new CheckboxGroup<>();
        groupWithHelper.setLabel("Select your interests");
        groupWithHelper.setItems("Sports", "Music", "Reading", "Travel", "Cooking");
        groupWithHelper.setHelperText("Select at least one interest");
        playground.addExample("Checkbox Group with Helper", groupWithHelper);

        // Vertical Checkbox Group
        CheckboxGroup<String> verticalGroup = new CheckboxGroup<>();
        verticalGroup.setLabel("Notification preferences");
        verticalGroup.setItems("Email notifications", "SMS notifications", "Push notifications");
        verticalGroup.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);
        playground.addExample("Vertical Checkbox Group", verticalGroup);

        // Required Checkbox Group
        CheckboxGroup<String> requiredGroup = new CheckboxGroup<>();
        requiredGroup.setLabel("Required selection");
        requiredGroup.setItems("Option A", "Option B", "Option C");
        requiredGroup.setRequired(true);
        requiredGroup.setRequiredIndicatorVisible(true);
        playground.addExample("Required Checkbox Group", requiredGroup);

        // Disabled items in group
        CheckboxGroup<String> partiallyDisabled = new CheckboxGroup<>();
        partiallyDisabled.setLabel("Available options");
        partiallyDisabled.setItems("Available 1", "Unavailable", "Available 2");
        partiallyDisabled.setItemEnabledProvider(item -> !item.equals("Unavailable"));
        playground.addExample("Partially Disabled Group", partiallyDisabled);

        add(playground);
    }
}
