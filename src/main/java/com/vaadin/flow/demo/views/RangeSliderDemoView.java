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

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.slider.RangeSlider;
import com.vaadin.flow.component.slider.RangeSliderValue;
import com.vaadin.flow.demo.MainLayout;
import com.vaadin.flow.demo.Playground;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

/**
 * Demo view for RangeSlider component.
 */
@Route(value = "range-slider", layout = MainLayout.class)
@PageTitle("Range Slider | Vaadin Kitchen Sink")
public class RangeSliderDemoView extends VerticalLayout {

    public RangeSliderDemoView() {
        setSpacing(true);
        setPadding(true);
        setMaxWidth("900px");

        add(new H1("Range Slider Component"));
        add(new Paragraph(
                "The Range Slider allows users to select a range within minimum and maximum values using two thumbs."));

        // Interactive playground
        RangeSlider playgroundSlider = new RangeSlider("Price Range");
        playgroundSlider.setMin(0);
        playgroundSlider.setMax(1000);
        playgroundSlider.setValue(new RangeSliderValue(200, 800));
        playgroundSlider.setWidthFull();
        Playground<RangeSlider> playground = new Playground<>(playgroundSlider)
                .withCheckbox("Enabled", true, RangeSlider::setEnabled)
                .withCheckbox("Read-only", false,
                        RangeSlider::setReadOnly)
                .withTextField("Label", "Price Range",
                        RangeSlider::setLabel);

        // Basic range slider
        RangeSlider basic = new RangeSlider("Select Range");
        basic.setMin(0);
        basic.setMax(100);
        basic.setValue(new RangeSliderValue(25, 75));
        basic.setWidthFull();
        basic.addValueChangeListener(e -> {
            RangeSliderValue v = e.getValue();
            Notification.show(v.start() + " - " + v.end());
        });
        playground.addExample("Basic Range Slider", basic);

        // Custom range
        RangeSlider customRange = new RangeSlider("Temperature (10-40)");
        customRange.setMin(10);
        customRange.setMax(40);
        customRange.setValue(new RangeSliderValue(18, 26));
        customRange.setWidthFull();
        playground.addExample("Custom Range", customRange);

        // With step
        RangeSlider withStep = new RangeSlider("Range (step 10)");
        withStep.setMin(0);
        withStep.setMax(100);
        withStep.setStep(10);
        withStep.setValue(new RangeSliderValue(20, 80));
        withStep.setWidthFull();
        playground.addExample("With Step", withStep);

        // With helper text
        RangeSlider withHelper = new RangeSlider("Price Filter");
        withHelper.setMin(0);
        withHelper.setMax(500);
        withHelper.setValue(new RangeSliderValue(50, 300));
        withHelper.setWidthFull();
        withHelper.setHelperText(
                "Drag the thumbs to set minimum and maximum price");
        playground.addExample("With Helper Text", withHelper);

        // Read-only
        RangeSlider readonly = new RangeSlider("Read-only");
        readonly.setMin(0);
        readonly.setMax(100);
        readonly.setValue(new RangeSliderValue(30, 70));
        readonly.setReadOnly(true);
        readonly.setWidthFull();
        playground.addExample("Read-only", readonly);

        // Disabled
        RangeSlider disabled = new RangeSlider("Disabled");
        disabled.setMin(0);
        disabled.setMax(100);
        disabled.setValue(new RangeSliderValue(20, 60));
        disabled.setEnabled(false);
        disabled.setWidthFull();
        playground.addExample("Disabled", disabled);

        add(playground);
    }
}
