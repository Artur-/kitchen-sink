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

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.datetimepicker.DateTimePickerVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.demo.MainLayout;
import com.vaadin.flow.demo.Playground;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

/**
 * Demo view for DateTimePicker component.
 */
@Route(value = "date-time-picker", layout = MainLayout.class)
@PageTitle("Date Time Picker | Vaadin Kitchen Sink")
public class DateTimePickerDemoView extends VerticalLayout {

    public DateTimePickerDemoView() {
        setSpacing(true);
        setPadding(true);
        setMaxWidth("900px");

        add(new H1("Date Time Picker Component"));
        add(new Paragraph("The DateTimePicker combines date and time selection."));

        // Interactive playground
        Playground<DateTimePicker> playground = new Playground<>(new DateTimePicker("Date & Time"))
                .withCheckbox("Enabled", true,
                        DateTimePicker::setEnabled)
                .withCheckbox("Read-only", false,
                        DateTimePicker::setReadOnly)
                .withCheckbox("Required", false, (dtp, val) ->
                        dtp.setRequiredIndicatorVisible(val))
                .withTextField("Label", "Date & Time",
                        DateTimePicker::setLabel)
                .withTextField("Helper text", "",
                        DateTimePicker::setHelperText)
                .withSelect("Variant", "Default",
                        List.of("Default", "Small"),
                        (dtp, variant) -> {
                            dtp.removeThemeVariants(
                                    DateTimePickerVariant.LUMO_SMALL);
                            if ("Small".equals(variant)) {
                                dtp.addThemeVariants(
                                        DateTimePickerVariant.LUMO_SMALL);
                            }
                        });

        // Basic date time picker
        DateTimePicker basic = new DateTimePicker("Appointment");
        basic.addValueChangeListener(e ->
            Notification.show("Selected: " + e.getValue()));
        playground.addExample("Basic Date Time Picker", basic);

        // Pre-selected value
        DateTimePicker preSelected = new DateTimePicker("Current date and time");
        preSelected.setValue(LocalDateTime.now());
        playground.addExample("Pre-selected Value", preSelected);

        // Custom step
        DateTimePicker step15 = new DateTimePicker("15-minute intervals");
        step15.setStep(Duration.ofMinutes(15));
        playground.addExample("15-Minute Step", step15);

        // Min and max
        DateTimePicker minMax = new DateTimePicker("Future appointments only");
        minMax.setMin(LocalDateTime.now());
        minMax.setMax(LocalDateTime.now().plusMonths(1));
        minMax.setHelperText("Select a date/time within the next month");
        playground.addExample("Min/Max Range", minMax);

        // With helper text
        DateTimePicker withHelper = new DateTimePicker("Meeting schedule");
        withHelper.setHelperText("Select the meeting date and time");
        playground.addExample("With Helper Text", withHelper);

        // Required
        DateTimePicker required = new DateTimePicker("Required field");
        required.setRequiredIndicatorVisible(true);
        playground.addExample("Required Field", required);

        // Small variant
        DateTimePicker small = new DateTimePicker("Small variant");
        small.addThemeVariants(DateTimePickerVariant.LUMO_SMALL);
        playground.addExample("Small Variant", small);

        // Custom labels
        DateTimePicker customLabels = new DateTimePicker("Custom field labels");
        customLabels.setI18n(new DateTimePicker.DateTimePickerI18n()
                .setDateLabel("Start date")
                .setTimeLabel("Start time"));
        playground.addExample("Custom Field Labels", customLabels);

        // Auto-open disabled
        DateTimePicker noAutoOpen = new DateTimePicker("Auto-open disabled");
        noAutoOpen.setAutoOpen(false);
        noAutoOpen.setHelperText("Calendar/dropdown won't open on focus");
        playground.addExample("Auto-open Disabled", noAutoOpen);

        // Read-only
        DateTimePicker readonly = new DateTimePicker("Read-only");
        readonly.setValue(LocalDateTime.now());
        readonly.setReadOnly(true);
        playground.addExample("Read-only", readonly);

        // Disabled
        DateTimePicker disabled = new DateTimePicker("Disabled");
        disabled.setValue(LocalDateTime.now());
        disabled.setEnabled(false);
        playground.addExample("Disabled", disabled);

        // Invalid state
        DateTimePicker invalid = new DateTimePicker("Invalid");
        invalid.setInvalid(true);
        invalid.setErrorMessage("Please select a valid date and time");
        playground.addExample("Invalid State", invalid);

        add(playground);
    }
}
