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

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.demo.MainLayout;
import com.vaadin.flow.demo.Playground;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

/**
 * Demo view for NumberField and IntegerField components.
 */
@Route(value = "number-field", layout = MainLayout.class)
@PageTitle("Number Field | Vaadin Kitchen Sink")
public class NumberFieldDemoView extends VerticalLayout {

    public NumberFieldDemoView() {
        setSpacing(true);
        setPadding(true);
        setMaxWidth("900px");

        add(new H1("Number Field Components"));
        add(new Paragraph("Number fields are used for numeric input."));

        // Interactive playground
        Playground<NumberField> playground = new Playground<>(new NumberField("Amount"))
                .withCheckbox("Enabled", true, NumberField::setEnabled)
                .withCheckbox("Read-only", false, NumberField::setReadOnly)
                .withCheckbox("Step buttons", false,
                        NumberField::setStepButtonsVisible)
                .withCheckbox("Clear button", false,
                        NumberField::setClearButtonVisible)
                .withTextField("Label", "Amount", NumberField::setLabel)
                .withTextField("Helper text", "",
                        NumberField::setHelperText)
                .withSelect("Variant", "Default",
                        List.of("Default", "Small"),
                        (nf, variant) -> {
                            nf.removeThemeVariants(
                                    TextFieldVariant.LUMO_SMALL);
                            if ("Small".equals(variant)) {
                                nf.addThemeVariants(
                                        TextFieldVariant.LUMO_SMALL);
                            }
                        });

        // Basic number field
        NumberField basic = new NumberField("Amount");
        basic.setPlaceholder("Enter a number");
        playground.addExample("Basic Number Field", basic);

        // With step controls
        NumberField withControls = new NumberField("Quantity");
        withControls.setStepButtonsVisible(true);
        withControls.setStep(0.5);
        withControls.setValue(5.0);
        playground.addExample("With Step Controls", withControls);

        // Min/Max values
        NumberField minMax = new NumberField("Min/Max Range (0-100)");
        minMax.setMin(0);
        minMax.setMax(100);
        minMax.setStepButtonsVisible(true);
        minMax.setHelperText("Value must be between 0 and 100");
        playground.addExample("Min/Max Values", minMax);

        // With prefix/suffix
        NumberField price = new NumberField("Price");
        price.setPrefixComponent(new Span("$"));
        price.setValue(99.99);

        NumberField percentage = new NumberField("Percentage");
        percentage.setSuffixComponent(new Span("%"));
        percentage.setMin(0);
        percentage.setMax(100);
        percentage.setValue(50.0);
        playground.addExample("With Prefix/Suffix", price, percentage);

        // Integer field
        IntegerField intField = new IntegerField("Integer Field");
        intField.setStepButtonsVisible(true);
        intField.setStep(1);
        intField.setValue(10);
        playground.addExample("Integer Field", intField);

        // Integer field with min/max
        IntegerField quantity = new IntegerField("Quantity (1-99)");
        quantity.setMin(1);
        quantity.setMax(99);
        quantity.setStepButtonsVisible(true);
        quantity.setValue(1);
        quantity.setHelperText("Select quantity");
        playground.addExample("Integer Field with Range", quantity);

        // Clear button
        NumberField clearButton = new NumberField("With Clear Button");
        clearButton.setClearButtonVisible(true);
        clearButton.setValue(42.0);
        playground.addExample("Clear Button", clearButton);

        // Read-only
        NumberField readonly = new NumberField("Read-only Field");
        readonly.setValue(123.45);
        readonly.setReadOnly(true);
        playground.addExample("Read-only", readonly);

        // Disabled
        NumberField disabled = new NumberField("Disabled Field");
        disabled.setValue(100.0);
        disabled.setEnabled(false);
        playground.addExample("Disabled", disabled);

        add(playground);
    }
}
