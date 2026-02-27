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
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextAreaVariant;
import com.vaadin.flow.demo.MainLayout;
import com.vaadin.flow.demo.Playground;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

/**
 * Demo view for TextArea component.
 */
@Route(value = "text-area", layout = MainLayout.class)
@PageTitle("Text Area | Vaadin Kitchen Sink")
public class TextAreaDemoView extends VerticalLayout {

    public TextAreaDemoView() {
        setSpacing(true);
        setPadding(true);
        setMaxWidth("900px");

        add(new H1("Text Area Component"));
        add(new Paragraph("The TextArea component is used for multi-line text input."));

        // Interactive playground
        Playground<TextArea> playground = new Playground<>(new TextArea("Description"))
                .withCheckbox("Enabled", true, TextArea::setEnabled)
                .withCheckbox("Read-only", false, TextArea::setReadOnly)
                .withCheckbox("Required", false, (ta, val) -> {
                    ta.setRequired(val);
                    ta.setRequiredIndicatorVisible(val);
                })
                .withCheckbox("Clear button", false,
                        TextArea::setClearButtonVisible)
                .withTextField("Label", "Description",
                        TextArea::setLabel)
                .withTextField("Placeholder", "",
                        TextArea::setPlaceholder)
                .withTextField("Helper text", "",
                        TextArea::setHelperText)
                .withSelect("Variant", "Default",
                        List.of("Default", "Small"),
                        (ta, variant) -> {
                            ta.removeThemeVariants(
                                    TextAreaVariant.LUMO_SMALL);
                            if ("Small".equals(variant)) {
                                ta.addThemeVariants(
                                        TextAreaVariant.LUMO_SMALL);
                            }
                        });

        // Basic text area
        TextArea basic = new TextArea("Description");
        basic.setPlaceholder("Enter multiple lines of text here");
        basic.setWidthFull();
        playground.addExample("Basic Text Area", basic);

        // With helper text
        TextArea withHelper = new TextArea("Description");
        withHelper.setHelperText("Provide a detailed description");
        withHelper.setWidthFull();
        playground.addExample("With Helper Text", withHelper);

        // Min/Max height
        TextArea autoHeight = new TextArea("Auto-resize Text Area");
        autoHeight.setMinHeight("100px");
        autoHeight.setMaxHeight("300px");
        autoHeight.setWidthFull();
        autoHeight.setPlaceholder("This area grows as you type, up to a maximum height");
        playground.addExample("Auto-resize with Min/Max Height", autoHeight);

        // Character counter
        TextArea charCounter = new TextArea("With Character Limit");
        charCounter.setMaxLength(200);
        charCounter.setHelperText("0/200 characters");
        charCounter.setWidthFull();
        charCounter.addValueChangeListener(e ->
            charCounter.setHelperText(e.getValue().length() + "/200 characters"));
        playground.addExample("Character Counter", charCounter);

        // Small variant
        TextArea small = new TextArea("Small Variant");
        small.addThemeVariants(TextAreaVariant.LUMO_SMALL);
        small.setWidthFull();
        playground.addExample("Small Variant", small);

        // Read-only
        TextArea readonly = new TextArea("Read-only Text Area");
        readonly.setValue("This content is read-only and cannot be edited by the user.\n\n" +
            "Multi-line content is displayed here.");
        readonly.setReadOnly(true);
        readonly.setWidthFull();
        playground.addExample("Read-only", readonly);

        // Disabled
        TextArea disabled = new TextArea("Disabled Text Area");
        disabled.setValue("This text area is disabled");
        disabled.setEnabled(false);
        disabled.setWidthFull();
        playground.addExample("Disabled", disabled);

        // Invalid state
        TextArea invalid = new TextArea("Invalid Text Area");
        invalid.setInvalid(true);
        invalid.setErrorMessage("Please enter a valid description");
        invalid.setWidthFull();
        playground.addExample("Invalid State", invalid);

        add(playground);
    }
}
